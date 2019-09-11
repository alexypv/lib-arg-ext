<%--
  Created by IntelliJ IDEA.
  User: popov.a
  Date: 20.08.2019
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link type="text/css" rel="stylesheet" href="resources/css/loginpage.css"/>
    <title>Вход в систему</title>
    <style>
        body {
            background-color: GhostWhite;
        }

        body {
            margin: 0;
            background-color: #fdfdfd;
            background-image: linear-gradient(90deg, transparent 98px, #e673a2 98px, #e673a2 300px, transparent 300px),
            linear-gradient(#e8e7e7 1px, transparent 0px),
            linear-gradient(90deg, #e8e7e7 1px, transparent 0px);
            background-size: 100% 100%, 15px 15px, 15px 15px;
            background-position: 0 0, -1px -1px, -1px 1px;
        }


        * {
            box-sizing: border-box;
        }

        .container {
            position: absolute;
            top: 0;
            bottom: 100px;
            left: 0;
            right: 0;
            margin: auto;
            width: 400px;
            height: 230px;

        }

        .input-container {
            display: -ms-flexbox; /* IE10 */
            display: flex;
            width: 100%;
            margin-bottom: 15px;
        }

        .icon {
            padding: 10px;
            background: dodgerblue;
            color: white;
            min-width: 50px;
            text-align: center;
        }

        .input-field {
            width: 100%;
            padding: 10px;
            outline: none;
        }

        .input-field:focus {
            border: 2px solid dodgerblue;
        }

        /* Set a style for the submit button */
        .btn {
            background-color: dodgerblue;
            color: white;
            padding: 15px 20px;
            border: none;
            cursor: pointer;
            width: 100%;
            opacity: 0.9;
        }

        h2 {

            text-align: center;
        }

        .footer {
            position: absolute;
            left: 0;
            bottom: 0;
            width: 100%;
            height: 50px;
        }

        .btn:hover {
            opacity: 1;
        }
    </style>
</head>
<body>

<div class="container">
    <form action="j_spring_security_check" method="post" style="max-width:500px;margin:auto">
        <h2>Вход в систему</h2>
        <div class="input-container">
            <i class="fa fa-user icon"></i>
            <input class="input-field" type="text" placeholder="Введите имя пользователя" name="j_username">
        </div>

        <div class="input-container">
            <i class="fa fa-key icon"></i>
            <input class="input-field" type="password" placeholder="Введите пароль" name="j_password">
        </div>
        <button type="submit" class="btn">Войти</button>
    </form>
</div>

<div class="footer">
    <p><a href="https://github.com/alexypv" target="_blank">Link on GitHub</a></p>
</div>

</body>
</html>
