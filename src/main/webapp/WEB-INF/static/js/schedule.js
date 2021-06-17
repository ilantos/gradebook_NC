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
        let lessonHref = 'onclick="location.href = \'/lessons/' + value.lesson.id+ '\';"';
        $('#schedule-table').append('<tr id="subject-' + value.id + '">' +
            '            <th>'+ dateTimeFromLocalDateTime(value.lesson.startDate) +'</th>' +
            '            <td> <a class="link-secondary" onclick="renderOnClickOfSubject(' + value.subjectId + ', \'' + value.role+ '\')">' + value.subjectName + '</a></td>' +
            '            <td> <a class="link-secondary" ' + lessonHref + '>' + value.lesson.title + '</a></td>' +
            '        </tr>');
    });
}

function renderOnClickOfSubject(id, role) {
    if (role === 'TEACHER') {
        localStorage.setItem('activeNavItem', 'nav-item-teaching');
        location.href = '/subjects/' + id;
    }
    if (role === 'STUDENT') {
        localStorage.setItem('activeNavItem', 'nav-item-studying');
        location.href = '/studying/subjects/' + id;
    }
}