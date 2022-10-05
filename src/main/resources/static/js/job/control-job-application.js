$(function () {
    logJobView();

    $(".apply-now-btn").on("click", function (e) {
        e.preventDefault();

        var url = $(this).attr("href");

        $.get("/authenticated", function (data) {
            if (!data) {

                swal({
                    title: "You must login to apply?",
                    text: "Do you want to login now!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, login !",
                    cancelButtonText: "No, cancel plx!",
                    closeOnConfirm: false,
                    closeOnCancel: false
                }).then(function (willDelete) {
                    if (willDelete) {
                        window.location = url;
                    }
                });

            } else {
                window.location = url;
            }
        });


    })

    function logJobView() {
        var trx = $("#transactionId").val();
        $.post(`/log-job-view/${trx}`);
    }
})