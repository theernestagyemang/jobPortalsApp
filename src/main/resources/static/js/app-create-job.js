$(function () {

    $('.textarea').wysihtml5();
    $("input[data-role=tagsinput], select[multiple][data-role=tagsinput]").tagsinput();
    $("#jobTags").tagsinput();


    $(document).on("submit", ".candidate", function (e) {
        e.preventDefault();
    })

    $(document).on("keyup", ".stc", function (e) {
        e.preventDefault();
        var searchTerm = $(this).val();
        initializeCompanyNameFilter(searchTerm);
    })

    function renderForm() {
        let main = document.getElementById("main-section");
        main.innerHTML = `<form class="form" id="job-frm">
							<div class="box-body">
								<h4 class="box-title text-info"><i class="ti-user mr-15"></i> COMPANY INFORMATION </h4>
								<hr class="my-15">
                                                                <div class="form-group">
                                                                    <label>Company</label>
                                                                    <input type="text" class="form-control stc" placeholder="Company Name" required>
                                                                    <input type="hidden" id="employeeid"  name="id" />
                                                                    <input type="hidden" value="0" id="jobId" name="jobId" />
								</div>
        
                                                                <div class="col-lg-12 col-md-12">
                                                                    <table id="mytable" class="table table-hover" style="width: 100%">
                                                                        <tbody>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
								<div class="row">
								  <div class="col-md-6">
									<div class="form-group">
									  <label for="companyName">Company Name</label>
									  <input type="text" class="form-control stc" placeholder="Company Name" id="companyName" data-validation-required-message="This field is required">
									</div>
								  </div>
								  <div class="col-md-6">
									<div class="form-group">
									  <label>Email </label>
									  <input type="email" class="form-control" placeholder="Email" id="compEmail" required>
									</div>
								  </div>
								</div>
								<div class="row">
								  <div class="col-md-6">
									<div class="form-group">
									  <label >Company Phone</label>
									  <input type="text" class="form-control" placeholder="Company Phone" id="compPhone" required>
									</div>
								  </div>
								  <div class="col-md-6">
									<div class="form-group">
									  <label >City </label>
									  <input type="text" class="form-control" placeholder="City" id="compCity" required>
									</div>
								  </div>
        
                                                                  <div class="col-md-6">
									<div class="form-group">
									  <label >Address </label>
									  <input type="text" class="form-control" placeholder="Address" id="compAddress" required>
									</div>
								  </div>
                                                                  
                                                                  <div class="col-md-6">
									<div class="form-group">
									  <label >Company Profile </label>
									  <input type="text" class="form-control" placeholder="Company Profile" id="comDescription" required>
									</div>
								  </div>
        
                                                                  <br/><br/>
								</div>
								<h4 class="box-title text-info"><i class="ti-save mr-15"></i> JOB DETAILS</h4>
								<hr class="my-15">
        
								
        
								<div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label>Job Category </label>
									  <select class="form-control" id="category" required>
										<option>Interested in</option>
										<option>design</option>
										<option>development</option>
										<option>illustration</option>
										<option>branding</option>
										<option>video</option>
									  </select>
									</div>
                                                                    </div>
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Job Title  </label>
									  <input type="text" class="form-control" placeholder="Job Title" id="jobTitle" required>
									</div>
                                                                     </div>
								</div>
                                                                
                                                                <div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Slot Available </label>
									  <input type="number" class="form-control" placeholder="Slot Available" id="slot" required>
									</div>
                                                                    </div>

                                                                    <div class="col-md-6">
                                                                          <div class="form-group">
                                                                            <label >Job Type  </label>
                                                                            <select class="form-control select2" style="width: 100%;" id="jobType" required>
                                                                                  <option value="">---Please select Type---</option>
                                                                                    <option value="Full Time">Full Time</option>

                                                                                    <option value="Part Time">Part Time</option>
                                                                                    <option value="Internship">Internship</option>
                                                                                    <option value="Freelance">Freelance</option>
                                                                            </select>
                                                                           
                                                                          </div>
                                                                    </div>
                                                                </div>
        
                                                                <div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Minimum Salary(Ghc) </label>
									  <input type="number" class="form-control" placeholder="Minimum Salary(Ghc)" id="renumeration" required>
									</div>
                                                                    </div>
                                                                  
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Maximum Salary(Ghc)   </label>
									  <input type="number" class="form-control" placeholder="Maximum Salary(Ghc)" id="toRenumeration" required>
									</div>
								   </div>
                                                                </div>
        
                                                                <div class="row">
                                                                    <div class="col-md-6">
                                                                          <div class="form-group">
                                                                            <label >Country</label>
                                                                            <input type="text" class="form-control" placeholder="Minimum Salary(Ghc)" id="myInput" required>
                                                                          </div>
                                                                    </div>
                                                                  
                                                                    <div class="col-md-6">
									<div class="form-group">
									   <label >Region   </label>
									   <select id="region" class="form-control select2" name="region">
                                                                                <option value="Bono East Region">Bono East Region - Techiman</option>
                                                                                <option value="Oti Region">Oti Region - Dambai</option>
                                                                                <option value="Ahafo Region">Ahafo Region - Goaso</option>
                                                                                <option value="Bono Region">Bono Region - Sunyani</option>
                                                                                <option value="North East Region">North East Region - Nalerigu</option>
                                                                                <option value="Savannah Region">Savannah Region - Damango</option>
                                                                                <option value=" Western North Region"> Western North Region- Sefwi Wiawso</option>

                                                                                <option value="Western Region">Western Region - Sekondi</option>    
                                                                                <option value="Volta Region">Volta Region - Ho</option>    
                                                                                <option value="Greater Accra Region">Greater Accra Region - Accra</option>    
                                                                                <option value="Eastern Region">Eastern Region - Koforidua</option> 
                                                                                <option value="Ashanti Region">Ashanti Region - Kumasi</option>    
                                                                                <option value="Central Region">Central Region - Cape Coast</option>    
                                                                                <option value="Northern Region">Northern Region - Tamale</option>    
                                                                                <option value="Upper East Region">Upper East Region - Bolgatanga</option> 
                                                                                <option value="Upper West Region"> Upper West Region - Wa</option> 
                                                                            </select>
									</div>
                                                                    </div>
                                                                </div>
                                                                  
                                                                <div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >City/Location/Town   </label>
									  <input type="text" class="form-control" placeholder="City/Location/Town " id="jobTown" required>
									</div>
                                                                    </div>
        
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Experience</label>
									   <select id="experience"  class="form-control select2" name="experience"  >
                                                                                <option value="1 Year">1 Years</option>
                                                                                <option value="2 Years">2 Years</option>
                                                                                <option value="3 Years">3 Years</option>
                                                                                <option value="4 Years">4 Years</option>
                                                                                <option value="5 Years">5 Years</option>
                                                                                <option value="6 Years">6 Years</option>
                                                                                <option value="7 Years">7 Years</option>
                                                                                <option value="8 Years">8 Years</option>
                                                                                <option value="9 Years">9 Years</option>
                                                                                <option value="10 Years">10 Years</option>
                                                                                <option value="10 Years +">10 Years +</option>
									    </select>
									</div>
                                                                    </div>
                                                                </div>   
                                                                  
                                                                <div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Minimum Qualification   </label>
									  <select id="minQualification" class="form-control js-example-basic-single1"   name="minQualification"  >
                                                                                <option  value="">----Please select Min Qualification-----</option>
                                                                                <option value="Bachelors Degreee">Bachelors Degree</option>
                                                                                <option value="Doctorate Degree">Doctorate Degree</option>
                                                                                <option value="Phd">Phd</option>
                                                                                <option value="Masters Degree">Masters Degree</option>
                                                                                <option value="Senior High School">Senior High School</option>
                                                                                <option value="Junior High School">Junior High School</option>
                                                                                <option value="Other Professional Qualification">Other Professional Qualification</option>

                                                                            </select>
									</div>
                                                                    </div>
        
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label>Special Requirements</label>
                                                                            <input type="text" class="form-control tags_input"  name="prefSkills" title="Please comma separated list of Preferred Skills"  id="prefSkills" />
                                                                            <i><span class="text-muted text-gray-dark " >Please type comma separated list of Preferred Skills</span></i>
									</div>
                                                                    </div>
                                                                </div> 
                                                                
                                                                <div class="form-group">
								  <label>Job Descriptionf </label>
                                                                    <textarea class="textarea" placeholder="Type Job Description here" id="jobDescription" 
                                                                        style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
								</div>
                                                                
                                                                
								<div class="form-group tags-default">
                                                                    <label>Required Knowledge, Skills, and Abilities</label>
                                                                    <input type="text" class="form-control tags_input"    maxlength="2147483647"  id="jobTags" />
                                                                    <i><span class="text-muted text-gray-dark " >Please type comma separated list of  Skills</span></i>
                                                                </div>
        
                                                                <div class="row">
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >Expiry Date </label>
									  <input type="date" class="form-control" placeholder="Expiry Date" id="expDate" required>
									</div>
                                                                    </div>
        
                                                                    <div class="col-md-6">
									<div class="form-group">
									  <label >How to Apply </label>
									  <input type="text" class="form-control" placeholder="How to Apply" id="howToApply" required>
									</div>
                                                                    </div>
                                                                </div> 
        
                                                                <div class="demo-checkbox">
                                                                        <input type="checkbox" id="job-alert-check" class="filled-in chk-col-primary"  />
                                                                        <label for="job-alert-check">Show 1 Company Name during job advertisement</label>
        
                                                                        <input type="checkbox" id="publish" class="filled-in chk-col-success"  />
                                                                        <label for="publish">Publish this Job after posting</label>
        
                                                                      						
                                                                </div>
        
                                                                
        
								
							</div>
							<!-- /.box-body -->
							<div class="box-footer">
								<button type="button" class="btn btn-rounded btn-warning btn-outline mr-1">
								  <i class="ti-trash"></i> Cancel
								</button>
								<button type="submit" class="btn btn-rounded btn-primary btn-outline rt">
								  <i class="ti-save-alt"></i> Save
								</button>
							</div>  
						</form>`;
        $('.textarea').wysihtml5();
        $("input[data-role=tagsinput], select[multiple][data-role=tagsinput]").tagsinput();


        var data = [
            'css',
            'html',
            'php',
            'jquery'
        ];
        $(".tags_input").tagComplete({
            keylimit: 1,
            hide: false,
            autocomplete: {
                data: data
            }
        });
    }

    $(".post-job").on("click", function (e) {
        e.preventDefault();
        renderForm();
    });

    function updateTextFields(data) {
        $('#jobTitle').val(data.jobTitle);
        $('#jobTags').val(data.jobTags);
        $("#jobType option:selected").val(data.jobType);
        $("#experience option:selected").val(data.experience);
        $("#region option:selected").text(data.region);
        $("#category option:selected").val(data.category);
        $('#renumeration').val(data.renumeration);
        $('#toRenumeration').val(data.toRenumeration);
        $('#myInput').val(data.jobCountry);
        $('#city').val(data.city);
        $('#howToApply').val(data.howToApply);
        $('#jobDescription').val(data.jobDescription);
        $('#slot').val(data.slot);
        $('#expDate').val(data.expDate);
        $('#employeeid').val(data.id);
        $('#jobId').val(data.jobId);
        $('#jobTown').val(data.jobTown);
        $("#minQualification option:selected").val(data.minQualification);

        $('#companyName').val(data.companyName);
        $('#comDescription').val(data.comDescription);
        $('#compPhone').val(data.compPhone);
        $('#compEmail').val(data.compEmail);
        $('#compCity').val(data.compCity);
        $('#compAddress').val(data.compAddress);

        $('#publish').val(data.publish);
        $('#job-alert-check').val(data.showComp);
    }

    $(".edit-job").on("click", function (e) {
        e.preventDefault();
        renderForm();

        var jobId = $(this).attr("job-id");
        $.get(`/recruiter/find-job/${jobId}`, function (data) {
            updateTextFields(data);
        });

    })

    function clearText() {
        $("#companyName").val("");
        $("#compPhone").val("");

        $("#compEmail").val("");
        $("#compCity").val("");
        $("#compAddress").val("");
    }

    function initializeCompanyNameFilter(searchTerm) {

        $("#mytable > tbody").html("");

        if (searchTerm === '') {
            return;
        }
        if (searchTerm.length === 1) {
            return;
        }
        var url = "/search-employers?q=" + searchTerm;

        $.ajax({
            type: "GET",
            url: url,

            processData: false,
            contentType: false,
            cache: false,
            timeout: 600000,
            success: function (data) {
                //applicantEmail invoiceType applicantTelephone amt

                $.each(data, function (idx, elem) {

                    var markup =
                        "<tr id=" + elem.id + " >" +

                        "<td>" + elem.companyName + "</td>" +
                        "<td>" + elem.telephone + "</td>" +
                        "<td>" + elem.webSite + "</td>" +
                        "<td>" + elem.city + "</td>" +
                        "<td>" + elem.address + "</td>" +
                        "<td> <button  type='button' class='btn-summary btn-sm use-address'><i class='fa fa-check' ></i> Choose</button></td>" +

                        "</tr>";
                    $("#mytable tbody").append(markup);
                });

            },
            error: function (e) {
            }

        });


        $('#mytable tbody').on('click', 'tr', function () {

            clearText();
            var companyName = $(this).closest("tr").find('td:eq(0)').text();
            var telephone = $(this).closest("tr").find('td:eq(1)').text();
            var email = $(this).closest("tr").find('td:eq(2)').text();
            var city = $(this).closest("tr").find('td:eq(3)').text();
            var address = $(this).closest("tr").find('td:eq(4)').text();

            $("#companyName").val(companyName);
            $("#compPhone").val(telephone);
            $("#compEmail").val(email);
            $("#compCity").val(city);
            $("#compAddress").val(address);

            deleteRow();
        });

        $(document).on("keydown", ":input:not(textarea)", function (event) {
            if (event.key === "Enter") {
                event.preventDefault();
            }
        })

    }

    function deleteRow() {
        $("#mytable > tbody").html("");
    }

    $(document).on("submit", "#job-frm", function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();

        var job = {
            jobTitle: $('#jobTitle').val(),
            jobTags: $('#jobTags').val(),
            jobType: $("#jobType option:selected").val(),
            experience: $("#experience option:selected").val(),
            region: $("#region option:selected").text(),
            category: $("#category option:selected").val(),
            renumeration: $('#renumeration').val(),
            toRenumeration: $('#toRenumeration').val(),
            jobCountry: $('#myInput').val(),
            city: $('#city').val(),
            howToApply: $('#howToApply').val(),
            jobDescription: $('#jobDescription').val(),
            slot: $('#slot').val(),
            expDate: $('#expDate').val(),
            id: $('#employeeid').val(),
            jobId: $('#jobId').val(),
            jobTown: $('#jobTown').val(),
            minQualification: $("#minQualification option:selected").val(),
            showComp: $('#job-alert-check').prop('checked'),
            companyName: $('#companyName').val(),
            comDescription: $('#comDescription').val(),
            compPhone: $('#compPhone').val(),
            compEmail: $('#compEmail').val(),
            compCity: $('#compCity').val(),
            compAddress: $('#compAddress').val(),
            publish: $('#publish').prop('checked')
        };


        $(".rt").prop("disabled", true);
        var url = `/recruiter/post-job`;

        $.ajax({
            type: "POST",
            url: url,
            data: JSON.stringify(job),
            processData: false,
            contentType: "application/json",
            cache: false,
            timeout: 600000,

            success: function (data) {
                switch (data) {
                    case "SUCCESS":
                        swal({
                            title: "Success!",
                            text: "Your job has been posted successfully!",
                            type: "success",
                            icon: "success",
                            button: "Ok!"
                        })
                            .then((value) => {
                                $(".rt").prop("disabled", false);
                            });
                    case "FAILED" :
                        swal({
                            title: "Unsuccessful!",
                            text: "Sorry, We couldn't post your job!",
                            type: "error",
                            icon: "danger",
                            button: "Ok!"
                        });
                        $(".rt").prop("disabled", false);
                }
            },
            error: function (e) {
                swal({
                    title: "Unsuccessful!",
                    text: "Sorry, We couldn't post your job!",
                    type: "error",
                    icon: "danger",
                    button: "Ok!"
                });

                $("#rt").prop("disabled", false);

            }

        });


    });

})