/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(".stc").keyup(function () {
    deleteRow();
    var searchTerm = $(".stc").val();
    if (searchTerm === '') {
        return;
    }
    var url = "/find-jobseeker?q=" + searchTerm;

    $.ajax({
        type: "GET",
        url: url,

        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            //applicantEmail invoiceType applicantTelephone amt

            $.each(data, function (idx, elem) {


                var markup = "<li id=" + elem.id + ">" +

                    "<div class='post-bx'>" +
                    "   <div class='d-flex m-b30'>" +
                    "      <div class='job-post-company'>" +
                    "           <a href='javascript:void(0);'>" +
                    "               <span>" +
                    "                  <img alt='Picture' src='images/testimonials/pic1.jpg'/>" +
                    "               </span>" +
                    "          </a>" +
                    "	</div>" +
                    "	<div class='job-post-info'>" +
                    "           <h4><a  href='/seeker-profile?id=" + elem.id + "'>" + elem.proffesionalTitile + "</a></h4>" +
                    "           <ul>" +
                    "		<li><i class='fa fa-map-marker'></i>" + elem.currentLocation + "</li>" +
                    "		<li><i class='fa fa-bank'></i>" + elem.currentCompany + " " + elem.employmentType + "</li>" +
                    "		<li><i class='fa fa-user'></i>" + elem.fullName + " </li>" +
                    "               <li><i class='fa fa-phone'></i>" + elem.primaryContact + " </li>" +
                    "                <li><i class='fa fa-envelope'></i>" + elem.email + " </li>	" +
                    "           </ul>" +
                    "	</div>" +
                    "   </div>" +

                    "    <input type='text' class='tags_input' id='keySkillsDesc'  value='" + elem.keySkills + "'/>" +
                    "	<label class='like-btn'>" +
                    "          <input type='checkbox'  onchange='rowClicked(" + elem.id + +")' title='Shortlist Candidate' class='tooltip'>" +
                    "		<span class='checkmark'></span>" +


                    "	</label>" +
                    "</div>" +
                    "</li>";


                $("#mytable").append(markup);
                createAllCss();
            });

        },
        error: function (e) {
            console.log(" Sorry, We couldn't find Job Seeker");

        }

    });


});

function deleteRow() {
    $("#mytable").empty();

}

function createAllCss() {
    var data = [
        'css',
        'html',
        'php',
        'jquery'
    ];

    $(".tags_input").tagComplete({
        keylimit: 1,
        hide: false,
        autocomplete: {
            data: data
        }
    });

}

function deleteRow2() {

}

$(".btn-search").on('click', function () {
    deleteRow2();
    var searchTerm = $(".stc").val();
    if (searchTerm === '') {
        return;
    }
    var url = "/find-jobseeker?q=" + searchTerm;

    $.ajax({
        type: "GET",
        url: url,

        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            //applicantEmail invoiceType applicantTelephone amt

            $.each(data, function (idx, elem) {


                var markup = "<li id=" + elem.id + ">" +

                    "<div class='post-bx'>" +
                    "   <div class='d-flex m-b30'>" +
                    "      <div class='job-post-company'>" +
                    "           <a href='javascript:void(0);'>" +
                    "               <span>" +
                    "                  <img alt='Picture' src='images/testimonials/pic1.jpg'/>" +
                    "               </span>" +
                    "          </a>" +
                    "	</div>" +
                    "	<div class='job-post-info'>" +
                    "           <h4><a  href='/seeker-profile?id=" + elem.id + "'>" + elem.proffesionalTitile + "</a></h4>" +
                    "           <ul>" +
                    "		<li><i class='fa fa-map-marker'></i>" + elem.currentLocation + "</li>" +
                    "		<li><i class='fa fa-bank'></i>" + elem.currentCompany + " " + elem.employmentType + "</li>" +
                    "		<li><i class='fa fa-user'></i>" + elem.fullName + " </li>" +
                    "               <li><i class='fa fa-phone'></i>" + elem.primaryContact + " </li>" +
                    "                <li><i class='fa fa-envelope'></i>" + elem.email + " </li>	" +
                    "           </ul>" +
                    "	</div>" +
                    "   </div>" +

                    "    <input type='text' class='tags_input' id='keySkillsDesc'  value='" + elem.keySkills + "'/>" +
                    "	<label class='like-btn'>" +
                    "          <input type='checkbox'  onchange='rowClicked(" + elem.id + +")' title='Shortlist Candidate' class='tooltip'>" +
                    "		<span class='checkmark'></span>" +


                    "	</label>" +
                    "</div>" +
                    "</li>";


                $("#mytable").append(markup);
                createAllCss();
            });

        },
        error: function (e) {
            console.log(" Sorry, We couldn't find Job Seeker");

        }

    });


});
    