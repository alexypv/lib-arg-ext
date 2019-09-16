function deleteBooksFromCatalog() {
    var div_data = '';
    div_data += '<form name="deleteBooksFromCatalogForm">';
    div_data += '<input type="hidden" id="choosenBooksForDeleteInCatalog" name="choosenBooksForDeleteInCatalog" value=""/>';
    div_data += '</form">';
    $('#confirmContent').html(div_data);
    $('#choosenBooksForDeleteInCatalog').val($('#selectedBooks').val());
    $.ajax({
        type: "POST",
        url: 'api/books/deleteBookFromCatalog',
        data: $("[name='deleteBooksFromCatalogForm']").serialize(),
        success: function (data, textStatus) {
            refreshTable(data);
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Выбранные книги были успешно удалены из каталога!');
            $('#resultWindow').modal('show');

        },
        error: function (jqXHR, errorThrown) {
            $('#confirmActionWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при удалении книг из каталога: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}