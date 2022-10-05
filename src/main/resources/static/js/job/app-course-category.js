$(function () {
    var now = new Date().toLocaleDateString('en-us', {weekday: "long", year: "numeric", month: "short", day: "numeric"})
    $("#today-date").html(now);

    $.get("/recruiter/current-user", function (data) {
        var fileName = data.filename;
        var username = data.name;

        $("#current-user-pic").attr("src", `/uploads/${fileName}`);
        $(".current-user-name").html(username);
    })

    submitCourseCategory();

    //File Upload
    function submitCourseCategory() {

        var form = document.getElementById('add-category-form');
        var policyUploadFile = document.getElementById('categoryPicture');
        var errorPanel = $(".error-panel");

        var statusDiv = document.getElementById('status');

        form.onsubmit = function (event) {
            event.preventDefault();
            statusDiv.innerHTML = 'Uploading . . . ';

            var id = $("#userId").val();
            var name = $("#courseCategory").val();
            var description = $("#courseDescription").val();

            var data = {
                name: name,
                description: description,
                status: 'Active',
                id: id
            };

            // Get policy files from the input
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


            // Add the file to the AJAX request.
            formData.append('category', data.name);
            formData.append('description', data.description);

            var xhr = new XMLHttpRequest();

            // Open the connection.
            xhr.open('POST', '/recruiter/courseCategory', true);


            // Set up a handler for when the task for the request is complete.
            xhr.onload = function () {
//                console.log("xhr in course category",xhr)
                if (xhr.status === 200) {
                    errorPanel.removeClass("hide");
                    errorPanel.removeClass("gradient-45deg-red-pink");
                    errorPanel.addClass("gradient-45deg-green-teal");
                    rerender();
                    form.reset();

                    statusDiv.innerHTML = xhr.statusText + 'SUCCESS: Your upload is successful..';
                } else {
                    errorPanel.removeClass("hide");
                    statusDiv.innerHTML = 'An error occurred during the upload. Try again.';
                }
            };

            // Send the data.
            xhr.send(formData);
        }
    }

    $("#add-user-form").on("submit", function (e) {
        e.preventDefault();

        var id = $("#userId").val();
        var name = $("#courseCategory").val();
        var description = $("#courseDescription").val();

        var data = {
            name: name,
            description: description,
            status: 'Active',
            id: id
        };

        var url = `/recruiter/courseCategory`;
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {
                    case "SUCCESS":
                        rerender();
                        clearText();
                        swal("Success!", "Category created successfully", "success");
                        break;
                    default:
                        swal("Error!", "Could not save", "error");
                }

            },
            error: function (e) {
                swal("Error!", "Could not save", "error");
            }
        });

    })
    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-course-category/${userId}`;

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
        var href = `/recruiter/findCourseCategory/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href
            $('#userId').val(user.id);
            $('#courseCategory').val(user.name);
            $('#courseDescription').val(user.description);

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

    function clearTable() {
        cartTable.clear().draw();
    }

    function rerender() {

        clearTable();
        $.get("/recruiter/course-category/api", function (cartItems) {
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.name,
                        item.description,
                        `<img src="/uploads/${item.fileName}" style="height:50px">`,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                         <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);

                });
            }
        });
    }


})