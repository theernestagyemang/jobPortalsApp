$(function () {

    if ($('#searchTerm').val() === "0") {
        $('#searchTerm').val("");
    }


    $(document).on("click", ".blacklist", function (e) {
        e.preventDefault();
        var dataid = $(this).attr("data-id");

        var modal = $('#deleteConfirmModal');
        modal.data('url', $(this).attr('href'));

        var url = `/findSeeker/${dataid}`;

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
        $("#deleteConfirm").prop("disabled", true);

        $("#deleteConfirm").html(`<i class="fa fa-circle-o-notch spin"></i> Processing....`);

        var tx = $("#msg").val();
        var seekerId = $("#seekerId").val();
        var modal = $('#deleteConfirmModal');

        var url = "/blacklist?tx=" + tx + "&id=" + seekerId;


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
                        $("#deleteConfirm").prop("disabled", false);
                        $("#deleteConfirm").html("Blacklist");
                        break;
                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Candiate blacklisted!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        });
                        $("#deleteConfirm").prop("disabled", false);
                        $("#deleteConfirm").html("Blacklist");
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


    $("a.open-coverletter").on("click", function (e) {
        e.preventDefault();
        var modal = $('#coverLetter');
        modal.data('url', $(this).attr('href'));
        var url = $(this).attr('href');

        $.get(url, function (data, status) {
            $("#cv").val(data.coverLetter);
            $("#seekerName").html(data.fullName);
        });


        modal.data('request_type', $(this).data('request-type'));
        modal.modal('show');
    });
});

function findShortlisted(val) {
    var url = "/findShortlisted/" + val;
    var shortlist = 0;
    $.get(url, function (data) {
        shortlist = data;
    });
    return shortlist;
}
 