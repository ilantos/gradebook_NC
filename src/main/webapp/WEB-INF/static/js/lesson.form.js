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
        editLocation(id);
    }
    if (getCookie("formLesson") === "add") {
        $('#form-button-submit').text('Add');
        $('#form-button-submit').attr('onclick', 'requestToAdd()');
        $('#form-headline').text("Add lesson");
    }
    $('#form-lesson').attr('action', '/subjects/' + subjectID);
}

function requestToAdd() {
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let maxGrade =  $('#form-max-grade').val();
    let data = {title:title, description:description, maxGrade:maxGrade};
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