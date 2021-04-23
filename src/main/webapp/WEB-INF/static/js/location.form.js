$(document).ready(function () {
    configureForm();
});

function configureTemplateByCookie () {
    if (getCookie("formLocation") === "edit") {
        $('#form-button-submit').text('Edit');
        $('#form-button-submit').attr('onclick', 'requestToEdit()');
        $('#form-headline').text("Edit location");
        const id = getCookie("idLocation");
        editLocation(id);
    }
    if (getCookie("formLocation") === "add") {
        $('#form-button-submit').text('Add');
        $('#form-button-submit').attr('onclick', 'requestToAdd()');
        $('#form-headline').text("Add location");
    }
}

function editLocation (id) {
    $.ajax({
        url:"/api/locations/" + id,
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let location = answer.message;
                    console.log(location);

                    $('#form-title').val(location.title);
                    $('#form-parent-location-container').remove();
                    $('#loc-type').remove();

                } else {
                    alert("Some problems at server: " + answer.message);
                }
            }
        ]
    });
}

function listenChangeLocParentSelect() {
    $('#loc-parent').on('change', function () {
        console.log("listen...")
        var optionSelected = $("option:selected", this);
        var valueSelected = optionSelected.attr('loc-parent-type');
        $('#loc-type').empty();
        $.ajax({
            url:"/api/locations/lowerType/" + valueSelected,
            type:"get",
            async:false,
            complete:[
                function (response) {
                    let answer = $.parseJSON(response.responseText);
                    console.log("Request: get lower type");
                    console.log(answer);
                    if (answer.response == true) {
                        $('#loc-type').append('<option value="'+ answer.message +'">'+ answer.message + '</option>');
                    } else {
                        alert("Some problems at server");
                    }
                }
            ]
        });
    });
}

function configureForm() {
    $.ajax({
        url:"/api/locations/withoutType/GROUP",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: get all locations");
                console.log(answer);
                if (answer.response == true) {
                    const option = $('select option[value="' + location.locType + '"]');
                    option.attr('selected', 'selected');
                    $('#form-parent-location-container').append(
                        '        <hr class="my-4">' +
                        '        <div class="col-4">' +
                        '            <label for="loc-parent" class="form-label">Parent location</label>' +
                        '            <select class="form-select" id="loc-parent" required="">' +
                        '            </select>' +
                        '        </div>');

                    $('#loc-parent').append('<option value="0" loc-parent-type="-"> - </option>');
                    $.each(answer.message, function(key, value) {
                        $('#loc-parent').append('<option value="'+ value.id +'" loc-parent-type="' + value.locationType + '">'+ value.title + '</option>');
                    });
                    listenChangeLocParentSelect();
                    configureTemplateByCookie();
                }
            }
        ]
    });
}
function requestToEdit() {
    let id = getCookie("idLocation");
    let parent = $('#loc-parent option:selected').val();
    let title = $('#form-title').val();
    let locType = $("#loc-type option:selected" ).text();
    let data = {id:id, parentLoc:parent, title:title, locationType:locType};

    console.log("Request: edit location");
    console.log(data);
    $.ajax({
        url:"/api/locations",
        type:"put",
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
}

function requestToAdd() {
    let id = 0;
    let parent = $('#loc-parent option:selected').val();
    let title = $('#form-title').val();
    let locType = $("#loc-type option:selected" ).text();
    let data = {id:id, parentLoc:parent, title:title, locationType:locType};

    console.log("Request: add location");
    console.log(data);
    $.ajax({
        url:"/api/locations",
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
}
