/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$("#empForm").toggle(1000);
$("#jobSeekerForm").toggle(1000);

$("#job_seeker").click(function () {
    if ($("#empForm").is(":visible")) {
        $("#empForm").toggle(1000);
    }
    if ($("#jobSeekerForm").is(":visible")) {

    } else {
        $("#jobSeekerForm").toggle(1000);

    }

});
$("#job_employer").click(function () {

    if ($("#jobSeekerForm").is(":visible")) {
        $("#jobSeekerForm").toggle(1000);
    } else {
        $("#empForm").toggle(1000);
    }
//            if($("#empForm").is(":visible")){
//
//            } else{
//                $("#empForm").toggle(1000);
//
//            }
});