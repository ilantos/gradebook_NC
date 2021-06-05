$(document).ready(function () {
    getUnrolledSubjects()
});

function getUnrolledSubjects() {
    let login = getCookie("username");
    $.ajax({
        url:"/api/subjects/" + login + "/unrolledSubjects",
        type:"get",
        contentType: 'application/json',
        async:false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response === true) {
                    renderSubjects(answer.message);
                }
            }
        ]
    });
}
function renderSubjects(subjects) {
    $.each(subjects, function(key, value) {
        $('#form-title').append('<option value=' + value.id + '>' + value.title + '</option>');
    });
}
function requestToEnroll() {
    let login = getCookie("username");
    let subjectId = $("#form-title").val();
    console.log("Request: enroll");
    $.ajax({
        url:"/api/subjects/" + login + "/enroll?subjectId=" + subjectId,
        type:"post",
        contentType: 'application/json',
        async:false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                alert(answer.message);
            }
        ]
    });
}
