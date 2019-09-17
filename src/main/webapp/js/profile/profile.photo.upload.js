$(document).ready(function () {
    $("#uploadPhotoButton").click(function (event) {
        event.preventDefault();
        $('#confirmActionButton').attr('onclick', 'ajaxUploadPhoto();');
        $('#confirmDescription').html('Будет загружена/обновлена фотография профиля');
        $('#confirmActionWindow').modal('show');
    });
});

function ajaxUploadPhoto() {
    var dataUrl = $('#croppedPhoto').attr('src');
    var url = '/lib-agr/api/users/uploadImage';
    $.ajax({
        url: url,
        data: {imageBase64: dataUrl},
        type: 'POST',
        dataType: 'application/image;base64',
        success: function (data, textStatus) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!')
            $("#feedbackDescription").html("Фото успешно обновлено!");
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Произошла ошибка!')
            $("#feedbackDescription").html("Произошла ошибка при загрузке/удалении фотографии! Код: " + jqXHR.status );
            $('#resultWindow').modal('show');
        }
    });
}