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
        $('#schedule-table').append('<tr id="subject-' + value.id + '">' +
            '            <th>'+ dateTimeFromLocalDateTime(value.lesson.startDate) +'</th>' +
            '            <td>' + value.subjectName + '</td>' +
            '            <td>' + value.lesson.title + '</td>' +
            '        </tr>');
    });
}