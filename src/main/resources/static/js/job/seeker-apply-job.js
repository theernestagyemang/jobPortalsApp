function applyforJob() {


    $("#seeker-apply").prop("disabled", true);

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
                            $("#seeker-apply").prop("disabled", false);
                        });
                    break;

                case "INVALID-USER":
                case "INVALID-JOB":
                case "INVALID-JOBSEEKER":
                    swal({
                        title: data,
                        text: "Sorry, You have provided an invalid credential!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    })
                        .then((value) => {
                            $("#seeker-apply").prop("disabled", false);
                        });
                    break;


                case "FAILED":
                case "ERROR":
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, We couldn't submit your application!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#seeker-apply").prop("disabled", false);

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

            $("#seeker-apply").prop("disabled", false);

        }

    });
}

function loadMyProfileStrengthDialog() {
    $('#myProfileModalDialog').modal({
        show: true
    });
}


$(function () {

    var jobCategory = $("#job-category").val();
    $.get(`/find-priv?category=${jobCategory}`, function (data) {

        if (data) {
            $("#alertBox").removeClass("showAnchor");
        } else {
            $("#alertBox").addClass("showAnchor");
        }
    })
    $('.lozad').Lazy();
    $("#chkPassport").click(function () {

        if ($(this).is(":checked")) {
            $("#uploadDiv").hide();
            $(".downloadCv").removeClass("showAnchor");

        } else {
            $(".downloadCv").addClass("showAnchor");
            $("#uploadDiv").show();
        }
    });

    $("#seeker-apply").on("click", function (e) {
        e.preventDefault();

        //check profile strength
        var url = "/seeker/findByCompletePctAndJobSeeker";
        $.get(url, function (data) {

            var mark = parseInt(data);
            var pct = ((mark / 10) * 100)
            $("#profileStrength").html(Math.round(pct) + "%");

            if (mark < 10) {
                loadMyProfileStrengthDialog();
            } else {
                applyforJob();
            }
        })


    });

});
 