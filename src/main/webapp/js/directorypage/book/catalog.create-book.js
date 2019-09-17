function createBook() {
    $('#tableContent').val('createBook');
    $.ajax({
        type: "POST",
        url: 'api/books/createBook',
        data: $("[name='createBookForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Книга была успешно создана!');
            $('#resultWindow').modal('show');
        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#bookInfoWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при создании книги: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}