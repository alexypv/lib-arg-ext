function getOrders(pageNumber) {
    var code = $('#ticketCode').val();
    $.ajax({
        type: "POST",
        url: 'api/orders/getHistory/' + pageNumber,
        data: {'ticketCode': code},
        success: function (data, textStatus) {
            var div_data = '';
            for (var key in data.ordersList) {
                div_data += '<tr>';
                div_data += '<td data-field="book_id">' + data.ordersList[key].id + '</td>';
                div_data += '<td data-field="book_id">' + data.ordersList[key].code + '</td>';
                div_data += '<td data-field="book_id">' + data.ordersList[key].creatorName + '</td>';
                div_data += '<td data-field="book_id">' + data.ordersList[key].startDate + '</td>';
                div_data += '<td data-field="book_id">' + data.ordersList[key].endDate + '</td>';
                div_data += '<td data-field="book_id"">' + data.ordersList[key].realReturnDate + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '" data-code="'+ data.ordersList[key].code +'">Пока нет</td>';
                div_data += '</tr>';
            }

            if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-history').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'historyField'
                    });
                });
            }
            $('#historyTableContent').html(div_data);
            $('#historyHeader').html('История читательского билета ' + code);
            $('#historyWindow').modal('show');


        },
        error: function (jqXHR, errorThrown) {
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