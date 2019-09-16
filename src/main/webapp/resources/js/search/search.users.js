function searchUser(pageNumber) {
    var searchValue = $('#searchUserField').val();
    var libraryId = $('#newLibrary').val();
    var roleId = $('#newRole').val();
    $.ajax({
        type: "POST",
        url: 'api/search/findUser/' + pageNumber,
        data: {'searchValue': searchValue, 'libraryId': libraryId, 'roleId': roleId},
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

            if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-users').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'usersBeforeSearchField'
                    });
                });
            }
            $('#tableContent').html('');
            $('#tableContent').html(div_data);

        },
        error: function (jqXHR, errorThrown) {
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $('#feedback').html('Доступ запрещен!');
            } else {
                $('#feedback').html('Произошла ошибка при поиске пользователей: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}