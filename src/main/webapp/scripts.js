$(document).ready(getList);
getCategories();

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
        let author = data[i]['user'].name;
        let categories = data[i]['categories'];
        let cats = '';
        for (let j = 0; j < categories.length; j++) {
            cats += categories[j]['name'] + '\n';
        }
        $('#table tr:last').after(
            '<tr>' +
            '<td>' + '<input type="checkbox" id="' + id + '" onchange="itemDone(id)">' + '</td>' +
            '<td>' + description + '</td>' +
            '<td>' + created + '</td>' +
            '<td>' + author + '</td>' +
            '<td>' + cats + '</td>' +
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
    let cats = $('#cIds').val();
    let categories = '';
    for (let i = 0; i < cats.length; i++) {
        categories += cats[i] + ',';
    }
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/job4j_todo_war_exploded/item.do",
        dataType: "json",
        data: {
            description: $('#add_item').val(),
            categories: categories
        }
    });
    getList();
}

function getUserName() {
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/job4j_todo_war_exploded/index.do",
        dataType: "json"
    }).done(function (user) {
        document.getElementById('username').innerHTML = user.name;
    }).fail(function () {
        console.log('error username')
    });
}

function getCategories() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/job4j_todo_war_exploded/category.do",
        dataType: "json"
    }).done(function (categories) {
        let catHtml = '';
        for (let i = 0; i < categories.length; i++) {
            let cat = categories[i]['name'];
            let catId = categories[i]['id'];
            catHtml += '<option value="' + catId + '">' + cat + '</option>';
        }
        $('#cIds').html(catHtml);
    }).fail(function () {
        console.log("Error categories")
    });
}
