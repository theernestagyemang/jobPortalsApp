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

    function checkRound(rounds, priority){
         // console.log(rounds);
        let status = false;
            for(let i=0; i<rounds.length; i++){
                if(rounds[i].roundPriority == priority){
                    // console.log("duplication")
                    status = true; break;
                }
            }
            return status;
    }
    function checkRoundDifference(rounds, priority){
        let maxRound = rounds[0].roundPriority;
        rounds.forEach(round=>{
            if(maxRound < round.roundPriority){
                maxRound = round.roundPriority;
            }
        });
        // console.log(maxRound);
        maxRound +=1;
        // console.log(maxRound);
        if (maxRound == priority){
            // console.log("difference is okay")
            return false;
        }else {
            return true;
        }

    }
    $("#add-user-form").on("submit", function (event) {
        event.preventDefault();

        var name = $("#round-title").val();
        var roundPriority = $("#round-priority").val();

        var data = {
            id: 0,
            roundName: name,
            roundPriority: roundPriority
        };

        $.get("/admin/round", function (rounds, status) {
           if(rounds.length > 0){
               if(checkRound(rounds,data.roundPriority)){
                   swal("Priority Duplication", "The priority is already used so, kindly change it", "error");
               }else {
                   if(checkRoundDifference(rounds,data.roundPriority)){
                       swal("Priorities Difference", "The difference between two consecutive priority must be one", "error");
                   }else {

                       var url = `/admin/add-round`;

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
                                       swal("Success!", "Assessment round saved successfully", "success");
                                       rerender();
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
                   }
               }
           }else {
               var url = `/admin/add-round`;

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
                               swal("Success!", "Assessment round saved successfully", "success");
                               rerender();
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
           }
        })

    });

    $(document).on("click", ".delete-user", function (e) {
        e.preventDefault();

        var userId = $(this).attr('data-id');
        var url = `/admin/delete-round/${userId}`;

        swal({
            title: "Are you sure?",
            text: "This is will delete this assessment around!",
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
                        swal("Success!", "Assessment round has been deleted successfully", "success");
                        rerender();
                        break;
                    default:
                        swal("Error!", "Could not delete Assessment Round", "error");
                        break;
                }

            })

        });
    });

    function clearText() {
        $('#userId').val(0);
        $('#round-title').val("");
        $('#round-priority').val("");
    }


    $(document).on("click", ".editBtn2", function (e) {
        e.preventDefault();
        clearText();

        var modal = $('#myModal2');
        var dataId = $(this).attr("data-id");
        var href = `/admin/round/${dataId}`;

        $.get(href, function (user, status) { //Declare a GET that takes in href
            $("#userId").val(user.id);
            $("#round-title").val(user.name);
            $("#round-priority").val(user.roundPriority);
        });
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
        $.get("/admin/round", function (cartItems) {
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index, myArr) {
                    cartTable.row.add([
                        item.id,
                        item.roundName,
                        `<a data-id="${item.id}"  class="btn btn-info btn-sm editBtn2"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${item.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-user"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }


})