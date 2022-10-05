$(function () {
    var now = new Date().toLocaleDateString('en-us', {weekday: "long", year: "numeric", month: "short", day: "numeric"})
    $("#today-date").html(now);


    var cartTable = $('#contentTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });

    var videoTable = $('#videoTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });

    var quizTable = $('#quizTable').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });
    renderContent();
    renderVideoTable();
    renderQuizTable();
    $.get("/recruiter/current-user", function (data) {
        var fileName = data.filename;
        var username = data.name;

        $("#current-user-pic").attr("src", `/uploads/${fileName}`);
        $(".current-user-name").html(username);
    })


    $("#add-content-form").on("submit", function (event) {
        event.preventDefault();

        var user = $("#userId").val();
        var title = $("#title").val();
        var courseId = $("#course-content-id").val();
        var content = $("#content").val();
        var introduction = $("#introduction").val();
        var order = $("#display-content-order").val();

        var data = {
            id: user,
            title: title,
            courseId: courseId,
            content: content,
            introduction: introduction,
            displayOrder: order
        };
        console.log(data);
        var url = `/recruiter/course-content`;

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
                        swal("Success!", "Text Content saved successfully", "success");
                        renderContent();
                        clearText();
                        break;
                    default:
                        swal("Error!", "Could not save", "error");
                        break;
                }

            },
            error: function (e) {
                swal("Error!", "Could not save", "error");
            }
        });

    });

    $("#add-video-form").on("submit", function (event) {
        event.preventDefault();

        var user = $("#userId").val();
        var name = $("#name").val();
        var courseId = $("#course-video-id").val();
        var url = $("#url").val();
        var order = $("#display-video-order").val();

        let resultUrl = url.replace("watch?v=", "embed/");
//        if(url.includes("watch?v=")){
//            console.log()
//        }
        console.log(resultUrl)
        var data = {
            id: user,
            name: name,
            courseId: courseId,
            url: resultUrl,
            displayOrder: order
        };

        var url = `/recruiter/course-video`;

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
                        swal("Success!", "Video Content saved successfully", "success");
                        renderVideoTable();
                        clearText();
                        break;
                    default:
                        swal("Error!", "Could not save", "error");
                        break;
                }
            },
            error: function (e) {
                swal("Error!", "Could not save", "error");
            }
        });

    });

    $("#add-quiz-form").on("submit", function (event) {
        event.preventDefault();
        var quizId = $("#course-quiz-id").val();
        var question = $("#quiz").val();
        var answer = $("#answer").val();
//                            var assessmentLines = $("#assessment-line").val();
        var optionNumber = $("#question-number").val();
        let options = [];
        var data = {
            question: question,
            answer: answer,
            courseId: quizId
        };
        // the is answer for checking if the one of the options match with the answer chosen
        let isAnswer = false;
        //                        console.log(data)
        for (let i = 0; i < optionNumber; i++) {
            const opt = $(`#option${i}`).val();

            const option = {
                id: i,
                option: opt,
                quizQuestionId: 0
            }
            options.push(option);
            if (opt === answer) {
                isAnswer = true;
            }
        }

        if (isAnswer) {
            var url = `/recruiter/course-quiz`;
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
                        renderQuizTable()
                        clearText();
                        options[0].quizQuestionId = response;
                        //                                                console.log(options);
                        //                                               swal("Success!", "Quiz saved successfully", "success");

                        fetch('/recruiter/quiz-option', {
                            method: 'POST', // or 'PUT'
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(options),
                        })
                            .then(response => response.json())
                            .then(result => {

                            })
                            .catch((error) => {
//                                                                                 swal("Error!", "Could not save options", "error");
                                    swal("Success!", "saved successfully", "success");
                                }
                            );

                    } else {
                        swal("Error!", "Could not save question", "error");
                    }
                },
                error: function (e) {
                    swal("Error!", "Network problem", "error");
                }
            });
        } else {
            swal("Options and Answer!", "Make sure one of your options matches with the given answer", "error");
        }

    });

    function addQuestionIdToMultipleChoice(list, elementToAdd) {
        for (let j = 0; j < list.length; j++) {
            list[0].assessmentLineId = elementToAdd;
        }
    }

    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/recruiter/delete-assessment/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this assessment!",
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
                        swal("Success!", "Assessment has been deleted successfully", "success");
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

        var modal = $('#myModalVideo');
        var dataId = $(this).attr("data-id");
        var href = `/recruiter/findVideo/${dataId}`;

        $.get(href, function (course, status) { //Declare a GET that takes in href
//                console.log(course);
            document.getElementById('modal-id').innerHTML += `
                    <iframe width="760" height="500"
                         src="${course.url}"
                         title="${course.name}" frameborder="0"
                         class="embed-responsive-item"
                         allowfullscreen>
                    </iframe>
                `

        });

        modal.modal('show');
    });

    function clearTable() {
        cartTable.clear().draw();
    }

    function clearVideoTable() {
        videoTable.clear().draw();
    }

    function clearQuizTable() {
        quizTable.clear().draw();
    }


    function renderContent() {
        clearTable();
        const id = $('#global-course-id').val();
        $.get(`/recruiter/find-contents-id/${id}`, function (cartItems) {

            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.course.courseTitle,
                        item.title,
                        item.displayOrder,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>

                         `
                    ]).draw(true);
                });
            }
        });
    }

    function renderVideoTable() {
        clearVideoTable();
        const courseId = $('#global-course-id').val();

        $.get(`/recruiter/find-courses-video/${courseId}`, function (cartItems) {

            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    videoTable.row.add([
                        item.id,
                        item.course.courseTitle,
                        item.name,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Watch This Video"><i class="fa fa-eye"></i> </a>
                                  `
                    ]).draw(true);
                });
            }
        });
    }

    function renderQuizTable() {
        clearQuizTable();
        const id = $('#global-course-id').val();
        $.get(`/recruiter/find-course-quiz/${id}`, function (cartItems) {
            console.log(cartItems)
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    quizTable.row.add([
                        item.id,
                        item.question,
                        item.answer,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                                 <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>
                                 `
                    ]).draw(true);
                });
            }
        });
    }


})
