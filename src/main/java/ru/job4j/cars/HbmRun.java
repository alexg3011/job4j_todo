package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Engine engine1 = Engine.of("V8");

            Driver driver1 = Driver.of("Alex");
            Driver driver2 = Driver.of("Petr");

            Car car1 = Car.of("Ford", engine1);

            car1.addDriver(driver1);
            car1.addDriver(driver2);

            session.persist(engine1);
            session.persist(driver1);
            session.persist(driver2);
            session.persist(car1);

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
