package ru.job4j.todo.storage;

import ru.job4j.todo.model.Item;

import java.util.Collection;

public interface Store {

    Collection<Item> findAll();

    void save(Item item);

    void update(int id);
}
