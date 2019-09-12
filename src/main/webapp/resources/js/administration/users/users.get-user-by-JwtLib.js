$("#sidebar").on('dblclick', "li", function () {
    document.getElementById("content").innerHTML = "";
    var role_name;
    var role_id;
    var pageNumber = 1;
    $(this).find("a")
        .each(function () {
            $(this).attr("selected", true);
            role_id = $(this).data('value');
            $('#newRole').val(role_id);
            role_name = $(this).attr("name");
        });
    $.ajax({
        type: "POST",
        url: 'api/users/getUsersInLibrary/' + pageNumber,
        data: {'role_id': role_id},
        success: function (data, textStatus) {
            var div_data = '';
            div_data +='<h3 id="contentDescription">‎</h3>';
            div_data +='<input type="hidden" class="catalog_id" id="catalog_id" name="catalog_id" value=""/>';
            div_data +='<div style="width: 100%; float: left; margin-bottom: 10px;">';
            div_data +='<input id="searchUserField" type="text" class="form-control" placeholder="Введите информацию о пользователе"  style="float: left; width: 95%;">';
            div_data +='<div class="input-group-append">';
            div_data +='<button class="btn btn-success" type="submit"  style="float: left; width: 5%;" onclick="searchUser(1)">&#128270;</button>';
            div_data +='</div>';
            div_data +='</div>';
            div_data +='<div style="float:right">';
            div_data +='</div>';
            div_data +='<br/>';
            div_data +='<br/>';
            div_data +='<table id="tableBooksInCatalog" class="table table-bordered table-sm">';
            div_data +='<thead>';
            div_data +='<tr class="header">';
            div_data +='<th style="width:1%;"><input type="checkbox" onclick="selectAllBooks(this)"/></th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">№ чит.билета</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Логин</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Фамилия</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(1)">Имя</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Отчество</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Кто создал</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Дата создания</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Кто изменил</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Дата изменения</th>';
            div_data +='<th style="width:6%;" onclick="sortTable(0)">Роль</th>';
            div_data +='<th style="width:10%;" onclick="sortTable(0)">Библиотека</th>';
            div_data +='</tr>';
            div_data +='</thead>';
            div_data +='<tbody id="tableContent">';
            for (var key in data.usersList) {
                div_data +='<tr>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '"><input class="checkboxBooks" type="checkbox" value="' + data.usersList[key].id + '" /></td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].ticketCode + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].username + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].surname + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].name + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].secondName + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].creatorName + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].createdWhen + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].updaterName + '</td>';
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].updatedWhen + '</td>';
                if (data.usersList[key].roleName === 'ROLE_ADMIN') {
                    div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Администратор библиотеки</td>';
                    $('#roleName').val('Администратор библиотеки');
                } else if (data.usersList[key].roleName === 'ROLE_LIBRARIER') {
                    div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Библиотекарь</td>';
                    $('#roleName').val('Библиотекарь');
                } else if (data.usersList[key].roleName === 'ROLE_READER') {
                    div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Читатель</td>';
                    $('#roleName').val('Читатель');
                }
                div_data +='<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].libraryName + '</td>';
                div_data +='</tr>';

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
            div_data +='</tbody>';
            div_data +='</table>';
            div_data +='<div id="light-pagination-users" class="pagination"></div>';
            div_data +='<div class="btn-group btn-group-justified" id="actionButtonGroup">';
            div_data +='<a class="btn btn-success" data-toggle="modal" data-target="#createReaderWindow">Создать пользователя</a>';
            div_data +='<a id = "deleteBooksButton" class="btn btn-danger" onClick = "checkCheckboxes(' + "'delete'" + ')" >Удалить пользователя</a>';
            div_data +='</div>';
            $('#content').html(div_data);

            if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-users').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'usersField'
                    });
                });
            }
        },
        error: function (textStatus, errorThrown) {
            $("#content").html(textStatus.responseText);
        }
    });
});

function uploadUsers(pageNumber) {
    var role_name;
    var role_id = $('#newRole').val();
    var library_id = $('#newLibrary').val();
    var url = 'api/users/getUsers/' + pageNumber;

    $.ajax({
        type: "POST",
        url: url,
        data: {'role_id': role_id, 'library_id': library_id},
        success: function (data, textStatus) {
            var div_data = '';
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
            $('#tableContent').html(div_data);

            if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-users').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'usersField'
                    });
                });
            }
        },
        error: function (jqXHR, errorThrown) {
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $('#feedback').html('Доступ запрещен!');
            } else {
                $('#feedback').html('Произошла ошибка при загрузке пользователей!: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}