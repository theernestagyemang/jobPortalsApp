$(function () {
    $("#forgot-password").toggle(1000);
    $(".forget-pass").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);
    });

    $(".back").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);
    });


    $('.lozad').Lazy();


});

function rowClicked(value) {
    var url = "/authenticated";

    $.get(url, function (data) {
        if (!data) {
            swal("Unauthenticated!", "You must login!", "error");

        } else {
            saveJob(value);
        }
    });

}

function jobAlert(url) {

    $.ajax({
        type: "POST",
        url: url,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,

        success: function (data) {
            switch (data) {
                case "SUCCESS":
                    swal({
                        title: "Success!",
                        text: "Your profile has been updated successfully!",
                        type: "success",
                        icon: "success",
                        button: "Ok!"
                    });
                    break;
                case "FAILED":
                    swal({
                        title: "FAILED!",
                        text: "Sorry, We couldn't update your profile!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });
                    break;
                case "ERROR":
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, We couldn't update your profile!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });
                    break;
                case "INVALID-USER":
                    swal({
                        title: "Unsuccessful!",
                        text: "Invalid ID provided!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });
                    break;
                case "INVALID-JOB-ID":
                    swal({
                        title: "Unsuccessful!",
                        text: "Invalid Job ID provided!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });
            }

        },
        error: function (e) {
            swal({
                title: "Unsuccessful!",
                text: "Sorry, We couldn't update your profile!",
                type: "error",
                icon: "danger",
                button: "Ok!"
            });
        }

    });
}

function saveJob(value) {
    var url = "/save-jobs?id=" + value;
    $.ajax({
        type: "POST",
        url: url,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,

        success: function (data) {

            swal({
                title: "Success!",
                text: "Your profile has been updated successfully!",
                type: "success",
                icon: "success",
                button: "Ok!"
            });

        },
        error: function (e) {
            swal({
                title: "Unsuccessful!",
                text: "Sorry, We couldn't update your profile!",
                type: "error",
                icon: "danger",
                button: "Ok!"
            });


        }

    });
}

function goBack() {
    window.history.back();
}
     
 