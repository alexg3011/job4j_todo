package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            Candidate candidate1 = Candidate.of("Alex", "Java", 100000);
            Candidate candidate2 = Candidate.of("Bob", "Python", 90000);
            Candidate candidate3 = Candidate.of("James", "Js", 70000);
            session.save(candidate1);
            session.save(candidate2);
            session.save(candidate3);

            Query query1 = session.createQuery("from Candidate ");
            for (Object c : query1.list()) {
                System.out.println(c);
            }

            Query query2 = session.createQuery("from Candidate c where c.id= :cId")
                    .setParameter("cId", 3);
            System.out.println(query2.uniqueResult());

            Query query3 = session.createQuery("from Candidate c where c.name = :cName");
            query3.setParameter("cName", "Alex");
            System.out.println(query3);

            session.createQuery("update Candidate c set c.experience = :newExp, c.salary = :newSal where c.id = :fId")
                    .setParameter("newExp", "Pascal")
                    .setParameter("newSal", 30000)
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :cId")
                    .setParameter("cId", 1)
                    .executeUpdate();

            VacancyBase base = VacancyBase.of("Java");

            Vacancy vacancy1 = Vacancy.of("Junior", "Sber need junior");
            Vacancy vacancy2 = Vacancy.of("Middle", "Alfa need middle");
            Vacancy vacancy3 = Vacancy.of("Senior", "Epam need senior");
            base.addVacancy(vacancy1);
            base.addVacancy(vacancy2);
            base.addVacancy(vacancy3);
            session.save(base);
            Candidate candidate4 = Candidate.of("Petr", "Java Senior", 200000);
            candidate4.setBase(base);
            session.save(candidate4);

            Candidate candidate = session.createQuery("select distinct c from Candidate c "
                    + "join fetch c.base b "
                    + "join fetch b.vacancies v "
                    + "where c.id = :ids", Candidate.class
            ).setParameter("ids", 4).uniqueResult();
            System.out.println(candidate);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
