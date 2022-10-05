let page = 1;
let totalPages = 0;


$(function () {

    $("#empDiv").toggle();
    $("#employeeDiv").toggle();
    $("#forgot-password").toggle(1000);

    $("#more").on('click', function (e) {
        e.preventDefault();
        reloadCategories();
    });

    $("#empClick").click(function (e) {
        e.preventDefault();
        var text = $(this).html();
        if (text === "Read More") {
            $(this).html("Read Less");
        } else {
            $(this).html("Read More");
        }

        $("#empDiv").toggle("slow", function () {
            // Animation complete.
        });
    });
    $("#employeeClick").click(function (e) {
        e.preventDefault();
        var text2 = $(this).html();

        if (text2 === "Read More") {
            $(this).html("Read Less");
        } else {
            $(this).html("Read More");
        }

        $("#employeeDiv").toggle("slow", function () {
            // Animation complete.
        });
    });

    $('.lozad').Lazy();

    $('#login').submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();


        var name = $('#fullName').val();
        var email = $('#em').val();
        var tel = $('#phone').val();

        // name,String email, String tel
        var url = "/create-cv-request?name=" + name + "&email=" + email + "&tel=" + tel;
        var modal = $('#cvConfirmModal');

        // disabled the submit button
        $("#btnSave").prop("disabled", true);

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
                        $("#btnSave").prop("disabled", false);
                    });
                } else {
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, we couldn't submit your request, Try again next time!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#btnSave").prop("disabled", false);
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
                $("#btnSave").prop("disabled", false);
            }

        });

    });

    $("#empForm").toggle(1000);
    $("#jobSeekerForm").toggle(1000);

    $("#job_seeker").click(function () {

        if ($("#empForm").is(":visible")) {
            $("#empForm").toggle(1000);
        }
        if ($("#jobSeekerForm").is(":visible")) {

        } else {
            $("#jobSeekerForm").toggle(1000);

        }

    });
    $("#job_employer").click(function () {

        if ($("#jobSeekerForm").is(":visible")) {
            $("#jobSeekerForm").toggle(1000);
        }
        if ($("#empForm").is(":visible")) {

        } else {
            $("#empForm").toggle(1000);

        }
    });

    let url = "/all-categories";

    $.ajax({
        type: "GET",
        url: url,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            var content = data.content;
            totalPages = data.totalPages;

            $.each(content, function (idx, elem) {

                var markup = "<li class='listLine'> " +
                    " <a title='" + elem + "' href='/job-by-category?ct=" + elem + "'> <i class='fa fa-search'></i> <em>" + elem + "</em></a>"
                    + "</li>";
                $("#categoriesBtn").append(markup);
            });
        },
        error: function (e) {
            console.log("Sorry, We couldn find Items");
        }
    });


});

function clearBtn() {
    $("#categoriesBtn").empty();
    resetTotal();
}

function resetTotal() {
    if (page > totalPages) {
        page = 0;
    }

}

function reloadCategories() {
    clearBtn();

    page = page + 1;
    let modifiedUrl = "/all-categories?page=" + page;

    addCategoryButtons(modifiedUrl);
    if (page === totalPages) {
        page = 0;
    }
}

function addCategoryButtons(url) {

    $.get(url, function (data) {
        var content = data.content;
        $.each(content, function (idx, elem) {

            var markup = "<li class='listLine'> " +
                " <a  title='" + elem + "' href='/job-by-category?ct=" + elem + "'> <i class='fa fa-search'></i> <em>" + elem + "</em></a>"
                + "</li>";
            $("#categoriesBtn").append(markup);

        });

    });
}
            
         
