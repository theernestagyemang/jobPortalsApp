$(function () {
    var now = new Date().toLocaleDateString('en-us', {weekday: "long", year: "numeric", month: "short", day: "numeric"})
    $("#today-date").html(now);


    var cartTable = $('#cartTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });
    rerender();

    $.get("/recruiter/current-user", function (data) {
        var fileName = data.filename;
        var username = data.name;

        $("#current-user-pic").attr("src", `/uploads/${fileName}`);
        $(".current-user-name").html(username);
    })


    $("#add-user-form").on("submit", function (event) {
        event.preventDefault();
        var courseId = $("#userId").val();
        var question = $("#question").val();
        var answer = $("#answer").val();
        var assessmentLines = $("#assessment-line").val();
        var optionNumber = $("#question-number").val();
        let options = [];
        var data = {
            question: question,
            answer: answer,
            assessmentLineId: assessmentLines
        };
//                        console.log(data)
        for (let i = 0; i < optionNumber; i++) {
            const opt = $(`#option${i}`).val();
//
            const option = {
                id: i,
                choice: opt,
                assessmentQuestionId: 0
            }
            options.push(option);
        }

        var url = `/recruiter/assess-question`;
        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(data),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,
            success: function (response) {
//                                            console.log(data);
                if (response != null) {
                    rerender();
                    clearText();
                    options[0].assessmentQuestionId = response;

                    swal("Success!", "Question saved successfully", "success");

                    fetch('/recruiter/multiple-choice', {
                        method: 'POST', // or 'PUT'
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(options),
                    })
                        .then(response => response.json())
                        .then(result => {
                            swal("Success!", "Options saved successfully", "success");
                        })
                        .catch((error) => {
                                swal("Error!", "Could not save", "error");
                            }
                        );

                } else {
                    swal("Error!", "Could not save", "error");
                }
            },
            error: function (e) {
                swal("Error!", "Could not save", "error");
            }
        });

    });

    function addQuestionIdToMultipleChoice(list, elementToAdd) {
        for (let j = 0; j < list.length; j++) {
            list[0].assessmentLineId = elementToAdd;
        }
    }

    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-one/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this question!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            cancelButtonText: "No, cancel plx!",
            closeOnConfirm: false,
            closeOnCancel: false
        }, function (isConfirm) {
            $.get(url, function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal("Success!", "Question has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete course", "error");
                        break;
                }

            })

        });
    });

    function clearText() {
        $('#userId').val(0);
        $('#courseTitle').val("");
        $('#courseCategory').val("");
    }

    $("#add-user-form1").on("submit", (e) => {
        e.preventDefault();
        var courseFeatured = $("#featured1").val();
        var featured = false;
        if (courseFeatured === "Featured Course") {
            featured = true;
        }
        const data = {
            id: $("#userId1").val(),
            courseTitle: $("#courseTitle1").val(),
            courseCategory: $("#courseCategory1").val(),
            status: 'Active',
            description: $("#courseDescription1").val(),
            featured: featured,
            price: $("#price1").val()
        }
    })
    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/findCourse/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href
//            console.log(user);
            if (user.featuredCourse) {
                $('#featured').val("Featured Course");
            } else {
                $('#featured').val("Not Featured Course");
            }
            $('#userId').val(user.id);
            $('#courseTitle').val(user.courseTitle);
            $('#courseCategory').val(user.category);
            $('#courseDescription').val(user.description);
            $('#price').val(user.price);

        });
//        $('#div-update').innerHTML = `<button type="submit" class="btn btn-warning" >Update Course</button>`;
        modal.modal('show');
    });

    function addCommas(nStr) {
        nStr += '';
        var x = nStr.split('.');
        var x1 = x[0];
        var x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        return x1 + x2;
    }

    $(document).ajaxStart(function () {
        Pace.restart();
    })


    function clearTable() {
        cartTable.clear().draw();
    }



    function rerender() {
        clearTable();
        $.get("/recruiter/multiple-choices", function (cartItems) {
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.choice,
                        item.assessmentQuestionId,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }
})