function getAllLocations() {
    $.ajax({
        url:"/locations",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    $.each(answer.message, function(key, value) {
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
            }
        ]
    });
}

function locationPage(id) {
    console.log(id);
    $('#content-container').load('/resources/location_page.html');
    $.ajax({
        url:"/locations/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let location = answer.message;
                    $('#location-page-title').text(location.title);
                    $('#location-page-loc-type').text(location.locType);
                    $.ajax({
                        url:"/locations/" + id + "/chain",
                        type:"get",
                        complete: [
                            function (response) {
                                let answer = $.parseJSON(response.responseText);
                                if (answer.response == true) {
                                    let locations = answer.message;
                                    console.log("chain:" + locations)
                                    let length = locations.length;
                                    $.each(locations, function (key, value) {
                                        $('#location-page-chain').append('<a class="link-secondary" onclick="locationPage('+ value.id +')">' + value.title + ' </a>');
                                        if (key + 1 < length) {
                                            $('#location-page-chain').append('    ->    ');
                                        }
                                    })
                                }
                            }]
                    });

                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}

function removeLocation (id) {
    $.ajax({
        url:"/locations/" + id,
        type:"delete",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    alert(answer.message);
                    $('#content-container').load('/resources/locations.html');
                    getAllLocations();
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}

function addLocation() {
    $('#content-container').load('/resources/form_location.html');
    $.ajax({
        url:"/locations",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    $('#form-headline').text('Add location');
                    $('#form-button-submit').text('Add');
                    $('#form-button-submit').attr('onclick', 'requestToAdd()');
                    const option = $('select option[value="' + location.locType + '"]');
                    option.attr('selected', 'selected');
                    $('#form-parent-location-container').append('' +
                        '<hr class="my-4">' +
                        '        <div class="col-4">' +
                        '            <label for="loc-parent" class="form-label">Parent location</label>' +
                        '            <select class="form-select" id="loc-parent" required="">' +
                        '            </select>' +
                        '        </div>');
                    $.each(answer.message, function(key, value) {
                        $('#loc-parent').append('<option value="'+ value.id +'">'+ value.title + '</option>');
                    });

                }
            }
        ]
    });
}

function requestToAdd() {
    let id = 0;
    let parent = $('#loc-parent option:selected').val();
    let title = $('#form-title').val();
    let locType = $("#loc-type option:selected" ).text();
    let data = {id:id, parentLoc:parent, title:title, locType:locType};

    $.ajax({
        url:"/locations",
        type:"post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response.responseText.message);
                //alert(response.responseText);
                pageLocations();
            }
        ]
    });
}

function editLocation (id) {
    $('#content-container').load('/resources/form_location.html');
    $.ajax({
        url:"/locations/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                let location;
                if (answer.response == true) {
                    location = answer.message;
                } else {
                    location = null;
                }
                console.log(location);
                $('#form-location').attr('id-location', location.id);
                $('#form-location').attr('id-parent', location.parentLoc);
                $('#form-headline').text('Edit location');
                $('#form-title').val(location.title);
                $('#form-button-submit').text('Edit');
                $('#form-button-submit').attr('onclick', 'requestToEditLocation()');
                const option = $('select option[value="' + location.locType + '"]');
                option.attr('selected', 'selected');
            }
        ]
    });
}

function requestToEditLocation() {
    let id = $('#form-location').attr('id-location');
    let parent = $('#form-location').attr('id-parent');
    let title = $('#form-title').val();
    let locType = $("#loc-type option:selected" ).text();

    let data = {id:id, parentLoc:parent, title:title, locType:locType};
    console.log(data);
    $.ajax({
        url:"/locations",
        type:"put",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                console.log(response);
                //alert(response.responseText);
                pageLocations();
            }
        ]
    });
}

function getLocationById (id) {
    $.ajax({
        url:"/locations/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    return answer.message;
                } else {
                    return null;
                }
            }
        ]
    });
}

function pageLocations() {
    $('#content-container').load('/resources/locations.html');
    getAllLocations();
}