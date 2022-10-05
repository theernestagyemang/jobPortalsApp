$(function () {

    $('a.edit-education').click(function (e) {
        e.preventDefault();

        var modal = $('#education');
        modal.data('url', $(this).attr('href'));

        var url = $(this).attr('href');
        $.get(url, function (data) {

            $('#nameOfSchool').val(data.institutionName);
            $('#educationLevel').val(data.qualificationReceived);
            $('#programStudied').val(data.programStudied);
            $("#course").val(data.courseDescription);
            $("#gradYear").val(data.yearGraduated);
            $("#startYear").val(data.yearStarted);
            $('#schooId').val(data.id);

        });

        modal.modal('show');
    });

    $("a.delete-education").on("click", function (e) {
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


    $("#educationFrm").on("submit", function (e) {
        e.preventDefault();
        alert("Updating............")
        updateSchool();
    });


    function updateSchool() {

        var nameOfSchool = $('#nameOfSchool').val();
        var jobSeekerId = parseInt($('#rsSeekerId').val());
        var course = $('#course').val();
        var educationLevel = $("#educationLevel option:selected").text();
        var gradYear = $('#gradYear').val();
        var startYear = $('#startYear').val();
        var schooId = parseInt($('#schooId').val());

        let shool = {
            nameOfSchool: nameOfSchool,
            course: course,
            educationLevel: educationLevel,
            gradYear: gradYear,
            jobSeekerId: jobSeekerId,
            schooId: schooId,
            startYear: startYear
        };

        var modal = $('#education');
        var url = "/seeker/update-schools";
        $("#schoolBtn").prop("disabled", true);
        event.preventDefault();

        $.ajax({
            type: "POST",
            url: url,
            processData: false,
            data: JSON.stringify(shool),
            contentType: false,
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Your profile has been updated successfully!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        }).then((value) => {
                            $("#schoolBtn").prop("disabled", false);
                            location.reload();
                        });
                        modal.modal('hide');
                        break;
                    default:
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't update your profile!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        modal.modal('hide');
                        $("#schoolBtn").prop("disabled", false);
                        break;
                }
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
});