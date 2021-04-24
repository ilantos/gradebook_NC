$(document).ready(function () {
    getAllSubjects();
});

function getAllSubjects() {
    $.ajax({
        url:"/api/subjects",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    renderSubject(answer.message);
                }
            }
        ]
    });
}
function renderSubject(subjects) {
    $.each(subjects, function(key, value) {
        $('#subjects-table').append('<tr id="subject-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td><a class="link-secondary" onclick="location.href = \'/subjects/'+ value.id +'\';">' + value.title + '</a></td>' +
            '            <td>' + value.description + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeSubject(' + value.id + ')">Delete</button>' +
            '            </td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-info" onclick="location.href=\'/subjects/edit/' + value.id + '\'">Edit</button>' +
            '            </td>' +
            '        </tr>');
    });
}
function  removeSubject(id) {
    $.ajax({
        url:"/api/subjects/" + id,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: delete subject " + id);
                console.log(answer);
                alert(answer.message);
                if (answer.response == true) {
                    $('#subjects-table').empty();
                    getAllSubjects();
                }
            }
        ]
    });
}




function editSubject (id) {
    $('#content-container').load('/resources/form_subject.html');
    $.ajax({
        url:"/api/subjects/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                let subject;
                if (answer.response == true) {
                    subject = answer.message;
                } else {
                    subject = null;
                }
                console.log(subject);
                $('#form-subject').attr('id-subject', subject.id);
                $('#form-headline').text('Edit Subject');
                $('#form-title').val(subject.title);
                $('#form-description').val(subject.description);
            }
        ]
    });
}
function addSubject() {
    window.location.href = "";
    $.ajax({
        url:"/api/subjects",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    $('#form-headline').text('Add subject');
                    $('#form-button-submit').text('Add');
                    $('#form-button-submit').attr('onclick', 'requestToAdd()');
                }
            }
        ]
    });
}



