$(document).ready(function() {
    let isAdmin = getCookie("isAdmin");
    if (isAdmin == "true") {
        $('#header-container').load('/resources/header.html');
    } else {
        $('#header-container').load('/resources/header_user.html');
    }
});