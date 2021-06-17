let subjectId;
$(document).ready(function () {
    subjectId = getCookie("subject_id");
    getAllStudents();
});

function getAllStudents() {
    $.ajax({
        url:"/api/students?subject_id=" + subjectId,
        type:"get",
        complete:[
            function (response) {
                console.log(response);
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    renderStudents(answer.message);
                }
            }
        ]
    });
}
function renderStudents(students) {
    $.each(students, function(key, value) {
        $('#students-table').append('<tr id="person-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td>' + value.firstName + " " + value.lastName + " " + value.patronymic + '</td>' +
            '            <td>' + value.login + '</td>' +
            '            <td>' + value.email + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeStudent(' + value.id + ')">Delete</button>' +
            '            </td>' +
            '        </tr>');
    });
}
function removeStudent(studentId) {
    $.ajax({
        url:"/api/students/" + studentId + "?subject_id=" + subjectId,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: delete student " + id);
                console.log(answer);
                alert(answer.message);
                if (answer.response == true) {
                    $('#students-table').empty();
                    getAllStudents();
                }
            }
        ]
    });
}
