package ru.job4j.todo.storage;

import ru.job4j.todo.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.List;

public class HbmStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public HbmStore() {
    }

    @Override
    public Collection<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item> result = session.createQuery("from ru.job4j.todo.model.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void save(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        List items = session.createQuery(
                "from ru.job4j.todo.model.Item where id = :id").setParameter("id", id).list();
        Item item = (Item) items.get(0);
        item.setDone(true);
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    public static void main(String[] args) {
        HbmStore h = new HbmStore();
        h.save(new Item(0, "sdfs", Timestamp.from(Instant.now()), false));
    }
}
