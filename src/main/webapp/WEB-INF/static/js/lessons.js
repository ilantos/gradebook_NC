function lessonPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/lessons/" + id,
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
                    localStorage.setItem('maxGrade', lesson.maxGrade);
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}
function subjectPageByLessonId(id) {
    $.ajax({
        url:"/api/subjects?lessonId=" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                window.location.href = "/subjects/" + answer.message.id;
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
        url:"/api/lessons",
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

function addLesson() {
    window.location.href = "";
    $.ajax({
        url:"/api/lessons",
        type:"post",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    $('#form-headline').text('Add lesson');
                    $('#form-button-submit').text('Add');
                    $('#form-button-submit').attr('onclick', 'requestToAdd()');
                }
            }
        ]
    });
}

function requestToAdd() {
    let id = $('#form-lesson').attr('id-lesson');
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let maxGrade =  $('#form-max-grade').val();
    let creationDate = $('#form-creation-date').val();
    let data = {id:id, title:title, description:description, maxGrade:maxGrade, creationDate:creationDate};

    $.ajax({
        url:"/api/lessons",
        type:"post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response.responseText.message);
                console.log("idSubject: " + getCookie("idSubject"));
                window.location.href = "/subjects/" + getCookie("idSubject");
            }
        ]
    });
}