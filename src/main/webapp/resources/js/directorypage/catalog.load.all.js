function loadCatalogs() {
    $.ajax({
        type: "POST",
        url: 'api/catalogs/loadCatalogs',
        success: function (data, textStatus) {

            var div_data = '';
            div_data += '<div style="float:left">';
            div_data += '<p style="display:inline;  margin-right: 10px;"><b>Список каталогов:</b></p>';
            div_data += '<div class="btn-group" id="adminCatalogButtons">';
            div_data += '<a class="btn btn-success btn-xs" data-toggle="modal" data-target="#createCatalogWindow">Создать</a>';
            div_data += '<a class="btn btn-danger btn-xs" data-toggle="modal" data-target="#deleteCatalogWindow">Удалить</a>';
            div_data += '</div>';
            div_data += '</div>';
            div_data += '<br/>';
            div_data += '<br/>';
            div_data += '<input class="form-control" type="text" id="catalogSearchInfo" onkeyup="searchCatalog()" placeholder="Введите название каталога..." title="Поиск по каталогам">';
            div_data += '<ul id="catalogsMenu">';
            div_data += '<li><b><a data-field="catalog_id" name = "allBooks" data-value="allBooks">Все книги</a></b></li>';
            div_data += '<li><b><a data-field="catalog_id" name = "withoutCatalog" data-value="withoutCatalog">Без каталога</a></b></li>';
            for (var key in data) {
                div_data += '<li> <a  data-field="catalog_id" name = "' + data[key].name + '" data-value="' + data[key].id + '">' + data[key].name + '</a></li>';
            }
            div_data += '</ul>';
            $('#sidebar').append(div_data);
            div_data = '';

            $('.catalogsList').html(div_data);
            div_data += '<select required class="form-control " id="catalogsChoosenList" name="catalogsChoosenList">';
            div_data += '<option data-field="catalog_id" name = "catalog_id" value="withoutCatalog">-</option>';
            for (var key in data) {
                div_data += '<option data-field="catalog_id" name = "catalog_id" value="' + data[key].id + '">' + data[key].name + '</option>';
            }
            div_data += '</select>';
            $('.catalogsList').append(div_data);
            div_data = '';

            div_data += '<input type="hidden" id="choosenBooks" name="choosenBooks" value=""/>';
            $('#moveBooksForm').append(div_data);

        },
        error: function (textStatus, errorThrown) {
        }
    });
}