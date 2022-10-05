/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('#jobSeekerForm').submit(function (event) {
    //stop submit the form, we will post it manually.
    event.preventDefault();

    var fullName = $('#fullName').val();
    var email = $('#ind-email').val();
    var password = $('#ind-password').val();
    var title = $('#title').val();
    var telephone = $('#telephone').val();
    var passwordConfirm = $('#confirm-password').val();

    if (passwordConfirm === password) {
        // fullName, email, password, title
        var url = "/register-ind?fullName=" + fullName + "&email=" + email + "&password=" + password + "&title=" + title + "&telephone=" + telephone;
        var modal = $('#deleteConfirmModal2');

        // disabled the submit button
        document.getElementById("ind-btnSave").disabled = true;
        // $('#individual-register-btn').prop("disabled", true);
        // add spinner to button
        document.getElementById("ind-btnSave").innerHTML =
            `<span class="spinner-border text-white" role="status" ></span><span class="text-white">Please Wait...</span>`

        // $("#indSpin").addClass("fas fa-circle-notch fa-spin");

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
                        document.getElementById("ind-btnSave").disabled = false;
                        // $('#individual-register-btn').prop("disabled", true);
                        // add spinner to button
                        document.getElementById("ind-btnSave").innerHTML = `<span class="text-white">CREATE JOB SEEKER</span>`

                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, account already exist.",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $("#ind-btnSave").prop("disabled", false);
                        $("#indSpin").removeClass("fas fa-circle-notch fa-spin");
                        modal.modal('hide');
                        break;
                    case "FAILED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't create your account, Try again later.",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });

                        $("#ind-btnSave").prop("disabled", false);
                        $("#indSpin").removeClass("fas fa-circle-notch fa-spin");
                        modal.modal('hide');
                        break;
                    case "SUCCESS":
                        // modal.modal('hide');
                        clearSeekerForm();
                        swal({
                            title: "Success!",
                            text: "Your account registration is successful pending activation, Please check your email to activate your account.",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                window.location.reload();
                                $("#ind-btnSave").prop("disabled", false);
                                $("#indSpin").removeClass("fas fa-circle-notch fa-spin");
                            });
                        break;

                }

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't create your account, Try again later.",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#ind-btnSave").prop("disabled", false);
                $("#ind-btnSave").removeClass("fas fa-circle-notch fa-spin");
            }

        });
    } else {
        swal({
            title: "Password Verification",
            text: "Passwords do not match!",
            type: "error",
            icon: "danger",
            button: "Ok!"
        });

    }


});

$('#empForm').submit(function (event) {
    //stop submit the form, we will post it manually.

    event.preventDefault();


    var companyName = $('#companyName').val();
    var email = $('#email').val();
    var password = $('#password').val();
    var passwordConfirm = $('#confirm-password1').val();
    var telephone = $('#companyTelephone').val();

    if (passwordConfirm === password) {
        // comp,String email,String password
        var url = "/register-company?comp=" + companyName + "&email=" + email + "&password=" + password + "&telephone=" + telephone;
        var modal = $('#deleteConfirmModal');

        // disabled the submit button
        document.getElementById("btnSave").disabled = true;
        // $('#individual-register-btn').prop("disabled", true);
        // add spinner to button
        document.getElementById("btnSave").innerHTML =
            `<span class="spinner-border text-white" role="status" ></span ><span class="text-white">Please Wait...</span> `

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
                        // disabled the submit button
                        document.getElementById("btnSave").disabled = false;
                        // $('#individual-register-btn').prop("disabled", true);
                        // add spinner to button
                        document.getElementById("btnSave").innerHTML =`<span class="text-white">CREATE EMPLOYER</span>`
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, account already exist.",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $("#btnSave").prop("disabled", false);
                        $("#compSpin").removeClass("fas fa-circle-notch fa-spin");
                        modal.modal('hide');
                        break;
                    case "FAILED":
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't create your account, Try again later",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });

                        $("#btnSave").prop("disabled", false);
                        $("#compSpin").removeClass("fas fa-circle-notch fa-spin");
                        modal.modal('hide');
                        break;
                    case "SUCCESS":
                        clearEmployerForm();
                        swal({
                            title: "Success!",
                            text: "Your account registration is successful pending activation, Please check your email to activate your account.",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                window.location.reload();
                                $("#btnSave").prop("disabled", false);
                                $("#compSpin").removeClass("fas fa-circle-notch fa-spin");
                            });
                        break;

                }

            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't create your account, Try again later.",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#btnSave").prop("disabled", false);
                $("#compSpin").removeClass("fas fa-circle-notch fa-spin");
            }

        });
    } else {
        swal({
            title: "Password Verification",
            text: "Password do not match!",
            type: "error",
            icon: "danger",
            button: "Ok!"
        });
    }


});

function clearEmployerForm(){
    $('#companyName').val('');
    $('#email').val('');
    $('#password').val('');
    $('#confirm-password1').val('');
    $('#companyTelephone').val('');
}

function clearSeekerForm(){
    $('#fullName').val('');
    $('#ind-email').val('');
    $('#ind-password').val('');
    $('#title').val('');
    $('#telephone').val('');
    $('#confirm-password').val('');
}