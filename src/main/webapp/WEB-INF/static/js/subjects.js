function getAllSubjects() {
    $.ajax({
        url:"/subjects",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    $.each(answer.message, function(key, value) {
                        $('#subjects-table').append('<tr id="subject-' + value.id + '">' +
                            '            <th scope="row">'+ (key + 1) +'</th>' +
                            '            <td><a class="link-secondary" onclick="subjectPage('+ value.id +')">' + value.title + '</a></td>' +
                            '            <td>' + value.description + '</td>' +
                            '            <td>' +
                            '                <button type="button" class="btn btn-danger" onclick="removeSubject(' + value.id + ')">Delete</button>' +
                            '            </td>' +
                            '            <td>' +
                            '                <button type="button" class="btn btn-info" onclick="editSubject(' + value.id + ')">Edit</button>' +
                            '            </td>' +
                            '        </tr>');
                    });
                }
            }
        ]
    });
}

function subjectPage(id) {
    console.log(id);
    $('#content-container').load('/resources/subject_page.html');
    $.ajax({
        url:"/subjects/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let subject = answer.message;
                    $('#subject-page-title').text(subject.title);
                    $('#subject-page-description').text(subject.description);
                    $.each(subject.lessons, function(key, value) {

                        $('#lessons-table').append('<tr id="lesson-' + value.id + '">' +
                            '            <th scope="row">'+ (key + 1) +'</th>' +
                            '            <td><a class="link-secondary" onclick="lessonPage('+ value.id +')">' + value.title + '</a></td>' +
                            '            <td>' + value.description + '</td>' + '<td>' + value.maxGrade + '</td>'+ '<td>' + value.creationDate.year + '.'
                            +  value.creationDate.monthValue + '.' + value.creationDate.dayOfMonth + '</td>' +
                            '            <td>' +
                            '                <button type="button" class="btn btn-danger" onclick="removeLesson(' + value.id + ')">Delete</button>' +
                            '            </td>' +
                            '            <td>' +
                            '                <button type="button" class="btn btn-info" onclick="editLesson(' + value.id + ')">Edit</button>' +
                            '            </td>' +
                            '        </tr>')


                    })
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}
function editSubject (id) {
    $('#content-container').load('/resources/form_subject.html');
    $.ajax({
        url:"/subjects/" + id,
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
    $('#content-container').load('/resources/form_subject.html');
    $.ajax({
        url:"/subjects",
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

function requestToAdd() {
    let id = 0;
    let title = $('#form-title').val();
    let description = $('#form-description').val();
    let lessons = null;
    let data = {id:id, title:title, description:description, lessons:lessons};

    $.ajax({
        url:"/subjects",
        type:"post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response.responseText.message);
                //alert(response.responseText);
                pageSubjects();
            }
        ]
    });
}

function requestToEditSubject() {
    let id = $('#form-subject').attr('id-subject');
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let lessons = null;
    let data = {id:id, title:title, description:description, lessons: lessons};
    console.log(data);
    $.ajax({
        url:"/subjects",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response);
            }
        ]
    });
    pageSubjects();
}

function removeSubject (id) {
    $.ajax({
        url:"/subjects/" + id,
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
                pageSubjects();
            }
        ]
    });
}
function pageSubjects() {
    $('#content-container').load('/resources/subjects.html');
    getAllSubjects();
}