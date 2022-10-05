/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {


    $('a.add-itskills').click(function (e) {
        var modal = $('#itskills');
        modal.modal('show');
    });


    $('a.edit-itskills').click(function (e) {
        e.preventDefault();

        $('#itskillsId').val(0);
        var modal = $('#itskills');
        modal.data('url', $(this).attr('href'));
        var url = modal.data('url');

        $.get(url, function (data, status) { //Declare a GET that takes in href

            $('#iTskill').val(data.skill);
            $('#iTversion').val(data.version);
            $('#lastUsed').val(data.lastUsed);
            $("#monthExp").val(data.monthExperience);
            $("#yearExp").val(data.yearExperience);
            $("#itskillsId").val(data.id);
            $("#proficiency").val(data.proficiency);

            $("#demo").html(data.proficiency);

        });

        modal.modal('show');
    });

    $("#txtConfirmPassword").keyup(function () {
        if ($("#password").val() != $("#txtConfirmPassword").val()) {
            $("#msg").html("Password do not match").css("color", "red");
            $("#btnUpdatepass").prop("disabled", true);
        } else {
            $("#msg").html("Password matched").css("color", "green");
            $("#btnUpdatepass").prop("disabled", false);
        }
    });

    $("#password").keyup(function () {

        if ($("#txtConfirmPassword").val() != "") {

            if ($("#password").val() != $("#txtConfirmPassword").val()) {
                $("#msg").html("Password do not match").css("color", "red");
                $("#btnUpdatepass").prop("disabled", true);
            } else {
                $("#msg").html("Password matched").css("color", "green");
                $("#btnUpdatepass").prop("disabled", false);
            }
        }

    });

    $("#itSkillsFrm").on("submit", function (e) {
        e.preventDefault();
        updateItSkills();
    });


    var slider = document.getElementById("proficiency");
    var output = document.getElementById("demo");
    output.innerHTML = slider.value; // Display the default slider value

    // Update the current slider value (each time you drag the slider handle)
    slider.oninput = function () {
        output.innerHTML = this.value;
    }

});


function updateItSkills() {


    var skill = $('#iTskill').val();
    var id = $('#rsSeekerId').val();

    var version = $('#iTversion').val();
    var lastUsed = $("#lastUsed").val();
    var monthExp = $("#monthExp option:selected").text();
    var yearExp = $("#yearExp").val();
    var strId = $("#itskillsId").val();
    var proficiency = parseInt($("#proficiency").val());
    var skillId = parseInt(strId);


    var url = "/update-itSkills?skill=" + skill + "&version=" + version + "&lastUsed=" + lastUsed + "&monthExp=" + monthExp + "&yearExp=" + yearExp + "&id=" + id + "&skillId=" + skillId + "&proficiency=" + proficiency;

    var modal = $('#itskills');
    // disabled the submit button
    $("#itSkillsBtn").prop("disabled", true);
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
                    $("#itSkillsBtn").prop("disabled", false);
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
            $("#itSkillsBtn").prop("disabled", false);

        }

    });

}