package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.storage.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class ItemServlet extends HttpServlet {

    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Item> itemList = (List<Item>) HbmStore.instOf().findAll();
        if (!"true".equals(req.getParameter("show_all"))) {
            itemList.removeIf(Item::isDone);
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        OutputStream output = resp.getOutputStream();
        String json = GSON.toJson(itemList);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String description = req.getParameter("description");

        Item item = new Item(
                0,
                description,
                Timestamp.from(Instant.now()),
                false,
                HbmStore.instOf().findUserByEmail(req.getParameter("email"))
        );
        HbmStore.instOf().save(item);
        doGet(req, resp);
    }

}