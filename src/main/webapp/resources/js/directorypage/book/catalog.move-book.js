function moveBooks() {
    $('#choosenBooks').val($('#selectedBooks').val());
    $.ajax({
        type: "POST",
        url: 'api/books/moveBook',
        data: $("[name='moveBooksForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#moveBookWindow').modal('hide');
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Выбранные книги были успешно перемещны в выбранный каталог!');
            $('#resultWindow').modal('show');
            $('#selectedBooks').val('g');
        },
        error: function (jqXHR, errorThrown) {
            $('#moveBookWindow').modal('hide');
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при перемещении книг: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });

}