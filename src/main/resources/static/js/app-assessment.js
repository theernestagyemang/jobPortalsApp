$(function () {

    $.get("/assessment-active/api", function (data) {
        if (data.length != 0) {


            $("#plans-sec").empty();

            var main = document.getElementById("assessment-div");

            $.each(data, function (idx, elem) {
                var markup = ``;
                var des = elem.benefits.substring(0, 120);
                if (elem.price !== 0) {
                    markup = `
                                        <div class="card my-1 d-flex justify-content-center mx-1 border-0" style="width: 18rem;">
                                                <div class="card-header" style="background-color: #425591; color: #fff;">
                                                                                                   ${elem.name}
                                                </div>
                                             <div class="card-body d-flex flex-column">

                                                 <p class="card-title fw-bolder" style="color:black">${elem.duration} minutes</p>
                                                 <h4 class="card-text" style="color:#fb236a">GH¢ ${elem.price}</h4>
                                                 <p class="card-text fs-6 fst-italic fw-light">
                                                     <span>${des}</span>
                                                 </p>
                                                 <div class = "mt-auto job-grid">
                                                    <a href="/assessment-details/${elem.id}">View Details</a>
                                                </div>

                                             </div>

                                        </div>
                                   
                                       `;

                } else {
                    markup = `
                                        <div class="card my-1 d-flex justify-content-center mx-1 border-0" style="width: 18rem;">
                                                <div class="card-header" style="background-color: #425591; color: #fff;">
                                                                                                   ${elem.name}
                                                </div>
                                             <div class="card-body d-flex flex-column">

                                                 <p class="card-title fw-bolder" style="color:black">${elem.duration} minutes</p>
                                                 <h4 class="card-text" style="color:#24324A">Free</h4>
                                                 <p class="card-text fs-6 fst-italic fw-light">
                                                     <span>${des}</span>
                                                 </p>
                                                 <div class = "mt-auto job-grid">
                                                    <a href="/assessment-details/${elem.id}">View Details</a>
                                                </div>

                                             </div>

                                        </div>
                                   
                                       `;

                }


                $("#plans-sec").append(markup);
            });
        } else {
            $("#plans-sec").append(" There are no assessment available at the moment");
        }


    });

    $(document).on('click', ".subscribe", function (e) {
        e.preventDefault();
        var dataId = $(this).attr("data-id");
        createSubscription(dataId, 3);
    });


})