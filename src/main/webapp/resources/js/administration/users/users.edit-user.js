function editUser() {
    $.ajax({
        type: "POST",
        url: 'api/users/changeUserInfo/true',
        data: $("[name='editUserForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Информация о пользователе успешно изменена!');
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {

            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при изменении информации о пользователе: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}