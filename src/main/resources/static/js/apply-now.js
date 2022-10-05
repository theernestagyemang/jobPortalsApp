/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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