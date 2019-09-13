<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" content="width=device-width, initial-scale=1" name="viewport">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <link type="text/css" rel="stylesheet" href="resources/css/profilepage.css"/>
    <link type="text/css" rel="stylesheet" href="resources/css/jcrop.css"/>
    <script src="resources/js/jquery/3.3.1/jquery.min.js"></script>
    <script src="resources/js/bootstrap/3.4.0/bootstrap.min.js"></script>
    <script src="resources/js/profile/profile.photo.upload.js"></script>
    <script src="resources/js/profile/profile.photo.delete.js"></script>
    <script src="resources/js/profile/profile.edit.js"></script>
    <script src="resources/js/jquery/jcrop.js"></script>
    <title>Профиль</title>

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

<div id="userImage">
    <div id="imageUser" class="cropme">
        <img id="ItemPreview" src="${image}" style="border-radius: 50%;"/>
    </div>
    <div id="photoButonGroup" class="btn-group btn-group-sm">
        <button id="uploadPhotoButton" type="button" class="btn btn-primary" disabled><b>Загрузить фото</b></button>
        <button id="deletePhotoButton" type="button" class="btn btn-danger"><b>Удалить фото</b></button>
    </div>
</div>

<div id="userInfoBox">
    <div class="container">
        <form id="userInfoForm" name="userInfoForm">
            <div class="row">
                <div class="col-25">
                    <label for="surname">Фамилия:</label>
                </div>
                <div class="col-50">
                    <input type="text" id="surname" name="surname" value="${surname}">
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <label for="name">Имя:</label>
                </div>
                <div class="col-50">
                    <input type="text" id="name" name="name" value="${name}">
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <label for="secondname">Отчество:</label>
                </div>
                <div class="col-50">
                    <input type="text" id="secondname" name="secondname" value="${secondname}">
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <label for="username">Логин:</label>
                </div>
                <div class="col-50">
                    <input type="text" id="username" name="username" value="${username}">
                </div>
            </div>
            <div class="row">
                <div class="col-25">
                    <label for="password">Пароль:</label>
                </div>
                <div class="col-50">
                    <input type="password" id="password" name="password" placeholder="Введите новый пароль..">
                </div>
            </div>
        </form>
        <div id="infoButtonGroup" class="btn-group btn-group-sm">
            <button id="saveInfoButton" type="submit" class="btn btn-primary"><b>Сохранить</b></button>
        </div>
    </div>
</div>

<div class="modal fade" id="resultWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <center><h4 class="modal-title" id="resultAction"></h4></center>
            </div>
            <div class="modal-body">
                <center><p id="feedback"></p>
                    <div id="feedbackDescription">
                    </div>
                </center>
            </div>
            <div class="modal-footer">
                <center>
                    <button id="closeButtonResultWindow" type="button" class="btn btn-default" data-dismiss="modal">
                        Закрыть
                    </button>
                </center>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="confirmActionWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <center><h4 class="modal-title">Подтверждение действия</h4></center>
            </div>
            <div class="modal-body">
                <div style="text-align: center;"><p id="confirmAnswer"></p>
                    <p id="confirmDescription"></p>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirmActionButton" type="button" class="btn btn-primary">Ок</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<%--Инициализация Jcrop-библиотеки--%>
<script type="text/javascript">
    $(document).ready(function () {
        $('.cropme').simpleCropper();
    });
</script>

</body>
</html>