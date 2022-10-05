let wishlist = [];
var paginationBtn;
var markup = "";
var liMarkup = "";
let wishcount = document.getElementById("wishcount");
let selectedJobId = document.getElementById("jobId").value;

function deteteTableRow() {
    markup = "";
    $(".emply-list-sec").empty();
}

function removeItemOnce(arr, value) {

    var index = arr.indexOf(value);
    if (index > -1) {
        arr.splice(index, 1);
    }
    return arr;
}

function clearCart() {
    localStorage.removeItem("shortlistedItems");

}

function paginationClicked(val) {

    var searchTerm = $(".searchBox").val();
    var url = `/public/find-multiple?q=${searchTerm}&page=${val}`;

    loadData(url);
}

function loadData(url) {
    deteteTableRow();
    $.get(url, function (data) {
        if (data) {

            var searchTerm = $(".searchBox").val();
            var totalPages = data.totalPages;
            var totalElements = data.numberOfElements;
            var size = data.size;

            var dt = `${totalElements} records where found `;
            paginationBtn = '';
            var i;
            for (i = 0; i < totalPages; i++) {
                paginationBtn += `<a href="${url}&page="${i + 1}" id="${i + 1}" data-page="${i}" >${i + 1}</a>`;
            }

            $("#records").html(dt);


            $.each(data.content, function (idx, elem) {

                markup += `<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
                                                <div class="emply-list box">
                                                        <div class="emply-list-thumb">
                                                                <a href="#" title="">
                                                                    <img src="/uploads/${elem.picFileName}" alt='' class="thumb-nail lozad"
                                                                </a>
                                                        </div>
                                                        <div class="emply-list-info">
                                                                <div class="emply-pstn">
                                                                    <a class="btn-link" href="#" onclick="shortlistCandidate(${elem.id})"><i class="la la-users"></i> Shortlist </a>
                                                                    <a class="btn-link" href="/downloadCv/${elem.id}"><i class="la la-download"></i> CV</a>
                                                                    <a class="btn-link signup-popup" href="#" data-id="${elem.id}" onclick="openModal(${elem.id})" ><i class="la la-eye"></i> Details</a>
        
                                                                </div>
                                                                <h3><a href="#" data-id="${elem.id}"  onclick="openModal(${elem.id})">${elem.fullName}</a></h3>
                                                                <span><i>${elem.proffesionalTitile} </i> :Experience ${elem.yearsOfExperiece} </span>
                                                                <h6><i class="la la-map-marker"></i> ${elem.currentLocation}</h6>
                                                                <p><i class="la la-phone"></i>${elem.primaryContact} </p>
                                                                <p><i class="la la-envelope"></i>${elem.email} </p>
                                                                
                                                        </div>
                                                </div> 
                                        </div>`;

                markup += `<div class="col-12 col-lg-4">
				<div class="box ribbon-box">
				  <div class="ribbon-two ribbon-two-primary"><span ></span></div>
				  <div class="box-header no-border p-0">				
					<a href="#">
					  <img class="img-fluid" src="/images/avatar/375x200/1.jpg" alt="">
					</a>
				  </div>
				  <div class="box-body">
						<div class="user-contact list-inline text-center">
							<a href="#" class="btn btn-circle mb-5 btn-facebook"><i class="fa fa-facebook"></i></a>
							<a href="#" class="btn btn-circle mb-5 btn-instagram"><i class="fa fa-instagram"></i></a>
							<a href="#" class="btn btn-circle mb-5 btn-twitter"><i class="fa fa-twitter"></i></a>
							<a href="#" class="btn btn-circle mb-5 btn-warning"><i class="fa fa-envelope"></i></a>				
						</div>
					  <div class="text-center">
						<h3 class="my-10"><a href="#">Tristan</a></h3>
						<h6 class="user-info mt-0 mb-10 text-fade">Project Manager</h6>
						<p class="text-fade w-p85 mx-auto">125 Ipsum Lorem Ave, Suite 458 New York, USA 154875 </p>
					  </div>
				  </div>
				</div>
			  </div>`


            });
            var pgination = `<div class="col-lg-12">
                                            <div class="pagination">
                                                
                                                    <a href="#"><i class="la la-long-arrow-left"></i> Prev</a>
                                                     ${paginationBtn}
                                                    <a href="#">Next <i class="la la-long-arrow-right"></i></a>
                                               
                                            </div>
                                        </div>`;


            $(".emply-list-sec").append(`
                                <div class="row" id="masonry">${markup}</div>
                                ${pgination}
                            `);
        }
    });
}

function findJobSeeker(fruits) {
    var url = `/public/findJobSeekerBySpecialization?q=${fruits}`;

    $.get(url, function (data) {
        loadData(url);
    });
}

function shortlistApplicant(val) {
    rowClicked(val);
}


var jobid = $("#jobId").val();

$(function () {


    let fruits = [];
    $(".spealism").on("click", function (e) {
        e.preventDefault();
        alert("clicked....")

        var desc = $(this).attr("data");

        if (this.checked === false) {
            removeItemOnce(fruits, desc);
            findJobSeeker(fruits);
        }

        if (this.checked === true) {
            fruits.push(desc);
            findJobSeeker(fruits);
        }

    });

    $('.removeFromList').on("click", function () {
        var id = $(this).attr("id");
        removeFromCart(id);
        $(".shortlist-dropdown").empty();
        liMarkup = "";
        rerender();
    });

    $('.lozad').Lazy();
    rerender();

    $(".btn-search").on('click', function () {
        var searchTerm = $(".searchBox").val();
        var url = `/public/find-multiple?q=${searchTerm}`;
        loadData(url);
    });


    $(document).on('click', '.start-shortlist', function () {
        let ct = wishcount.innerHTML;
        let ct2 = parseInt(ct);
        var selectedJob = $("#jobTitle").val();
        if (ct2 > 0) {
            swal({
                    title: "Shortlist applicants ?",
                    text: `You are shortlisting ${ct2.toLocaleString()} applicant(s) for ${selectedJob}!`,
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, go ahead!",
                    cancelButtonText: "No, cancel plx!",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        if (!shortlistAllApplicants()) {
                            swal("Error", "Sorry some error occured, we could not shortlist  :)", "error");
                        }

                    } else {
                        swal("Cancelled", "Candidate not shortlisted :)", "error");
                    }
                });
        }


    });

    $(".clear-shortlisted").on('click', function () {
        let ct = wishcount.innerHTML;
        let ct2 = parseInt(ct);


        if (ct2 > 0) {
            swal({
                    title: "Are you sure?",
                    text: "All shortlisted candidates will be deleted!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "Yes, delete it!",
                    cancelButtonText: "No, cancel plx!",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        clearCart();
                        $(".shortlist-dropdown").empty();
                        wishcount.innerHTML = 0;
                        swal("Deleted!", "Your shortlisted candidates has been deleted.", "success");
                    } else {
                        swal("Cancelled", "Your shortlisted candidates are safe :)", "error");
                    }
                });
        }


    });
});

function getCartItems() {
    const shortlistedItems = localStorage.getItem('shortlistedItems')
        ? JSON.parse(localStorage.getItem('shortlistedItems'))
        : [];
    return shortlistedItems;
}

function addtoLocalStorage(item, forceUpdate) {
    $(".shortlist-dropdown").empty();
    liMarkup = "";
    let shortlistedItems = getCartItems();

    let existItem = shortlistedItems.find(x => x.id === item.id);

    if (existItem) {
        if (forceUpdate) {
            shortlistedItems = shortlistedItems.map((x) => x.id === existItem.id ? item : x);
        }
    } else {
        shortlistedItems = [...shortlistedItems, item];
    }
    setCartItems(shortlistedItems);
    if (forceUpdate) {
        rerender();
    }

}

function setCartItems(shortlisted) {
    localStorage.setItem('shortlistedItems', JSON.stringify(shortlisted));
}

function addtocart(products) {

    //Get Items from local Storage
    let cart = {
        id: products.id,
        picFileName: products.picFileName,
        title: products.proffesionalTitile,
        currentCompany: products.currentCompany,
        fullName: products.fullName
    };
    addtoLocalStorage(cart);
    rerender();
}

function updateCart(item) {
    let shortlistedItems = getCartItems();
    const existItem = shortlistedItems.find(x => x.id === item.id);
    if (existItem) {
        shortlistedItems = shortlistedItems.map((x) => x.id === existItem.id ? item : x);
    }
    setCartItems(shortlistedItems);

    let total = shortlistedItems.reduce((a, c) => a + parseInt(c.qty), 0);
    // itemTotal.innerHTML = total;
}

function removeFromCart(id) {
    id = parseInt(id);
    setCartItems(getCartItems().filter((x) => x.id !== id));

}

function shortlistCandidate(transactionId) {
    var url = `/public/findJobSeeker/${transactionId}`;

    $.get(url, function (elem) {
        var id = elem.id;

        if (!wishlist.includes(id)) {
            wishlist.push(id);
            addtocart(elem);
        }
    });
}

function rerender() {

    const li = "";
    $(".shortlist-dropdown").empty();

    const cartItems = getCartItems();
    if (cartItems.length !== 0) {

        cartItems.map(function (products, index, myArr) {
            let item = {
                'id': products.id,
                'picFileName': products.picFileName,
                'title': products.title,
                'currentCompany': products.currentCompany,
                'fullName': products.fullName
            };
            liMarkup += `<li>
                         <div class="job-listing">
                                 <div class="job-title-sec">
                                         <div class="c-logo"> <img src="/uploads/${item.picFileName}" alt="" style="height: 50px; width: 50px" /> </div>
                                         <h3><a href="#" title="">${item.fullName} / ${item.title}</a></h3>
                                         <span id="${item.id}" onclick="removeFromList()" class="removeFromList"> <i class="la la-close"></i> Remove</span>
                                 </div>
                         </div> 
                     </li>`;

        });
        $(".shortlist-dropdown").append(liMarkup);


        let total = cartItems.length;
        wishcount.innerHTML = total;
    }


}

function getSalesData(salesDetails) {
    let cart = getCartItems();
    let data = {
        "job": salesDetails.jobId,
        "cart": cart
    };

    return data;
}

function shortlistAllApplicants() {
    const cart = getCartItems();

    let data = {
        id: 0,
        jobId: selectedJobId,
        cart: cart
    };

    var url = `/recruiter/shortlist-candidates`;

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(data),
        processData: false,
        contentType: "application/json",
        cache: false,
        timeout: 600000,

        success: function (data) {
            console.log(data);
            clearCart();
            $(".shortlist-dropdown").empty();
            wishcount.innerHTML = 0;
            swal('Shortlisted!', 'Applicant has been shortlisted./ Result: ' + data, "success");

        },
        error: function (e) {
            swal('Error!', 'Somer error occured, we couldnt shortlist ', "error");
        }
    });


    return true;
}

function udatePic() {

    var form = $('#picForm')[0];
    var data = new FormData(form);
    var id = $('#seekerId').val();

    data.append(id, id);

    $.ajax({
        type: "POST",
        url: '/update-emp-pic?id=' + id,
        enctype: 'multipart/form-data',
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,

        success: function (data) {
            location.reload();
        },
        error: function (e) {
            location.reload();
        }

    });
}

    
      
         
        
 