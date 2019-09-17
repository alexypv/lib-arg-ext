function createOrder() {
	$('#orderBooks').val(getCheckedBooksInOrderForm());
    $.ajax({
        type: "POST",
        url: 'api/orders/createOrder',
		data: $("[name='giveOrderForm']").serialize(),
		success: function (data, textStatus) {
			$('#createOrderWindow').modal('hide');
            $('#resultAction').html('Успешно!');
            $('#feedback').html('Заказ № <b>' + data + ' </b>был успешно создан!');
            $('#resultWindow').modal('show');
        },
		
        error: function (jqXHR, errorThrown) {
            $('#createOrderWindow').modal('hide');
            $('#resultAction').html('Ошибка!');
            if (jqXHR.status == 403) {
                $('#feedback').html('Доступ запрещен!');
            } else {
                $('#feedback').html('Произошла ошибка при создании заказа: ' + jqXHR.responseText);
            }
            $('#resultWindow').modal('show');
        }
    });
}