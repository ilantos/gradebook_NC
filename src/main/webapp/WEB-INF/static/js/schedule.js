$(document).ready(function () {
    getSchedule();
});

function getSchedule() {
    let login = getCookie("username");
    $.ajax({
        url:"/api/users/schedule/" + login,
        type:"get",
        complete:[
            function (response) {
                console.log(response);
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    renderSchedule(answer.message);
                }
            }
        ]
    });
}
function renderSchedule(schedule) {
    $.each(schedule, function(key, value) {
        let subjectHref;
        let lessonHref = 'onclick="location.href = \'/lessons/' + value.lesson.id+ '\';"';
        if (value.role === 'TEACHER') {
            subjectHref = 'onclick="location.href = \'/subjects/' + value.subjectId+ '\';"';

        }
        if (value.role === 'STUDENT') {
            subjectHref = 'onclick="location.href = \'/studying/subjects/' + value.subjectId+ '\';"';
        }
        $('#schedule-table').append('<tr id="subject-' + value.id + '">' +
            '            <th>'+ dateTimeFromLocalDateTime(value.lesson.startDate) +'</th>' +
            '            <td> <a class="link-secondary" '+ subjectHref + '>' + value.subjectName + '</a></td>' +
            '            <td> <a class="link-secondary" ' + lessonHref + '>' + value.lesson.title + '</a></td>' +
            '        </tr>');
    });
}