//[Dashboard Javascript]

//Project:	Joblly - Responsive Admin Template
//Primary use:   Used only for the main dashboard (index.html)


$(function () {

    'use strict';


    $.get("/recruiter/applications-api2", function (data) {
        //Total Applications

        var options = {
            series: [
                {
                    name: "Applications",
                    data: [data.jan, data.feb, data.mar, data.apr, data.may, data.june, data.jul]
                },
                {
                    name: "Shortlisted",
                    data: [data.s_jan, data.s_feb, data.s_mar, data.s_apr, data.s_may, data.s_june, data.s_jul]
                },

                {
                    name: "Rejected",
                    data: [data.r_jan, data.r_feb, data.r_mar, data.r_apr, data.r_may, data.r_june, data.r_jul]
                },
                {
                    name: "Hired",
                    data: [data.h_jan, data.h_feb, data.h_mar, data.h_apr, data.h_may, data.h_june, data.h_jul]
                },

            ],
            chart: {
                height: 355,
                type: 'bar',
                zoom: {
                    enabled: false
                },
                toolbar: {
                    show: false,
                },
            },
            dataLabels: {
                enabled: false
            },
            colors: ['#007BFF', '#673ab7', '#FF0000', '#3da643'],
            grid: {
                show: true,
            },

            plotOptions: {
                bar: {
                    horizontal: false,
                    columnWidth: '40%',
                    endingShape: 'rounded'
                },
            },

            legend: {
                show: true,
                position: 'top',
                horizontalAlign: 'left',
            },
            xaxis: {
                categories: ['Mon', 'Tue', 'Wed', 'Thur', 'Fri', 'Sat', 'Sun'],
                labels: {
                    show: true,
                },
                axisBorder: {
                    show: true,
                },
                axisTicks: {
                    show: true,
                },
            },

            yaxis: {
                labels: {
                    show: true,
                }
            },
        };

        var chart = new ApexCharts(document.querySelector("#active_jobs"), options);
        chart.render();
    });

}); // End of use strict
