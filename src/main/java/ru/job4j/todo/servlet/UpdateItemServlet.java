package ru.job4j.todo.servlet;

import ru.job4j.todo.storage.HbmStore;
import ru.job4j.todo.storage.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateItemServlet extends HttpServlet {
    private final Store store = new HbmStore();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        store.update(id);
    }
}
