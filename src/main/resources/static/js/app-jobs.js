$(function () {
    var paginationBtn;

    $(".categories").on('change', function () {
        var selected = $(this).val();
        if (selected !== 'Please Select Category') {
            loadJobsByCategory(selected);
        }

    })

    var url = "/recruiter/jobs-api";
    $.get(url, function (data) {
        var numberOfElements = data.numberOfElements;
        var totalPages = data.totalPages;
        var page = data.page;
        var max = data.max;
        var lines = data.lines;
    })

    $(document).on("click", ".publish", function (e) {
        e.preventDefault();
        var url = $(this).attr("href");
        swal({
            title: "Are you sure?",
            text: "This will make this job visible on the public page for job seekers!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, publish it!",
            closeOnConfirm: false
        }, function () {
            $.get(url, function (data) {
                if (data) {
                    swal("Publish!", "Your job has been published.", "success");
                    $("#lblStatus").html("Open");
                } else {
                    swal("Failed!", "Sorry we couldn't publish this job", "error");
                    $("#lblStatus").html("Closed");
                }
            })

        });
    })

    $(document).on("click", ".unpublish", function (e) {
        e.preventDefault();
        var url = $(this).attr("href");
        swal({
            title: "Are you sure?",
            text: "This will remove the job from the public page!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, unpublish it!",
            closeOnConfirm: false
        }, function () {
            $.get(url, function (data) {
                if (data) {
                    swal("Done!", "Your job has been unpublish.", "success");
                    $("#lblStatus").html("Closed");
                } else {
                    swal("Failed!", "Sorry we couldn't unpublish this job", "error");
                    $("#lblStatus").html("Open");
                }
            })
        });
    })


    //set up categories
    $.get("/recruiter/job-categories", function (data) {

        $.each(data, function (idx, item) {
            $('.categories').append($('<option>', {
                value: item,
                text: item
            }));
        })
    })

    $(".search-jb").on("keyup", function () {
        var searchTerm = $(this).val();
        console.log(searchTerm)
        if (searchTerm.length === 0) {
            loadAllJobs();
        } else {
            loadSearchJobs(searchTerm);
        }
    });

    function loadAllJobs() {
    }

    $(document).on("click", ".paginated-list", function (e) {
        e.preventDefault();
        var str = $(this).attr("data-url");
        var page = $(this).attr("data-page");

        var url = `${str}&page=${page + 1}`;
        loadJobs(url);
    })

    function loadJobs(url) {
        var markup = ``;
        var mainDiv = document.getElementById("jobs-div");
        mainDiv.innerHTML = markup;
        $.get(url, function (data) {

            var content = data.lines;
            var totalPages = data.totalPages;
            var totalElements = data.numberOfElements;
            var size = data.size;

            paginationBtn = '';
            var i;
            for (i = 0; i < totalPages; i++) {
                paginationBtn += `<a  href="#" class="paginated-list" data-url="${url}" id="${i + 1}" data-page="${i}" >${i + 1}</a>`;
            }

            $.each(content, function (idx, comp) {
                var markup = `<div class="col-lg-4 col-12">
                                              <div class="box">
                                                      <div class="box-body">	
                                                              <div class="d-flex flex-wrap align-items-center">							
                                                                      <div class="mr-25 bg-danger-light h-60 w-60 rounded text-center b-1">
                                                                                <img src="../images/logo/logo-1.jpg" class="align-self-center" alt="">
                                                                      </div>

                                                                      <div class="d-flex flex-column flex-grow-1 my-lg-0 my-10 pr-15">
                                                                              <span class="text-fade font-weight-600 font-size-16" >
                                                                                     <a href="/recruiter/jobs-details?trx=${comp.transactionId}"> ${comp.company} </a>
                                                                              </span>
                                                                          <a href="#" class="text-dark font-weight-600 hover-danger font-size-18" >
                                                                                      ${comp.position}
                                                                              </a>
                                                                      </div>
                                                              </div>
                                                              <div class="mt-20">
                                                                        <h4 class="text-primary mb-20">
                                                                            Ghc ${comp.minSal}  - ${comp.maxSal}  
                                                                        </h4>
                                                                        <p>${comp.summary}  </p>

                                                                        <div class="d-flex flex-column w-p100 mt-30">
                                                                              <span class="text-dark mr-10 font-size-16 font-weight-600 pb-15">
                                                                                  <a href="/applied-applicants/${comp.transactionId}"> Applied  ${comp.applied}</a>,
                                                                                  <a href="/shortlisted-candidate/${comp.transactionId}"> Shortlisted  ${comp.shortlisted} </a>,
                                                                              </span>

                                                                              <div class="progress progress-xs w-p100">
                                                                                      <div class="progress-bar bg-danger" role="progressbar" style="width: 100%;" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                                                                              </div>
                                                                      </div>
                                                                      <div class="mt-10 d-flex justify-content-between align-items-center">
                                                                              <a  href="/recruiter/jobs-details?trx=${comp.transactionId}" class="waves-effect waves-light btn btn-primary mb-5">View Details  </a>
                                                                              <p class="mb-0 text-fade"><i class="fa fa-map-marker"></i> ${comp.location}</p>
                                                                      </div>
                                                              </div>
                                                      </div>					
                                              </div>
                                      </div>
                                    </div>`;

                mainDiv.innerHTML = markup;
            });

            var pgination = `<div class="col-lg-12">
                                            <div class="pagination">
                                                <span>
                                                    <a href="#"><i class="la la-long-arrow-left"></i> Prev</a>
                                                     ${paginationBtn}
                                                    <a href="#">Next <i class="la la-long-arrow-right"></i></a>
                                                </span>
                                            </div>
                                        </div>`;

            $("#paginationDiv").html(pgination);
        })
    }

    function loadSearchJobs(searchTerm) {

        var url = `/recruiter/search-jobs?q=${searchTerm}`;
        loadJobs(url);

    }

    function loadJobsByCategory(searchTerm) {

        var markup = ``;
        var mainDiv = document.getElementById("jobs-div");
        mainDiv.innerHTML = markup;

        var url = `/recruiter/search-jobs-by-category?ct=${searchTerm}`;
        loadJobs(url);

    }
});
