package ru.job4j.toone;

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

            CarModel carModel1 = CarModel.of("2101");
            CarModel carModel2 = CarModel.of("2110");
            CarModel carModel3 = CarModel.of("2115");
            CarModel carModel4 = CarModel.of("Vesta");
            CarModel carModel5 = CarModel.of("X-Ray");
            session.save(carModel1);
            session.save(carModel2);
            session.save(carModel3);
            session.save(carModel4);
            session.save(carModel5);

            CarMark carMark = CarMark.of("VAZ");
            carMark.addCar(session.load(CarModel.class, 1));
            carMark.addCar(session.load(CarModel.class, 2));
            carMark.addCar(session.load(CarModel.class, 3));
            carMark.addCar(session.load(CarModel.class, 4));
            carMark.addCar(session.load(CarModel.class, 5));

            session.save(carMark);
            session.getTransaction().commit();

            CarMark carMark1 = session.get(CarMark.class, 1);
            System.out.println(carMark1.toString());

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
