<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"/>
    <link type="text/css" rel="stylesheet" href="css/simplePagination.css"/>
    <link type="text/css" rel="stylesheet" href="css/directorypage.css"/>
    <script src="js/jquery/3.3.1/jquery.min.js"></script>
    <script src="js/bootstrap/3.4.0/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script type="text/javascript" src="js/jquery/jquery.simplePagination.js"></script>
    <script type="text/javascript" src="js/directorypage/catalog.create.js"></script>
    <script type="text/javascript" src="js/directorypage/catalog.delete.js" async></script>
    <script type="text/javascript" src="js/directorypage/catalog.load.all.js" async></script>
    <script type="text/javascript" src="js/directorypage/catalog.open.js" async></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.create-book.js"></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.delete-book.js"></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.move-book.js"></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.edit-book.js" async></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.open-book.js" async></script>
    <script type="text/javascript" src="js/directorypage/book/catalog.delete-book-from-catalog.js"></script>
    <script type="text/javascript" src="js/directorypage/catalog.refresh-table.js"></script>
    <script type="text/javascript" src="js/directorypage/catalog.check-user-select.js" async></script>
    <script type="text/javascript" src="js/directorypage/catalog.create-order.js"></script>


    <title>Каталог</title>
</head>

<body onload="loadCatalogs();">

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
<div id="sidebar"></div>
<div id="content"></div>
<div id="buttonPanel"></div>

<div class="modal fade" id="createBookWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Создать книгу</h4>
                </div>
            </div>
            <div class="modal-body">
                <form name="createBookForm">
                    <label for="newISBNBookNumber">ISBN:</label>
                    <input id="newISBNBookNumber" value="" onkeypress="validateNumericField(event)" class="form-control"
                           placeholder="Введите ISBN-номер книги" name="newISBNBookNumber" type="text" maxlength="13"
                           aria-required="true"/>
                    <label for="newAuthorLastName">Фамилия автора:</label>
                    <input id="newAuthorLastName" class="form-control" name="newAuthorLastName"
                           placeholder="Введите фамилию автора" type="text" aria-required="true"/>
                    <label for="newAuthorFirstName">Имя автора:</label>
                    <input id="newAuthorFirstName" class="form-control" name="newAuthorFirstName"
                           placeholder="Введите имя автора" type="text" aria-required="true"/>
                    <label for="newAuthorSecondName">Отчество автора:</label>
                    <input id="newAuthorSecondName" class="form-control" name="newAuthorSecondName"
                           placeholder="Введите отчество автора" type="text" aria-required="true"/>
                    <label for="newBookName">Название книги:</label>
                    <input id="newBookName" class="form-control" name="newBookName" placeholder="Введите название книги"
                           type="text" aria-required="true"/>
                    <label for="newPublishingName">Издательство:</label>
                    <input id="newPublishingName" class="form-control" name="newPublishingName"
                           placeholder="Введите название издательства" type="text" aria-required="true"/>
                    <label for="newYearPublishing">Год издания:</label>
                    <input id="newYearPublishing" width="50" value="" onkeypress="validateNumericField(event)"
                           class="form-control" placeholder="Введите год издания книги" name="newYearPublishing"
                           type="text" maxlength="4" size="4" aria-required="true"/>
                    <label for="createFormCatalogId">Поместить в каталог:</label>
                    <div id="createFormCatalogId" class="catalogsList">
                    </div>
                    <label for="countCreate">Количество:</label>
                    <input id="countCreate" width="50" type="number" name="countCreate" class="form-control" min="1"
                           max="99" size="4"/>
                </form>
            </div>
            <div class="modal-footer">
                <button onclick="createBook()" class="btn btn-success">Создать</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="createCatalogWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Создать каталог</h4>
                </div>
            </div>
            <div class="modal-body">
                <form name="createCatalogForm">
                    <label for="newCatalogName">Название:</label>
                    <input id="newCatalogName" value="" class="form-control" placeholder="Введите название каталога"
                           name="newCatalogName" type="text" aria-required="true"/>
                </form>
            </div>
            <div class="modal-footer">
                <button onclick="createCatalog()" class="btn btn-success">Создать</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="deleteCatalogWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <center><h4 class="modal-title">Удаление каталога</h4></center>
            </div>
            <div class="modal-body">
                    <center><p id="confirmAnswerDeleteCatalog">Выберите каталог(и) для удаления</p>
                        <form id="deleteCatalogForm" name="deleteCatalogForm" class="catalogsList">
                        </form>
                    </center>
            </div>
            <div class="modal-footer">
                <button id="confirmActionButtonDeleteCatalog" type="button" class="btn btn-danger"
                        onClick="deleteCatalog();">
                    Удалить
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="bookInfoWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Информация о книге</h4></div>
            </div>
            <div id="bookInfoField" class="modal-body"></div>
            <div class="modal-footer">
                <button id="editBookButton" type="button" class="btn btn-success">Изменить</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="moveBookWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <center><h4 class="modal-title">Перемещение книги</h4></center>
            </div>
            <div class="modal-body">
                <center><p id="confirmAnswerMoveBooks">Книги будут перемещены в выбранный каталог.</p>
                    <label>Выберите каталог:</label>
                    <form name="moveBooksForm" class="catalogsList" id="moveBooksForm">
                        <input type="hidden" id="choosenBooks" name="choosenBooks" value=""/>
                    </form>
                </center>
            </div>
            <div class="modal-footer">
                <button id="confirmActionButtonMoveBooks" type="button" class="btn btn-primary" onClick="moveBooks();">
                    Ок
                </button>
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

<div class="modal fade" id="createOrderWindow" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <div style="text-align: center;"><h4 class="modal-title">Формирование заказа (Предпросмотр)</h4></div>
            </div>
            <input type="hidden" name="selectedBooks" id="selectedBooks"/>


            <div id="giveOrderContainer" style="margin: 5px">
                <div class="col-sm-4">
                    <label for="search" class="fb-date-label">Читатель</label>
                    <div align="center">
                        <input type="text" name="search" id="search" placeholder="Введите № чит. билета" class="form-control" />
                    </div>
                    <ul class="list-group" id="result"></ul>
                </div>
                <form id="giveOrderForm" name="giveOrderForm">
                    <div class="col-sm-4">
                        <label for="giveDate" class="fb-date-label">Дата выдачи</label>
                        <input type="date" class="form-control" name="giveDate" id="giveDate"/>
                    </div>
                    <div class="col-sm-4">
                        <label for="returnDate" class="fb-date-label">Дата возврата</label>
                        <input type="date" class="form-control" name="returnDate" id="returnDate"/>
                    </div>
                    <h4>Выбранные книги</h4>
                    <div id="ordersContent">
                        <table class="table table-bordered table-sm">
                            <thead>
                            <tr class="header">
                                <th style="width:1%;"><input type="checkbox" onclick="selectAllBooksInOrdersTable(this)"/></th>
                                <th style="width:3%;" onclick="sortTable(0)">ID</th>
                                <th style="width:5%;" onclick="sortTable(0)">ISBN</th>
                                <th style="width:10%;" onclick="sortTable(1)">Автор</th>
                                <th style="width:20%;" onclick="sortTable(0)">Название</th>
                                <th style="width:10%;" onclick="sortTable(0)">Издательство</th>
                                <th style="width:5%;" onclick="sortTable(0)">Год издания</th>
                                <th style="width:5%;" onclick="sortTable(0)">Каталог</th>
                                <th style="width:10%;" onclick="sortTable(0)">Дата создания</th>
                                <th style="width:5%;" onclick="sortTable(0)">Создатель</th>
                                <th style="width:10%;" onclick="sortTable(0)">Последнее изменение</th>
                                <th style="width:5%;" onclick="sortTable(0)">Кто изменил</th>
                            </tr>
                            </thead>
                            <tbody id="ordersTableContent">
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" id="ticketCode" name="ticketCode"/>
                    <input type="hidden" id="orderBooks" name="orderBooks"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-success" onclick="createOrder();">Сформировать</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<script>
    $('#search').keypress(function(e){
        if(e.which === 13){
            $('#result').html('');
            $('#state').val('');
            var searchField = $('#search').val();
            if(searchField.length > 2){
                var pageNumber = 1;
                $.ajax({
                    url: 'api/search/findTicketByCode',
                    data: {'code': searchField, 'pageNumber' : pageNumber},
                    type: 'POST',
                    success: function (data, textStatus) {
                        for(var key in data.ticketList)
                            $('#result').append('<li class="list-group-item link-class" value="' + data.ticketList[key].code + '">' + data.ticketList[key].code + '</li>');
                    },
                    error: function (jqXHR, errorThrown) {
                        $('#confirmActionWindow').modal('hide');
                        $('#resultAction').html('Произошла ошибка!');
                        if (jqXHR.status === 403) {
                            $("#feedbackDescription").html('Доступ запрещен!');
                        } else {
                            $("#feedbackDescription").html('Произошла ошибка поиска чит. билета: ' + jqXHR.responseText);
                        }
                        $('#resultWindow').modal('show');
                    }
                });
            }
        }
    });

    $('#result').on('click', 'li', function() {
        var click_text = $(this).text().split('|');
        $('#search').val($.trim(click_text[0]));
        $('#result').html('');
        $('#ticketCode').val(click_text);
    });
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

<script>
    function selectAllBooksInOrdersTable(obj) {
        var items = document.getElementsByClassName("checkboxBooksInOrder"),
            len, i;
        for (i = 0, len = items.length; i < len; i += 1) {
            if (items.item(i).type && items.item(i).type === "checkbox") {
                if (obj.checked) {
                    items.item(i).checked = true;
                }
                else {
                    items.item(i).checked = false;
                }
            }
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
        var checkedValues = Array.from(selectedCheckBoxes).map(cb => cb.value);
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
</body>
</html>
