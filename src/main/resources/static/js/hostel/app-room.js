$(document).ready( function () {
    $("[data-toggle='tooltip']").tooltip();

    $('#room-table').DataTable({
        "pageLength":50,
        dom: 'Bfrtip',
        buttons: [
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    });

    var roomTable = $('#room-table').DataTable({
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

    //grab all hotels
    $.get("/hostel/hostels", function (data) {
        $("#hostel").empty();
        $('#hostel').append($('<option>').val("default").text("Choose the Hostel"));
        $.each(data, function (idx, hostel) {
            $('#hostel').append($('<option>', {
                value: hostel.id,
                text: hostel.name
            }));
        });
    });

    $("#hostel").on("blur",()=>{
        const selectedHostel = $("#hostel").val();
        // console.log(selectedHostel);
        if(selectedHostel !== 'undefined'){
            $.get(`/hostel/blocks-by-hostel/${selectedHostel}`, function (data) {
                $("#block").empty();
                $('#block').append($('<option>').val("default").text("Choose the Block"));
                $.each(data, function (idx, hostel) {
                    $('#block').append($('<option>', {
                        value: hostel.id,
                        text: hostel.name
                    }));
                });
            });
        }
    });

    $("#block").on("blur",()=>{
        const selectedBlock = $("#block").val();
        // console.log(selectedHostel);
        if(selectedBlock !== 'undefined'){
            $.get(`/hostel/floors-by-block/${selectedBlock}`, function (data) {
                $("#floor").empty();
                $('#floor').append($('<option>').val("default").text("Choose the Floor"));
                $.each(data, function (idx, hostel) {
                    $('#floor').append($('<option>', {
                        value: hostel.id,
                        text: hostel.name
                    }));
                });
            });
        }
    });

    // $("#floor").on("blur",()=>{
    //     const selectedFloor = $("#floor").val();
    //     // console.log(selectedHostel);
    //     if(selectedBlock !== 'undefined'){
    //         $.get(`/hostel/floors-by-block/${selectedBlock}`, function (data) {
    //             $("#block").empty();
    //             $('#block').append($('<option>').val("default").text("Choose the Floor"));
    //             $.each(data, function (idx, hostel) {
    //                 $('#block').append($('<option>', {
    //                     value: hostel.id,
    //                     text: hostel.name
    //                 }));
    //             });
    //         });
    //     }
    // });

    getBlocks();

    //create a block
    $("#room-form").on("submit",(e)=>{
        e.preventDefault();
        const id = $("#room-id").val();
        const name = $("#room-name").val();
        const floorId = $("#floor").val();
        const status = $("#status").val();
        const roomCapacity = $("#room-capacity").val();
        const roomNumber = $("#room-number").val();
        const data = {
            id:id,
            name:name,
            numberOfBeds:roomCapacity,
            roomNumber:roomNumber,
            status: status,
            floorId:floorId
        };

        // console.log(data);
        fetch("/hostel/create-room",
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
            console.log(result);
            Swal.fire({
                icon: 'success',
                title: 'Successful',
                text: 'Room Saved Successfully!'
            });
            clearForm();
            getBlocks();
        })
    });
    
    //edit room
    $(document).on("click", ".edit-room", function (e) {
        e.preventDefault();
        clearForm();

        const modal = $('#addHostelRoomModal');
        const roomId = $(this).attr("data-id");

        $.get(`/hostel/rooms/${roomId}`, function (room) {
            $("#room-id").val(room.id);
            $("#room-name").val(room.name);
            $("#room-capacity").val(room.numberOfBeds);
            $("#status").val(room.status);
            $("#floor").val(room.hostelFloor.name);
            $("#room-number").val(room.roomNumber);

            $('#floor').append($('<option>', {
                value: room.hostelFloor.id,
                text: room.hostelFloor.name
            }));
        });
        modal.modal('show');
    });
    //deleting a hostel
    $(document).on("click", ".delete-room", function (e) {
        // e.preventDefault();

        const roomId = $(this).attr('data-id');
        const url = `/hostel/room/delete/${roomId}`;

        $.get(url, function (data) {
            switch (data) {
                case true:
                    Swal.fire({
                        icon: 'success',
                        title: 'Successful',
                        text: 'Room deleted Successfully!'
                    })
                    getBlocks();
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
        roomTable.clear().draw();
    }

    //clean the form
    function clearForm() {
        $("#room-id").val(0);
        $("#room-name").val('');
        $("#room-capacity").val('');
        $("#status").val();
        $("#floor").val('');
        $("#room-number").val('')
    }

    //fill the table
    function getBlocks() {
        clearTable();
        $.get("/hostel/room-list", function (rooms) {
            // console.log(rooms)
            if (rooms.length !== 0) {
                rooms.map(function (room,index) {
                    roomTable.row.add([
                        index+1,
                        room.name,
                        room.hostelFloor.hostelBlock.hostel.name,
                        room.hostelFloor.hostelBlock.name,
                        room.hostelFloor.name,
                        room.numberOfBeds,
                        `<a data-id="${room.id}"  class="btn btn-info btn-sm edit-room"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${room.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-room"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }

} );
