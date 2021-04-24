const id = getCookie("idLocation");
$(document).ready(function () {
    locationPage(id);
    parentLocationsChain();
});

function locationPage(id) {
    console.log(id);
    $.ajax({
        url:"/api/locations/" + id,
        type:"get",
        async: false,
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                console.log(answer);
                if (answer.response == true) {
                    let location = answer.message;
                    $('#location-page-title').text(location.title);
                    $('#location-page-loc-type').text(location.locationType);
                } else {
                    alert("Some problems at server");
                }
            }
        ]
    });
}

function parentLocationsChain() {
    $.ajax({
        url:"/api/locations/" + id + "/chain",
        type:"get",
        complete: [
            function (response) {
                let answer = $.parseJSON(response.responseText);
                if (answer.response == true) {
                    let locations = answer.message;
                    console.log("chain:" + locations)
                    let length = locations.length;
                    $.each(locations, function (key, value) {
                        $('#location-page-chain').append('<a class="link-secondary" onclick="location.href=\'/location/' + value.id + '\'">' + value.title + '</a>');
                        if (key + 1 < length) {
                            $('#location-page-chain').append('    ->    ');
                        }
                    })
                }
            }]
    });
}