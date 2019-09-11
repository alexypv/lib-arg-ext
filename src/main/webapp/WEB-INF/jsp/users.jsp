<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"/>

    <link type="text/css" rel="stylesheet" href="resources/css/simplePagination.css"/>

    <link type="text/css" rel="stylesheet" href="resources/css/userspage.css"/>
    <script src="resources/js/jquery/3.3.1/jquery.min.js"></script>
    <script src="resources/js/bootstrap/3.4.0/bootstrap.min.js"></script>

    <script type="text/javascript" src="resources/js/administration/users/history-table-pagination.js"></script>
    <script type="text/javascript" src="resources/js/administration/users/roles.load.all.js"></script>
    <script type="text/javascript" src="resources/js/administration/users/users.create-user.js"></script>
    <script type="text/javascript" src="resources/js/administration/users/users.refresh-table.js"></script>
    <script type="text/javascript" src="resources/js/administration/users/users.edit-user.js"></script>
    <script type="text/javascript" src="resources/js/administration/users/users.get-history.js"></script>

    <title>Каталог</title>
</head>

<body onload="loadRoles();">

<div id="snackbar"></div>
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
            <li><a href="profile"><span class="glyphicon glyphicon-user"></span>${username}</a></li>
            <li><a href="logout"><span class="glyphicon glyphicon-log-in"></span> Выйти</a></li>
        </ul>
    </div>
</nav>

<div id="sidebar"></div>
<div id="content"></div>
<div id="buttonPanel"></div>

<div class="modal fade" id="createReaderWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Создать книгу</h4>
                </div>
            </div>
            <div class="modal-body">
                <form id = "createUserForm" name="createUserForm">

                    <label for="newUsername">Новое имя пользователя:</label>
                    <input id="newUsername" value="" class="form-control" placeholder="Введите имя пользователя" name="newUsername" type="text" aria-required="true"></input>
                    <label for="newPassword">Новый пароль:</label>
                    <input id="newPassword" value="" class="form-control" placeholder="Введите пароль" name="newPassword" type="password" aria-required="true"></input>
                    <label for="newSurname">Фамилия пользователя:</label>
                    <input id="newSurname" class="form-control" name="newSurname"
                           placeholder="Введите фамилию автора" type="text" aria-required="true"></input>
                    <label for="newName">Имя пользователя:</label>
                    <input id="newName" class="form-control" name="newName"
                           placeholder="Введите имя автора" type="text" aria-required="true"></input>
                    <label for="newSecondName">Отчество пользователя:</label>
                    <input id="newSecondName" class="form-control" name="newSecondName"
                           placeholder="Введите отчество автора" type="text" aria-required="true"></input>

                    <label for="roleName">Тип:</label>
                    <input id="roleName" class="form-control" name="roleName"
                           value="" type="text" aria-required="true" disabled></input>

                    <input id="newRole" class="form-control" name="newRole" type="hidden"></input>
                    <input id="newLibrary" class="form-control" name="newLibrary" type="hidden"></input>
                </form>
            </div>
            <div class="modal-footer">
                <button onclick="createUser()" class="btn btn-success">Создать</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="userInfoWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Информация о книге</h4></div>
            </div>
            <div id="userInfoField" class="modal-body"></div>
            <div class="modal-footer">
                <button id="getUserHistory" value="1" type="button" class="btn btn-primary" style="float:left" onclick = "getHistory(1)">Посмотреть историю заказов</button>
                <button id="editUserButton" type="button" class="btn btn-success">Изменить</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
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
                    <div id="confirmContent"></div>
                </div>
            </div>
            <div class="modal-footer">
                <button id="confirmActionButton" type="button" class="btn btn-primary">Ок</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="resultWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="resultAction"></h4>
            </div>
            <div class="modal-body">
                <p id="feedback"></p>
                <div id="feedbackDescription"></div>
            </div>
            <div class="modal-footer">
                <button id="closeButtonResultWindow" type="button" class="btn btn-default" data-dismiss="modal">
                    Закрыть
                </button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="historyWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title" id="historyHeader"></h4>
                <div class="modal-body">

                    <div style="width: 100%; float: left; margin-bottom: 10px;">
                        <input id="searchHistoryField"type="text" class="form-control" placeholder="Введите информацию о заказе"  style="float: left; width: 95%;">
                        <div class="input-group-append">
                            <button class="btn btn-success" type="submit"  style="float: left; width: 5%;" onclick="searchHistory(1)">&#128270;</button>
                        </div>
                    </div>

                    <div id="ordersContent">
                        <table id="historyTable" class="table table-bordered table-sm">
                            <thead>
                            <tr class="header">
                                <th style="width:5%;" onclick="sortTable(0)">ID</th>
                                <th style="width:10%;" onclick="sortTable(0)">Код</th>
                                <th style="width:10%;" onclick="sortTable(1)">Кто выдал</th>
                                <th style="width:20%;" onclick="sortTable(0)">Дата выдачи</th>
                                <th style="width:20%;" onclick="sortTable(0)">Вернуть до</th>
                                <th style="width:20%;" onclick="sortTable(0)">Дата возврата</th>
                                <th style="width:10%;" onclick="sortTable(0)">Возврат принял</th>
                            </tr>
                            </thead>
                            <tbody id="historyTableContent">
                            </tbody>
                        </table>
                        <div id="light-pagination-history" class="pagination"></div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        function searchHistory(pageNumber) {
            var code = $('#ticketCode').val();
            var searchValue = $('#searchHistoryField').val();
            $.ajax({
                type: "POST",
                url: 'api/orders/findOrder/' + pageNumber,
                data: {'ticketCode': code, 'searchValue' : searchValue},
                success: function (data, textStatus) {
                    var div_data = '';
                    for (var key in data.ordersList) {
                        div_data += '<tr>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].id + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].code + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].creatorName + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].startDate + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].endDate + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].realReturnDate + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">Пока нет</td>';
                        div_data += '</tr>';
                    }

                    if (pageNumber === 1) {
                        var countPages = data.countPages;
                        $(function () {
                            $('#light-pagination-history').pagination({
                                items: parseInt(countPages),
                                itemsOnPage: 1,
                                cssStyle: 'light-theme'
                            });
                        });
                    }
                    $('#historyTableContent').html('');
                    $('#historyTableContent').html(div_data);


                },
                error: function (jqXHR, errorThrown) {
                    $('#resultAction').html('Ошибка!');
                    if (jqXHR.status == 403) {
                        $('#feedback').html('Доступ запрещен!');
                    } else {
                        $('#feedback').html('Произошла ошибка при создании заказа: ' + jqXHR.responseText);
                    }
                    $('#resultWindow').modal('show');
                }
            });


        }
    </script>

    <script>
        function findBook() {
            var input, filter, found, table, tr, td, i, j;
            input = document.getElementById("bookSearch");
            filter = input.value.toUpperCase();
            table = document.getElementById("tableContent");
            tr = table.getElementsByTagName("tr");
            for (i = 0; i < tr.length; i++) {
                td = tr[i].getElementsByTagName("td");
                for (j = 0; j < td.length; j++) {
                    if (td[j].innerHTML.toUpperCase().indexOf(filter) > -1) {
                        found = true;
                    }
                }
                if (found) {
                    tr[i].style.display = "";
                    found = false;
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    </script>

    <script type="text/javascript">

    </script>

    <script>
        $(document).on('change', '.checkboxBooks', function () {
            if (this.checked) {
                var arr = $('#selectedBooks').val().split(',');
                if ($('#selectedBooks').val() === '') {
                    delete arr[0];
                }
                arr = arr.filter(function () {
                    return true
                });
                arr.push($(this).val());
                arr = Array.from(new Set(arr));
                $('#selectedBooks').val(arr);
                $('#choosenBooksCount').html('Отмечено книг (' + arr.length + ')');
            } else {
                var arr = $('#selectedBooks').val().split(',');
                var index = arr.indexOf($(this).val());
                if (index >= 0) arr.splice(index, 1);
                arr = Array.from(new Set(arr));
                $('#selectedBooks').val(arr);
                $('#choosenBooksCount').html('Отмечено книг (' + arr.length + ')');

            }

        });

        function selectAllBooks(obj) {
            var items = document.getElementsByClassName("checkboxBooks"),
                len, i;
            for (i = 0, len = items.length; i < len; i += 1) {
                if (items.item(i).type && items.item(i).type === "checkbox") {
                    if (obj.checked) {
                        items.item(i).checked = true;
                        var arr = $('#selectedBooks').val().split(',');
                        if ($('#selectedBooks').val() === '') {
                            delete arr[0];
                        }
                        arr = arr.filter(function () {
                            return true
                        });
                        arr.push($(items.item(i)).val());
                        arr = Array.from(new Set(arr));
                        $('#selectedBooks').val(arr);
                        $('#choosenBooksCount').html('Отмечено книг (' + arr.length + ')');
                    } else {
                        items.item(i).checked = false;
                        var arr = $('#selectedBooks').val().split(',');
                        var index = arr.indexOf($(items.item(i)).val());
                        if (index >= 0) arr.splice(index, 1);
                        arr = Array.from(new Set(arr));
                        $('#selectedBooks').val(arr);
                        $('#choosenBooksCount').html('Отмечено книг (' + arr.length + ')');
                    }

                }
            }
        }
    </script>

    <script type="text/javascript">
        function validateNumericField(evt) {
            var theEvent = evt || window.event;
            var key = theEvent.keyCode || theEvent.which;
            key = String.fromCharCode(key);
            var regex = /[0-9]|\./;
            if (!regex.test(key)) {
                theEvent.returnValue = false;
                if (theEvent.preventDefault) theEvent.preventDefault();
            }
        }
    </script>

    <script type="text/javascript">
        function validateName(evt) {
            var theEvent = evt || window.event;
            var key = theEvent.keyCode || theEvent.which;
            key = String.fromCharCode(key);
            var regex = /[А-Яа-яЁё ]|\./;
            if (!regex.test(key)) {
                theEvent.returnValue = false;
                if (theEvent.preventDefault) theEvent.preventDefault();
            }
        }
    </script>


    <script type="text/javascript">
        function getCheckedBooksInOrderForm() {
            var selectedCheckBoxes = document.querySelectorAll('input.checkboxBooksInOrder:checked');
            var checkedValues = Array.from(selectedCheckBoxes).map(cb => cb.value
        )
            ;
            return checkedValues;
        }
    </script>

    <script>
        function searchCatalog() {
            var input, filter, ul, li, a, i;
            input = document.getElementById("catalogSearchInfo");
            filter = input.value.toUpperCase();
            ul = document.getElementById("catalogsMenu");
            li = ul.getElementsByTagName("li");
            for (i = 0; i < li.length; i++) {
                a = li[i].getElementsByTagName("a")[0];
                if (a.innerHTML.toUpperCase().indexOf(filter) > -1) {
                    li[i].style.display = "";
                } else {
                    li[i].style.display = "none";
                }
            }
        }
    </script>

    <script>
        function test() {
            alert(getCheckedBooksInOrderForm())
        }
    </script>

    <script>
        $("#sidebar").on('dblclick', "li", function () {
            document.getElementById("content").innerHTML = "";
            var role_name;
            var role_id;

            var url = "api/users/getUsers/";
            $(this).find("a")
                .each(function () {
                    $(this).attr("selected", true);
                    role_id = $(this).data('value');
                    $('#newRole').val(role_id);
                    url += role_id + "/1";
                    role_name = $(this).attr("name");
                });
            $.ajax({
                type: "POST",
                url: url,
                success: function (data, textStatus) {
                    var div_data = '';
                    div_data += '<h3 id="contentDescription">‎</h3>';
                    div_data += '<input type="hidden" class="catalog_id" id="catalog_id" name="catalog_id" value=""/>';
                    div_data += ' <input type="text" style= "display:inline; width:550px" name="bookSearch" id="bookSearch" onkeyup="findBook();" class="form-control" placeholder="Введите любую информацию о книге..."/>';
                    div_data += '<div style="float:right">';
                    div_data += '<a style="display:inline; margin-right: 10px;" id="choosenBooksCount">Отмечено книг (0)</a>';
                    div_data += '</div>';
                    div_data += '<br/>';
                    div_data += '<br/>';
                    div_data += '<table id="tableBooksInCatalog" class="table table-bordered table-sm">';
                    div_data += '<thead>';
                    div_data += '<tr class="header">';
                    div_data += '<th style="width:1%;"><input type="checkbox" onclick="selectAllBooks(this)"/></th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">№ чит.билета</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Логин</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Фамилия</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(1)">Имя</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Отчество</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Кто создал</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Дата создания</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Кто изменил</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Дата изменения</th>';
                    div_data += '<th style="width:6%;" onclick="sortTable(0)">Роль</th>';
                    div_data += '<th style="width:10%;" onclick="sortTable(0)">Библиотека</th>';
                    div_data += '</tr>';
                    div_data += '</thead>';

                    div_data += '<tbody id="tableContent">';
                    for (var key in data.usersList) {
                        div_data += '<tr>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '"><input class="checkboxBooks" type="checkbox" value="' + data.usersList[key].id + '" /></td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].ticketCode + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].username + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].surname + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].name + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].secondName + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].creatorName + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].createdWhen + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].updaterName + '</td>';
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].updatedWhen + '</td>';
                        if (data.usersList[key].roleName === 'ROLE_ADMIN') {
                            div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Администратор библиотеки</td>';
                            $('#roleName').val('Администратор библиотеки');
                        } else if (data.usersList[key].roleName === 'ROLE_LIBRARIER') {
                            div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Библиотекарь</td>';
                            $('#roleName').val('Библиотекарь');
                        } else if (data.usersList[key].roleName === 'ROLE_READER') {
                            div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Читатель</td>';
                            $('#roleName').val('Читатель');
                        }
                        div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].libraryName + '</td>';
                        div_data += '</tr>';

                    }

                    if (data.currentCatalog.name === 'ROLE_ADMIN') {
                        $('#contentDescription').html('Список пользователей типа «Администратор библиотеки»');
                        $('#roleName').val('Администратор библиотеки');
                    } else if (data.currentCatalog.name === 'ROLE_LIBRARIER') {
                        $('#contentDescription').html('Список пользователей типа «Библиотекарь»');
                        $('#roleName').val('Библиотекарь');
                    } else if (data.currentCatalog.name === 'ROLE_READER') {
                        $('#contentDescription').html('Список пользователей типа «Читатель»');
                        $('#roleName').val('Читатель');
                    }

                    $('#newLibrary').val(data.currentLibraryId);
                    div_data += '</tbody>';
                    div_data += '</table>';
                    div_data += '<div id="light-pagination" class="pagination"></div>';
                    div_data += '<div class="btn-group btn-group-justified" id="actionButtonGroup">';
                    div_data += '<a class="btn btn-success" data-toggle="modal" data-target="#createReaderWindow">Создать пользователя</a>';
                    div_data += '<a id = "deleteBooksButton" class="btn btn-danger" onClick = "checkCheckboxes(' + "'delete'" + ')" >Удалить пользователя</a>';
                    div_data += '</div>';
                    $('#content').html(div_data);

                    $(function () {
                        $('#light-pagination').pagination({
                            items: parseInt(data.countPage),
                            itemsOnPage: 1,
                            cssStyle: 'light-theme'
                        });
                    });
                },
                error: function (textStatus, errorThrown) {
                    $("#content").html(textStatus.responseText);
                }
            });
        });
    </script>

    <script>
        $("#content").on('dblclick', "tr", function () {
            $('#userInfoField').html('');
            var user_id;
            $(this).find("td")
                .each(function () {
                    user_id = $(this).data('value');
                });
            $.ajax({
                type: "POST",
                url: 'api/users/getUserInfo/' + user_id,
                success: function (data, textStatus) {
                    var div_data = '';

                    div_data += '<label for="ticketNumber">№ читательского билета</label>  ';
                    div_data += '<input type="text" class="form-control" id="ticketCode" name="ticketCode" value = "' + data.ticketCode + '" disabled></input>';

                    div_data += '<label for="library">Библиотека</label>  ';
                    div_data += '<input type="text" class="form-control" name="library" value = "' + data.libraryName + '" disabled></input>';

                    div_data += '<form name="editUserForm" method="post">';
                    div_data += '<label for="username">Имя пользователя:</label>';
                    div_data += '<input type="text" class="form-control" name="username"  value= "' + data.username + '"  maxlength = "13"></input>';

                    div_data += '<label for="surname">Фамилия:</label>';
                    div_data += '<input type="text"class="form-control" name="surname" value = "' + data.surname + '"></input>';

                    div_data += '<label for="name">Имя</label>  ';
                    div_data += '<input type="text"class="form-control" name="name" value = "' + data.name + '"></input>';

                    div_data += '<label for="secondName">Отчество:</label>';
                    div_data += '<input type="text"class="form-control" name="secondname" value = "' + data.secondName + '"></input>';

                    div_data += '<label for="password">Пароль</label>  ';
                    div_data += '<input type="password" class="form-control" name="password" placeholder = "Введите новый пароль"></input>';

                    div_data += '<input type="hidden" name="user_id"  value = "' + data.id + '"></input>';
                    div_data += '</form>';

                    div_data += '</br>';
                    $('#userInfoField').html(div_data);
                    $('#userInfoWindow').modal('show');
                },
                error: function (textStatus, errorThrown) {
                }
            });

            $('#editUserButton').click(function (event) {
                event.preventDefault();
                $('#confirmActionWindow').attr('onclick', 'editUser();');
                $('#confirmDescription').html('Будет изменена информация о пользователе. Требуется подтверждение.');
                $('#confirmActionWindow').modal('show');
            });

        });


    </script>

</body>
</html>
