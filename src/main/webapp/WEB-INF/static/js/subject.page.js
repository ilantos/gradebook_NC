const id = getCookie("idSubject");
$(document).ready(function () {
    subjectPage(id);
    parentLocationsChain();
});

function subjectPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/subject/" + id,
        type:"get",
        async: false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let subject = answer.message;
                    $('#subject-page-title').text(subject.title);
                    $('#subject-page-description').text(subject.description);
                    renderLessons(subject.lessons);
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}
function renderLessons(lessons) {
    $.each(lessons, function(key, value) {
        $('#lessons-table').append('<tr id="lesson-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td><a class="link-secondary" onclick="location.href = \'/lessons/'+ value.id +'\';">' + value.title + '</a></td>' +
            '            <td>' + value.description + '</td>' +
            '            <td>' + value.maxGrade + '</td>'+
            '            <td>' + value.creationDate.year + '.' +  value.creationDate.monthValue + '.' + value.creationDate.dayOfMonth + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeLesson(' + value.id + ')">Delete</button>' +
            '            </td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-info" onclick="location.href=\'/lessons/edit/' + value.id + '\'">Edit</button>' +
            '            </td>' +
            '        </tr>')


    })
}
function removeLesson(id) {
    $.ajax({
        url:"/api/lessons/" + id,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    alert(answer.message);
                } else {
                    alert("Some problems at server");
                }
                window.location.href = "/subjects/" + getCookie("idSubject");
            }
        ]
    });
}
