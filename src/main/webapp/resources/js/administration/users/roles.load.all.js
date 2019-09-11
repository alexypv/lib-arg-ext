function loadRoles() {
    $.ajax({
        type: "POST",
        url: 'api/roles/getRoles',
        success: function (data, textStatus) {

            var div_data = '';
            div_data += '<div style="float:left">';
            div_data += '<p style="display:inline;  margin-right: 10px;"><b>Выберите тип пользователя:</b></p>';
            div_data += '</div>';
            div_data += '<br/>';
            div_data += '<br/>';
            div_data += '<input class="form-control" type="text" id="catalogSearchInfo" onkeyup="searchCatalog()" placeholder="Введите название каталога..." title="Поиск по каталогам">';
            div_data += '<ul id="catalogsMenu">';
            for (var key in data.rolesList) {
			if(data.rolesList[key].name === 'ROLE_ADMIN') {
                div_data += '<li> <a  data-field="role_id" name = "' + data.rolesList[key].name + '" data-value="' + data.rolesList[key].id + '"><b>Администратор библиотеки</b></a></li>';
            } else 
				if(data.rolesList[key].name === 'ROLE_LIBRARIER') {
                div_data += '<li> <a  data-field="role_id" name = "' + data.rolesList[key].name + '" data-value="' + data.rolesList[key].id + '"><b>Библиотекарь</b></a></li>';
			} else 
			if(data.rolesList[key].name === 'ROLE_READER') {
                div_data += '<li> <a  data-field="role_id" name = "' + data.rolesList[key].name + '" data-value="' + data.rolesList[key].id + '"><b>Читатель</b></a></li>';
			}
			}
            div_data += '</ul>';
            $('#sidebar').append(div_data);  
        },
        error: function (textStatus, errorThrown) {
        }
    });
}