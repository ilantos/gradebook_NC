$(document).ready(function () {
    getAllLocations();
});

function getAllLocations() {
    $.ajax({
        url:"/api/locations",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: get all locations");
                console.log(answer);
                if (answer.response == true) {
                    renderLocations(answer.message);
                } else {
                    alert(answer.message);
                }
            }
        ]
    });
}

function renderLocations(locations) {
    $.each(locations, function(key, value) {
        $('#locations-table').append('<tr id="location-' + value.id + '">' +
            '            <th scope="row">'+ (key + 1) +'</th>' +
            '            <td><a class="link-secondary" onclick="locationPage('+ value.id +')">' + value.title + '</a></td>' +
            '            <td>' + value.locType + '</td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-danger" onclick="removeLocation(' + value.id + ')">Delete</button>' +
            '            </td>' +
            '            <td>' +
            '                <button type="button" class="btn btn-info" onclick="editLocation(' + value.id + ')">Edit</button>' +
            '            </td>' +
            '        </tr>');
    });
}

function removeLocation (id) {
    $.ajax({
        url:"/api/locations/" + id,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: delete location " + id);
                console.log(answer);
                alert(answer.message);
                if (answer.response == true) {
                    $('#locations-table').empty();
                    getAllLocations();
                }
            }
        ]
    });
}