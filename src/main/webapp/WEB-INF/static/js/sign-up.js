$(document).ready(function () {
    configureForm();
});

function configureForm() {
    $.ajax({
        url:"/api/locations",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: get all locations");
                console.log(answer);
                if (answer.response == true) {
                    $.each(answer.message, function(key, value) {
                        if (value.locationType === 'UNIVERSITY' || value.locationType === 'GROUP')
                        $('#location').append('<option value="'+ value.id +'" loc-parent-type="' + value.locationType + '">'+ value.title + '</option>');
                    });
                }
            }
        ]
    });
}

function onClickSubmit() {
    let firstName = $('#first-name').val();
    let lastName = $('#last-name').val();
    let patronymic = $('#patronymic').val();
    let location = $("#location option:selected" ).val();
    let login = $('#login').val();
    let email = $('#email').val();
    let password = $('#password').val();
    let secondPassword = $('#password-second').val();

    if (secondPassword !== password) {
        alert("Password isn't equal confirm password");
        return;
    }

    let data = {
        idLocation:location,
        firstName:firstName,
        lastName:lastName,
        patronymic:patronymic,
        login:login,
        password:password,
        email:email
    };
    console.log(data);

    $.ajax({
        url:"/registration",
        type:"post",
        contentType:'application/json',
        data:JSON.stringify(data),
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(response);
                console.log(answer);
                if (answer.message === true) {
                    alert(answer.message);
                    window.location.replace('/');
                } else {
                    alert(answer.message);
                    return;
                }
            }
        ]
    });
}