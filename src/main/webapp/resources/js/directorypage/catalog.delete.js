function deleteCatalog() {
    $.ajax({
        type: "POST",
        url: 'api/catalogs/deleteCatalog',
        data: $("[name='deleteCatalogForm']").serialize(),
        success: function (data, textStatus) {
            $('#sidebar').html('');
            $('#deleteCatalogWindow').modal('hide');
            $('#resultAction').html('Успешно!')
            $("#feedbackDescription").html('Выбранные каталоги успешно удалены!');
            $('#resultWindow').modal('show');
            loadCatalogs();
        },
        error: function (jqXHR, errorThrown) {
            $('#deleteCatalogWindow').modal('hide');
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