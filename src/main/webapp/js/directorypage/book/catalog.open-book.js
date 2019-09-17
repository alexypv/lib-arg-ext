$("#content").on('dblclick', "tr", function () {
    $('#bookInfoField').html('');
    var book_id;
    $(this).find("td")
        .each(function () {
            book_id = $(this).data('value');
        });
    $.ajax({
        type: "POST",
        url: 'api/books/openBook/' + book_id,
        success: function (data, textStatus) {
            var div_data = '';
            div_data += '<form name="editBookForm" method="post">';

            div_data += '<label for="newISBNBookNumber">ISBN:</label>';
            div_data += '<input type="text" class="form-control" onkeypress="validateNumericField(event)" name="newISBNBookNumber" placeholder = "' + data.isbnNumber + '"  maxlength = "13"></input>';
            div_data += '<label for="newBookAuthor">Автор:</label>  ';
            div_data += '<input type="text"class="form-control" name="newAuthorName" placeholder = "' + data.author + '"></input>';
            div_data += '<label for="newBookName">Название книги</label>  ';
            div_data += '<input type="text"class="form-control" name="newBookName" placeholder = "' + data.name + '"></input>';
            div_data += '<label for="newPublishingName">Издательство:</label>';
            div_data += '<input type="text"class="form-control" name="newPublishingName" placeholder = "' + data.publishingName + '"></input>';
            div_data += '<label for="newYearPublishing">Год издания</label>  ';
            div_data += '<input type="text" class="form-control" onkeypress="validateNumericField(event)" name="newYearPublishing" placeholder = "' + data.yearPublishing + '" maxlength="4"></input>';
            div_data += '<input type="hidden" name="book_id"  value = "' + data.id + '"></input>';
            div_data += '</form>';
            div_data += '</br>';

            $('#bookInfoField').append(div_data);
            $('#bookInfoWindow').modal('show');
        },
        error: function (textStatus, errorThrown) {
        }
    });

    $('#editBookButton').click(function (event) {
        event.preventDefault();
        $('#confirmActionWindow').attr('onclick', 'editBook();');
        $('#confirmDescription').html('Будет изменена информация о книге. Требуется подтверждение.');
        $('#confirmActionWindow').modal('show');
    });

});

