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

        var title = $("#rating-title").val();
        var minScore = $("#min-score").val();
        var maxScore = $("#max-score").val();

        var data = {
            id: 0,
            title: title,
            minScore: minScore,
            maxScore: maxScore
        };
        console.log(data);
        var url = `/admin/rating`;

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
                        swal("Success!", "Rating saved successfully", "success");
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
        var url = `/admin/delete-rating/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this rating!",
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
                        swal("Success!", "Rating has been deleted successfully", "success");
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
        $("#rating-title").val("");
        $("#min-score").val("");
        $("#max-score").val("");
    }

    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/admin/rating/${dataId}`;

        $.get(href, function (rating, status) { //Declare a GET that takes in href
            $("#rating-title").val(rating.title);
            $("#min-score").val(rating.minScore);
            $("#max-score").val(rating.maxScore);
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
        $.get("/admin/ratings", function (cartItems) {

            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.title,
                        item.minScore,
                        item.maxScore,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }


})