$(document).ready(function () {
    getAllSubjects();
});

function getAllSubjects() {
    let login = getCookie("username");
    $.ajax({
        url:"/api/subjects/studying/" + login,
        type:"get",
        complete:[
            function (response) {
                console.log(response);
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    renderSubject(answer.message);
                }
            }
        ]
    });
}
function renderSubject(subjects) {
    if (subjects.size === 0) {
        alert("0 subjects");
        return;
    }
    $.each(subjects, function(key, value) {
        $('#subjects-table').append('<tr id="subject-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td><a class="link-secondary" onclick="location.href = \'/studying/subjects/'+ value.id +'\';">' + value.title + '</a></td>' +
            '            <td>' + value.description + '</td>' +
            '        </tr>');
    });
}

function subjectPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/subjects/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let subject = answer.message;
                    $('#subject-page-title').text(subject.title) ;
                    $('#subject-page-description').text(subject.description);
                    $.each(subject.lessons, function(key, value) {
                        $('#lessons-table').append('<tr id="lesson-' + value.id + '">' +
                            '            <th scope="row">'+ (key + 1) +'</th>' +
                            '            <td><a class="link-secondary" onclick="location.href = \'/lessons/'+ value.id +'\';">' + value.title + '</a></td>' +
                            '            <td>' + value.description + '</td>' +
                            '            <td>' + value.maxGrade + '</td>'+
                            '            <td>' + value.creationDate.year + '.' +  value.creationDate.monthValue + '.' + value.creationDate.dayOfMonth + '</td>' +
                            '        </tr>')


                    })
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}
