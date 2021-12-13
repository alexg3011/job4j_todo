package ru.job4j.toone;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        List<CarMark> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            CarMark carMark1 = CarMark.of("VAZ");
            CarMark carMark2 = CarMark.of("Mercedes");
            session.save(carMark1);
            session.save(carMark2);
            CarModel carModel1 = CarModel.of("2101", carMark1);
            CarModel carModel2 = CarModel.of("2110", carMark1);
            CarModel carModel3 = CarModel.of("2115", carMark1);
            CarModel carModel4 = CarModel.of("Vesta", carMark1);
            CarModel carModel5 = CarModel.of("X-Ray", carMark1);
            CarModel carModel6 = CarModel.of("Maybach", carMark2);
            CarModel carModel7 = CarModel.of("AMG", carMark2);
            session.save(carModel1);
            session.save(carModel2);
            session.save(carModel3);
            session.save(carModel4);
            session.save(carModel5);
            session.save(carModel6);
            session.save(carModel7);

            list = session.createQuery("select distinct c from CarMark c join fetch c.cars").list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (CarMark car: list) {
            System.out.println(car);
        }
    }
}
