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

        var name = $("#name").val();
//                      var userType = $("#user-type").val();
        var price = $("#price").val();
        var comment1 = $("#comment1").val();
        var comment2 = $("#comment2").val();
        var comment3 = $("#comment3").val();
        var comment4 = $("#comment4").val();
        var comment5 = $("#comment5").val();
        var comment6 = $("#comment6").val();
        var data = {
            id: 0,
            name: name,
            price: price,
            comment1: comment1,
            comment2: comment2,
            comment3: comment3,
            comment4: comment4,
            comment5: comment5,
            comment6: comment6
        };
        console.log(data);
        var url = `/admin/add-package`;

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
                        swal("Success!", "Package saved successfully", "success");
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
        var url = `/recruiter/delete-assessment/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this package!",
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
                        swal("Success!", "Assessment has been deleted successfully", "success");
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
        $('#name').val("");
        $('#price').val("");
        $('#comment1').val("");
        $('#comment2').val("");
        $('#comment3').val("");
        $('#comment4').val("");
        $('#comment5').val("");
        $('#comment6').val("");
    }

    $("#add-user-form1").on("submit", (e) => {
        e.preventDefault();
        var courseFeatured = $("#featured1").val();
        var featured = false;
        if (courseFeatured === "Featured Course") {
            featured = true;
        }
        const data = {
            id: $("#userId1").val(),
            courseTitle: $("#courseTitle1").val(),
            courseCategory: $("#courseCategory1").val(),
            status: 'Active',
            description: $("#courseDescription1").val(),
            featured: featured,
            price: $("#price1").val()
        }
//        console.log(data);
    })
    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/find-assessment/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href
            console.log(user);
            if (user.active) {
                $('#ass-status').val("Active");
            } else {
                $('#ass-status').val("Inactive");
            }
            $("#userId").val(user.id);
            $("#title").val(user.name);
            $("#price").val(user.price);
            $("#duration").val(user.duration);
            $("#description").val(user.benefits);

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


    function rerender() {
        clearTable();
        $.get("/admin/packages", function (cartItems) {

            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.name,
                        `GH₵ ${item.price}`,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }


})