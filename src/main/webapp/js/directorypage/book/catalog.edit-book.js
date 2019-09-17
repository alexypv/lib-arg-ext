function editBook() {
    $.ajax({
        type: "POST",
        url: 'api/books/editBook',
        data: $("[name='editBookForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Информация о книге успешно изменена!');
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {

            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при удалении каталогов: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}