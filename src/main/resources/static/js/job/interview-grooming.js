$(function () {

    $("#forgot-password").toggle(1000);
    var url88 = "/find-address";

    $.get(url88, function (data, status) {
        $('#comp-tel').html(data.companyAddress);
        $('#adabraka-email').html(data.hodEmail);
    });

    $('.lozad').Lazy();


    $("#empForm").toggle(1000);

    $("#empForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });
    $("#jobSeekerForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });


    $('#alertForm').submit(function (e) {
        e.preventDefault();

        updateDesiredCareer();
    });

    function updateDesiredCareer() {

        var jobKeywors = $('#jobKeywors').val();
        var jobLocation = $('#jobLocation').val();
        var jobWorkExp = $('#jobWorkExp').val();

        var name = $('#jobName').val();
        var expSalary = $('#expSalary').val();
        var jobCategory = $('#jobCategory').val();

        var id = $('#seekerId').val();
        var alertId = $('#alertId').val();

        var url = "/create-alert?jobKeywors=" + jobKeywors + "&jobLocation=" + jobLocation + "&jobWorkExp=" + jobWorkExp + "&name=" + name +
            "&expSalary=" + expSalary + "&jobCategory=" + jobCategory + "&id=" + id + "&alertId=" + alertId;

        // disabled the submit button
        $("#certBtn").prop("disabled", true);

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
                    text: "Your job alert submitted successfully!",
                    type: "success",
                    icon: "success",
                    button: "Ok!"
                })
                    .then((value) => {
                        $("#certBtn").prop("disabled", false);
                        location.reload();
                    });

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't submit your job alert, please try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#certBtn").prop("disabled", false);

            }

        });

    }


    $('#cv-restructure').on("submit", function (e) {
        //stop submit the form, we will post it manually.

        e.preventDefault();


        var name = $('#requesterFullName').val();
        var email = $('#requesterEmail').val();
        var tel = $('#requesterPhone').val();
        var serviceType = $('#serviceType').val();

        // name,String email, String tel
        var url = "/create-cv-request?name=" + name + "&email=" + email + "&tel=" + tel + "&serviceType=" + serviceType;

        var modal = $('#cvConfirmModal');

        // disabled the submit button
        $("#btnSave2").prop("disabled", true);
        $("#btnSave2").html("Sending Request...");

        $.ajax({
            type: "POST",
            url: url,

            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if (data) {
                    modal.modal('hide');
                    swal({
                        title: "Success!",
                        text: "Your request has been submitted Successfully, We will get back to you shortly!",
                        type: "success",
                        icon: "success",
                        button: "Ok!"
                    }).then((value) => {
                        $("#btnSave2").prop("disabled", false);
                        $("#btnSave2").html("CREATE");
                    });
                } else {
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, we couldn't submit your request, Try again next time!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#btnSave2").prop("disabled", false);
                    $("#btnSave2").html("CREATE");
                    modal.modal('hide');
                }
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, we couldn't submit your request, Try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });
                $("#btnSave2").prop("disabled", false);
                $("#btnSave2").html("CREATE");
            }

        });

    });

});//End of document.ready