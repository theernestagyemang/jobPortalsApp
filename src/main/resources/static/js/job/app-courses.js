$(function () {
    var now = new Date().toLocaleDateString('en-us', {weekday: "long", year: "numeric", month: "short", day: "numeric"})
    $("#today-date").html(now);


    var cartTable = $('#cartTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });
    rerender();

    $.get("/recruiter/current-user", function (data) {
        var fileName = data.filename;
        var username = data.name;

        $("#current-user-pic").attr("src", `/uploads/${fileName}`);
        $(".current-user-name").html(username);
    })

    submitCourse()

    //File Upload
    function submitCourse() {
        var form = document.getElementById('add-user-form');
//            var policyUploadFile = document.getElementById('coursePicture');
        var errorPanel = $(".error-panel");
        var statusDiv = document.getElementById('status');

        form.onsubmit = function (event) {
            event.preventDefault();
            statusDiv.innerHTML = 'Uploading . . . ';
            var policyUploadFile = document.getElementById('coursePicture');

            var courseId = $("#userId").val();
            var courseTitle = $("#courseTitle").val();
            var courseCategory = $("#courseCategory").val();
            var courseDescription = $("#courseDescription").val();
            var courseFeatured = $("#featured").val();
            var status = $("#course-status").val();
            var price = $("#price").val();
            var firstTime = $("#first-time").val();
            var featured = false;
            if (courseFeatured === "Featured Course") {
                //            console.log("not featured")
                featured = true;
            }

            if(firstTime == 0){
                status = "Inactive";
            }
            // console.log(status);
            var data = {
                id: courseId,
                courseTitle: courseTitle,
                courseCategory: courseCategory,
                description: courseDescription,
                featured: featured,
                status: status,
                price: price
            };
            // console.log(data)

            var policyfiles = policyUploadFile.files;
            //Grab only one file since this script disallows multiple file uploads.
            var policyfile = policyfiles[0];

            // Create a FormData object.
            var formData = new FormData();

            if (policyfiles.length !== 0) {
                formData.append('picture', policyfile, policyfile.name);
                if (policyfile.size >= 2000000) {
                    errorPanel.removeClass("hide");
                    statusDiv.innerHTML = 'You cannot upload this file because its size exceeds the maximum limit of 2 MB.';
                    return;
                }
            }

            formData.append('id', data.id);
            formData.append('courseTitle', data.courseTitle);
            formData.append('courseCategory', data.courseCategory);
            formData.append('description', data.description);
            formData.append('price', data.price);
            formData.append('featured', data.featured);
            formData.append('courseStatus', data.status);
            // Set up the request.
            var xhr = new XMLHttpRequest();

            // Open the connection.
            xhr.open('POST', '/recruiter/courses', true);
            xhr.onload = function () {
                if (xhr.status === 200) {
                    errorPanel.removeClass("hide");
                    errorPanel.removeClass("gradient-45deg-red-pink");
                    errorPanel.addClass("gradient-45deg-green-teal");
                    rerender();
                    form.reset();
                    swal("Success!", "Course created successfully", "success");
                } else {
                    errorPanel.removeClass("hide");
                    swal("Error!", "An error occurred during the upload. Please try again", "error");
                }
            };

            xhr.send(formData);
        }
    }

    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-course/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this course!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel plx!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            $.get(url, function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Success!", "Course has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete course", "error");
                        break;
                }

            })

        });
    });

    function clearText() {
        $('#userId').val(0);
        $('#courseTitle').val("");
        $('#courseCategory').val("");
    }

    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/findCourse/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href

            if (user.featuredCourse) {
                $('#featured').val("Featured Course");
            } else {
                $('#featured').val("Not Featured Course");
            }
            $('#userId').val(user.id);
            $('#courseTitle').val(user.courseTitle);
            $('#courseCategory').val(user.courseCategory.name);
            $('#courseDescription').val(user.description);
            $('#price').val(user.price);
            $('#course-status').val(user.status);
            $("#first-time").val(2);
        });
        modal.modal('show');
    });

    function addCommas(nStr) {
        nStr += '';
        var x = nStr.split('.');
        var x1 = x[0];
        var x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        return x1 + x2;
    }

    $(document).ajaxStart(function () {
        Pace.restart();
    })


    function clearTable() {
        cartTable.clear().draw();
    }


    $.get("/course-categories", function (data) {
        $(".courseCategory").empty();
        $('.courseCategory').append($('<option>').val("default").text("Choose course category "));
        $.each(data, function (idx, elem) {
            $('.courseCategory').append($('<option>', {
                value: elem.id,
                text: elem.category
            }));
        });
    });


    function rerender() {
        clearTable();
        $.get("/recruiter/courses/api", function (cartItems) {

            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.courseTitle,
                        item.category,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                         <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>
                         `,
                        `<a href="/course/contents/${item.id}" data-request-type="delete" class="btn btn-success btn-sm">
                            Course Content
                         </a>`
                    ]).draw(true);
                });
            }
        });
    }


})