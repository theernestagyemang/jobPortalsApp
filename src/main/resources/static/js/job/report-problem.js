$(function () {

    $("#forgot-password").toggle(1000);
    $('.lozad').Lazy();


    $("#report-prob-frm").on("submit", function (e) {
        e.preventDefault();

        var fullName = $("#fullName").val();
        var telephone = $("#telephone").val();
        var email = $("#email").val();
        var message = $("#message").val();
        var subject = $("#subject").val();

        $("#btnSend").prop("disabled", true);

        let data = {
            fullName: fullName,
            telephone: telephone,
            email: email,
            message: message,
            subject: subject
        };

        $("#btnSend").html("Sending.......");

        $.ajax({
            type: "POST",
            url: `/report-problem`,
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/JSON",
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {

                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Your issue has been submitted successfully, we will get back to you soon!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                document.getElementById("report-prob-frm").reset();
                                $("#btnSend").prop("disabled", false);
                                $("#btnSend").html("Send");
                            });
                        break;


                    case "FAILED":
                    case "ERROR":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't submit your issue, please try again later!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $("#btnSend").prop("disabled", false);
                        $("#btnSend").html("Send");
                        break;
                }

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't submit your issue, please try again later!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#btnSend").prop("disabled", false);
                $("#btnSend").html("Send");
            }

        });

    })


    $(document).on("click", ".editBtn2", function (e) {


        e.preventDefault();

        $("#message").val("");
        $("#reportid").val("");

        var modal = $('#signupModal');
        modal.data('url', $(this).attr('href'));
        modal.data('request_type', $(this).data('request-type'));

        var href = modal.data('url');

        $.get(href, function (user, status) { //Declare a GET that takes in href

            $('#message').val(user.message);
            $('#reportid').val(user.id);

        });
        modal.modal('show');

    });
})