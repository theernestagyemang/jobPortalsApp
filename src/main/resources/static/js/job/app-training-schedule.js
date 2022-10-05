$(function () {
    var now = new Date().toLocaleDateString('en-us', {weekday: "long", year: "numeric", month: "short", day: "numeric"})
    $("#today-date").html(now);

    $.get("/recruiter/current-user", function (data) {
        var fileName = data.filename;
        var username = data.name;

        $("#current-user-pic").attr("src", `/uploads/${fileName}`);
        $(".current-user-name").html(username);
    })


    $("#add-user-form").on("submit", function (e) {
        e.preventDefault();
        var policyUploadFile = document.getElementById('picture');
        var form = document.getElementById('add-user-form');
        var id = $("#userId").val();
        var courseScheduled = $("#title").val();
        var location = $("#location").val();
        var status = $("#status").val();
        var eventDate = $("#eventDate").val();
        var timeFrom = $("#time-from").val();
        var timeTo = $("#time-to").val();
        var trainingCost = $("#training-cost").val();
        var description = $("#description").val();


        var data = {
            eventDate: eventDate,
            location: location,
            courseScheduled: courseScheduled,
            trainingStatus: status,
            timeTo: timeTo,
            timeFrom: timeFrom,
            description: description,
            trainingCost: trainingCost,
            id: id
        };

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
        formData.append('location', data.location);
        formData.append('course', data.courseScheduled);
        formData.append('eventDate', data.eventDate);
        formData.append('timeFrom', data.timeFrom);
        formData.append('timeTo', data.timeTo);
        formData.append('description', data.description);
        formData.append('trainingCost', data.trainingCost);
        formData.append('trainingStatus', data.trainingStatus);

        // Set up the request.
        var xhr = new XMLHttpRequest();

        // Open the connection.
        xhr.open('POST', '/recruiter/training-schedule', true);

        // Set up a handler for when the task for the request is complete.
        xhr.onload = function () {
            if (xhr.status === 200) {
                rerender();
                form.reset();
                swal("Success!", "Training Schedule created successfully", "success");
            } else {
                swal("Error!", "An error occurred during the upload. Please try again", "error");
            }
        };
        xhr.send(formData);

    })
    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-training-schedule/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this training schedule!",
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
                        swal("Success!", "Training Schedule has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete training schedule", "error");
                        break;
                }
            })

        });
    });

    function clearText() {
        $('#userId').val(0);
        $('#time-to').val("");
        $('#time-from').val("");
//        $('#courseId').val("");
        $("#location").val("");
    }

    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/find-training-schedule/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href
            $('#userId').val(user.id);
            $('#title').val(user.courseScheduled);
            $('#status').val(user.trainingStatus);
            $("#location").val(user.location);
            $("#eventDate").val(user.eventDate);
            $("#time-from").val(user.timeFrom);
            $("#time-to").val(user.timeTo);
            $("#training-cost").val(user.training_cost);
            $("#description").val(user.description)
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

        $.get("/recruiter/training-schedule/api", function (cartItems) {
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.course,
                        item.location,
                        `GH₵ ${item.trainingCost}`,
                        `${item.month}, ${item.year}:  ${item.timeFrom} - ${item.timeTo}`,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Training Schedule"> <i class="fa fa-edit"></i> </a>
                         <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);

                });
            }
        });
    }

    $('.select2').select2();
})