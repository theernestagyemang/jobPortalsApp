$(function () {
    "use strict";

    //Friday, 4th December 2020

    function calcPct(app, total) {
        if (total === 0) {
            return 0;
        }
        var result = ((app / total) * 100);
        if (Number.isInteger(result)) {
            return result;
        } else {
            return result.toFixed(2);
        }

    }

    loadByCategory("/recruiter/find-applications-by-job-category");

    function loadByCategory(url) {
        $.get(url, function (data) {
            $("#job-title-app").html(`<div class="bb-1 d-flex justify-content-between">
                                          <h5>Job title</h5>
                                          <h5>Applications</h5>
                                  </div>`);
            $.each(data, function (idx, item) {
                $("#job-title-app").append(`<div class="d-flex justify-content-between my-15">
                                              <p>${item.job} </p>
                                              <p> 
                                                  <strong>${item.applications}</strong>
                                                  <button type="button" class="waves-effect waves-light btn btn-xs btn-outline btn-primary-light mx-5">
                                                          <i class= "fa fa-line-chart"></i>
                                                  </button>
                                              </p>
                                      </div>`);
            })
        })
    }


    function loadStats(data) {

        var hired = data.hired * 1;
        var shortlisted = data.shortlisted * 1;
        var onHold = data.onHold * 1;
        var rejected = data.rejected * 1;

        var total = hired + shortlisted + onHold + rejected;

        var pct_hired = calcPct(hired, total);
        var pct_shortlisted = calcPct(shortlisted, total);
        var pct_onHold = calcPct(onHold, total);
        var pct_rejected = calcPct(rejected, total);


        $("#total-hired").html(hired);
        $("#total-shortlisted").html(shortlisted);
        $("#total-on-hold").html(onHold);
        //Chart

        //Shortlisted
        var options = {
            chart: {
                height: 150,
                width: 150,
                type: "radialBar"
            },

            series: [pct_shortlisted],
            colors: ['#673ab7'],
            plotOptions: {
                radialBar: {
                    hollow: {
                        margin: 0,
                        size: "60%"
                    },
                    track: {
                        background: '#e9f5ea',
                    },

                    dataLabels: {
                        showOn: "always",
                        name: {
                            show: false,
                        },
                        value: {
                            offsetY: 5,
                            color: "#111",
                            fontSize: "20px",
                            show: true
                        }
                    }
                }
            },

            stroke: {
                lineCap: "round",
            },
            labels: ["Progress"]
        };
        var chart = new ApexCharts(document.querySelector("#revenue2"), options);
        chart.render();

        //On holde
        var options = {
            chart: {
                height: 150,
                width: 150,
                type: "radialBar"
            },

            series: [pct_onHold],
            colors: ['#fdac42'],
            plotOptions: {
                radialBar: {
                    hollow: {
                        margin: 0,
                        size: "60%"
                    },
                    track: {
                        background: '#fde5ba',
                    },

                    dataLabels: {
                        showOn: "always",
                        name: {
                            show: false,
                        },
                        value: {
                            offsetY: 5,
                            color: "#111",
                            fontSize: "20px",
                            show: true
                        }
                    }
                }
            },

            stroke: {
                lineCap: "round",
            },
            labels: ["Progress"]
        };
        var chart = new ApexCharts(document.querySelector("#revenue3"), options);
        chart.render();

        //Hired
        var options = {
            chart: {
                height: 150,
                width: 150,
                type: "radialBar"
            },

            series: [pct_hired], colors: ['#3da643'],
            plotOptions: {
                radialBar: {
                    hollow: {
                        margin: 0,
                        size: "60%"
                    },
                    track: {
                        background: '#e7daff',
                    },

                    dataLabels: {
                        showOn: "always",
                        name: {
                            show: false,
                        },
                        value: {
                            offsetY: 5,
                            color: "#111",
                            fontSize: "20px",
                            show: true
                        }
                    }
                }
            },

            stroke: {
                lineCap: "round",
            },
            labels: ["Progress"]
        };
        var chart = new ApexCharts(document.querySelector("#revenue1"), options);
        chart.render();


        var main = document.getElementById("applications-statistics");

        var markup = `<div class="box-header">
                            <h4 class="box-title">Total Applications ${total}</h4>
                        </div>
                        <div class="box-body">
                                <div class="d-flex w-p100 rounded100 overflow-hidden">
                                        <div class="bg-danger h-10" style="width: ${pct_rejected}%;"></div>
                                        <div class="bg-warning h-10" style="width: ${pct_onHold}%;"></div>
                                        <div class="bg-success h-10" style="width: ${pct_hired}%;"></div>
                                        <div class=" h-10" style="width: ${pct_shortlisted}%; background-color: #673ab7"></div>
                                </div>
                        </div>
                        <div class="box-body p-0" id="total-application-stats">
                                <div class="media-list media-list-hover media-list-divided">
                                        <a class="media media-single rounded-0" href="#">
                                          <span class="badge badge-xl badge-dot badge-info" style="background-color: #673ab7; color : white"></span>
                                          <span class="title">Shortlisted </span>
                                          <span class="badge badge-pill badge-info-light" style="background-color: #673ab7; color : white">${pct_shortlisted}%</span>
                                        </a>

                                          <a class="media media-single rounded-0" href="#">
                                          <span class="badge badge-xl badge-dot badge-warning"></span>
                                          <span class="title">On-Hold</span>
                                          <span class="badge badge-pill badge-warning-light" style="background-color: #FFFF00; color : black">${pct_onHold}%</span>
                                        </a>
                                        <a class="media media-single rounded-0" href="#">
                                          <span class="badge badge-xl badge-dot badge-success"></span>
                                          <span class="title">Hired</span>
                                          <span class="badge badge-pill badge-success-light" style="background-color: #008000; color : white">${pct_hired}%</span>
                                        </a>

                                        <a class="media media-single rounded-0" href="#">
                                          <span class="badge badge-xl badge-dot badge-danger"></span>
                                          <span class="title">Rejected</span>
                                          <span class="badge badge-pill badge-danger-light" style="background-color: #FF0000; color : white">${pct_rejected}%</span>
                                        </a>
                                </div>
                        </div>`;
        main.innerHTML = markup;
    }

    var url = `/recruiter/total-applications`;
    $.get(url, function (data) {

        loadStats(data);
        $("#total-hired").attr("href", "/recruiter/application-by-status?status=Hired");
        $("#total-shortlisted").attr("href", "/recruiter/application-by-status?status=Shortlisted");
        $("#total-on-hold").attr("href", "/recruiter/application-by-status?status=Pending");
    })


    $('#reservation').daterangepicker({
        format: 'DD/MM/YYYY',
        dateLimit: {
            'months': 2,
            'days': -1
        },
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract('days', 1), moment().subtract('days', 1)],
            'Last 7 Days': [moment().subtract('days', 6), moment()],
            'Last 30 Days': [moment().subtract('days', 29), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')]
        },
    });

    $("#reservation").on("change", function () {
        var start = $(this).data('daterangepicker').startDate.format('DD/MM/YYYY');
        var end = $(this).data('daterangepicker').endDate.format('DD/MM/YYYY');

        if (typeof start !== 'undefined') {
            loadStatistics(start, end);
            var caturl = `"/recruiter/find-applications-by-job-category?st=${start}$ed=${end}`
            loadByCategory(caturl);
        }
    })


    function loadStatistics(start, end) {
        if (typeof start === 'undefined') {
            loadStatistics();
            return;
        }
        //applications
        var url = `/recruiter/total-applications?st=${start}&ed=${end}`;
        $.get(url, function (data) {
            loadStats(data);

            $("#total-hired").attr("href", `/recruiter/application-by-status?status=Hired&st=${start}&ed=${start}`);
            $("#total-shortlisted").attr("href", `/recruiter/application-by-status?status=Shortlisted&st=${start}&ed=${start}`);
            $("#total-on-hold").attr("href", `/recruiter/application-by-status?status=Pending&st=${start}&ed=${start}`);
        })

    }

    //Meeting Schedule
    loadMeeting();


    function loadMeeting() {
        $.get("/recruiter/shedule-meeting-api", function (data) {

            $.each(data, function (idx, item) {

                $("#meeting-shedule").append(`<div class="d-flex align-items-center mb-30 justify-content-between">
                                                                                          <div class="mr-15 text-center rounded15 box-shadowed h-50 w-50 d-table">
                                                                                                  <p class="mt-5 mb-0 text-warning">${item.day.substring(0, 3)}</p>
                                                                                                  <p class="mb-0">${item.dayOfMonth}</p>
                                                                                          </div>
                                                                                          <div class="d-flex flex-column flex-grow-1 font-weight-500 min-w-p60">
                                                                                                  <a href="#" class="text-dark hover-primary mb-1 font-size-16 overflow-hidden text-nowrap text-overflow">${item.title}</a>
                                                                                                  <span class="font-size-10 text-fade"><i class="fa fa-clock-o"></i> ${item.timeFrom} - ${item.timeFrom} </span>
                                                                                          </div>
                                                                                          <div class="dropdown">
                                                                                                  <a class="px-10 pt-5" href="#" data-toggle="dropdown"><i class="ti-more-alt"></i></a>
                                                                                                  <div class="dropdown-menu dropdown-menu-right">
                                                                                                    <a class="dropdown-item" href="#">Action</a>
                                                                                                    <a class="dropdown-item" href="#">Delete</a>
                                                                                                  </div>
                                                                                          </div>
                                                                                  </div>`);
            })
        })
    }

    $("#meeting-frm").on("submit", function (e) {
        e.preventDefault();

        var title = $("#title").val();
        var timeFrom = $("#time-from").val();
        var timeTo = $("#time-to").val();
        var comment = $("#comment").val();
        var eventDate = $("#event-date").val();

        $("#btnSaveSales").prop("disabled", true);


        let data = {
            eventDate: eventDate,
            title: title,
            timeFrom: timeFrom,
            timeTo: timeTo,
            comment: comment
        };


        var url = `/recruiter/shedule-meeting-api`;

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {
                    case "SUCCESS":
                        $.toast({
                            heading: 'Saved',
                            text: 'Meeting Schedulled successfuly',
                            position: 'top-right',
                            loaderBg: '#ff6849',
                            icon: 'success',
                            hideAfter: 3000,
                            stack: 6
                        });
                        document.getElementById("meeting-frm").reset();


                        $("#btnSaveSales").prop("disabled", false);
                        break;
                    default:
                        $.toast({
                            heading: 'Failed:',
                            text: 'Meeting Schedulled not saved',
                            position: 'top-right',
                            loaderBg: '#ff6849',
                            icon: 'error',
                            hideAfter: 3000,
                            stack: 6

                        });

                        $("#btnSaveSales").prop("disabled", false);
                }

            },
            error: function (e) {
                $.toast({
                    heading: 'Error:',
                    text: 'Meeting Schedulled not saved',
                    position: 'top-right',
                    loaderBg: '#ff6849',
                    icon: 'error',
                    hideAfter: 3000,
                    stack: 6,

                });

                $("#btnSaveSales").prop("disabled", false);
            }
        });

    });

    //New Applications
    $.get("/recruiter/new-applications", function (data) {
        var lines = data.lines;
        //var totalRecords = data.totalRecords;
        $.each(lines, function (idx, item) {

            $("#new-applications").append(`<div class="d-flex align-items-center mb-30">
                  <div class="mr-15">
                          <img src="/uploads/${item.jobSeeker.fileName}" class="avatar avatar-lg rounded100 bg-primary-light" alt="" />
                  </div>
                  <div class="d-flex flex-column flex-grow-1 font-weight-500">
                          <a href="/recruiter/job-seeker-details?q=${item.jobSeeker.traxId}" class="text-dark hover-primary mb-1 font-size-16">${item.jobSeeker.fullName} </a>
                          <span class="font-size-12"><span class="text-fade">Applied for</span> ${item.position}  </span>
                  </div>
                  <div class="dropdown">
                          <a class="px-10 pt-5" href="#" data-toggle="dropdown"><i class="ti-more-alt"></i></a>
                          <div class="dropdown-menu dropdown-menu-right">
                            <a class="dropdown-item" href="/recruiter/job-seeker-details?q=${item.jobSeeker.traxId}">View Details</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item"  href="/recruiter/jobs-details?trx=${item.jobTransactionId}">View Job Details</a>
                          </div>
                  </div>
          </div>`);
        })

        $("#totalRecords").html(`10 New Applications`);
    })

    //Job Category


    $(document).ajaxStart(function () {
        Pace.restart();
    });
})

