$(document).ready(function() {
    $('#header-container').load('/resources/header.html');
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