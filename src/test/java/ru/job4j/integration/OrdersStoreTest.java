package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrdersStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void shutDown() throws SQLException {
        pool.getConnection().prepareStatement("drop table orders").executeUpdate();
    }

    @Test
    public void whenSaveOrder() {
        OrdersStore store = new OrdersStore(pool);

        Order order = Order.of("name1", "description1");
        store.save(order);
        Order savedOrder = store.findById(order.getId());
        assertEquals(order, savedOrder);
    }

    @Test
    public void whenFindById() {
        OrdersStore store = new OrdersStore(pool);

        Order order = Order.of("name1", "description1");
        store.save(order);
        Order find = store.findById(order.getId());
        assertEquals(order, find);
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertEquals(all.size(), 1);
        assertEquals(all.get(0).getDescription(), "description1");
        assertEquals(all.get(0).getId(), 1);
    }

    @Test
    public void whenFindByName() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name", "desc"));
        Order savedOrder = store.save(Order.of("name2", "desc2"));

        Order foundByNameOrder = store.findByName(savedOrder.getName());
        assertEquals(foundByNameOrder, savedOrder);
    }

    @Test
    public void whenUpdateOrder() {
        OrdersStore store = new OrdersStore(pool);
        Order saveOrder = store.save(Order.of("name", "description"));
        saveOrder.setDescription("description1");
        saveOrder.setName("name1");
        store.updateOrder(saveOrder);
        Order updateOrder = store.findById(saveOrder.getId());
        assertEquals(updateOrder, saveOrder);
    }
}