/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function rowClicked(value) {
    let mainPanel = document.getElementById("main-details");
    var url = "/findJob-db/" + value;
    $.get(url, function (job) {


        let detalis = `
                    <div class="job-single-sec" style="width: 100%">
			<div class="job-single-head">
                            <div class="job-thumb">
                                <img class="c-logo lozad" alt="" data-src="/images/logo/icon1.png" style="height: 50px;width: 80px"/>
                            </div>
                            <div class="job-head-info">
                                <h4>${job.postedBy.companyName}</h4>
                                <span >${job.location}</span>
                                <p ><i class="la la-unlink"></i> ${job.natureOfContract}</p>
                                <p ><i class="la la-phone"></i> ${job.postedBy.telephone}</p>
                                <p ><i class="la la-envelope-o"></i> ${job.postedBy.email}</p>
                            </div>
			</div>
                        <!-- Job Head -->
                        <div class="job-details">
                                <h3>Job Description</h3>


                                <p><span>${job.details}</span></p>

                                <h3>Duties and responsibilities </h3>
                                <ul>
                                    ${
            job.dutiesAndRespo.length == 0 ?
                '<div> -- </div>' :
                job.dutiesAndRespo.map(item => `
                                                <li>${item}</li>
                                    `).join('\n')
        }
                                     
                                </ul>
                                <h3>Required Knowledge, Skills, and Abilities</h3>
                                <ul>
                                ${
            job.skillls.length == 0 ?
                '<div> -- </div>' :
                job.skillls.map(item => `
                                                <li>${item}</li>
                                    `).join('\n')
        }
                                    
                                </ul>

                                <h3>Special Requirements </h3>
                                <ul>
                                    ${
            job.specialRequirements.length == 0 ?
                '<div> -- </div>' :
                job.specialRequirements.map(item => `
                                                <li>${item}</li>
                                    `).join('\n')
        }
                                    
                                </ul>
                                <h3>How to apply</h3>
                                <p><span>${job.howToApply}</span></p>
                        </div>
                    </div>
                    <h3>Job Overview</h3>
                    <div class="manage-jobs-sec">
                        <table class="table table">
                            <body>
                                    <tr><td>Offered Salary</td> <td><i class="la la-money"></i>  Ghc ${job.renumeration}.00 - ${job.toRenumeration}.00            </td></tr>   
                                    <tr><td>Gender        </td> <td><i class="la la-mars-double"></i>  ${job.gender}            </td></tr>   
                                    <tr><td>Career Level  </td> <td><i class="la la-thumb-tack"></i> ${job.category}        </td></tr>   
                                    <tr><td>Industry      </td> <td><i class="la la-puzzle-piece"></i> ${job.industry}  </td></tr>   
                                    <tr><td>Experience    </td> <td><i class="la la-shield"></i> ${job.minYearsExperience}        </td></tr>   
                                    <tr><td>Qualification </td> <td><i class="la la-line-chart "></i> ${job.qualification} </td></tr> 
                                    <tr><td>Slot Available </td> <td><i class="la la-file-text"></i> ${job.slot} </td></tr> 
                            </body>
                        <table>
                    </div>
                  
                    <div class="share-bar">
                        <span>Share</span><a href="#" title="" class="share-fb"><i class="fa fa-facebook"></i></a><a href="#" title="" class="share-twitter"><i class="fa fa-twitter"></i></a>
                    </div>
                    <div class="apply-alternative">
         
                        <a href="/seeker/apply/${job.transactionId}"  class="apply-thisjob"  title="Apply for job"><i class="la la-paper-plane"></i>Apply for job</a>
                        <a class="jobalert" href="#" title="" th:href="/add-job-alert/${job.id}" ><i class="la la-envelope-o"></i> Email me Jobs Like These</a>
                        <span onclick="rowClicked(${job.id})"><i class="la la-heart-o addtofavorite"></i> favorite</span>
                    </div>
            
    `;
        mainPanel.innerHTML = detalis;
    });
}