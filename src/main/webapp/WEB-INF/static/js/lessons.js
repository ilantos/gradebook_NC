function lessonPage(id) {
    console.log(id);
    $('#content-container').load('/resources/lesson_page.html');
    $.ajax({
        url:"/lessons/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let lesson = answer.message;
                    $('#lesson-page-title').text(lesson.title);
                    $('#lesson-page-description').text(lesson.description);
                    $('#lesson-page-max_grade').text(lesson.maxGrade);
                    let creationDate = lesson.creationDate;
                    $('#lesson-page-creation-date').text(creationDate.year + "." + creationDate.monthValue + "." + creationDate.dayOfMonth);
                    $('#lesson-cancel-button').attr("onclick", "subjectPageByLessonId(" + id + ")");
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}
function subjectPageByLessonId(id) {
    $.ajax({
        url:"/subjects?lessonId=" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                subjectPage(answer.message.id);
            }
        ]
    });
}
function editLesson(id) {
    $.ajax({
        url:"/lessons/" + id,
        type:"get",
        complete: [
            function (response) {
                $('#content-container').load('/resources/form_lesson.html');
                $('#form-headline').text('Edit Lesson');
                $('#lesson-cancel-button').attr("onclick", "subjectPageByLessonId(" + id + ")");
                $('#form-headline').text('Edit subject');
                $('#form-button-submit').text('Edit');
                $('#form-button-submit').attr('onclick', 'requestToEditLesson()');
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                let lesson;
                if (answer.response == true) {
                    lesson = answer.message;
                } else {
                    lesson = null;
                }
                console.log(lesson);
                $('#form-lesson').attr('id-lesson', lesson.id);
                $('#form-title').val(lesson.title);
                $('#form-description').val(lesson.description);
                $('#form-max-grade').val(lesson.maxGrade);
            }
        ]
    });
}

function requestToEditLesson() {
    let id = $('#form-lesson').attr('id-lesson');
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let maxGrade =  $('#form-max-grade').val();
    let creationDate = $('#form-creation-date').val();
    let data = {id:id, title:title, description:description, maxGrade:maxGrade, creationDate:creationDate};
    console.log(data);
    $.ajax({
        url:"/lessons",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response);
            }
        ]
    });
    subjectPageByLessonId(id);
}
function removeLesson(id) {
    $.ajax({
        url:"/lessons/" + id,
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
function addLesson() {
    $('#content-container').load('/resources/form_lesson.html');
    $('#form-headline').text('Add subject');
    $('#form-button-submit').text('Add');
    $('#form-button-submit').attr('onclick', 'requestToAdd()');
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