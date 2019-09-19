<%--
  Created by IntelliJ IDEA.
  User: popov.a
  Date: 20.08.2019
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script type="text/javascript"  src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="<spring:url value="js/ext-4/resources/css/ext-all.css" />"type="text/css">
    <script type="text/javascript" src="<spring:url value="js/ext-4/ext-all.js"/>"></script>
    <title>Главная</title>
    <style type="text/css">

    </style>
</head>
<body>
<script type="text/javascript">
    Ext.require('*');
    Ext.onReady(function() {
        var currentName,
            panelCache = [],
            panel,
            replace = function(config, name) {
                var btns = Ext.getCmp('btns');
                if (name && name != currentName) {
                    currentName = name;
                    if (btns.items.length) {
                        btns.remove(0, false);
                    } else {
                        btns.body.innerHTML = '';
                    }
                    config.border = false,
                        panel = panelCache[name] || (panelCache[name] = Ext.widget('panel', config));
                    btns.add(panel);
                }
            };

        Ext.create('Ext.Viewport', {
            layout:'border',
            title: 'Список пользователей',
            items: [{
                id:'btns',
                region:'west',
                split:true,
                width:300,
                minWidth: 100,
                maxWidth: 300,
                layout:'fit',
                margin: '5 0 5 5',

            }, {
                region:'center',
                margin: '5 5 5 0',
                dockedItems:[{
                    xtype: 'toolbar',
                    enableOverflow: true,
                    dock: 'top',
                    defaults:{
                        margin:'0 5 0 0',
                        pressed: false,
                        toggleGroup:'btns',
                        allowDepress: false
                    },
                    items: [{

                    }]
                }]
            }]
        });
    });

</script>
</body>
</html>
