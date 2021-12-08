package ru.job4j.todo.storage;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

public class HbmStore implements Store {

    private static final Logger LOG = Logger.getLogger(HbmStore.class);

    private final BasicDataSource pool = new BasicDataSource();

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new InputStreamReader(
                        HbmStore.class.getClassLoader()
                                .getResourceAsStream("db.properties")
                )
        )) {
            cfg.load(io);
        } catch (Exception e) {
            LOG.error("Invalid parameters", e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            LOG.error("Driver not loaded", e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List findAll() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.Item").list());

    }

    @Override
    public void save(Item item) {
        tx(session -> session.save(item));
    }

    @Override
    public void update(int id) {
        tx(session -> session.createQuery(
                "from ru.job4j.todo.model.Item where id = :id").setParameter("id", id).list());
    }

    @Override
    public void saveUser(User user) {
        tx(session -> session.save(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(session -> {
            Query query = session.createQuery(
                            "from ru.job4j.todo.model.User where email = :email")
                    .setParameter("email", email);
            return (User) query.getResultList().get(0);
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
