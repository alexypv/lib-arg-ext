$(document).ready(function () {
    $("#saveInfoButton").click(function (event) {
        event.preventDefault();
        $('#confirmActionButton').attr('onclick', 'ajaxChangeInfo();');
        $('#confirmDescription').html('Информация о пользователе будет изменена');
        $('#confirmActionWindow').modal('show');
    });
});

function ajaxChangeInfo() {
    var url = '/lib-agr/api/users/changeUserInfo/false';
    $.ajax({
        url: url,
        data: $("[name='userInfoForm']").serialize(),
        type: 'POST',
        success: function (data, textStatus) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!')
            $("#feedbackDescription").html("Информация о пользователе успешно изменена!");
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Ошибка!')
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка поиска чит. билета: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}