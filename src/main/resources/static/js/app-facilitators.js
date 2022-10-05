$(function () {
    reloadPage();

    function clearText() {
        $("#first-name").val("");
        $("#lastName").val("");
        $("#email").val("");
        $("#phone").val("");
    }

    $(document).on("click", ".edit-facilitator", function (e) {
        e.preventDefault();
        clearText();
        var id = $(this).attr("data-id");
        var url = `/recruiter/facilitors/api/${id}`;


        $.get(url, function (data) {
            //firstName lastName email phone


            $("#firstName").val(data.firstName);
            $("#lastName").val(data.lastName);
            $("#email").val(data.email);
            $("#phone").val(data.contactNumber);
            $("#experience").val(data.experience);
            $("#profile").val(data.profile);
            $("#skill").val(data.skill);
            $("#title").val(data.title);
            $("#facilitator-id").val(data.id);
        })
        var modal = $('#modal-center');
        modal.modal('show');
    })

    //recruiter/delete-facilitator
    $(document).on("click", ".delete-facilitator", function (e) {
        e.preventDefault();
        var id = $(this).attr("data-id");
        var url = `/recruiter/delete-facilitator/${id}`;

        swal({
            title: "Are you sure?",
            text: "You are about to delete a facilitator!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
            $.get(url, function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Deleted!", "Facilitator has been deleted.", "success");
                        reloadPage();
                        break;
                    default:
                        swal("Failed!", "Could not be deleted.", "error");
                        break;
                }
            })

        });

    })

    function reloadPage() {
        var url = `/facilitor/api`;
        $("#team-div").empty();

        $.get(url, function (data) {

            $.each(data, function (idx, item) {

                var markup = `<div class="col-12 col-lg-4">
				<div class="box ribbon-box">
				  <div class="ribbon-two ribbon-two-primary"><span>${item.title}</span></div>
				  <div class="box-header no-border p-0">				
					<a href="#">
					  <img class="img-fluid" src="/uploads/${item.fileName}" alt="" style="height: 200px">
					</a>
				  </div>
				  <div class="box-body">
						<div class="user-contact list-inline text-center">
							<a href="#" data-id="${item.id}" class="btn btn-circle mb-5 btn-info edit-facilitator"><i class="fa fa-edit"></i></a>
							<a href="#" data-id="${item.id}" class="btn btn-circle mb-5 btn-danger delete-facilitator"><i class="fa fa-trash"></i></a>				
						</div>
					  <div class="text-center">
						<h3 class="my-10"><a href="#">${item.firstName} ${item.lastName}</a></h3>
						<h6 class="user-info mt-0 mb-10 text-fade">${item.contactNumber}</h6>
					  </div>
				  </div>
				</div>
			  </div>`;

                $("#team-div").append(markup);
            });
            //alert(markup)
            //main .innerHTML =`markup`;
            //
        })
    }

    submitFacilitator();


    function submitFacilitator() {

        var form = document.getElementById('submit-frm');
        var policyUploadFile = document.getElementById('customFile');
        var errorPanel = $(".error-panel");


        var url = `/recruiter/facilitors`;


        form.onsubmit = function (event) {
            event.preventDefault();

            var firstName = $("#firstName").val();
            var lastName = $("#lastName").val();
            var email = $("#email").val();
            var contactNumber = $("#phone").val();
            var experience = $("#experience").val();
            var skill = $("#skill").val();
            var profile = $("#profile").val();
            var title = $("#title").val();
            var id = $("#facilitator-id").val();

            var data22 = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                contactNumber: contactNumber,
                experience: experience,
                title: title,
                skill: skill,
                profile: profile,
                id: id
            }

            $("#btnSaveFacilitaror").prop("disabled", true);
            // Get policy files from the input
            var policyfiles = policyUploadFile.files;

            //Grab only one file since this script disallows multiple file uploads.
            var policyfile = policyfiles[0];
            var formData = new FormData();

            if (policyfiles.length !== 0) {
                formData.append('policy', policyfile, policyfile);

                if (policyfile.size >= 2000000) {
                    errorPanel.removeClass("hide");
                    swal("Error!", "You cannot upload this file because its size exceeds the maximum limit of 2 MB.", "error");
                    $("#btnSaveFacilitaror").prop("disabled", false);
                    return;
                }
            }

            // Add the file to the AJAX request.

            formData.append('fn', data22.firstName);
            formData.append('ln', data22.lastName);
            formData.append('email', data22.email);
            formData.append('tel', data22.contactNumber);
            formData.append('id', data22.id);
            formData.append('title', data22.title);
            formData.append('skill', data22.skill);
            formData.append('experience', data22.experience);
            formData.append('profile', data22.profile);


            // Set up the request.
            var xhr = new XMLHttpRequest();
            // Open the connection.
            xhr.open('POST', url, true);


            // Set up a handler for when the task for the request is complete.
            xhr.onload = function () {
                if (xhr.status === 200) {
                    swal("Good job!", "System updated successfully", "success");
                    reloadPage();
                    form.reset();
                    $("#btnSaveFacilitaror").prop("disabled", false);
                } else {
                    swal("Failed!", "Could not save", "error");
                    $("#btnSaveFacilitaror").prop("disabled", false);
                }
            };

            // Send the data.
            xhr.send(formData);
        }
    }


})