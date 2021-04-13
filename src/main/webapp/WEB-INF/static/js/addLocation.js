function addLocation() {
    $.ajax({
        url:"/api/locations",
        type:"get",
        complete:[
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log("Request: get all locations");
                console.log(answer);
                if (answer.response == true) {
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

    console.log("Request: add location");
    console.log(data);
    alert('');
    $.ajax({
        url:"/api/locations",
        type:"post",
        contentType: 'application/json',
        data: JSON.stringify(data),
        complete: [
            function (response) {
                alert(response.responseText.message);
            }
        ]
    });
}
