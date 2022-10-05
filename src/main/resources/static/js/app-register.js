$(function () {
    $('#indRegister').click(function () {
        $('#myIndividualModalDialog').modal({
            show: true
        });
    });

    $('#compRegister').click(function () {
        $('#companyModal').modal({
            show: true
        })
    });
    $(document).on('show.bs.modal', '.modal', function (event) {
        var zIndex = 1040 + (10 * $('.modal:visible').length);
        $(this).css('z-index', zIndex);
        setTimeout(function () {
            $('.modal-backdrop').not('.modal-stack').css('z-index', zIndex - 1).addClass('modal-stack');
        }, 0);
    });

    $('#company-register-form').on('submit', function (e) {
        e.preventDefault();
        // console.log("company register")
        $("#btnSpin").addClass('fa-spin fa-spinner');

        var companyName = $('#mycompanyName').val();
        var email = $('#mycompanyEmail').val();
        var password = $('#mycompanyPassword').val();
        // comp,String email,String password
        var url = "/register-company?comp=" + companyName + "&email=" + email + "&password=" + password;
        var modal = $('#companyModal');

        // disabled the submit button
        $("#company-register-btn").prop("disabled", true);

        $.ajax({
            type: "POST",
            url: url,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

                switch (data) {
                    case "EMAIL-EXIST":
                        modal.modal('hide');
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, your email is unavailable, please try again with another email!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $("#company-register-btn").prop("disabled", false);
                        $("#btnSpin").removeClass('fa-spin fa-spinner');

                        break;

                    case "FAILED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't create your account, Try again next time!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });

                        $("#company-register-btn").prop("disabled", false);
                        $("#btnSpin").removeClass('fa-spin fa-spinner');
                        break;

                    case "SUCCESS":
                        modal.modal('hide');
                        swal({
                            title: "Success!",
                            text: "Your account registration is successful pending activation, Please check your email to activate your account!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        }).then((value) => {
                            $("#company-register-btn").prop("disabled", false);
                            $("#btnSpin").removeClass('fa-spin fa-spinner');
                        });
                        break;
                }
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't create your account, Try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#company-register-btn").prop("disabled", false);
                $("#btnSpin").removeClass('fa-spin fa-spinner');

            }
        });
    });


    $('#individual-register-form').on('submit', function (e) {
        e.preventDefault();
        $("#btnSpin2").addClass('fa-spin fa-spinner');

        var fullName = $("#applicantFullName").val();
        var email = $('#applicantEmail').val();
        var password = $('#applicantPassword').val();
        var title = "";

        var url = `/register-ind?fullName=${fullName}&email=${email}&password=${password}&title=${title}`;

        // console.log("register user form");
        var modal = $('#myIndividualModalDialog');


        // disabled the submit button
        // $("#individual-register-btn").prop("disabled", true);

        $('#individual-register-btn').prop("disabled", true);
        // add spinner to button
        $("#individual-register-btn").html(
            `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Loading...`
        );

        $.ajax({
            type: "POST",
            url: url,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {

                switch (data) {
                    case "EMAIL-EXIST":

                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, your email is unavailable, please try again with another email!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $("#individual-register-btn").prop("disabled", false);
                        $("#btnSpin2").removeClass('fa-spin fa-spinner');
                        modal.modal('hide');
                        break;
                    case "FAILED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't create your account, Try again next time!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });

                        $("#individual-register-btn").prop("disabled", false);
                        $("#btnSpin2").removeClass('fa-spin fa-spinner');
                        modal.modal('hide');
                        break;
                    case "SUCCESS":

                        swal({
                            title: "Success!",
                            text: "Your account registration is successful pending activation, Please check your email to activate your account!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                $("#individual-register-btn").prop("disabled", false);
                                $("#btnSpin2").removeClass('fa-spin fa-spinner');
                                modal.modal('hide');
                            });
                        break;

                }

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't create your account, Try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#individual-register-btn").prop("disabled", false);
                $("#btnSpin2").removeClass('fa-spin fa-spinner');
                modal.modal('hide');
            }

        });

    });


    $("#forgot-password").toggle(1000);
    $('.lozad').Lazy();


    $("#empForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });
    $("#jobSeekerForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });

    $(".forget-pass").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);
    });

    $(".back").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);
    });

    $(".password-view").on("click", function () {
        myFunction("mycompanyPassword");
    })
    $(".password-view2").on("click", function () {
        myFunction("applicantPassword");
    })

    function myFunction(element) {
        var x = document.getElementById(element);
        if (x.type === "password") {
            x.type = "text";
        } else {
            x.type = "password";
        }
    }


});//End of document.ready
    
    
    
 