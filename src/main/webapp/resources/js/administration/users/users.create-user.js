function createUser() {
    $("#feedback").html('');
    $.ajax({
        type: "POST",
        url: 'api/users/createUser',
        data: $("[name='createUserForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Успешно! Номер чит. билета: ' + data.newTicketCode);
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedback").html('Доступ запрещен!');
            } else {
                $("#feedback").html('Произошла ошибка при создании пользователя: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}