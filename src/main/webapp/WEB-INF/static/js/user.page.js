const userId = getCookie("idUser");
$(document).ready(function () {
    userPage(userId);
});
function userPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/users/" + id,
        type:"get",
        async: false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let user = answer.message;
                    $('#user-page-name').text(user.firstName + " " + user.lastName + " " + user.patronymic);
                    $('#user-page-email').text(user.email);
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}