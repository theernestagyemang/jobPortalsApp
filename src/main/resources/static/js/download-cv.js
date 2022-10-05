/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {

    $("#cvChange").on('change', function () {
        var seekerId = $("#seekerId").val();
        var employerId = $('#employerId').val();
        var url = "/add-tocart?seekerId=" + seekerId + "&emp=" + employerId;

        var url2 = "/remove-tocart?seekerId=" + seekerId;

        if ($('#cvChange').is(":checked")) {
            $.get(url, function (data, status) {
                $("#cvBadge").html(data);
            });
        } else {
            $.get(url2, function (data, status) {
                $("#cvBadge").html(data);
            });
        }

    });

});

