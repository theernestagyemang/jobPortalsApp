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

    var blockTable = $('#block-table').DataTable({
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

    getBlocks();

    //create a block
    $("#block-form").on("submit",(e)=>{
        e.preventDefault();
        const id = $("#block-id").val();
        const name = $("#block-name").val();
        const hostelId = $("#hostel").val();
        const status = $("#status").val();
        const numberOfFloor = $("#number-of-floor").val();
        const description= $("#description").val();


        const data = {
            id:id,
            name:name,
            hostelId:hostelId,
            numberOfFloors:numberOfFloor,
            status: status,
            description: description
        };

        console.log(data);
        fetch("/hostel/create-block",
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
                text: 'Block Saved Successfully!'
            });
            clearForm();
            getBlocks();
        })
    });

    //grab the hostels and attach them to the input select field
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
    //edit hostel
    $(document).on("click", ".edit-block", function (e) {
        e.preventDefault();
        clearForm();

        const modal = $('#addHostelBlockModal');
        const blockId = $(this).attr("data-id");

        $.get(`/hostel/blocks/${blockId}`, function (block) {
            $("#block-name").val(block.name);
            $("#number-of-floor").val(block.numberOfFloors);
            $("#status").val(block.status);
            $("#description").val(block.description);
            $("#block-id").val(block.id);

        });
        modal.modal('show');
    });
    //deleting a hostel
    $(document).on("click", ".delete-block", function (e) {
        // e.preventDefault();

        const blockId = $(this).attr('data-id');
        const url = `/hostel/block/delete/${blockId}`;

        $.get(url, function (data) {
            switch (data) {
                case true:
                    Swal.fire({
                        icon: 'success',
                        title: 'Successful',
                        text: 'Block deleted Successfully!'
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
        blockTable.clear().draw();
    }

    //clean the form
    function clearForm() {
        $("#block-name").val('');
        $("#number-of-floor").val('');
        $("#status").val();
        $("#description").val('');
    }

    //fill the table
    function getBlocks() {
        clearTable();
        $.get("/hostel/block-list", function (blocks) {
            // console.log(blocks)
            if (blocks.length !== 0) {
                blocks.map(function (hostel) {
                    blockTable.row.add([
                        hostel.id,
                        hostel.name,
                        hostel.numberOfFloors,
                        hostel.status,
                        hostel.hostel.name,
                        `<a data-id="${hostel.id}"  class="btn btn-info btn-sm edit-block"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${hostel.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-block"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }

} );
