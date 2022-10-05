//function generateUniqSerial() {
//    return 'xxxx-xxxx-xxx-xxxx'.replace(/[x]/g, (c) => {
//        const r = Math.floor(Math.random() * 16);
//        return r.toString(16);
//    });
//}
//
//var transactionId = generateUniqSerial();
//
//function getAmountAndEntity() {
//    const amount = document.getElementsByClassName("price")[0].value;
//    const entity = document.getElementsByClassName("entity")[0].value;
//    const itemId = document.getElementsByClassName("identity")[0].value;
//
//    const item = {
//        id: itemId,
//        entity: entity,
//        amount: amount,
//        transactionId: transactionId
//    }
//    window.localStorage.setItem('item', JSON.stringify(item));
//    return amount
//}

//fetch(
//    'https://payproxyapi.hubtel.com/items/initiate',
//    {
//        method: 'POST',
//        headers: {
//            'Content-Type': 'application/json',
//            'Authorization': `Basic WVlPd0w5OjliMWE5ZmYzYTc3MzQ5YWI5ZjViNGZlM2NmNTM4ZTRi`
//        },
//        body: JSON.stringify(
//            {
//                totalAmount: getAmountAndEntity(),
//                description: 'Payment for gold subscription',
//                callbackUrl: 'http://localhost:8106',
//                returnUrl: 'http://localhost:8106',
//                merchantAccountNumber: '1645572',
//                cancellationUrl: 'http://localhost:8106/academy',
//                clientReference: transactionId
//            }
//        )
//    }
//).then((response) => {
//    response.json().then(jsonResponse => {
//        getPaymentForm(jsonResponse.data)
//    }).catch(error => {
//        console.log(error)
//    })
//})
//    .catch(err => {
//        console.log(err)
//    })

//hubtel payment logic
//function getPaymentForm(result) {
//    var loadSubscribe = document.getElementById("load-subscribe");
//
//    //create the iframe itself
//    var substack = document.createElement("iframe");
//
//    //creating attribute for the iframe
//    substack.src = result.checkoutDirectUrl;
//    substack.width = "400";
//    substack.height = "650";
//    substack.frameBorder = "0";
//    substack.scrolling = "0";
//    substack.style.border = "none";
//    substack.style.background = "black";
//
//    loadSubscribe.appendChild(substack);
//}