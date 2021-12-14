package ru.job4j.todo.storage;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.Collection;

public interface Store {

    Collection<Item> findAll();

    void save(Item item, String[] categories);

    void update(int id);

    void saveUser(User user);

    User findUserByEmail(String email);

    void saveCategory(String name);

    Category readCategory(String name);

    void updateCategory(String name, String newName);

    void deleteCategory(String name);
    Collection<Category> findAllCategories();
}
