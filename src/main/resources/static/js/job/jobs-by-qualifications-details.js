$(function () {
    $('.lozad').Lazy();
    $("#forgot-password").toggle(1000);
    var url88 = "/find-address";
    //Find User's location
    $.get(url88, function (data, status) {
        $('#comp-tel').html(data.companyAddress);
        $('#adabraka-email').html(data.hodEmail);
    });
})