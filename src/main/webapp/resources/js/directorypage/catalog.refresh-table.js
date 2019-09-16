function refreshTable(data) {
    var div_data = '';
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
    $('#tableContent').html('');
    $('#tableContent').html(div_data);
}