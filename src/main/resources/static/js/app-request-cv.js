// $(function () {
//
//     var cartTable = $('#cartTable').DataTable({
//         "paging": true,
//         "lengthChange": false,
//         "searching": true,
//         "ordering": true,
//         "info": true,
//         "autoWidth": false,
//         "responsive": true,
//         "pageLength": 5,
//
//     });
//
//     reloadTable();
//
//
//     function clearTable() {
//         cartTable.clear().draw();
//     }
//
//     function reloadTable() {
//         clearTable();
//         var url = `/recruiter/service/requested`;
//         $.get(url, function (data) {
//                 rerender(data);
//         })
//     }
//
//     $(document).on("click", ".resume-action", function (e) {
//         e.preventDefault();
//         var id = $(this).attr("data-id");
//         var action = $(this).attr("data-action");
//
//         $.get(`/recruiter/resume-request-status-change/${id}/${action}`, function (data) {
//             switch (data) {
//                 case "SUCCESS" :
//                     $.toast({
//                         heading: 'Success:',
//                         text: 'Request status has been changed successfully',
//                         position: 'top-right',
//                         loaderBg: '#ff6849',
//                         icon: 'info',
//                         hideAfter: 3000,
//                         stack: 6
//                     });
//                     reloadTable();
//                     break;
//
//                 case "FAILED":
//                     $.toast({
//                         heading: 'Failed:',
//                         text: 'Could not update this request status',
//                         position: 'top-right',
//                         loaderBg: '#ff6849',
//                         icon: 'error',
//                         hideAfter: 3500
//                     });
//                     reloadTable();
//                     break;
//
//                 default:
//                     $.toast({
//                         heading: 'Failed:',
//                         text: 'Could not update this request status',
//                         position: 'top-right',
//                         loaderBg: '#ff6849',
//                         icon: 'error',
//                         hideAfter: 3500
//                     });
//                     reloadTable();
//                     break;
//             }
//         })
//
//
//     })
//
//     function rerender(cartItems) {
//         console.log("function called")
//         console.log(cartItems)
//         if (cartItems.length !== 0) {
//             cartItems.map(function (item, index) {
//
//                 cartTable.row.add([
//                     index+1,
//                     item.entryDate,
//                     item.requestedBy.fullName,
//                     item.requestedBy.username,
//                     item.requestedBy.telephone,
//                     item.transactionId,
//                     item.transactionId,
//                     `<div class="dropdown">
//                             <button class="btn btn-rounded btn-outline btn-success dropdown-toggle" type="button" data-toggle="dropdown">Action</button>
//                             <div class="dropdown-menu">
//                               <a data-id="${item.id}" data-action="contact" class="dropdown-item resume-action" href="#">Contacted</a>
//                               <a data-id="${item.id}" data-action="reject" class="dropdown-item resume-action" href="#">Rejected</a>
//                               <div class="dropdown-divider"></div>
//                               <a data-id="${item.id}" data-action="delete" class="dropdown-item resume-action" href="#">Delete </a>
//                             </div>
//                       </div>`
//
//                 ]).draw(true);
//
//             });
//
//
//         }
//
//
//     }
// });
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


    $(document).ajaxStart(function () {
        Pace.restart();
    })


    function clearTable() {
        cartTable.clear().draw();
    }


    function rerender() {
        clearTable();
        $.get("/recruiter/service/requested", function (cartItems) {
           console.log("endpoint called")
           console.log(cartItems);
            if (cartItems.length !== 0) {
                cartItems.map(function (item, index) {
                    cartTable.row.add([
                        index+1,
                        item.entryDate,
                        item.requestedBy.fullName,
                        item.requestedBy.username,
                        item.requestedBy.telephone,
                        item.transactionId,
                        `<div class="dropdown">
                            <button class="btn btn-rounded btn-outline btn-success dropdown-toggle" type="button" data-toggle="dropdown">Action</button>
                            <div class="dropdown-menu">
                              <a data-id="${item.id}" data-action="contact" class="dropdown-item resume-action" href="#">Contacted</a>
                              <a data-id="${item.id}" data-action="reject" class="dropdown-item resume-action" href="#">Rejected</a>
                              <div class="dropdown-divider"></div>
                              <a data-id="${item.id}" data-action="delete" class="dropdown-item resume-action" href="#">Delete </a>
                            </div>
                      </div>`

                    ]).draw(true);
                });
            }
        });
    }


})