function deleteBooks() {
    $("#feedback").html('');
    var div_data = '';
    div_data += '<form name="choosenBooksForDeleteForm">';
    div_data += '<input type="hidden" id="choosenBooksForDelete" name="choosenBooksForDelete" value=""/>';
    div_data += '</form">';
    $('#confirmContent').html(div_data);
    $('#choosenBooksForDelete').val($('#selectedBooks').val());
    $.ajax({
        type: "POST",
        url: 'api/books/deleteBook',
        data: $("[name='choosenBooksForDeleteForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedbackDescription').html('Выбранные книги были успешно удалены!');
            $('#resultWindow').modal('show');
            $('#selectedBooks').val('');

        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedback").html('Доступ запрещен!');
            } else {
                $("#feedback").html('Произошла ошибка при удалении книг: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}