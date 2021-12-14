package ru.job4j.todo.storage;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

public class HbmStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmStore() {
    }

    private static final class Lazy {
        private static final Store INST = new HbmStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List findAll() {
        return tx(session -> session.createQuery("select distinct i from Item i join fetch i.categories").list());

    }

    @Override
    public void save(Item item, String[] categories) {
        tx(session -> {
            for (String cId : categories) {
                Category category = session.find(Category.class, Integer.parseInt(cId));
                item.addCategory(category);
            }
            return session.save(item);
        });
    }

    @Override
    public void update(int id) {
        tx(session -> {
            session.createQuery(
                            "update Item set done = :true where id =: id")
                    .setParameter("true", true)
                    .setParameter("id", id)
                    .executeUpdate();
            return null;
        });
    }

    @Override
    public void saveUser(User user) {
        tx(session -> session.save(user));
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(session -> {
            Query<User> query = session.createQuery(
                    "from User where email = : email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    @Override
    public void saveCategory(String name) {
        tx(session -> {
            session.save(Category.of(name));
            return null;
        });
    }

    @Override
    public Category readCategory(String name) {
        return tx(session -> {
            Query<Category> query = session.createQuery(
                    "from Category where name = : name");
            query.setParameter("name", name);
            return query.uniqueResult();
        });
    }

    @Override
    public void updateCategory(String oldName, String newName) {
        tx(session -> {
            session.createQuery(
                            "update Category set name = :newName where name =: oldName")
                    .setParameter("newName", newName)
                    .setParameter("oldName", oldName)
                    .executeUpdate();
            return null;
        });
    }

    @Override
    public void deleteCategory(String name) {
            tx(session -> session.createQuery("delete from Category  where name =: name"))
                    .setParameter("name", name)
                    .executeUpdate();

    }

    @Override
    public List findAllCategories() {
        return tx(session -> session.createQuery("from ru.job4j.todo.model.Category").list());
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
