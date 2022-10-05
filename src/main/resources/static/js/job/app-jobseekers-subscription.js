$(function () {
    $(".paynow").on("click", function () {
        alert("Paying.........");
        createHubtelPayment();
    })

    $("#forgot-password").toggle(1000);
    var url88 = "/find-address";

    $.get(url88, function (data, status) {
        console.log(data.hodEmail);
        $('#comp-tel').html(data.companyAddress);
        $('#adabraka-email').html(data.hodEmail);
    });

    $('.lozad').Lazy();

    $('#btnSave2').on("click", function (event) {
        //stop submit the form, we will post it manually.

        event.preventDefault();

        var name = $('#requesterFullName').val();
        var email = $('#requesterEmail').val();
        var tel = $('#requesterPhone').val();
        var serviceType = $('#serviceType').val();

        // name,String email, String tel
        var url = "/create-cv-request?name=" + name + "&email=" + email + "&tel=" + tel + "&serviceType=" + serviceType;

        var modal = $('#cvConfirmModal');

        // disabled the submit button
        $("#btnSave2").prop("disabled", true);

        $.ajax({
            type: "POST",
            url: url,

            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                if (data) {
                    modal.modal('hide');
                    swal({
                        title: "Success!",
                        text: "Your request has been submitted Successfully, We will get back to you shortly!",
                        type: "success",
                        icon: "success",
                        button: "Ok!"
                    }).then((value) => {
                        $("#btnSave2").prop("disabled", false);
                    });
                } else {
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, we couldn't submit your request, Try again next time!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#btnSave2").prop("disabled", false);
                    modal.modal('hide');
                }
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, we couldn't submit your request, Try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });
                $("#btnSave2").prop("disabled", false);
            }

        });

    });

    $("#empForm").toggle(1000);

    $("#empForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });
    $("#jobSeekerForm").click(function () {
        $("#jobSeekerForm").toggle(1000);
        $("#empForm").toggle(1000);
    });

    $("#basicSup, #premiumSub").on('click', function (e) {
        e.preventDefault();
        var dataId = $(this).attr("data-id");
        createSubscription(dataId, 0);
    });

    $("#startNow").on('click', function (e) {
        e.preventDefault();

        $.ajax(`/find-auth-user`, {
            dataType: 'json',
            timeout: 500,
            success: function (data, status, xhr) {
                $("#requesterFullName").val(data.fullName);
                $("#requesterEmail").val(data.username);
                $("#requesterPhone").val(data.telephone);
            },
            error: function (error, status) {

            }
        });

        var modal = $("#cvConfirmModal");
        modal.modal('show');
    });


    let url = "/findFourCategories";

    $.ajax({
        type: "GET",
        url: url,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {

            $.each(data, function (idx, elem) {

                var markup = `<div class="col-lg-3 col-md-3 col-sm-3">
                                        <div class="quick-select">
                                            <a href="/job-by-category?ct=${elem}" title="">
                                                <i class="la la-line-chart "></i>
                                                <span>${elem}</span>
                                            </a>
                                        </div>
                                    </div>`;
                $("#categoriesBtn").append(markup);
            });
        },
        error: function (e) {
            console.log("Sorry, We couldn find Items");
        }
    });

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


});
   

 
      
    

    
    
   
 