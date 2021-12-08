function getList() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_todo_war_exploded/item.do",
        dataType: "json",
        data: {
            show_all: $('#show_all').is(':checked')
        }
    }).done(function (data) {
        showAllItems(data);
    }).fail(function () {
        console.log('error into getList');
    });
}

function showAllItems(data) {
    $('#table tbody > tr').html('');
    for (let i = 0; i < data.length; i++) {
        let id = data[i]['id'];
        let description = data[i]['description'];
        let created = data[i]['created'];
        let done = data[i]['done'];
        $('#table tr:last').after(
            '<tr>' +
            '<td>' + '<input type="checkbox" id="' + id + '" onchange="itemDone(id)">' + '</td>' +
            '<td>' + description + '</td>' +
            '<td>' + created + '</td>' +
            '</tr>');
        if (done) {
            document.getElementById(id).setAttribute('checked', 'true');
        }
    }
    console.log('take list done');
}

function itemDone(id) {
    console.log('checked=' + id);
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_todo_war_exploded/update.do",
        dataType: "json",
        data: {
            id: id
        }
    });
    setTimeout(getList, 1000);
}

function addItem() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/job4j_todo_war_exploded/item.do",
        dataType: "json",
        data: {
            description: $('#add_item').val()
        }
    });
    getList();
}