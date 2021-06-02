const subjectId = getCookie("idSubject");
$(document).ready(function () {
    subjectPage(subjectId);
});

function subjectPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/subjects/" + id + "/studying",
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
            '            <td>' + value.grade + '</td>'+
            '            <td>' + value.creationDate.year + '.' +  value.creationDate.monthValue + '.' + value.creationDate.dayOfMonth + '</td>' +
            '        </tr>')


    })
}
