$(function () {
    $('.lozad').Lazy();

    $("#forgot-password").toggle(1000);

    $(".forget-pass").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);

    });


    $(".back").click(function () {
        $("#tggl").toggle(1000);
        $("#forgot-password").toggle(1000);
    });


    $('#prev').on('click', function (e) {
        e.preventDefault();

        var url_string = window.location.href;
        var url = new URL(url_string);
        var paramValue = url.searchParams.get("page");

    });


});
   

