$(document).ready(function () {
    let id = getCookie("idLesson");
    getGrades(id);
});

function getGrades(id) {
    $.ajax({
        url:'/api/lessons/' + id + '/grades',
        type:'get',
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    renderGrades(answer.message);
                }
            }
        ]
    });
}

function renderGrades(grades) {
    $.each(grades, function(key, value) {
        $('#grades-table').append('<tr id="student-' + value.student.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td>' +  value.student.firstName + ' ' + value.student.lastName + '</td>' +
            '            <td id="student-grade-' + value.student.id + '">' + value.grade + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeGrade(' + value.student.id + ')">Delete</button>' +
            '            </td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-info" onclick="editGrade(' + value.student.id + ')">Edit</button>' +
            '            </td>' +
            '        </tr>');
    });
}

function removeGrade(id) {
    editGradeRequest(id, 0);
}

function editGrade(id) {
    let grade = Number.parseFloat($('#student-grade-' + id).text());
    let result = Number.parseFloat(prompt("Edit grade of lesson", grade));
    let maxGrade = Number.parseFloat(localStorage.getItem('maxGrade'));
    if (isNaN(result)) {
        alert("Grade isn't a number");
        return;
    }
    if (result > maxGrade) {
        alert("Grade higher than max grade!");
        return;
    }
    if (result < 0) {
        alert("Grade cannot be < 0!");
        return;
    }
    editGradeRequest(id, result);
}

function editGradeRequest(idPerson, grade) {
    let idLesson = Number.parseInt(getCookie('idLesson'));
    let data = {idLesson:idLesson, idPerson:idPerson, grade:grade};
    console.log('Request to edit: ' + JSON.stringify(data));
    $.ajax({
        url:"/api/lessons/grades",
        type:"put",
        contentType:'application/json',
        data:JSON.stringify(data),
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                alert(answer.message);
                if (answer.response === true) {
                    $('#student-grade-' + idPerson).text(grade);
                }
            }
        ]
    });
}