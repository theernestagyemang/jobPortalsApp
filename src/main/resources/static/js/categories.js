/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    let url = "/all-categories";
    let page = 1;
    let totalPages = 0;
    $.ajax({
        type: "GET",
        url: url,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            var content = data.content;
            totalPages = data.totalPages;
            var more = `<a href="#" title="View More Categories" onclick='addLess()' class="btn btn-success" id="more"><i class="fa fa-arrow-right"></i>Prev </a>`;
            $.each(content, function (idx, elem) {

                var markup = "<div class='col-lg-2 col-md-2 col-sm-6 btn btn-success'>" +
                    " <a  title='" + elem + "' href='/job-by-category?ct=" + elem + "'> <i class='fa fa-search'></i> <em>" + elem + "</em></a>"
                    + "</div>";
                $("#categories").append(markup);
            });
            var more = `<a href="#" title="View More Categories" onclick='addMore()' class="btn btn-success" id="more"><i class="fa fa-arrow-right"></i>Next </a>`;
            $("#categories").append(more);

        },
        error: function (e) {
            console.log("Sorry, We couldn find Items");
        }
    });

    function addLess() {
        page = page - 1;
        if (page == 0) {
            page = 1;
        }
        let modifiedUrl = "/all-categories?page=" + page;

        alert("Url..." + modifiedUrl);
        addCategoryButtons(modifiedUrl);
        5

    }

    function addMore() {
        page = page + 1;
        let modifiedUrl = "/all-categories?page=" + page;

        alert("Url..." + modifiedUrl);
        addCategoryButtons(modifiedUrl);
    }

    function addCategoryButtons(url) {

        $.get(url, function (data) {
            var content = data.content;
            $.each(content, function (idx, elem) {
                var markup = "<div class='col-lg-2 col-md-2 col-sm-6 btn btn-success'>" +
                    " <a  title='" + elem + "' href='/job-by-category?ct=" + elem + "'> <i class='fa fa-search'></i> <em>" + elem + "</em></a>"
                    + "</div>";
                $("#categories").append(markup);
            });
            var more = `<a href="/categories" title="View More Categories" class="btn btn-success moreCategory" id="more"><i class="fa fa-arrow-right"></i>More </a>`;
            $("#categories").append(more);
        });
    }
});

 