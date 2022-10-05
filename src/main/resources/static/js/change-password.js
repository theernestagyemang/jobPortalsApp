/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function startFu() {
    alert('Starting....');
}

$('#password, #confirm_password').on('keyup', function () {

    if (!$('#password').val() && !$('#confirm_password').val()) {
        $('#message').html('');

    } else {
        if ($('#password').val() == $('#confirm_password').val()) {
            $('#message').html('Matching').css('color', 'green');
            $("#btnSave").prop("disabled", false);
        } else {
            $("#btnSave").prop("disabled", true);
            $('#message').html('Passwords dont Match').css('color', 'red');
        }
    }

});


$('#btnSave').click(function () {
    //stop submit the form, we will post it manually.
    event.preventDefault();

    var pass = $('#password').val();
    var modal = $('#car-details').val();

    $("#btnSave").prop("disabled", true);

    var url = "/change-password-2?pass=" + pass;
    $.get(url, function (assessment, status) { //Declare a GET that takes in href

        if (assessment == true) {
            swal({
                title: "Success!",
                text: "Your password has been changed successfully!",
                type: "success",
                icon: "success",
                button: "Ok!"
            })
                .then((value) => {
                    $("#btnSave").prop("disabled", false);
                    location.reload();
                });
            modal.modal('hide');
        } else {
            swal({
                title: "Unsuccessful!",
                text: "Sorry, We couldn't change your password!",
                type: "error",
                icon: "danger",
                button: "Ok!"
            });

            $("#btnSave").prop("disabled", false);
            modal.modal('hide');
        }

    });
    // disabled the submit button
});
           
        