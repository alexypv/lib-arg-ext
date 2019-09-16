function getOrders(pageNumber) {
    var code = $('#ticketCode').val();
    $.ajax({
        type: "POST",
        url: 'api/orders/getOrders/' + pageNumber,
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
                    $('#light-pagination-orders').pagination({
                        items: parseInt(countPages),
                        itemsOnPage: 1,
                        cssStyle: 'light-theme',
                        fieldName: 'ordersField'
                    });
                });
            }
            $('#tableContent').html(div_data);
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

function loadOrders() {
    document.getElementById("content").innerHTML = "";
    var pageNumber = 1;

    var div_data = '';
    div_data +='<h3 id="contentDescription">‎</h3>';
    div_data +='<div style="width: 100%; float: left; margin-bottom: 10px;">';
    div_data +='<input id="searchOrderField" type="text" class="form-control" placeholder="Введите номер заказа"  style="float: left; width: 95%;">';
    div_data +='<div class="input-group-append">';
    div_data +='<button class="btn btn-success" type="submit"  style="float: left; width: 5%;" onclick="searchOrder(1)">&#128270;</button>';
    div_data +='</div>';
    div_data +='</div>';
    div_data +='<div style="float:right">';
    div_data +='</div>';
    div_data +='<br/>';
    div_data +='<br/>';
    div_data +='<table id="tableOrders" class="table table-bordered table-sm">';
    div_data +='<thead>';
    div_data +='<tr class="header">';
    div_data +='<th style="width:5%;" onclick="sortTable(0)">ID</th>';
    div_data +=' <th style="width:10%;" onclick="sortTable(0)">Код</th>';
    div_data +='  <th style="width:10%;" onclick="sortTable(1)">Кто выдал</th>';
    div_data +=' <th style="width:20%;" onclick="sortTable(0)">Дата выдачи</th>';
    div_data +=' <th style="width:20%;" onclick="sortTable(0)">Вернуть до</th>';
    div_data +='  <th style="width:20%;" onclick="sortTable(0)">Дата возврата</th>';
    div_data +='   <th style="width:10%;" onclick="sortTable(0)">Возврат принял</th>';
    div_data +='</tr>';
    div_data +='</thead>';
    div_data +='<tbody id="tableContent">';
    div_data +='</tbody>';
    div_data +='</table>';
    div_data +='<div id="light-pagination-orders" class="pagination"></div>';
    div_data +='<div class="btn-group btn-group-justified" id="actionButtonGroup">';
    div_data +='<a class="btn btn-success" data-toggle="modal" data-target="#createReaderWindow">Создать пользователя</a>';
    div_data +='<a id = "deleteBooksButton" class="btn btn-danger" onClick = "checkCheckboxes(' + "'delete'" + ')" >Удалить пользователя</a>';
    div_data +='</div>';
    $('#content').html(div_data);
    getOrders(pageNumber);
}
