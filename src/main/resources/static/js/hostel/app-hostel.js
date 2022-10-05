$(document).ready( function () {
    $("[data-toggle='tooltip']").tooltip();

    $('#hostel-table').DataTable({
        "pageLength":50,
        dom: 'Bfrtip',
        buttons: [
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    });

    var hostelTable = $('#hostel-table').DataTable({
        "paging": true,
        "lengthChange": false,
        "searching": true,
        "ordering": true,
        "info": true,
        "retrieve":true,
        "autoWidth": false,
        "responsive": true,
        "pageLength": 5
    });

    getHostels();

    //create a hostel
    $("#hostel-form").on("submit",(e)=>{
        e.preventDefault();
        const id = $("#hostel-id").val();
        const name = $("#hostel-name").val();
        const location = $("#location").val();
        const address = $("#address").val();
        const telephone = $("#telephone").val();
        const status = $("#status").val();
        const description= $("#description").val();

        const data = {
            id:id,
            name:name,
            location: location,
            address: address,
            telephone:telephone,
            status: status,
            description: description
        };

       fetch("/hostel/create-hostel",
           {
               method:"POST",
               headers: {
                   'Content-Type': 'application/json'
               },
               body:JSON.stringify(data)
           }).then(result=>{
               // console.log(result);
               if(result.status === 200){
                   clearForm();
                   $("#addHostelModal .close").click();
                   Swal.fire({
                       icon: 'success',
                       title: 'Successful',
                       text: 'Hostel Saved Successfully!'
                   });
                   // clearForm();
                   getHostels();
               }else {
                   Swal.fire({
                       icon: 'error',
                       title: 'Not Saved',
                       text: 'Could not save, try again!'
                   });
               }
               Swal.fire({
                   icon: 'success',
                   title: 'Successful',
                   text: 'Hostel Saved Successfully!'
               });
               // clearForm();
               getHostels();
       })
    });

    //edit hostel
    $(document).on("click", ".edit-hostel", function (e) {
        e.preventDefault();
        clearForm();

        const modal = $('#addHostelModal');
        const hostelId = $(this).attr("data-id");

        $.get(`/hostel/hostels/${hostelId}`, function (hostel) {
            $("#hostel-id").val(hostel.id);
            $("#hostel-name").val(hostel.name);
            $("#location").val(hostel.location);
            $("#address").val(hostel.address);
            $("#telephone").val(hostel.telephone);
            $("#status").val(hostel.status);
            $("#description").val(hostel.description);
        });
        modal.modal('show');
    });
    //deleting a hostel
    $(document).on("click", ".delete-hostel", function (e) {
        // e.preventDefault();

        const hostelId = $(this).attr('data-id');
        const url = `/hostel/hostel/delete/${hostelId}`;

        $.get(url, function (data) {
            switch (data) {
                case true:
                    Swal.fire({
                        icon: 'success',
                        title: 'Successful',
                        text: 'Hostel deleted Successfully!'
                    })
                    getHostels();
                    break;
                default:
                    Swal.fire({
                        icon: 'error',
                        title: 'Error',
                        text: 'Could not delete, try again later'
                    })
                    break;
            }

        })

    });

    //clean the table
    function clearTable() {
        hostelTable.clear().draw();
    }

    //clean the form
    function clearForm() {
        $("#hostel-name").val('');
        $("#location").val('');
        $("#address").val('');
        $("#telephone").val('');
        $("#status").val();
        $("#description").val('');
    }

    //fill the table
    function getHostels() {
        clearTable();
        $.get("/hostel/hostels", function (hostels) {
            if (hostels.length !== 0) {
                hostels.map(function (hostel) {
                    hostelTable.row.add([
                        hostel.id,
                        hostel.name,
                        hostel.address,
                        hostel.location,
                        hostel.status,
                        hostel.telephone,
                        `<a data-id="${hostel.id}"  class="btn btn-info btn-sm edit-hostel"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${hostel.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-hostel"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }

} );

// $(function (){
//
// })