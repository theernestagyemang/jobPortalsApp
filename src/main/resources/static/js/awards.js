/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {


    $('a.edit-awards').click(function (e) {
        e.preventDefault();

        var modal = $('#awards');
        modal.data('url', $(this).attr('href'));

        var url = $(this).attr('href');
        $.get(url, function (data) {

            $('#title').val(data.title);
            $('#fromYear').val(data.fromYear);
            $('#toYear').val(data.toYear);
            $('#awardId').val(data.id);

        });

        modal.modal('show');
    });

    $("a.delete-awards").on("click", function (e) {
        e.preventDefault();
        var url = $(this).attr("href");

        swal({
                title: "Are you sure?",
                text: "You will not be able to recover this !",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                confirmButtonText: "Yes, delete it!",
                cancelButtonText: "No, cancel plx!",
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    $.get(url, function (del) {
                        if (del) {
                            swal("Deleted!", "Your institution has been deleted.", "success");
                            table.$('tr.selected').remove().draw(false);
                        } else {
                            swal("Error", "Sorry we couldnt delete your institution :)", "error");
                        }
                    });
                } else {
                    swal("Cancelled", "Your institution is safe :)", "error");
                }
            });

    });


    $("#frm").on("submit", function (e) {
        e.preventDefault();
        updateAward();
    });
});


function updateAward() {


    var title = $('#title').val();
    var fromYear = $('#fromYear').val();
    var toYear = $('#toYear').val();
    var awardId = parseInt($('#awardId').val());

    //Integer id, Integer fromYear, String title, Integer toYear)

    var url = "/awards?title=" + title + "&fromYear=" + fromYear + "&toYear=" + toYear + "&id=" + awardId;

    var modal = $('#education');
    // disabled the submit button
    $("#schoolBtn").prop("disabled", true);
    event.preventDefault();
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
            })
                .then((value) => {
                    $("#schoolBtn").prop("disabled", false);
                    location.reload();
                });
            modal.modal('hide');
        },
        error: function (e) {
            swal({
                title: "Unsuccessful!",
                text: "Sorry, We couldn't update your profile!",
                type: "error",
                icon: "danger",
                button: "Ok!"
            });
            modal.modal('hide');
            $("#schoolBtn").prop("disabled", false);

        }

    });

}