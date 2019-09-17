function searchHistory(pageNumber) {
    var code = $('#ticketCode').val();
    var searchValue = $('#searchHistoryField').val();
    $.ajax({
        type: "POST",
        url: 'api/search/findOrder/' + pageNumber,
        data: {'ticketCode': code, 'searchValue': searchValue},
        success: function (data, textStatus) {
            var div_data = '';
            for (var key in data.ordersList) {
                div_data += '<tr>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].id + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].code + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].creatorName + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].startDate + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].endDate + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">' + data.ordersList[key].realReturnDate + '</td>';
                div_data += '<td data-field="book_id" data-value= "' + data.ordersList[key].id + '">Пока нет</td>';
                div_data += '</tr>';
            }

            if (pageNumber === 1) {
                var countPages = data.countPages;
                $(function () {
                    $('#light-pagination-history').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme'
                    });
                });
            }

            $('#historyTableContent').html('');
            $('#historyTableContent').html(div_data);


        },
        error: function (jqXHR, errorThrown) {
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $('#feedback').html('Доступ запрещен!');
            } else {
                $('#feedback').html('Произошла ошибка при поиске заказа: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}