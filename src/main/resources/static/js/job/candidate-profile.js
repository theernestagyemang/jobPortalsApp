function udatePic() {

    var form = $('#picForm')[0];
    var data = new FormData(form);
    var id = $('#seekerId').val();

    data.append(id, id);

    $.ajax({
        type: "POST",
        url: '/upload-seeker-pic?id=' + id,
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,

        success: function (data) {
            location.reload();
        },
        error: function (e) {
            location.reload();
        }

    });
}

$(function () {
    $('.lozad').Lazy();

    $('#myHeader').removeClass("forsticky");

    $("a.blacklist").on("click", function (e) {
        e.preventDefault();
        var modal = $('#deleteConfirmModal');
        modal.data('url', $(this).attr('href'));
        var url = $(this).attr('href');

        $.get(url, function (data, status) {
            $("#seekerId").val(data.id);
            $("#seekerName").html(data.fullName);
        });

        var url =
            modal.data('request_type', $(this).data('request-type'));
        modal.modal('show');
    });

    $("#blacklistForm").on("submit", function (e) {
        e.preventDefault();

        var tx = $("#msg").val();
        var seekerId = $("#seekerId").val();
        var modal = $('#deleteConfirmModal');

        var url = "/blacklist?tx=" + tx + "&id=" + seekerId;
        $("#deleteConfirm").prop("disabled", true);

        $.ajax({
            type: "POST",
            url: url,

            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                switch (data) {
                    case "FAILED":
                    case "ERROR":
                        swal("Unsuccessful", "Could not blacklist candidate", "error");
                        break;
                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Candiate blacklisted!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                $("#deleteConfirm").prop("disabled", false);

                            });
                        modal.modal('hide');
                }
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!", text: "Sorry, We couldn't update user!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!",
                });

                $("#deleteConfirm").prop("disabled", false);

            }

        });
    });
});