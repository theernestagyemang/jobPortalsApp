$(document).ready(function () {

    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/findAppUser/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href

            $('#username').val(user.username);
            $('#fullName').val(user.fullName);
            $('#userType').val(user.userType);
            $('#roles').val(user.role);
            $('#userId').val(user.id);
            $('#email').val(user.email);
            $('#fn').val(user.fn);
            $('#ln').val(user.ln);
            $('#tel').val(user.telephone);

        });
        modal.modal('show');
    });


    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/admin/deleteUser/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this user!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel plx!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            $.get(url, function (data) {
                if (data) {
                    swal("Success!", "User has been deleted successfully", "success");
                } else {
                    swal("Error!", "Could not delete user", "error");
                }
            })

        });
    });

    $(document).on("click", ".reset-staff-password", function (e) {

        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/admin/resetStaffPass/${userId}`;
        console.log(url)
        swal({
            title: "Are you sure?",
            text: "You are about to reset this user's password!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, reset it!",
            closeOnConfirm: false
        }, function () {

            $.get(url, function (data) {
                if (data) {
                    swal("Success!", "Password reset successful, user will receive email", "success");
                } else {
                    swal("Error!", "Could not reset password", "error");
                }
            })
        });

        swal({
            title: "Are you sure?",
            text: "You are about to reset this user's password!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, reset it!",
            cancelButtonText: "No, cancel plx!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            if (isConfirm) {
                $.get(url, function (data) {
                    if (data) {
                        swal("Success!", "Password reset successful, user will receive email", "success");
                    } else {
                        swal("Error!", "Could not reset password", "error");
                    }
                })

            }

        });
    });


    $("#add-user-form").on("submit", function (e) {

        e.preventDefault();
        alert("Submitting....")
        submitForm();
    })

    $(document).on("click", ".reset-link", function (e) {
        e.preventDefault();

        var userId = $(this).attr("data-id");
        var url = `/admin/resend-link/${userId}`;

        swal({
            title: "Are you sure?",
            text: "You are about to resend activation link to user!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, reset it!",
            closeOnConfirm: false
        }, function () {

            $.post(url, function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Success!", "Password reset link sent successful, user will receive email", "success");
                        break;
                    case "FAILED":
                        swal("Error!", "Could not send reset link", "error");
                        break;
                }

            })
        });

    })


    function udatePic() {

        var form = $('#picForm')[0];
        var data = new FormData(form);
        var id = $('#seekerId').val();

        data.append(id, id);
        $.ajax({
            type: "POST",
            url: '/upload-pic?id=' + id,
            enctype: 'multipart/form-data',
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,

            success: function (data) {
                location.reload();

            },
            error: function (e) {
                location.reload();
            }

        });
    }

    function submitForm() {

        var telphone = $('#telephone').val();
        var email = $('#email').val();
        var username = $('#username').val();
        var role = $('#roles').val();
        var fn = $('#fn').val();
        var ln = $('#ln').val();
        var password = $('#password').val();

        // fn ln username password telphone email role,Long id staffId
        let data = {
            fn: fn,
            ln: ln,
            username: username,
            password: password,
            telphone: telphone,
            email: email,
            role: role
        }

        var url = `/admin/create-user`;


        var modal = $('#signupModal');

        //String ,String ,String ,String ,String role
        // disabled the submit button
        $("#btnSubmit").prop("disabled", true);
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,
            success: function (data) {

                swal({
                    title: "Success!",
                    text: "User update was successful!",
                    type: "success",
                    icon: "success",
                    button: "Ok!"
                })
                    .then((value) => {
                        $("#btnSubmit").prop("disabled", false);
                    });
                modal.modal('hide');
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't update user!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!",


                });

                $("#savBtn").prop("disabled", false);

            }

        });


    }
});
 