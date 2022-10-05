/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function () {
    $('.lozad').Lazy();
    let menus = document.getElementById("menus");
    let text = `
        <ul >
                                                                    
				 					<li><a href="/manage-jobs" title=""><i class="la la-file-text"></i> Manage Jobs</a></li>
                                                                        <li><a href="/jobs" title=""><i class="la la-search"></i> Search Jobs</a></li>
                                                                        <li><a href="/rec-post-jobs" title="Post a New Job"><i class="la la-briefcase"></i>Post a New Job</a></li>
                                                                        <li><a href="/posted-jobs" title="All Posted Jobs"><i class="la la-money"></i> Posted Jobs</a></li>
                                                                        <li><a href="/users" title="All Users"><i class="la la-users activ"></i> System Users</a></li>
                                                                        <li><a href="/assigned-posted-jobs" title="Jobs assigned to recruiters "><i class="la la-paper-plane"></i>Assigned jobs </a></li>
                                                                        <li><a href="/filled-jobs" title=""><i class="la la-user"></i>Filled jobs</a></li>
                                                                        <li><a href="/job-alert-list" title=""><i class="la la-flash"></i>Job Alerts </a></li>
                                                                        <li><a href="/rec-profile" ><i class="la la-user-o" ></i> Profile</a> </li>
                                                                        <li><a href="/assigned-posted-jobs" ><i class="la la-briefcase" ></i> Recruiter Assigned Jobs</a> </li>
                                                                        <li><a href="/resume-request-ls"><i class="la la-bell-o" ></i> Resume Request</a></li>
                                                                        <li><a href="/setup-address" title=""><i class="la la-book"></i>Setup-address</a></li>
                                                                        <li><a href="/job-categories" title=""><i class="lab la-squarespace"></i>Job Category</a></li>
                                                                        <li><a href="/work-shop" title=""><i class="las la-universal-access"></i>Career Workshop</a></li>
                                                                        <li><a href="/settings" title=""><i class="la la-gears"></i>Settings</a></li>
                                                                        <li><a href="/change-password" title=""><i class="la la-lock"></i>Change Password</a></li>
                                                                         <li><a href="/admin/reported-problems" title="View Reported Issues"><i class="la la-briefcase"></i>Reported Issues</a></li>
                                                                        <li><a href="/logout" title="logout of the system"><i class="la la-unlink"></i>Logout</a></li>
				 				</ul>
            `;

    menus.innerHTML = text;


});