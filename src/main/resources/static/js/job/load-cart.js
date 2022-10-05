/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
let wishlist = [];
var liMarkup = "";
let wishcount = document.getElementById("wishcount");

function getCartItems() {
    const shortlistedItems = localStorage.getItem('shortlistedItems')
        ? JSON.parse(localStorage.getItem('shortlistedItems'))
        : [];
    return shortlistedItems;
}

function addtoLocalStorage(item, forceUpdate) {
    let shortlistedItems = getCartItems();

    console.log("getting cart (shortlistedItems).....", shortlistedItems);
    let existItem = shortlistedItems.find(x => x.id === item.id);

    if (existItem) {
        if (forceUpdate) {
            shortlistedItems = shortlistedItems.map((x) => x.id === existItem.id ? item : x);
        }
    } else {
        console.log("Here.");
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
    var url = `/admin/findJobSeeker/${transactionId}`;

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
    const cartItems = getCartItems();
    if (cartItems.length !== 0) {

        cartItems.map(function (products, index, myArr) {
            let item = {
                'id': products.id,
                'picFileName': products.picFileName,
                'title': products.proffesionalTitile,
                'currentCompany': products.currentCompany,
                'fullName': products.fullName
            };
            liMarkup += `<li>
                         <div class="job-listing">
                                 <div class="job-title-sec">
                                         <div class="c-logo"> <img src="/uploads/${item.picFileName}" alt="" style="height: 50px; width: 50px" /> </div>
                                         <h3><a href="#" title="">${item.currentCompany} / ${item.title}</a></h3>
                                         <span>${item.fullName}</span>
                                 </div>
                         </div> 
                     </li>`;

        });
        $(".shortlist-dropdown").append(liMarkup);


        let total = cartItems.length;
        wishcount.innerHTML = total;
    }


}