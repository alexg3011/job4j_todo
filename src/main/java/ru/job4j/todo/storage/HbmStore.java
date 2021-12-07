package ru.job4j.todo.storage;

import org.hibernate.Transaction;
import ru.job4j.todo.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public HbmStore() {
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

    public static void main(String[] args) {
        HbmStore h = new HbmStore();
        h.save(new Item(0, "sdfs", Timestamp.from(Instant.now()), false));
    }
}
