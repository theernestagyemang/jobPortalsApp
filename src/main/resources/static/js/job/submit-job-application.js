/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let mark = 0;
let totalMark = 10;

function myFunction() {
    var x = document.getElementById("alertBox");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function loadMyProfileModalDialog() {
    $('#myProfileModalDialog').modal({
        show: true
    });
}

function loadMyIndividualModalDialog() {

    $.get(url, function (data) {

        if (data === false) {
            var x = document.getElementById("alertBox");
            x.style.display = "block";

            var xx = document.getElementById("submitBox");
            xx.style.display = "none";

        } else {
            var x = document.getElementById("alertBox");
            x.style.display = "none";

            var xx = document.getElementById("submitBox");
            xx.style.display = "block";
        }
    });


    var category = $("#jobCategory").val();
    var url = `/find-priv?category=${category}`;


    $('#myIndividualModalDialog').modal({
        show: true
    });
}

function profileComplete() {
    var url = "/seeker/findByCompletePctAndJobSeeker";
    var complete = false;
    $.get(url, function (data) {
        var mark = parseInt(data);

        if (mark >= 10) {
            complete = true;
        }
    });

    return complete;
}

$(function () {


    $("#chkPassport").click(function () {

        if ($(this).is(":checked")) {
            $("#uploadDiv").hide();
            $(".downloadCv").removeClass("showAnchor");
        } else {
            $(".downloadCv").addClass("showAnchor");
            $("#uploadDiv").show();
        }
    });

    $('#applyForthisJob').click(function (e) {
        e.preventDefault();
        var url = "/seeker/findByCompletePctAndJobSeeker";

        $.get(url, function (data) {

            var mark = parseInt(data);
            var pct = ((mark / 10) * 100)
            $("#profileStrength").html(Math.round(pct) + "%");

            if (mark < 10) {
                loadMyProfileModalDialog();
            } else {
                loadMyIndividualModalDialog();
            }
        });
        //check profie complete

    });

    $('.site-button').on('click', function (evt) {
        $(".site-button").prop("disabled", true);
        evt.preventDefault();


        var transactionId = $("#transactionId").val();
        var url = `/job/apply/${transactionId}`;

        $.ajax({
            type: "POST",
            url: url,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {
                    case "ALREADY-APPLIED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, You have already applied for this Job!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        break;

                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Your application has been successfully Submitted!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                $(".site-button").prop("disabled", false);
                                location.reload();
                            });
                        modal.modal('hide');
                        break;


                    case "FAILED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't submit your application!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });

                        $(".site-button").prop("disabled", false);
                        break;
                }

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't submit your application!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $(".site-button").prop("disabled", false);

            }

        });


    });

    myFunction();
});
