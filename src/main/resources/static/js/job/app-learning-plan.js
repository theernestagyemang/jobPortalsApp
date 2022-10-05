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
        var id = $("#userId").val();
        var name = $("#name").val();
        var description = $("#description").val();
        var price = $("#price").val();
        var color = $("#color").val();

        var data = {
            name: name,
            desc: description,
            price: price,
            id: id,
            color: color
        };

        var url = `/recruiter/learning-plan`;
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

    })
    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-learning-plan/${userId}`;
        swal({
            title: "Are you sure?",
            text: "This is will delete this learning-plan !",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
            $.get(url, function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Success!", "Learning Plan has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete learning planc", "error");
                        break;
                }
            })
        });

    });

    function clearText() {
        $('#userId').val(0);
        $('#description').val("");
        $('#name').val("");
        $('#price').val(0);
        $('#color').val("");

    }

    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/find-learning-plan/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href

            $('#userId').val(user.id);
            $('#description').val(user.description);
            $('#name').val(user.name);
            $('#price').val(user.price);
            $('#color').val(user.color);

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

        $.get("/recruiter/learning-plan/api", function (cartItems) {
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.name,
                        item.description,
                        item.color,
                        item.price,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Learning Plan"> <i class="fa fa-edit"></i> </a>
                         <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);

                });
            }
        });
    }

    $('.select2').select2();
})