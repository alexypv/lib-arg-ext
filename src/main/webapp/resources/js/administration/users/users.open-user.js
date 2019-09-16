$("#content").on('dblclick', "tr", function () {
    $('#userInfoField').html('');
    $("#feedback").html('');
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
            div_data += '<div id="staticUserInfo" style= "height:260px;"></div>';
            $('#userInfoField').html(div_data);
            div_data = '';
            div_data += '<div style="float:left; height: 250px; width:250px;">';
            div_data += '<img id="ItemPreview" src="' + data.userImage + '" style="border-radius: 50%;"/>'
            div_data += '</div>';

            div_data += '<div style="float:right; width:50%; margin-top: 10%;>'
            div_data += '<label for="ticketNumber">№ читательского билета</label>  ';
            div_data += '<input type="text" class="form-control" id="ticketCode" name="ticketCode" value = "' + data.userInfo.ticketCode + '" disabled></input>';

            div_data += '<label for="library">Библиотека</label>  ';
            div_data += '<input type="text" class="form-control" name="library" value = "' + data.userInfo.libraryName + '" disabled></input>';
            div_data += '</div>';


            $('#staticUserInfo').html(div_data);

            div_data = '';

            div_data += '<div id="editedUserInfo";></div>';
            $('#userInfoField').append(div_data);
            div_data = '';
            div_data += '<form name="editUserForm" method="post">';
            div_data += '<label for="username">Имя пользователя:</label>';
            div_data += '<input type="text" class="form-control" name="username"  value= "' + data.userInfo.username + '"  maxlength = "13"></input>';

            div_data += '<label for="surname">Фамилия:</label>';
            div_data += '<input type="text"class="form-control" name="surname" value = "' + data.userInfo.surname + '"></input>';

            div_data += '<label for="name">Имя</label>  ';
            div_data += '<input type="text"class="form-control" name="name" value = "' + data.userInfo.name + '"></input>';

            div_data += '<label for="secondName">Отчество:</label>';
            div_data += '<input type="text"class="form-control" name="secondname" value = "' + data.userInfo.secondName + '"></input>';

            div_data += '<label for="password">Пароль</label>  ';
            div_data += '<input type="password" class="form-control" name="password" placeholder = "Введите новый пароль"></input>';

            div_data += '<input type="hidden" name="user_id"  value = "' + data.userInfo.id + '"></input>';
            div_data += '</form>';
            $('#editedUserInfo').append(div_data);
            $('#userInfoWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $('#feedback').html('Доступ запрещен!');
            } else {
                $('#feedback').html('Произошла ошибка при загрузке информации о пользователе!: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });

    $('#editUserButton').click(function (event) {
        event.preventDefault();
        $('#confirmActionWindow').attr('onclick', 'editUser();');
        $('#confirmDescription').html('Будет изменена информация о пользователе. Требуется подтверждение.');
        $('#confirmActionWindow').modal('show');
    });

});