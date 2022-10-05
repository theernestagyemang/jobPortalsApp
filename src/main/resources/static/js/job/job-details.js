/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {


    function deleteSection() {
        $(".job-seeker-details").empty();
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

    function openModal(val) {
        var url = `/admin/findJobSeeker/${val}`;
        $.get(url, function (seeker) {
            var data = `
                <section class="overlape">
		<div class="block remove-top">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<div class="cand-single-user">
							<div class="share-bar circle">
				 				<a href="${seeker.google}" title="" class="share-google"><i class="la la-google"></i></a>
                                                                <a href="${seeker.facebook}" title="" class="share-fb"><i class="fa fa-facebook"></i></a>
                                                                <a href="${seeker.twitter}" title="" class="share-twitter"><i class="fa fa-twitter"></i></a>
                                                                <a href="${seeker.linkedIn}" title="" class="share-linkedin"><i class="fa fa-linkedin"></i></a>
				 			</div>
				 			<div class="can-detail-s">
				 				<div class="cst canditate-des">
                                                                    
                                                                    <img src="/uploads/${seeker.picFileName}" />
                                                                     
                                                                </div>
                                                                
				 				<h3>${seeker.fullName}</h3>
				 				<span><i>${seeker.proffesionalTitile},</i> Experience: ${seeker.yearsOfExperience} </span>
                                                                <input type="hidden" id="seekerId" value="${seeker.id}" />
				 				<p><a href="#" > ${seeker.email}</a></p>
				 				
				 				<p><i class="la la-map-marker"></i> ${seeker.homeTown} /  ${seeker.countryOfOrigin}</p>
				 			</div>
				 			<div class="download-cv">
				 				<button class="flashing_effect circle btn btn-danger" hidden="${!seeker.blacklisted}" >${seeker.blacklistComment}</button>
                                                                <a class="blacklist" href="#" href="/findSeeker/${seeker.id}" hidden="${seeker.blacklisted}" title="Blacklist Candidate">Blacklist <i class="la la-times"></i></a>
				 			</div>
                                                        <div class="share-bar circle">
                                                             <a href="/downloadCv/${seeker.id}" title="Download CV" tooltip="Download CV" class="share-google"><i class="la la-download"></i></a>
                                                        </div>
                                                    
				 		</div>
				 		<ul class="cand-extralink">
                                               
				 			<li><a href="#about" title="">About</a></li>
				 			<li><a href="#education" title="">Education</a></li>
				 			<li><a href="#experience" title="">Work Experience</a></li>
				 			<li><a href="#portfolio" title="">Portfolio</a></li>
				 			<li><a href="#skills" title="">Professional Skills</a></li>
				 			<li><a href="#awards" title="">Awards</a></li>
                                                         
				 		</ul>
				 		<div class="cand-details-sec">
				 			<div class="row">
				 				<div class="col-lg-8 column">
				 					<div class="cand-details" id="about">
				 						<h2>Candidates Profile</h2>
                                                                                <p > ${seeker.profileSummary}</p>
				 						<p class="m-b0">${seeker.description}</p>
                                                                                
				 						<div class="edu-history-sec" id="education">
                                                                                    <h2>Education  </h2>
                                                                                      ${
                seeker.edulist.length === 0 ?
                    '<div> --No Educational History Data Provided-- </div>' :
                    seeker.edulist.map(edu => `
                                                                                                       <div class="edu-history">
                                                                                                        <i class="la la-graduation-cap"></i>
                                                                                                        <div class="edu-hisinfo">
                                                                                                            <h3>${edu.institutionName}</h3>
                                                                                                            <i>${edu.yearStarted} - ${edu.yearGraduated}</i>
                                                                                                            <span>Qualication Received <i> ${edu.qualificationReceived} </i></span>
                                                                                                        </div>
                                                                                                    </div>
                                                                                            `).join('\n')
            }
                                                                                       
				 						</div>
                                                                                
				 						<div class="edu-history-sec" id="experience">
				 							<h2>Work & Experience</h2>
                                                                                        ${
                seeker.wlist.length === 0 ?
                    '<div> --No Work And Experience Data Provided-- </div>' :
                    seeker.wlist.map(edu => `
                                                                                                       <div class="edu-history style2">
                                                                                                                <i></i>
                                                                                                                <div class="edu-hisinfo">
                                                                                                                        <h3>${edu.companyName} <span> ${edu.jobTitle}</span></h3>
                                                                                                                        <i>${edu.monthStartedWork} ${edu.yearStartedWork} - ${edu.monthStopedWork} ${edu.yearStartedWork}</i>
                                                                                                                        <p>${edu.descriptions}</p>
                                                                                                                </div>
                                                                                                        </div>
                                                                                           `).join('\n')
            }
                                                                                         
				 							 
				 						</div>
				 						
				 						<div class="progress-sec" id="skills">
				 							<h2>Professional Skills</h2>
                                                                                        ${
                seeker.skillslist.length == 0 ?
                    '<div> --No Professional Skills Data Provided-- </div>' :
                    seeker.skillslist.map(item => `
                                                                                                       <div class="progress-sec">
                                                                                                        <span>${item.skill}</span>
                                                                                                        <div class="progressbar"> <div class="progress" style="width: ${item.proficiency}%"><span>${item.proficiency}%</span></div> </div>
                                                                                                        </div>
                                                                                           `).join('\n')
            }
                                                                                       
				 							
				 						</div>
				 						<div class="edu-history-sec" id="awards">
				 							<h2>Awards</h2>
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
				 						<div class="companyies-fol-sec">
				 			
				 						</div>
				 					</div>
				 				</div>
				 				<div class="col-lg-4 column">
						 			<div class="job-overview">
							 			<h3>Job Overview</h3>
							 			<ul>
							 				<li><i class="la la-money"></i><h3>Expected Salary</h3><span > ${seeker.expectedSalary.toLocaleString()}</span></li>
							 				<li><i class="la la-mars-double"></i><h3>Gender</h3><span>${seeker.gender}</span></li>
							 				<li><i class="la la-thumb-tack"></i><h3>Career Level</h3><span > ${seeker.educationLevel}</span></li>
							 				<li><i class="la la-puzzle-piece"></i><h3>Industry</h3><span >${seeker.industry}</span></li>
							 				<li><i class="la la-shield"></i><h3>Experience</h3><span > ${seeker.yearsOfExperience} </span></li>
							 				<li><i class="la la-line-chart "></i><h3>Qualification</h3><span > ${seeker.highestQualification} </span></li>
							 			</ul>
							 		</div><!-- Job Overview -->
							 		<div class="quick-form-job">
							 			
							 			
							 		</div>
						 		</div>
				 			</div>
				 		</div>
					</div>
				</div>
			</div>
		</div>
	</section> `;

            $(".job-seeker-details").html(data);
        });


        $('.signup-popup-box').fadeIn('fast');
        $('html').addClass('no-scroll');

    }


})