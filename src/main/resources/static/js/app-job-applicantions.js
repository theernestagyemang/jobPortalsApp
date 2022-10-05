$(function () {
    var url = `/recruiter/job-applications`;
    $.get(url, function (data) {
        var lines = data.lines;
        var totalPages = data.totalPages;
    })
})