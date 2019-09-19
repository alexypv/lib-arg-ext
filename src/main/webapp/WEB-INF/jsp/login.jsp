<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%--<meta http-equiv="X-UA-Compatible" content="IE=8" />--%>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <title>Вход в системку</title>


    <link rel="stylesheet" href="<spring:url value="js/ext-4/resources/css/ext-all.css" />" type="text/css">
    <script type="text/javascript" src="<spring:url value="js/ext-4/ext-all.js"/>"></script>

</head>
<body>

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

<script>
    Ext.onReady(function(){

        let loginForm = Ext.create('Ext.form.Panel', {
            title: 'Авторизация',
            width: 300,
            height: 150,
            bodyPadding: 10,
            layout: 'anchor',
            frame: true,
            defaults: {
                anchor: '80%'
            },
            renderTo: Ext.getBody(),
            items: [{
                xtype: 'textfield',
                fieldLabel: 'Логин',
                name: 'j_username'
            },
                {
                    xtype: 'textfield',
                    name: 'j_password',
                    fieldLabel: 'Пароль',
                    inputType: 'password'
                }],
            buttons: [{
                text: 'Оправить',
                handler: function () {
                    loginForm.getForm().submit({
                        url: 'j_spring_security_check',
                        success: function (form, action) {
                        },
                        failure: function (form, action) {
                        }
                    });
                }
            }]
        });
    });
</script>

</body>
</html>