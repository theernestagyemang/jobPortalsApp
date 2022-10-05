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
                                                                    
                <li><a href="/company-dashboard" title=""><i class="la la-dashboard"></i>Company Dashboard</a></li>
                <li><a href="/company-profile" title=""><i class="la la-file-text"></i>Company Profile</a></li>
                <li><a href="/employer/manage-jobs" title=""><i class="la la-briefcase"></i>Manage Jobs</a></li>
                <li><a href="/company-transactions" title=""><i class="la la-money"></i>Transactions</a></li>
                <li><a href="/employer/browse-by-shortlisted" title=""><i class="la la-users"></i>Shortlisted Candidates</a></li>
                <li><a href="/company-resume" title=""><i class="la la-paper-plane"></i>Resumes</a></li>
                <li><a href="/comp-subscription" title=""><i class="la la-user"></i>Packages</a></li>
                <li><a href="/company-post-jobs" title=""><i class="la la-file-text"></i>Post a New Job</a></li>
                <li><a href="/change-password" title=""><i class="la la-lock"></i>Change Password</a></li>
                <li><a href="/logout" title=""><i class="la la-unlink"></i>Logout</a></li>
        </ul>
            `;

    menus.innerHTML = text;


});