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


    $("#add-user-form").on("submit", function (event) {
        event.preventDefault();
        const id = $("#service-id").val();
        const name = $("#name").val();
        const price = $("#price").val();
        const duration = $("#duration").val();
        var status = $("#service-status").val();
        const description = $("#description").val();
        const benefit = $("#benefit").val();

        if (status === "Active") {
             status = true;
        }else {
            status = false;
        }

        const data = {
            id: id,
            name: name,
            price: price,
            duration: duration,
            description: description,
            status: status,
            benefits: benefit
        };

        $.ajax({
            type: "POST",
            url:"/admin/create-service",
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,
            success: function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Success!", "Service type saved successfully", "success");
                        rerender();
                        clearText();
                        break;
                    default:
                        swal("Error!", "Could not save", "error");
                        break;
                }
            },
            error: function (e) {
                swal("Error!", "Could not save", "error");
            }
        });

    });

    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/admin/services/delete/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this service type!",
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
                        swal("Success!", "" +
                            "Service Type has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete Assessment Round", "error");
                        break;
                }

            })

        });
    });

    function clearText() {
        $("#service-id").val(0);
        $("#name").val('');
        $("#price").val('');
        $("#duration").val('');
        $("#service-status").val('');
        $("#description").val('');
        $("#benefit").val('');
    }


    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/admin/services/${dataId}`;

        $.get(href, function (service, status) { //Declare a GET that takes in href
            if(service.status){
                $("#service-status").val("Active");
            }else {
                $("#service-status").val("Inactive");
            }

            $("#service-id").val(service.id);
            $("#name").val(service.name);
            $("#price").val(service.price);
            $("#duration").val(service.duration);

            $("#description").val(service.description);
            $("#benefit").val(service.benefits);
        });
        modal.modal('show');
    });




    function clearTable() {
        cartTable.clear().draw();
    }


    function rerender() {
        clearTable();
        $.get("/admin/services", function (cartItems) {
            if (cartItems.length !== 0) {
                // console.log(cartItems)
                cartItems.map(function (item, index) {
                    cartTable.row.add([
                       index+1,
                        item.name,
                        item.price,
                        item.duration,
                        item.status? "Active" : "Inactive",
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }


})