package ru.job4j.todo.storage;

import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Collection;

public interface Store {

    Collection<Item> findAll();

    void save(Item item);

    void update(int id);

    void saveUser(User user);

    User findUserByEmail(String email);
}
