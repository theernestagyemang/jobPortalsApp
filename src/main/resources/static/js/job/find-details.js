$(function () {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var transactionId = url.searchParams.get("q");
    var jobSeekerUrl = `/public/find-job-seeker/${transactionId}`;
    jobSeekerDetails(jobSeekerUrl);

    $(document).on('click', ".bl-details", function (e) {
        var details = $(this).attr("data-details");
        $("#bl-details").html(details);
    });

    $(document).on('click', ".shortlist-selected-candidate", function (e) {
        var dataId = $(this).attr("data-id");
        var blacklist = $(this).attr("data-blacklist");
        if (blacklist === 'Yes') {
            alert("Job Seeker has been blacklisted...");
            return;
        }
        shortlistCandidate(dataId);

    })

})