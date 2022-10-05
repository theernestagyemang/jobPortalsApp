function createTellerPayment(data) {

    var email = data.seekerEmail;
    var total = data.total;
    var txId = data.invoiceNumber;

    $("#status").html(data.invoiceStatus);
    $("#transactionId").html(data.invoiceNumber);
    $("#amount").html($.number(data.amt, 2));
    $("#cot").html($.number(data.cot, 2));
    $("#ttTotal").html($.number(data.total, 2));
    $("#applicantName").html("Name: " + data.seekerName);
    $("#applicantEmail").html("Email: " + data.seekerEmail);
    $("#orderDate").html(data.strInvoiceDate);
    $("#description").html(data.description);

    $("a.ttlr_inline").attr("data-transid", txId);
    $("a.ttlr_inline").attr("data-amount", total);
    $("a.ttlr_inline").attr("data-customer_email", email);

    var modal2 = $('#basicSubModal');
    modal2.modal('show');

}

function createSubscription(dataId, option) {

    $.ajax(`/find-auth-user`, {
        dataType: 'json',
        timeout: 500,
        success: function (user, status, xhr) {
            //options
            //0= jobseeker job subscription
            //1= employer subscription --employerSubscription
            //2= jobseeker  -- learningPlanSubscription
            //3= jobseeker  -- assessment
            var url = `/subscribe-by-email/${dataId}/?email=${user.username}&option=${option}`;
            $.get(url, function (data) {
                createTellerPayment(data);
            })

        },
        error: function (error, status) {
            Swal.fire({
                title: 'Please provide your email or xjobs username',
                input: 'text',
                inputAttributes: {
                    autocapitalize: 'off'
                },
                showCancelButton: true,
                confirmButtonText: 'Look up',
                showLoaderOnConfirm: true,

                preConfirm: (login) => {
                    $.ajax(`/subscribe-by-email/${dataId}/?email=${login}`, {
                        dataType: 'json',
                        timeout: 500,
                        success: function (data, status, xhr) {

                            createTellerPayment(data);
                        },
                        error: function (error, status) {
                            var st = error.status;

                            if (st === 406) {
                                Swal.fire({
                                    icon: 'error',
                                    text: `Invalid Email`,
                                    footer: "<a href='/faq'>Why do I have this issue?</a>"
                                })
                            }

                        }
                    });
                },
                allowOutsideClick: () => !Swal.isLoading()

            }).then((result) => {

                if (result.isConfirmed) {

                }
            });
        }
    });
}

function createHubtelPayment(amount, description, transactionId) {
    //var transactionId  = $("#transactionId").html();

    //var amount = $("#amount").html();
    //var description =$("#description").html();
    //var url2 = `/public/payhubtel?amount=${amount}&description=${description}&clientReference=${transactionId}`;
    const url = 'https://payproxyapi.hubtel.com/items/initiate';

    const payload = {
        totalAmount: amount,
        description: description,
        callbackUrl: 'http://localhost:8106/job-seekers',
        returnUrl: 'http://localhost:8106/job-seekers/cancelled',
        merchantAccountNumber: transactionId,
        cancellationUrl: 'http://localhost:8106/job-seekers/cancelled',
        clientReference: transactionId,
    };

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(payload),
        processData: false,
        contentType: "application/json",
        cache: false,
        timeout: 600000,
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Basic WVlPd0w5OjliMWE5ZmYzYTc3MzQ5YWI5ZjViNGZlM2NmNTM4ZTRi`,
        },

        success: function (data) {
            console.log(JSON.stringify(data))
        },
        error: function (e) {
            alert(e);
        }
    });

}
