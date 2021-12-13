package ru.job4j.manytomany;

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

            Book one = Book.of("Братья Карамазовы");
            Book two = Book.of("Двеннадцать стульев");
            Book three = Book.of("Преступление и наказание");

            Author first = Author.of("Достоевский");
            Author second = Author.of("Ильф");
            Author third = Author.of("Петров");

            first.getBooks().add(one);
            first.getBooks().add(three);
            second.getBooks().add(two);
            third.getBooks().add(two);

            session.persist(first);
            session.persist(second);
            session.persist(third);

            Author author = session.get(Author.class, 1);
            session.remove(author);
            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
