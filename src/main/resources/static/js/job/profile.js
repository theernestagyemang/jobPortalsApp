$(function () {

    $("#picData").on("change", function () {
        udatePic();
    })

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
                $("#userPic").attr("src", `uploads/${data}`);
            },
            error: function (e) {

            }

        });
    }

    $(function () {
        $('.lozad').Lazy();

    });
})