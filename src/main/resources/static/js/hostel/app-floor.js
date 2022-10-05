$(document).ready( function () {
    $("[data-toggle='tooltip']").tooltip();

    $('#block-table').DataTable({
        "pageLength":50,
        dom: 'Bfrtip',
        buttons: [
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    });

    var floorTable = $('#floor-table').DataTable({
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

    getFloors();

    //create a block
    $("#floor-form").on("submit",(e)=>{
        e.preventDefault();
        const id = $("#floor-id").val();
        const name = $("#floor-name").val();
        const blockId = $("#block-id").val();
        const status = $("#status").val();
        const numberOfRooms = $("#number-of-rooms").val();


        const data = {
            id:id,
            name:name,
            numberOfRooms:numberOfRooms,
            status: status,
            blockId:blockId
        };

        // console.log(data);
        fetch("/hostel/create-floor",
            {
                method:"POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                body:JSON.stringify(data)
            }).then(result=>{
            // console.log(result);
            // if(result.status === 200){
            //     clearForm();
            //     $("#addHostelModal .close").click();
            //     Swal.fire({
            //         icon: 'success',
            //         title: 'Successful',
            //         text: 'Block Saved Successfully!'
            //     });
            //     // clearForm();
            //     getHostels();
            // }else {
            //     Swal.fire({
            //         icon: 'error',
            //         title: 'Not Saved',
            //         text: 'Could not save, try again!'
            //     });
            // }
            // console.log(result);
            Swal.fire({
                icon: 'success',
                title: 'Successful',
                text: 'Floor Saved Successfully!'
            });
            clearForm();
            getFloors();
        })
    });

    // //grab the hostels and attach them to the input select field
    // $.get("/hostel/hostels", function (data) {
    //     $("#hostel").empty();
    //     $('#hostel').append($('<option>').val("default").text("Choose the Hostel"));
    //     $.each(data, function (idx, hostel) {
    //         $('#hostel').append($('<option>', {
    //             value: hostel.id,
    //             text: hostel.name
    //         }));
    //     });
    // });
    //edit hostel
    $(document).on("click", ".edit-floor", function (e) {
        e.preventDefault();
        clearForm();

        const modal = $('#addHostelFloorModal');
        const floorId = $(this).attr("data-id");

        $.get(`/hostel/floors/${floorId}`, function (floor) {
            // console.log(floor)
            $("#floor-id").val(floor.id);
            $("#floor-name").val(floor.name);
            $("#block-id").val(floor.hostelBlock.name);
            $("#status").val(floor.status);
            $("#number-of-rooms").val(floor.numberOfRooms);

        });
        modal.modal('show');
    });
    //deleting a hostel
    $(document).on("click", ".delete-floor", function (e) {
        // e.preventDefault();

        const blockId = $(this).attr('data-id');
        const url = `/hostel/floor/delete/${blockId}`;

        $.get(url, function (data) {
            switch (data) {
                case true:
                    Swal.fire({
                        icon: 'success',
                        title: 'Successful',
                        text: 'Floor deleted Successfully!'
                    })
                    getFloors();
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
        floorTable.clear().draw();
    }

    //clean the form
    function clearForm() {
        $("#floor-id").val(0);
        $("#floor-name").val('');
        $("#block-id").val('');
        $("#status").val('');
        $("#number-of-rooms").val('');
    }

    //fill the table
    function getFloors() {
        clearTable();
        $.get("/hostel/floors", function (floors) {

            if (floors.length !== 0) {
                floors.map(function (floor) {
                    floorTable.row.add([
                        floor.id,
                        floor.name,
                        floor.hostelBlock.hostel.name,
                        floor.hostelBlock.name,
                        floor.numberOfRooms,
                        floor.status,
                        `<a data-id="${floor.id}"  class="btn btn-info btn-sm edit-floor"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${floor.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-floor"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }

} );
