function getOrderInfo(pageNumber, orderID) {
    $.ajax({
        type: "POST",
        url: 'api/books/getBooksInOrder/' + pageNumber,
        data: {'orderID': orderID},
        success: function (data, textStatus) {
            var div_data = '';
            for (var key in data.booksList) {
                div_data += '<tr>';
                div_data += '<td data-field="book_id">' + data.booksList[key].isbnNumber + '</td>';
                div_data += '<td data-field="book_id">' + data.booksList[key].name + '</td>';
                div_data += '<td data-field="book_id">' + data.booksList[key].author + '</td>';
                div_data += '<td data-field="book_id">' + data.booksList[key].publishingName + '</td>';
                div_data += '<td data-field="book_id">' + data.booksList[key].yearPublishing + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.booksList[key].id + '">' + data.booksList[key].catalogName + '</td>';
                div_data += '</tr>';
            }
			 $('#orderInfoContent').html(div_data);
           
		   if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-orderInfo').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'orderInfoField'
                    });
                });
            }

            $('#orderInfoHeader').html('Информация о заказе ' + $('#currentOrderCode').val());
            $('#orderInfoWindow').modal('show');


        },
        error: function (jqXHR, errorThrown) {
            $('#historyWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            $("#feedbackDescription").html('');
            if (jqXHR.status == 403) {
                $('#feedbackDescription').html('Доступ запрещен!');
            } else {
                $('#feedbackDescription').html('Произошла ошибка при создании заказа: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}