$(document).ready(function () {
    configureTemplateByCookie();
});

function configureTemplateByCookie () {
    console.log("1");
    if (getCookie("formSubject") === "edit") {
        $('#form-button-submit').text('Edit');
        $('#form-button-submit').attr('onclick', 'requestToEdit()');
        $('#form-headline').text("Edit subject");
        console.log("cookie: " + getCookie("idSubject"));
        id = getCookie("idSubject");
        editSubject(id);
    }
    if (getCookie("formSubject") === "add") {
        $('#form-button-submit').text('Add');
        $('#form-button-submit').attr('onclick', 'requestToAdd()');
        $('#form-headline').text("Add subject");
    }
}

function editSubject (id) {
    $.ajax({
        url:"/api/subjects/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let subject = answer.message;
                    console.log(subject);

                    $('#form-title').val(subject.title);
                    $('#form-description').val(subject.description);
                } else {
                    alert("Some problems at server: " + answer.message);
                }
            }
        ]
    });
}

function requestToEdit() {
    let id = getCookie("idSubject");
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let lessons = null;
    let data = {id:id, title:title, description:description, lessons: lessons};
    console.log("Request: edit subject");
    console.log(data);
    $.ajax({
        url:"/api/subjects",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
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

function requestToAdd() {
    let id = 0;
    let title = $('#form-title').val();
    let description = $("#form-description" ).val();
    let lessons = null;
    let data = {id:id, title:title, description:description, lessons: lessons};
    console.log("Request: add subject");
    console.log(data);
    $.ajax({
        url:"/api/subjects",
        type:"post",
        contentType:'application/json',
        data:JSON.stringify(data),
        async:false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                alert(answer.message);
            }
        ]
    });
    window.location.href = "/subjects";
}
