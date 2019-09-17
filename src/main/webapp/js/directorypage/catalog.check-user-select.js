function checkCheckboxes(info) {

    var arr = $('#selectedBooks').val().split(',');
    if ($('#selectedBooks').val() === '') {
        delete arr[0];
    }
    arr = arr.filter(function () {
        return true
    });
    arr = Array.from(new Set(arr));

    if (arr.length == 0) {
        var x = document.getElementById("snackbar");
        x.className = "show";
        $('#snackbar').html('Выберите хотя бы одну книгу!');
        setTimeout(function () {
            x.className = x.className.replace("show", "");
        }, 3000);
    } else {
        if (info === 'move') {
            $('#moveBookWindow').modal('show');
        } else if (info === 'deleteFromCatalog') {
            $('#confirmAnswer').html('Выбранные книги будут удалены из выбранного каталога и помещены в каталог "Без каталога". Подтвердите действие');
            $('#confirmActionButton').attr('onclick', 'deleteBooksFromCatalog()');
            $('#confirmActionWindow').modal('show');
        } else if (info === 'delete') {
            $('#confirmAnswer').html('Выбранные книги будут удалены. Удаление необратимо. Подтвердите действие');
            $('#confirmActionButton').attr('onclick', 'deleteBooks()');
            $('#confirmActionWindow').modal('show');
        } else if (info === 'createOrder') {
            $.ajax({
                type: "POST",
                url: 'api/books/getBooksForCreateOrder',
                data: {"selectedBooks": $('#selectedBooks').val()},
                success: function (data, textStatus) {
                    var div_data = '';
                    for (var key in data.booksList) {
                        div_data += '<tr>';
                        div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '"><input class="checkboxBooksInOrder" type="checkbox" value="' + data.booksList[key].id + '" /></td>';
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
                    $('#ordersTableContent').html(div_data);
				
					div_data = '';
					for (var key in data.readersList) {
					div_data += '<option data-field="reader_id" name="reader_id" value="' + data.readersList[key].id + '">' + data.readersList[key].ticketCode + '</option>';
					}
					$('#readersList').html(div_data);
					div_data = '';
                    $('#createOrderWindow').modal('show');
                },

                error: function (textStatus, errorThrown) {
                }
            });

        }
    }
} 