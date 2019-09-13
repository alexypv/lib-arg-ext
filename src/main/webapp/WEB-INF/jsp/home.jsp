<%--
  Created by IntelliJ IDEA.
  User: popov.a
  Date: 20.08.2019
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript"  src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <title>Главная</title>
    <style type="text/css">
        body {
            margin: 0;
            background-color: #fdfdfd;
            background-image: linear-gradient(90deg, transparent 298px, #e673a2 298px, #e673a2 300px, transparent 300px),
            linear-gradient(#e8e7e7 1px, transparent 0px),
            linear-gradient(90deg, #e8e7e7 1px, transparent 0px);
            background-size: 100% 100%, 15px 15px, 15px 15px;
            background-position: 0 0, -1px -1px, -1px 1px;
        }
    </style>
</head>
<body>
<nav class="navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="home">Главная</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="directory">Каталог книг</a></li>
            <li><a href="users" class="active">Пользователи</a></li>
            <li><a href="#">Журнал выдачи книг</a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">

            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">${username}<span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li><a href="profile">Личный кабинет</a></li>
                    <li><a href="logout">Выйти</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
