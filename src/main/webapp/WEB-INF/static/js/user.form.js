const userID = getCookie("idUser");
$(document).ready(function () {
    configureTemplateByCookie();
});

function configureTemplateByCookie () {
    if (getCookie("formUser") === "edit") {
        $('#form-button-submit').text('Edit');
        $('#form-button-submit').attr('onclick', 'requestToEdit()');
        $('#form-headline').text("Edit user");
        editUser(userID);
    }
    if (getCookie("formUser") === "add") {
        $('#form-button-submit').text('Add');
        $('#form-button-submit').attr('onclick', 'requestToAdd()');
        $('#form-headline').text("Add user");
    }
}
function editUser (id) {
    $.ajax({
        url:"/api/users/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let user = answer.message;
                    console.log(user);
                    $('#form-first-name').val(user.firstName);
                    $('#form-last-name').val(user.lastName);
                    $('#form-patronymic').val(user.patronymic);
                } else {
                    alert("Some problems at server: " + answer.message);
                }
            }
        ]
    });
}
function requestToEdit() {
    let firstName = $('#form-first-name').val();
    let lastName = $('#form-last-name').val();
    let patronymic = $('#form-patronymic').val();
    let data = {id:userID, firstName: firstName, lastName: lastName, patronymic: patronymic};
    console.log("Request: edit user");
    console.log(data);
    $.ajax({
        url:"/api/users",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
        async:false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                alert(answer.message);
            }
        ]
    });
}
function requestToAdd() {
    let firstName = $('#form-first-name').val();
    let lastName = $('#form-last-name').val();
    let patronymic = $('#form-patronymic').val();
    let data = {id:0, firstName: firstName, lastName: lastName, patronymic: patronymic};
    console.log("Request: add user");
    console.log(data);
    $.ajax({
        url:"/api/users",
        type:"post",
        contentType:'application/json',
        data:JSON.stringify(data),
        async:false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                alert(answer.message);
            }
        ]
    });
}