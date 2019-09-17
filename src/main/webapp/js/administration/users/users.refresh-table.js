function refreshTable(data) {
    var div_data = '';
        for (var key in data.usersList) {
                div_data += '<tr>';
                div_data += '<td><input class="checkboxBooks" type="checkbox" value="' + data.usersList[key].id + '" /></td>';
                div_data += '<td>' + data.usersList[key].ticketCode + '</td>';
                div_data += '<td>' + data.usersList[key].username + '</td>';
                div_data += '<td>' + data.usersList[key].surname + '</td>';
                div_data += '<td>' + data.usersList[key].name + '</td>';
                div_data += '<td>' + data.usersList[key].secondName + '</td>';
                div_data += '<td>' + data.usersList[key].creatorName + '</td>';
                div_data += '<td>' + data.usersList[key].createdWhen + '</td>';
                div_data += '<td>' + data.usersList[key].updaterName + '</td>';
                div_data += '<td>' + data.usersList[key].updatedWhen + '</td>';
                
				if (data.usersList[key].roleName === 'ROLE_ADMIN') {
                    div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Администратор библиотеки</td>';
                    $('#roleName').val('Администратор библиотеки');
					$('#contentDescription').html('Список пользователей с ролью "Администратор библиотеки"');
                } else if (data.usersList[key].roleName === 'ROLE_LIBRARIER') {
                    div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Библиотекарь</td>';
                    $('#roleName').val('Библиотекарь');
					$('#contentDescription').html('Список пользователей с ролью "Библиотекарь"');
                } else if (data.usersList[key].roleName === 'ROLE_READER') {
                    div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">Читатель</td>';
                    $('#roleName').val('Читатель');
					$('#contentDescription').html('Список пользователей с ролью "Читатель"');
                }
                div_data += '<td data-field="book_id" data-value= "' + data.usersList[key].id + '">' + data.usersList[key].libraryName + '</td>';
                div_data += '</tr>';
            }
    $('#tableContent').html('');
    $('#tableContent').html(div_data);
}