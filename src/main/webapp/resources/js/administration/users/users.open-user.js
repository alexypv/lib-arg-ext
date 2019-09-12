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