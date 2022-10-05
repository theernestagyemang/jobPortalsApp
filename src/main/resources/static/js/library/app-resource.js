$(document).ready( function () {
    $("[data-toggle='tooltip']").tooltip();

    $('#resource-table').DataTable({
        "pageLength":50,
        dom: 'Bfrtip',
        buttons: [
            'excelHtml5',
            'csvHtml5',
            'pdfHtml5'
        ]
    });

    var resourceTable = $('#resource-table').DataTable({
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

    getresources();

    //create a resource
    $("#resource-form").on("submit",(e)=>{
        e.preventDefault();
        const id = $("#resource-id").val();
        const name = $("#resource-name").val();
        const description = $("#description").val();
        const type = $("#type").val();
        const quantity = $("#quantity").val();
        const barcode = $("#barcode").val();
        const isbn = $("#isbn").val();
        const status = $("#status").val();
        const author = $("#author").val();
        const publisher = $("#publisher").val();
        const filename = $("#filename").val();
        const numberOfFloor = $("#number-of-floor").val();
        const resourceLocationId = $("#location").val();
        const resourceCategoryId = $("#category").val();


        const data = {
            id:id,
            name:name,
            description:description,
            numberOfFloors:numberOfFloor,
            type:type,
            quantity:quantity,
            barcode:barcode,
            isbn:isbn,
            status:status,
            author:author,
            publisher:publisher,
            filename:filename,
            resourceLocationId:resourceLocationId,
            resourceCategoryId:resourceCategoryId

        };

        console.log(data);
        fetch("/hostel/create-resource",
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
            //         text: 'resource Saved Successfully!'
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
                text: 'resource Saved Successfully!'
            });
            clearForm();
            getresources();
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
    $(document).on("click", ".edit-resource", function (e) {
        e.preventDefault();
        clearForm();

        const modal = $('#addHostelresourceModal');
        const resourceId = $(this).attr("data-id");

        $.get(`/hostel/resources/${resourceId}`, function (resource) {
            $("#resource-name").val(resource.name);
            $("#number-of-floor").val(resource.numberOfFloors);
            $("#status").val(resource.status);
            $("#description").val(resource.description);
            $("#resource-id").val(resource.id);

        });
        modal.modal('show');
    });
    //deleting a hostel
    $(document).on("click", ".delete-resource", function (e) {
        // e.preventDefault();

        const resourceId = $(this).attr('data-id');
        const url = `/hostel/resource/delete/${resourceId}`;

        $.get(url, function (data) {
            switch (data) {
                case true:
                    Swal.fire({
                        icon: 'success',
                        title: 'Successful',
                        text: 'resource deleted Successfully!'
                    })
                    getresources();
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
        resourceTable.clear().draw();
    }

    //clean the form
    function clearForm() {
        $("#resource-name").val('');
        $("#number-of-floor").val('');
        $("#status").val();
        $("#description").val('');
    }

    //fill the table
    function getresources() {
        clearTable();
        $.get("/hostel/resource-list", function (resources) {
            // console.log(resources)
            if (resources.length !== 0) {
                resources.map(function (hostel) {
                    resourceTable.row.add([
                        hostel.id,
                        hostel.name,
                        hostel.numberOfFloors,
                        hostel.status,
                        hostel.hostel.name,
                        `<a data-id="${hostel.id}"  class="btn btn-info btn-sm edit-resource"  rel="tooltip" title="Edit Course"> <i class="fa fa-edit"></i> </a>
                        <a href="" data-id="${hostel.id}" data-request-type="delete" class="btn btn-danger btn-sm delete-resource"> <i class="fa fa-trash"></i></a>`
                    ]).draw(true);
                });
            }
        });
    }

} );
