/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 
 * "id": 1,
    "name": "AO Holdings Limited",
    "fileName": null,
    "location": "Accra"
 */


$(function () {

    $('.lozad').Lazy();

    let companies = document.getElementById("companies");
    let members = document.getElementById("members");
    let postedJobs = document.getElementById("posted-jobs");
    let filledJobs = document.getElementById("filled-jobs");


    var url = "/statistics";
    $.get(url, function (data) {

        companies.innerHTML = data.companies.toLocaleString();
        members.innerHTML = data.members.toLocaleString();
        postedJobs.innerHTML = data.posted.toLocaleString();
        filledJobs.innerHTML = data.filled.toLocaleString();
    });


});