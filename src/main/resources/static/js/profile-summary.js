$(function () {
    $('.lozad').Lazy();

    //work-experience

    $("#add-profile-summary").on("click", function (e) {
        $("#profile-summary-tex").val("");
    });

    $("#profile-sumary-frm").on("submit", function (e) {
        e.preventDefault();
        var summary = $("#profile-summary-tex").val();
        var seekerId = $("#seekerId").val();

        var url = `/seeker/profile-summary?description=${summary}&id=${seekerId}`
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

    })
    $('#jobProfile').summernote({
        placeholder: 'Please type your message here',
        tabsize: 2,
        height: 120,
        toolbar: [
            ['style', ['style']],
            ['font', ['bold', 'underline', 'clear']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['link', 'picture', 'video']],
            ['view', ['fullscreen', 'codeview', 'help']]
        ]
    });

    $(".delete-p-summary").on("click", function (e) {
        e.preventDefault();
        var seekerId = $("#seekerId").val();
        swal({
                title: "Are you sure?",
                text: "You are about to delete your profile summary !",
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
                            swal("Deleted!", "Your profile summary has been deleted.", "success");
                            table.$('tr.selected').remove().draw(false);
                        } else {
                            swal("Error", "Sorry we couldnt delete your profile summary :)", "error");
                        }
                    });
                }
            });

    });

});