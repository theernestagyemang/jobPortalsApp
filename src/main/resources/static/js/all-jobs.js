$(function () {

    function saveJob(value) {
        var url = "/save-jobs?id=" + value;
        $.ajax({
            type: "POST", url: url, processData: false, contentType: false, cache: false, timeout: 600000,

            success: function (data) {

                swal({
                    title: "Success!",
                    text: "Your profile has been updated successfully!",
                    type: "success",
                    icon: "success",
                    button: "Ok!"
                });

            }, error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't update your profile!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });


            }

        });
    }

    function rowClicked(value) {
        var url = "/authenticated";

        $.get(url, function (data) {
            if (!data) {
                swal("Unauthenticated!", "You must login!", "error");
            } else {
                saveJob(value);
            }
        });
    }

    function removeItemOnce(arr, value) {
        var index = arr.indexOf(value);
        if (index > -1) {
            arr.splice(index, 1);
        }
        return arr;
    }

    function findJobSeeker(url) {
        $(".emply-resume-sec").empty();

        $(".emply-resume-sec").empty();
        $.get(url, function (data) {
            if (data) {
                $.each(data, function (idx, elem) {
                    var markup = `<div class="job-listing wtabs">
                                        <div class="job-title-sec">
                                                <div class="c-logo"> <img src="/uploads/${elem.fileName}" alt='Picture' class="thumb-nail lozad" style="height: 80px;width: 80px"/> </div>
                                                <h3><a href="/job/details/${elem.transactionId}" title="View Details">${elem.profession}</a></h3>
                                                 <span>${elem.postedBy}</span>
                                                <div class="job-lctn"><i class="la la-map-marker"></i>${elem.location}</div>
                                        </div>
                                            <div class="job-style-bx">
                                                    <span class="job-is ft ">${elem.natureOfContract}</span>
                                                    <span class="fav-job"><i class="la la-heart-o"></i></span>
                                                    <i>${elem.duration}</i>
                                            </div>
                                    </div>`;

                    $(".emply-resume-sec").append(markup);
                });

            }
        });
    }

    function findJobSeekerHeader(url) {


        $(".emply-resume-sec").empty();

        $.get(url, function (data) {
            if (data) {
                $("#paginated-items").empty();
                var lines = data.lines;
                var page = data.page;
                var totalPages = data.totalPages;

                if (totalPages > 1 && totalPages < 5) {
                    $(".pagination").removeClass("hide");
                    var alteredURL = removeParam("page", url);
                    $("#paginated-items").append(`<li><a class="first-anchor" href="#" data="${url}" > << </a></li>`);
                    for (let i = 0; i < totalPages; i++) {
                        $("#paginated-items").append(`<li class="${page === (i + 1) ? 'active' : ''}"><a class="paginated-anchor " href="#" data="${alteredURL}&page=${i + 1}" > ${i + 1} </a></li>`);
                    }
                    $("#paginated-items").append(`<li><a class="last-anchor" href="#" data="${url}"> >> </a></li>`);
                }

                $.each(lines, function (idx, elem) {
                    var markup = `<div class="job-listing wtabs">
                                        <div class="job-title-sec">
                                                <div class="c-logo"> <img src="/uploads/${elem.fileName}" alt='Picture' class="thumb-nail lozad" style="height: 80px;width: 80px"/> </div>
                                                <h3><a href="/job/details/${elem.transactionId}" title="View Details">${elem.profession}</a></h3>
                                                 <span>${elem.postedBy}</span>
                                                <div class="job-lctn"><i class="la la-map-marker"></i>${elem.location}</div>
                                        </div>
                                            <div class="job-style-bx">
                                                    <span class="job-is ft ">${elem.natureOfContract}</span>
                                                    <span class="fav-job"><i class="la la-heart-o"></i></span>
                                                    <i>${elem.duration}</i>
                                            </div>
                                    </div>`;

                    $(".emply-resume-sec").append(markup);
                });

            }
        });
    }

    $(document).on("click", ".paginated-anchor", function (e) {
        e.preventDefault();
        var url = $(this).attr("data");
        findJobSeekerHeader(url);
    });

    $(document).on("click", ".first-anchor", function (e) {
        e.preventDefault();
        var url_string = $(this).attr("data");
        var url = new URL("https://xjobs.com.gh" + url_string);
        var pageString = url.searchParams.get("page");
        var page = parseInt(pageString) - 1;
        if (page === 0) {
            page = 1;
        }
        var alteredURL = removeParam("page", url);

        var newUrl = `${alteredURL}&page=${page}`;

        alert(newUrl);

        findJobSeekerHeader(newUrl);
    });
    $(document).on("click", ".second-anchor", function (e) {
        e.preventDefault();
        var url_string = $(this).attr("data");
        var url = new URL("https://xjobs.com.gh" + url_string);
        var pageString = url.searchParams.get("page");
        var page = parseInt(pageString) + 1;
        if (page === 0) {
            page = 1;
        }
        var alteredURL = removeParam("page", url);

        var newUrl = `${alteredURL}&page=${page}`;

        alert(newUrl);

        findJobSeekerHeader(newUrl);
    });

    $('.lozad').Lazy();
    $("#forgot-password").toggle(1000);
    var url88 = "/find-address";
    //Find User's location
    $.get(url88, function (data, status) {
        $('#comp-tel').html(data.companyAddress);
        $('#adabraka-email').html(data.hodEmail);
    });

    $(".fav-job").on('click', function (e) {
        var id = $(this).attr('data-id');
        rowClicked(id);
    });

    let fruits = [];
    $('.spealism').click(function () {
        $(".emply-resume-sec").empty();
        $(".pagination-centered").addClass("hide");

        var desc = $(this).attr("data");

        if (this.checked === false) {

            removeItemOnce(fruits, desc);
            var url = `/findSeekerJob?q=${fruits}`;
            findJobSeeker(url);
        } else {
            fruits.push(desc);
            var url = `/findSeekerJob?q=${fruits}`;
            findJobSeeker(url);
        }

    });

    //Filter Specialization
    $("#searchSpec").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        //alert(value);
        $("#myDIV *").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });


    //Find Jobs By Salary

    $('.job-by-salary').on("click", function () {
        $(".emply-resume-sec").empty();
        $(".pagination-centered").addClass("hide");
        var url = ``;
        var desc = $(this).val();

        switch (desc) {
            case "under-thousand":
                url = `/find-seeker-job-by-salary?start=1&end=1000`;
                break;
            case "under-five-thousand":
                url = `/find-seeker-job-by-salary?start=1000&end=5000`;
                break;

            default:
                url = `/find-seeker-job-above-five?salary=5000`;
        }

        findJobSeeker(url);

    });

    $('.job-by-experiece').on("click", function () {

        var url = ``;
        var desc = $(this).val();

        switch (desc) {
            case "one-to-five":
                url = `/find-seeker-job-by-experience?start=1&end=5`;
                break;
            case "six-to-ten":
                url = `/find-seeker-job-by-experience?start=6&end=10`;
                break;

            case "above-ten":
                url = `/find-seeker-job-by-experience-above?exp=10`;
        }

        findJobSeekerHeader(url);

    });


    function removeParam(key, sourceURL) {
        var rtn = sourceURL.split("?")[0], param, params_arr = [],
            queryString = (sourceURL.indexOf("?") !== -1) ? sourceURL.split("?")[1] : "";
        if (queryString !== "") {
            params_arr = queryString.split("&");
            for (var i = params_arr.length - 1; i >= 0; i -= 1) {
                param = params_arr[i].split("=")[0];
                if (param === key) {
                    params_arr.splice(i, 1);
                }
            }
            if (params_arr.length) rtn = rtn + "?" + params_arr.join("&");
        }
        return rtn;
    }

});
