$(document).ready(function () {
    getAllUsers();
});

function getAllUsers() {
    $.ajax({
        url:"/api/users",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    renderUsers(answer.message);
                }
            }
        ]
    });
}
function renderUsers(users) {
    $.each(users, function(key, value) {
        $('#users-table').append('<tr id="users-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td><a class="link-secondary" onclick="location.href = \'/users/'+ value.id +'\';">' + value.firstName + " " + value.lastName + " " + value.patronymic + '</a></td>' +
            '            <td>' + value.login + '</td>' +
            '            <td>' + value.email + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeUser(' + value.id + ')">Delete</button>' +
            '            </td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-info" onclick="location.href=\'/users/edit/' + value.id + '\'">Edit</button>' +
            '            </td>' +
            '        </tr>');
    });
}
function removeUser(id) {
    $.ajax({
        url:"/api/users/" + id,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: delete user " + id);
                console.log(answer);
                alert(answer.message);
                if (answer.response == true) {
                    $('#users-table').empty();
                    getAllUsers();
                }
            }
        ]
    });
}