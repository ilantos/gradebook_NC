$(document).ready(function() {
    let isAdmin = getCookie("isAdmin");
    if (isAdmin == "true") {
        $('#header-container').load('/resources/header.html');
    } else {
        $('#header-container').load('/resources/header_user.html');
    }
});

function header_location_click() {
    $('#link-locations').attr('class', 'nav-link active');
    window.location.href = "/locations";
    getAllLocations();
}
function header_subject_click() {
    $('#link-subjects').attr('class', 'nav-link active');
    window.location.href = "/subjects";
    getAllSubjects();
}

function nav_default () {
    $('#link-locations').attr('class', 'nav-link');
    $('#link-subjects').attr('class', 'nav-link');
    $('#link-users').attr('class', 'nav-link');
}