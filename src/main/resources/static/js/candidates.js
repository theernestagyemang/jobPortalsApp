$(function () {
    var cartTable = $('#example2').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5,

    });

    $(document).on("click", ".candidate", function (e) {
        e.preventDefault();
        $(".candidate").removeClass("active");
        $(this).addClass("active");

        var type = $(this).attr("data-id");

        var url_string = window.location.href;
        var url = new URL(url_string);
        var trx = url.searchParams.get("trx");


        var url = `/recruiter/candidates/${trx}/${type}`;

        $.get(url, function (data) {
            var line = data.lines;
            rerender(line);
        })
    })

    function reloadPage() {
        var url_string = window.location.href;
        var url = new URL(url_string);
        var trx = url.searchParams.get("trx");

        $(".candidate").removeClass("active");
        $("#shortlistedCand").addClass("active");
        var url = `/recruiter/candidates/${trx}/Shortlisted`;
        $.get(url, function (data) {
            var line = data.lines;
            rerender(line);
        })
    }

    $(document).on("click", ".change-status", function (e) {
        e.preventDefault();


        var seekerTrx = $(this).attr("data-seeker-trx");

        var jobAppTrx = $(this).attr("data-app-trx");
        var type = $(this).attr("data-status");

        //Job Trx
        var url_string = window.location.href;
        var url = new URL(url_string);
        var jobTrx = url.searchParams.get("trx");

        let data = {
            seekerTrx: seekerTrx,
            jobTrx: jobTrx,
            jobAppTrx: jobAppTrx,
            type: type
        };


        var url = `/recruiter/shortlist-candidate`;


        swal({
            title: "Are you sure?",
            text: "You are about to change this applicantion status!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes!",
            closeOnConfirm: false
        }, function () {

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
                            swal("Done !", "Applicantion status changed successfuly.", "success");
                            reloadPage();
                            break;
                        case "SHORTLISTED":
                            swal("Failed!", "Already shortlisted", "error");
                            break;

                        case "FAILED":
                            swal("Failed!", "Could not save", "error");
                            break;
                        default:
                            swal("Failed!", "Could not save", "error");
                            break;
                    }

                },
                error: function (e) {
                    swal("Failed!", "Could not save", "error");
                }
            })

        });

    })


    function clearTable() {
        cartTable.clear().draw();
    }


    function rerender(cartItems) {

        clearTable();
        if (cartItems.length !== 0) {

            cartItems.map(function (item, index, myArr) {

                cartTable.row.add([
                    `<a href="/recruiter/job-seeker-details?q=${item.jobSeeker.traxId}" >${item.jobSeeker.fullName} </a>`,
                    item.jobSeeker.email,
                    item.jobSeeker.telephone,
                    item.appliedDate,
                    `<div class="btn-group">
                      <button type="button" class="waves-effect waves-light btn btn-outline btn-success change-status" data-status="Shortlisted" data-seeker-trx="${item.jobSeeker.traxId}" data-app-trx="${item.id}"><i class="fa fa-check"></i></button>
                      <button type="button" class="waves-effect waves-light btn btn-outline btn-primary change-status" data-status="Hired" data-seeker-trx="${item.jobSeeker.traxId}" data-app-trx="${item.id}"><i class="fa fa-question"></i></button>
                      <button type="button" class="waves-effect waves-light btn btn-outline btn-danger change-status" data-status="Rejected" data-seeker-trx="${item.jobSeeker.traxId}" data-app-trx="${item.id}"><i class="fa fa-close"></i></button>
                    </div>`,
                    ``

                ]).draw(true);

            });


        }


    }
})