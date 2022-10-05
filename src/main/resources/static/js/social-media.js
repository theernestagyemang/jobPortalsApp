/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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

    let compTel = document.getElementById("comp-tel");
    let adabrakaEmail = document.getElementById("adabraka-email");
    let facebook = document.getElementById("facebook");
    let twitter = document.getElementById("twitter");
    let instagram = document.getElementById("instagram");


    $.get("/find-company", function (data) {

        let adabraka = {
            facebook: data.facebook,
            google: data.facebook,
            twitter: data.twitter,
            address: data.address,
            name: data.name,
            phone: data.phone,
            email: data.email,
            instagram: data.instagram
        };

        compTel.innerHTML = adabraka.phone;
        adabrakaEmail.innerHTML = adabraka.email;

        facebook.href = adabraka.facebook;
        twitter.href = adabraka.twitter;
        instagram.href = adabraka.instagram;

    });


});