$(function () {
    function rowClicked2(val) {
        window.location.href = "job-details?id=" + val;

    }

    $("#forgot-password").toggle(1000);
    $('.lozad').Lazy();


    var jobDescription = $("#jobDescription").html();
    const strippedString = jobDescription.replace(/(<([^>]+)>)/gi, "");
    var trx = truncateString(strippedString, 220);
    $("#jobDescription").html(escape_HTML(trx));


    var jobDescription2 = $("#jobDescription2").html();
    const strippedString2 = jobDescription2.replace(/(<([^>]+)>)/gi, "");
    var trx = truncateString(strippedString2, 220);
    $("#jobDescription2").html(escape_HTML(trx));


    function truncateString(str, num) {
        if (str.length <= num) {
            return str;
        }
        return str.slice(0, num) + '...'
    }

    function escape_HTML(html_str) {
        'use strict';

        return html_str.replace(/[&<>"]/g, function (tag) {
            var chars_to_replace = {
                '&': '&',
                '<': '<',
                '>': '>',
                '"': '"'
            };

            return chars_to_replace[tag] || tag;
        });
    }


});//End of document.ready
   
   
 