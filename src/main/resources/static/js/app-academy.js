$(function () {

    $.get("/assessment/api", function (data) {
        var title = data.academy;
        var main = document.getElementById("assessment-div");
        main.innerHTML = `${title}`;
    });


    //Learning Plance
    fetchLearningPlan("/learningPlan")

    //Course List
    let courseListDiv = document.getElementById("course-list-div");
    $.get("/featured-courses/api", function (data) {
        if (data.length != 0) {
            var markup = '';
            $.each(data, function (idx, item) {
                let price = '';
                if (item.price === 0) {
                    price = 'Free';
                } else {
                    price = 'GHS ' + item.price;
                }
                markup += `
                            <div class="card mx-2 my-1 border-0 d-flex flex-column justify-content-center" style="width: 18rem;">

                                <img class="card-img-top img-responsive" src="/uploads/${item.picture}" style="height:200px;">
                                <div class="card-body">
                                    <h5 class="card-title" style="font-weight: bold">${item.courseTitle}</h5>
                                    <p class="card-text text-truncate" > ${item.category} </p>
                                    <h3 class="card-text" style="color: #fb236a; font-weight: bold;" > ${price} </h3>

                                </div>
                                 <div class="job-grid  mt-auto">
                                <a href="/course-details/${item.id}">View
                                    Details</a>
                                </div>
                            </div>
                           `;
            })
            courseListDiv.innerHTML = markup;
            document.getElementById("available-courses").innerHTML += `
            <div class="job-grid mt-auto text-center">
            <a href="/all-courses">View All Courses</a>
            </div>` ;

        } else {
            $("#course-list-div").append(" There are no Courses available at the moment");
        }
    })


    //Training Schedule List

    fetchTrainingPlan("/trainingSchedule");

    function fetchTrainingPlan(url) {
        let trainingScheduleDiv = document.getElementById("training-schedule-div");
        $.get(url, function (data) {
            if (data.length != 0) {
                var markup = '';
                $.each(data, function (idx, item) {
                    markup += `<div class="job-listing wtabs">
               <a title="View Details" href="/training-schedule-details/${item.id}">
                                <div class="job-title-sec" style="width:40%">
                                    <div class="c-logo">
                                        <i class="fa fa-unlock-alt green"></i>
                                    </div>
                                    <h5>${item.courseScheduled} </h5>
                                </div>
                                <div class="justify-content-start">
                                    <div class="d-inline-flex px-2" >
                                        <span>${item.eventDate}</span>
                                    </div>
                                    <div class="d-inline-flex px-4">
                                       <span class="mt-2">GH₵ ${item.training_cost}</span>
                                    </div>
                                </div>
                                <div class="job-style-bx">
                                    <span class="job-is ft"   >${item.location}  </span>
                                </div>
                                </a>
                            </div>`;
                })
                trainingScheduleDiv.innerHTML = markup;

                 document.getElementById("available-training").innerHTML +=`
                        <div class="job-grid mt-auto text-center">
                          <a href="/all-trainings">
                             View All Available Trainings
                          </a>
                        </div>`
            } else {
                $("#training-schedule-div").append(" There are no Trainings available at the moment");

            }
        })
    }

    function fetchLearningPlan(url) {
        let main = document.getElementById("learning-plan-div");
        $.get(url, function (data) {
            var markup = '';
            $.each(data, function (idx, item) {
                markup += `
                <div class="card  mx-2 my-1 border-0 d-flex justify-content-center" style="width: 17rem;">
                <div class="card-header" style="background-color: #276678; color: #fff;">
                                           ${item.name}
                                        </div>
                    <div class="card-body d-flex flex-column">

                        <p class="card-title fw-bolder" style="color:black"></p>
                        <h4 class="card-text text-success">GH¢ ${item.price}</h4>
                        <p class="card-text fs-6 fst-italic fw-light">
                        <span>${item.description}</span>
                        </p>
                        <div class="mt-auto">
                           <a href="/payment" class="btn" style="background-color: #49a997; color: #fff; width:100%">Subscribe</a>
                        </div>
                     </div>
                  </div>
               `;

            });
            main.innerHTML = markup;
        })
    }

    //Facilitators
    let facilitatorDiv = document.getElementById("facilitator-div");
    $.get("/facilitor/api", function (data) {
        if (data.length != 0) {

            var markup = '';
            var id = 0;
            $.each(data, function (idx, item) {
//                var id = idx + 1;
                if (idx % 4 === 0) {
                    console.log(idx)
                    id = idx;
                    if (idx === 0) {
                        document.getElementById("facilitator-div").innerHTML += `<div class="carousel-item active" data-bs-interval="10000" >
                            <div class="row" id='facilitator-div${id}'></div>
                          </div>`
                    } else {
                        document.getElementById("facilitator-div").innerHTML += `<div class="carousel-item" data-bs-interval="10000">
                            <div class="row" id='facilitator-div${id}'></div>
                          </div>`
                    }
                } else {

                }
                document.getElementById(`facilitator-div${id}`).innerHTML += `
                                

                                    <div class="card my-2 mx-4 mt-4 border-0" style="width: 16rem;">

                                        <img class="img-responsive" style="border-radius:50%; margin-left: 18px; width: 200px; height: 200px;" src="/uploads/${item.fileName}">
                                            <div class="card-body d-flex flex-column">
                                                 <h5 class="card-text card-title text-center">
                                                        ${item.firstName} ${item.lastName}
                                                 </h5>
                                                 <p class="card-text text-center">
                                                    Profession: <span class="fst-italic">${item.title}</span>
                                                 </p>
                                                 <div class = "job-grid mt-auto text-center">
                                                    <a href="/facilitator-details/${item.id}" class="btn" >View Details</a>
                                                </div>

                                              </div>

                                       </div>

                                    </div>

                            `;
            })


        } else {
            $("#facilitator-div").append(" There are no facilitators available at the moment");
        }
    })


    $("#empDiv").toggle();
    $("#employeeDiv").toggle();
    $("#forgot-password").toggle(1000);

    $.get("/find-address", function (data, status) {
        $('#comp-tel').html(data.companyAddress);
        $('#adabraka-email').html(data.hodEmail);
    });

    $("#empClick").click(function (e) {
        e.preventDefault();
        var text = $(this).html();
        if (text === "Read More") {
            $(this).html("Read Less");
        } else {
            $(this).html("Read More");
        }

        $("#empDiv").toggle("slow", function () {
            // Animation complete.
        });
    });
    $("#employeeClick").click(function (e) {
        e.preventDefault();
        var text2 = $(this).html();

        if (text2 === "Read More") {
            $(this).html("Read Less");
        } else {
            $(this).html("Read More");
        }

        $("#employeeDiv").toggle("slow", function () {
            // Animation complete.
        });
    });

    $('.lozad').Lazy();

    $('#login').submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();


        var name = $('#fullName').val();
        var email = $('#em').val();
        var tel = $('#phone').val();

        // name,String email, String tel
        var url = "/create-cv-request?name=" + name + "&email=" + email + "&tel=" + tel;
        var modal = $('#cvConfirmModal');

        // disabled the submit button
        $("#btnSave").prop("disabled", true);
        event.preventDefault();
        $.ajax({
            type: "POST", url: url,

            processData: false, contentType: false, cache: false, timeout: 600000, success: function (data) {
                if (data) {
                    modal.modal('hide');
                    swal({
                        title: "Success!",
                        text: "Your request has been submitted Successfully, We will get back to you shortly!",
                        type: "success",
                        icon: "success",
                        button: "Ok!"
                    }).then((value) => {
                        $("#btnSave").prop("disabled", false);
                    });
                } else {
                    swal({
                        title: "Unsuccessful!",
                        text: "Sorry, we couldn't submit your request, Try again next time!",
                        type: "error",
                        icon: "danger",
                        button: "Ok!"
                    });

                    $("#btnSave").prop("disabled", false);
                    modal.modal('hide');
                }
            }, error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, we couldn't submit your request, Try again next time!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });
                $("#btnSave").prop("disabled", false);
            }

        });

    });


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
        }
        if ($("#empForm").is(":visible")) {

        } else {
            $("#empForm").toggle(1000);

        }
    });
    var itemsMainDiv = ('.MultiCarousel');
    var itemsDiv = ('.MultiCarousel-inner');
    var itemWidth = "";

    $('.leftLst, .rightLst').click(function () {
        var condition = $(this).hasClass("leftLst");
        if (condition) click(0, this); else click(1, this)
    });

    ResCarouselSize();


    $(window).resize(function () {
        ResCarouselSize();
    });

    //this function define the size of the items
    function ResCarouselSize() {
        var incno = 0;
        var dataItems = ("data-items");
        var itemClass = ('.item');
        var id = 0;
        var btnParentSb = '';
        var itemsSplit = '';
        var sampwidth = $(itemsMainDiv).width();
        var bodyWidth = $('body').width();
        $(itemsDiv).each(function () {
            id = id + 1;
            var itemNumbers = $(this).find(itemClass).length;
            btnParentSb = $(this).parent().attr(dataItems);
            itemsSplit = btnParentSb.split(',');
            $(this).parent().attr("id", "MultiCarousel" + id);


            if (bodyWidth >= 1200) {
                incno = itemsSplit[3];
                itemWidth = sampwidth / incno;
            } else if (bodyWidth >= 992) {
                incno = itemsSplit[2];
                itemWidth = sampwidth / incno;
            } else if (bodyWidth >= 768) {
                incno = itemsSplit[1];
                itemWidth = sampwidth / incno;
            } else {
                incno = itemsSplit[0];
                itemWidth = sampwidth / incno;
            }
            $(this).css({'transform': 'translateX(0px)', 'width': itemWidth * itemNumbers});
            $(this).find(itemClass).each(function () {
                $(this).outerWidth(itemWidth);
            });

            $(".leftLst").addClass("over");
            $(".rightLst").removeClass("over");

        });
    }


    //this function used to move the items
    function ResCarousel(e, el, s) {
        var leftBtn = ('.leftLst');
        var rightBtn = ('.rightLst');
        var translateXval = '';
        var divStyle = $(el + ' ' + itemsDiv).css('transform');
        var values = divStyle.match(/-?[\d\.]+/g);
        var xds = Math.abs(values[4]);
        if (e == 0) {
            translateXval = parseInt(xds) - parseInt(itemWidth * s);
            $(el + ' ' + rightBtn).removeClass("over");

            if (translateXval <= itemWidth / 2) {
                translateXval = 0;
                $(el + ' ' + leftBtn).addClass("over");
            }
        } else if (e == 1) {
            var itemsCondition = $(el).find(itemsDiv).width() - $(el).width();
            translateXval = parseInt(xds) + parseInt(itemWidth * s);
            $(el + ' ' + leftBtn).removeClass("over");

            if (translateXval >= itemsCondition - itemWidth / 2) {
                translateXval = itemsCondition;
                $(el + ' ' + rightBtn).addClass("over");
            }
        }
        $(el + ' ' + itemsDiv).css('transform', 'translateX(' + -translateXval + 'px)');
    }

    //It is used to get some elements from btn
    function click(ell, ee) {
        var Parent = "#" + $(ee).parent().attr("id");
        var slide = $(Parent).attr("data-slide");
        ResCarousel(ell, Parent, slide);
    }

    //productCourse();

    function removeItemOnce(arr, value) {
        var index = arr.indexOf(value);
        if (index > -1) {
            arr.splice(index, 1);
        }
        return arr;
    }

    function productCourse() {

        let input = document.getElementById('search-course');

        // Init a timeout variable to be used below
        let timeout = null;

        // Listen for keystroke events
        input.addEventListener('keyup', function (e) {
            // Clear the timeout if it has already been set.
            // This will prevent the previous task from executing
            // if it has been less than <MILLISECONDS>
            clearTimeout(timeout);

            // Make a new timeout set to go off in 1000ms (1 second)
            timeout = setTimeout(function () {

                var url = null;
                deleteRows();

                var searchTerm = input.value;
                if (searchTerm === '') {
                    url = `/trainingSchedule`;
                } else {
                    url = `/trainingSchedule/${searchTerm}`;
                }

                fetchTrainingPlan(url);

            }, 1000);
        });

    }


    let fruits = [];
    $('.spealism').click(function () {

        $("#training-schedule-div").empty();

        var desc = $(this).attr("id");

        if (this.checked === false) {
            removeItemOnce(fruits, desc);
        } else {
            fruits.push(desc);
        }

        var url = `/trainingScheduleByMonth?month=${fruits}`;
        fetchTrainingPlan(url);

    });

    $(document).on("click", ".subscribe", function (e) {
        e.preventDefault();
        $(this).prop("disabled", true);
        $(this).find('i').addClass("fas fa-circle-notch fa-spin");
        var dataId = $(this).attr("data");

        createSubscription(dataId, 2);
    });

});//End of document.ready    