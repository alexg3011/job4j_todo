package ru.job4j.todo.servlet;

import ru.job4j.todo.model.User;
import ru.job4j.todo.storage.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = new User(0, name, email, password);
        User dbUser = HbmStore.instOf().findUserByEmail(email);
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        if (name.equals("")) {
            req.setAttribute("error", "Введите имя");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (Objects.equals(email, "")) {
            req.setAttribute("error", "Введите email");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (!Objects.requireNonNull(matcher).matches()) {
            req.setAttribute("error", "Неверно введен E-mail");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (password.equals("")) {
            req.setAttribute("error", "Введите пароль");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else if (dbUser == null) {
            HbmStore.instOf().saveUser(User.of(name, email, password));
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else if (dbUser.equals(user)) {
            req.setAttribute("error", "Такой пользователь уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Заполните все поля");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
    }
}
