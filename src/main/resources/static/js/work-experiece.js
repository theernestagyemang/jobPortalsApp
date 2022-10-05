/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {


    $('a.editWork').click(function (e) {
        e.preventDefault();
        var modal = $('#employment');
        modal.data('url', $(this).attr('href'));

        var url = $(this).attr('href');
        $.get(url, function (data) {

            $('#employment-designation').val(data.jobTitle);
            $('#employment-name').val(data.companyName);
            $('#curComp').val(data.stillWorkThere);
            $("#employment-start-year").val(data.yearStartedWork);
            $("#employement-start-month").val(data.monthStartedWork);
            $("#employment-end-year").val(data.yearStopedWork);
            $("#employment-end-month").val(data.monthStopedWork);
            $('#employment-job-profile').val(data.jobProfile);
            $('#curComp').val(data.stillWorkThere);
            $('#eduId').val(data.id);
            var status = (data.stillWorkThere) === 'Yes';

            $('#employ_no').prop('checked', !status);
            $('#employ_yes').prop('checked', status);
        });

        modal.modal('show');
    });

    $("a.delete-work").on("click", function (e) {
        e.preventDefault();
        var url = $(this).attr("href");

        swal({
                title: "Are you sure?",
                text: "You are about to delete your work experience !",
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
                            swal("Deleted!", "Your work experience has been deleted.", "success");
                            table.$('tr.selected').remove().draw(false);
                        } else {
                            swal("Error", "Sorry we couldnt delete your work experienc :)", "error");
                        }
                    });
                } else {
                    swal("Cancelled", "Your work experienc is safe :)", "error");
                }
            });

    });


    $("#employment-frm").on("submit", function (e) {
        e.preventDefault();

        updateEmployment();
    });
});


function updateEmployment() {
    //stop submit the form, we will post it manually.


    var designation = $('#employment-designation').val();
    var id = parseInt($('#seekerId').val());
    var org = $('#org').val();
    var curComp = $("#curComp option:selected").val();
    var startYear = $("#employment-start-year").val();

    var startMonth = $("#employement-start-month").val();
    var endYear = $("#employement-end-year").val();
    var endMonth = $("#employement-end-month").text();
    var jobProfile = $('#employment-job-profile').val();
    var stillWork = $('#curComp').val();
    var strEdu = $('#eduId').val();
    var eduId = parseInt(strEdu);
    var modal = $('#employment');

    var url = "/seeker/update-employment";

    let employment = {
        designation: designation,
        id: id,
        org: org,
        curComp: curComp,
        startYear: startYear,
        startMonth: startMonth,
        endYear: endYear,
        endMonth: endMonth,
        jobProfile: jobProfile,
        stillWork: stillWork,
        strEdu: strEdu,
        eduId: eduId
    }

    $("#employment-btn").prop("disabled", true);

    $.ajax({
        type: "POST",
        url: url,
        processData: false,
        data: JSON.stringify(employment),
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
                    })
                        .then((value) => {
                            $("#employment-btn").prop("disabled", false);
                            location.reload();
                        });
                    modal.modal('hide');
                    break;

                case "FAILED":
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, We couldn't update your profile!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#employment-btn").prop("disabled", false);
                    break;
                default:
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

            $("#employment-btn").prop("disabled", false);

        }

    });


}