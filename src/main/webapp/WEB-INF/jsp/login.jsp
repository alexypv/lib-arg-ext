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
<style>

    body {
        margin: 0;
        background-color: #fdfdfd;
        background-image: linear-gradient(90deg, transparent 98px, #e673a2 98px, #e673a2 300px, transparent 300px),
        linear-gradient(#e8e7e7 1px, transparent 0px),
        linear-gradient(90deg, #e8e7e7 1px, transparent 0px);
        background-size: 100% 100%, 15px 15px, 15px 15px;
        background-position: 0 0, -1px -1px, -1px 1px;
    }

    .container {
        position: absolute;
        top: 0;
        bottom: 100px;
        left: 0;
        right: 0;
        margin: auto;
        width: 300px;
        height: 150px;
    }

</style>

<body>

<div id ="loginForm" class="container"></div>

<script>
    Ext.onReady(function(){
        var loginForm = Ext.create('Ext.form.Panel', {
            title: 'Авторизация',
            width: 300,
            height: 150,
            bodyPadding: 10,
            layout: 'anchor',
            frame: true,
            defaults: {
                anchor: '80%'
            },
            renderTo: Ext.getElementById('loginForm'),
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
                    loginForm.getForm().standartSubmit = true;
                    loginForm.getForm().submit({
                        url: 'j_spring_security_check',
                        standardSubmit: true,
                        method: 'POST'
                    });
                }
            }]
        });
    });
</script>

</body>
</html>


