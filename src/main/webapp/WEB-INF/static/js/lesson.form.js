const subjectID = getCookie("idSubject");
$(document).ready(function () {
    configureTemplateByCookie();
});

function configureTemplateByCookie () {
    if (getCookie("formLesson") === "edit") {
        $('#form-button-submit').text('Edit');
        $('#form-button-submit').attr('onclick', 'requestToEdit()');
        $('#form-headline').text("Edit lesson");
        const id = getCookie("idLesson");
        editLesson(id);
    }
    if (getCookie("formLesson") === "add") {
        $('#form-button-submit').text('Add');
        $('#form-button-submit').attr('onclick', 'requestToAdd()');
        $('#form-headline').text("Add lesson");
    }
    $('#form-lesson').attr('action', '/subjects/' + subjectID);
}

function editLesson (id) {
    $.ajax({
        url:"/api/lessons/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let lesson = answer.message;
                    console.log(lesson);

                    $('#form-title').val(lesson.title);
                    $('#form-description').val(lesson.description);
                    $('#form-max-grade').val(lesson.maxGrade);
                    let startDate = lesson.startDate;
                    let month = startDate.monthValue < 10 ? "0" + startDate.monthValue : startDate.monthValue;
                    let dayOfMonth = startDate.dayOfMonth < 10 ? "0" + startDate.dayOfMonth : startDate.dayOfMonth;
                    let hour = startDate.hour < 10 ? "0" + startDate.hour : startDate.hour;
                    let minute = startDate.minute < 10 ? "0" + startDate.minute : startDate.minute;
                    let startDateString = startDate.year + "-" + month + "-" + dayOfMonth + "T" + hour + ":" + minute;
                    console.log(startDateString)
                    $('#form-start-date').val(startDateString);
                } else {
                    alert("Some problems at server: " + answer.message);
                }
            }
        ]
    });
}

function requestToAdd() {
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let maxGrade =  $('#form-max-grade').val();
    let startDate = $('#form-start-date').val();
    let data = {title:title, description:description, maxGrade:maxGrade, startDate:startDate};
    window.location.href = "/subjects/" + subjectID;
    $.ajax({
        url:"/api/lessons?subjectId=" + subjectID,
        type:"post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response.responseText.message);
                console.log("idSubject: " + subjectID);
            }
        ]
    });
}
function requestToEdit() {
    let id = getCookie("idLesson");
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let maxGrade =  $('#form-max-grade').val();
    let startDate = $('#form-start-date').val();
    let data = {id:id, title:title, description:description, maxGrade:maxGrade, startDate};
    window.location.href = "/subjects/" + subjectID;
    $.ajax({
        url:"/api/lessons",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response.responseText.message);
                console.log("idSubject: " + subjectID);
            }
        ]
    });
}