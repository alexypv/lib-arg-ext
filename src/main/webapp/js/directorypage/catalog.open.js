$("#sidebar").on('dblclick', "li", function () {
    document.getElementById("content").innerHTML = "";
    var catalog_name;
    var catalog_id;
    var arr = $('#selectedBooks').val().split(',');
    if ($('#selectedBooks').val() === '') {
        delete arr[0];
    }
    arr = arr.filter(function () {
        return true
    });
    arr = Array.from(new Set(arr));
    $('#choosenBooksCount').html('Отмечено книг (' + arr.length + ')');

    var url = "api/books/openCatalog/";
    $(this).find("a")
        .each(function () {
            $(this).attr("selected", true);
            catalog_id = $(this).data('value');
            url += catalog_id + "/1";
            catalog_name = $(this).attr("name");
        });
    $.ajax({
        type: "POST",
        url: url,
        success: function (data, textStatus) {
            var div_data = '';
            div_data += '<h3>Список книг в каталоге «' + catalog_name + '»‎</h3>';
            div_data += '<input type="hidden" class="catalog_id" id="catalog_id" name="catalog_id" value=""/>';
            div_data += ' <input type="text" style= "display:inline; width:550px" name="bookSearch" id="bookSearch" onkeyup="findBook();" class="form-control" placeholder="Введите любую информацию о книге..."/>';
            div_data += '<div style="float:right">';
            div_data += '<a style="display:inline; margin-right: 10px;" id="choosenBooksCount">Отмечено книг (0)</a>';
            div_data += '<a class="btn btn-success" onClick = "checkCheckboxes(' + "'createOrder'" + ')" style="display:inline">Сформировать заказ</a>';
            div_data += '</div>';
            div_data += '<br/>';
            div_data += '<br/>';
            div_data += '<table id="tableBooksInCatalog" class="table table-bordered table-sm">';
            div_data += '<thead>';
            div_data += '<tr class="header">';
            div_data += '<th style="width:1%;"><input type="checkbox" onclick="selectAllBooks(this)"/></th>';
            div_data += '<th style="width:3%;" onclick="sortTable(0)">ID</th>';
            div_data += '<th style="width:5%;" onclick="sortTable(0)">ISBN</th>';
            div_data += '<th style="width:10%;" onclick="sortTable(1)">Автор</th>';
            div_data += '<th style="width:20%;" onclick="sortTable(0)">Название</th>';
            div_data += '<th style="width:10%;" onclick="sortTable(0)">Издательство</th>';
            div_data += '<th style="width:5%;" onclick="sortTable(0)">Год издания</th>';
            div_data += '<th style="width:5%;" onclick="sortTable(0)">Каталог</th>';
            div_data += '<th style="width:10%;" onclick="sortTable(0)">Дата создания</th>';
            div_data += '<th style="width:5%;" onclick="sortTable(0)">Создатель</th>';
            div_data += '<th style="width:10%;" onclick="sortTable(0)">Последнее изменение</th>';
            div_data += '<th style="width:5%;" onclick="sortTable(0)">Кто изменил</th>';
            div_data += '</tr>';
            div_data += '</thead>';

            div_data += '<tbody id="tableContent">';
            for (var key in data.booksList) {
                div_data += '<tr>';
                if (data.booksList[key].available === false) {
                    div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '"><input class="checkboxBooks" type="checkbox" value="' + data.booksList[key].id + '" disabled /></td>';
                } else {
                    div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '"><input class="checkboxBooks" type="checkbox" value="' + data.booksList[key].id + '" /></td>';
                }
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].id + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].isbnNumber + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].author + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].name + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].publishingName + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].yearPublishing + '</td>';
                if (data.booksList[key].catalogEntity === null) {
                    div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + "Без каталога" + '</td>';
                } else {
                    div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].catalogName + '</td>'
                }
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].createdWhen + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].creatorName + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].updatedWhen + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].updaterName + '</td>';
                div_data += '</tr>';
            }
            div_data += '</tbody>';
            div_data += '</table>';
            div_data += '<div id="light-pagination" class="pagination"></div>';
            div_data += '<input type="hidden" id="currentCatalog" name="currentCatalog" value="' + catalog_id + '"/>';
            div_data += '<div class="btn-group btn-group-justified" id="actionButtonGroup">';
            div_data += '<a class="btn btn-success" data-toggle="modal" data-target="#createBookWindow">Создать книгу</a>';
            div_data += '<a class="btn btn-primary" onClick = "checkCheckboxes(' + "'move'" + ')">Переместить в другой каталог</a>';
            div_data += '<a id="deleteFromCatalogButton" class="btn btn-warning" onClick = "checkCheckboxes(' + "'deleteFromCatalog'" + ')">Удалить из каталога</a>';
            div_data += '<a id = "deleteBooksButton" class="btn btn-danger" onClick = "checkCheckboxes(' + "'delete'" + ')" >Удалить книгу</a>';
            div_data += '</div>';
            $('#content').html(div_data);

            $(function () {
                $('#light-pagination').pagination({
                    items: parseInt(data.countPage),
                    itemsOnPage: 1,
                    cssStyle: 'light-theme'
                });
            });

            $('.catalog_id').val(catalog_id);
        },
        error: function (textStatus, errorThrown) {
            $("#content").html(textStatus.responseText);
        }
    });
});