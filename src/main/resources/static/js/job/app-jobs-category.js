$(function () {

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

    $('a.editBtn2').click(function (e) {
        e.preventDefault();

        var modal = $('#signupModal');
        modal.data('url', $(this).attr('href'));
        modal.data('request_type', $(this).data('request-type'));
        var href = modal.data('url');

        $.get(href, function (user, status) { //Declare a GET that takes in href
            $('#jobId').val(user.id);
            $('#name').val(user.name);
            $('#jobIcon').val(user.catIcon);
        });
        modal.modal('show');
    });

    $('a.editBtn').click(function (e) {
        e.preventDefault();

        $('#jobId').val(0);
        $('#name').val("");
        $('#jobIcon').val("");

        var modal = $('#signupModal');
        modal.modal('show');
    });

    $('#myform').on("submit", function (e) {

        e.preventDefault();

        var name = $('#name').val();
        var jobId = $('#jobId').val();
        var jobIcon = $('#jobIcon').val();


        var url = `/create-job-category?name=${name}&id=${jobId}&jobIcon=${jobIcon}`;
        var modal = $('#signupModal');
        // disabled the submit button
        $("#btnSubmit").prop("disabled", true);
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
                    text: "Job category updated successfully!",
                    type: "success",
                    icon: "success",
                    button: "Ok!"
                });
                modal.modal('hide')
                $("#btnSubmit").prop("disabled", false);
                location.reload();


            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't update job category!",
                    type: "error",
                    icon: "error",
                    button: "Ok!"
                });

                $("#btnSubmit").prop("disabled", false);
                modal.modal('hide');
            }

        });

    });

    $('a.delete-action').on("click", function (e) {
        e.preventDefault();


        var url = $(this).attr('href');
        var row = $(this).parents("tr");
        swal({
                title: "Are you sure?",
                text: "You will not be able to recover this job category!",
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
                            swal("Deleted!", "Your job category has been deleted.", "success");
                            row.remove();

                        } else {
                            swal("Error", "Sorry we couldnt delete your job category :)", "error");
                        }
                    });
                } else {
                    swal("Cancelled", "Your job category is safe :)", "error");
                }
            });
    });

    $('#deleteConfirm').click(function () {

        var modal = $('#deleteConfirmModal');
        var url = modal.data('url');


        $.get(url, function (data, status) { //Declare a GET that takes in href
            if (data == true) {
                swal({
                    title: "Success!",
                    text: "Job category deleted successfully!",
                    type: "success",
                    icon: "success",
                    button: "Ok!"
                })
                    .then((value) => {
                        $("#btnSubmit").prop("disabled", false);
                        location.reload();
                    });
                modal.modal('hide');
            }
        });


        modal.modal('hide');
    });

    function rerender() {

        $.get("/admin/all-job-categories", function (cartItems) {
            console.log(cartItems);
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                   cartTable.row.add([
                        item.id,
                        item.name,
                        item.grade,
                        item.entryDate,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                         <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);

                });
            }
        });
    }

});