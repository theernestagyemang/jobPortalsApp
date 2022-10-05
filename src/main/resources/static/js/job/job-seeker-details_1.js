/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function deleteSection() {
    $(".job-seeker-details").empty();
}

function getAge(dateString) {
    var today = new Date();
    var birthDate = new Date(dateString);
    var age = today.getFullYear() - birthDate.getFullYear();
    var m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    return age;
}

function loadList(list) {
    list.map(function (edu) {
        return `
            <div class="edu-history">
                <i class="la la-graduation-cap"></i>
                <div class="edu-hisinfo">
                    <h3>${edu.institutionName}</h3>
                    <i>${edu.yearStarted} - ${edu.yearGraduated}</i>
                    <span>Qualication Received <i>${edu.qualificationReceived}</i></span>
                </div>
            </div> `;
    })
}

function jobSeekerDetails(url) {
    $.get(url, function (seeker) {
        var main = document.getElementById("main-panel");
        main.innerHTML = `<section class="content">

		  <div class="row">
			<div class="col-lg-12">
				<div class="box">
					<div class="box-body">
						<div class="row">
							<div class="col-md-4 col-sm-6">
								<div class="box box-body b-1 text-center no-shadow">
									<img src="/uploads/${seeker.picFileName}"id="product-image" class="img-fluid" alt="" />
								</div>
                                                                 
                                                                <div style="display : ${seeker.blacklisted === 'Yes' ? 'block' : 'none'}" >
                                                                    <div class="media bg-danger mb-20" >
                                                                            <div class="media-body">
                                                                                  <p  class="text-white"><strong>Blacklisted </strong></p>
                                                                                  <p class="text-white">This Job Seeker has been blacklisted</p>
            
                                                                            </div>
                                                                            <div>
                                                                                  <a class="btn btn-block btn-primary btn-sm btn-rounded bl-details" type="button" data-details="${seeker.blacklistComment}" data-toggle="modal" data-target="#modal-center">Details</a>
                                                                            </div>
                                                                     </div>	
                                                                </div>
								<div class="pro-photos center">
									<center>
                                                                            <h3> ${seeker.fullName} </h3> 
                                                                            ${getAge(seeker.dob)} Years
                                                                        </center>
								
								</div>
								<div class="clear"></div>
							</div>
							<div class="col-md-8 col-sm-6">
                                                                <p>Proffesional Title:</p>
								<h2 class="box-title mt-0"> <span>${seeker.proffesionalTitile}  </span></h2>
								<div class="list-inline ">
                                                                        Experience: ${seeker.yearsOfExperience}
									
								</div>
								<h3 class="pro-price mb-0 mt-20">Profile Sumary </h3>
								<hr>
                                                                <div class="profile-summary scrollbar">
								   <p> ${seeker.profileSummary}</p>
                                                                </div>
								<div class="row">
									<div class="col-sm-12">
										
										<div class="input-group">
                                                                                            
											<ul class="icolors">
                                                                                            
                                                                                                <li class="bg-primary rounded-circle"><a style="color:white" href="${seeker.google}" title="" class="la la-google"> </a></li>
                                                                                                <li class="bg-primary rounded-circle"><a style="color:white" href="${seeker.facebook}" title="" class="la la-facebook"></a></li>
                                                                                                <li class="bg-primary rounded-circle"><a style="color:white" href="${seeker.twitter}" title="" class="la la-twitter"></a></li>
                                                                                                <li class="bg-primary rounded-circle "><a style="color:white" href="${seeker.linkedIn}" title="" class="la la-linkedin"></a></li>
											</ul>
										</div>
										
									</div>
								</div>
								<hr>
								<div class="gap-items">
									<button  id="shortlist-selected2" class="btn btn-success shortlist-selected-candidate"  data-blacklist="${seeker.blacklisted}" data-id="${seeker.id}"><i class="la la-users"></i> Shortlist!</button>
									<a class="btn btn-primary"  href="/downloadCv/${seeker.id}"><i class="la la-download"></i> Download CV</a>
								</div>
        
								<h4 class="box-title mt-20">Other Details</h4>
        
                                                                    <div class="vtabs customvtab">
                                                                        <ul class="nav nav-tabs tabs-vertical" role="tablist">
                                                                                <li class="nav-item"> <a class="nav-link active" data-toggle="tab" href="#home3" role="tab" aria-expanded="true"><span class="hidden-sm-up"><i class="ion-home"></i></span> <span class="hidden-xs-down">Educational History</span> </a> </li>
                                                                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#profile3" role="tab" aria-expanded="false"><span class="hidden-sm-up"><i class="ion-person"></i></span> <span class="hidden-xs-down">Work Experience</span></a> </li>
                                                                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#messages3" role="tab" aria-expanded="false"><span class="hidden-sm-up"><i class="ion-email"></i></span> <span class="hidden-xs-down">Professional Skills</span></a> </li>
                                                                                <li class="nav-item"> <a class="nav-link" data-toggle="tab" href="#messages4" role="tab" aria-expanded="false"><span class="hidden-sm-up"><i class="ion-email"></i></span> <span class="hidden-xs-down">Awards </span></a> </li>
                                                                        </ul>

                                                                        <div class="tab-content">
                                                                                <div class="tab-pane active" id="home3" role="tabpanel" aria-expanded="true">
                                                                                        <div class="p-15">
                                                                                              ${
            seeker.edulist.length === 0 ?
                '<div> --No Educational History Data Provided-- </div>' :
                seeker.edulist.map(edu => `


                                                                                                            <div class="edu-hisinfo">
                                                                                                                <h3>${edu.institutionName}</h3>
                                                                                                                <i>${edu.yearStarted} - ${edu.yearGraduated}</i>
                                                                                                                <span style="color:red">, Qualication Received: <i> ${edu.qualificationReceived} </i></span>
                                                                                                            </div>

                                                                                                `).join('\n')
        }
                                                                                        </div>
                                                                                </div>
                                                                                <div class="tab-pane" id="profile3" role="tabpanel" aria-expanded="false">
                                                                                        <div class="p-15">
                                                                                            ${
            seeker.wlist.length === 0 ?
                '<div> --No Work And Experience Data Provided-- </div>' :
                seeker.wlist.map(edu => `
                                                                                                           <div class="edu-history style2">
                                                                                                                    <i></i>
                                                                                                                    <div class="edu-hisinfo">
                                                                                                                            <h3>${edu.companyName}</h3> 
                                                                                                                             <i>${edu.jobTitle} </i><br/>
                                                                                                                            <i>${edu.monthStartedWork} ${edu.yearStartedWork} - ${edu.monthStopedWork} ${edu.yearStartedWork}</i>
                                                                                                                            <p>${edu.descriptions}</p>
                                                                                                                    </div>
                                                                                                            </div>
                                                                                               `).join('\n')
        }
                                                                                        </div>
                                                                                </div>
                                                                                <div class="tab-pane" id="messages3" role="tabpanel" aria-expanded="false">
                                                                                        <div class="p-15">
                                                                                                ${
            seeker.skillslist.length === 0 ?
                '<div> --No Professional Skills Data Provided-- </div>' :
                seeker.skillslist.map(item => `
                                                                                                       <div class="progress-sec">
                                                                                                        <span>${item.skill}</span>
                                                                                                        <div class="progressbar"> <div class="progress" style="width: ${item.proficiency}%"><span>${item.proficiency}%</span></div> </div>
                                                                                                        </div>
                                                                                           `).join('\n')
        }
                                                                                        </div>
                                                                                </div>
        
                                                                                <div class="tab-content">
                                                                                    <div class="tab-pane active" id="messages4" role="tabpanel" aria-expanded="true">
                                                                                            <div class="p-15">
                                                                                                ${
            seeker.awardList.length === 0 ?
                '<div> --No Awards Data Provided-- </div>' :
                seeker.awardList.map(edu => `
                                                                                                            <div class="edu-history style2">
                                                                                                                    <i></i>
                                                                                                                    <div class="edu-hisinfo">
                                                                                                                            <h3>${edu.title}</h3>
                                                                                                                            <i>${edu.fromYear} - ${edu.toYear}</i>
                                                                                                                    </div>
                                                                                                            </div>
                                                                                                `).join('\n')
        }
                                                                                            </div>
                                                                                    </div>
                                                                                </div>
                                                                        </div>
                                                                </div>

                                                                    
								<hr>
                                                                
                                                                
									
							</div>
							<div class="col-lg-12 col-md-12 col-sm-12">
								<h4 class="box-title mt-40">General Info</h4>
								<div class="table-responsive">
									<table class="table">
										<tbody>
                                                                                        <tr>
												<td width="390">Email</td>
												<td>  ${seeker.email}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Primary Contact</td>
												<td>  ${seeker.primaryContact}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Proffesional Titile</td>
												<td>  ${seeker.proffesionalTitile}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Current Location </td>
												<td>  ${seeker.currentLocation}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Current Company </td>
												<td>  ${seeker.currentCompany}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Employment Type </td>
												<td>  ${seeker.employmentType}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Date of Birth </td>
												<td>  ${seeker.dob}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Marital Status </td>
												<td>  ${seeker.maritalStatus}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Key Skills  </td>
												<td>  ${seeker.keySkills}  </td>
											</tr>
                                                                                      
											<tr>
												<td width="390">Highest Qualification</td>
												<td>  ${seeker.highestQualification}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Highest Qualification</td>
												<td>  ${seeker.gender}  </td>
											</tr>
                                                                                        <tr>
												<td width="390">Expected Salary</td>
												<td>  ${seeker.expectedSalary.toLocaleString()}  </td>
											</tr>
											
											<tr>
												<td width="390">Career Level</td>
												<td> ${seeker.educationLevel}  </td>
											</tr>
											<tr>
												<td width="390">Industry</td>
												<td> ${seeker.industry} </td>
											</tr>
											<tr>
												<td width="390">Qualification </td>
												<td> ${seeker.highestQualification} </td>
											</tr>

										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>				
				</div>
			</div>
		</div>

		</section>`
        initializeShortlisting()
    })
}

function openModal(val) {

    var url = `/public/findJobSeeker/${val}`;
    jobSeekerDetails(url);

    $('.signup-popup-box').fadeIn('fast');
    $('html').addClass('no-scroll');

}


 