$(document).ready(function () {
    $("#deletePhotoButton").click(function (event) {
        event.preventDefault();
        $('#confirmActionButton').attr('onclick', 'ajaxDeletePhoto();');
        $('#confirmDescription').html('Фотография профиля будет удалена');
        $('#confirmActionWindow').modal('show');
    });
});

function ajaxDeletePhoto() {
    var url = '/lib-agr/api/users/deleteImage';
    $.ajax({
        url: url,
        type: 'POST',
        success: function (data, textStatus) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!')
            $("#feedbackDescription").html("Информация о пользователе успешно изменена!");
            $('#resultWindow').modal('show');
            $('#ItemPreview').attr('src', 'resources/img/icon/noimage.png');
        },
        error: function (textStatus, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Произошла ошибка!')
            $("#feedbackDescription").html("Произошла ошибка при удалении фотографии!");
            $('#resultWindow').modal('show');
        }
    });
}