<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script>function validate() {
        if ($('#name').val() === '') {
            alert('Введите имя');
            return false;
        } else if ($('#email').val() === '') {
            alert('Введите почту');
            return false;
        } else if (!$('#email').val().match("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            alert('Некорректная почта');
        } else if ($('#password').val() === '') {
            alert('Введите пароль');
            return false;
        }
        return true;
    }</script>
    <title>Регистрация</title>
</head>
<body>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Новый пользователь
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/reg.do" method="post">
                    <div class="form-group">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" placeholder="Введите имя" id="name">
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="text" class="form-control" name="email" placeholder="Введите email" id="email">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="password" class="form-control" name="password" placeholder="Введите пароль"
                               id="password">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="validate();">Создать</button>
                    <div class="container">
                        <div class="row">
                            <% String error = (String) request.getAttribute("error");
                                if (error != null) { %>
                            <div style="color:red; font-weight: bold; margin: 30px 0;">
                                <%=error%>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <ul class="nav">
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.do">На главную</a>
        </li>
    </ul>
</div>
</div>
</body>
</html>