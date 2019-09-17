function createCatalog() {
    $.ajax({
        type: "POST",
        url: 'api/catalogs/createCatalog',
        data: $("[name='createCatalogForm']").serialize(),
        success: function (data, textStatus) {
            $('#sidebar').html('');
            $('#createCatalogWindow').modal('hide');
            $('#resultAction').html('Успешно!')
            $("#feedbackDescription").html('Каталог успешно создан!');
            $('#resultWindow').modal('show');
            loadCatalogs();
        },
        error: function (jqXHR, errorThrown) {
            $('#createCatalogWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $("#feedbackDescription").html('Доступ запрещен!');
            } else {
                $("#feedbackDescription").html('Произошла ошибка при создании каталога: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}