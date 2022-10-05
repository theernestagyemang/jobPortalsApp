var id = document.getElementById("assessment-id").value;
var duration = document.getElementById("duration").value;
fetchQuestionsAndOptions("/seeker/assessment-exam/api/"+id);
displayAssessmentCountDown(2);


function fetchQuestionsAndOptions(url) {
    //        let main = document.getElementById("form-content");
    var markup = '';
    $.get(url, function (data) {
        $.each(data, function (idx, item) {
            let tem = '';
            for (let i = 0; i < item.options.length; i++) {
                tem += `
                                                 <div class="form-check form-check-inline">
                                                      <input class="form-check-input" type="radio" name="radioName${idx}" id="inlineRadio${i + 1}${idx}" value="${item.options[i]}">
                                                      <label class="form-check-label" for="inlineRadio${i + 1}${idx}">${item.options[i]}</label>
                                                 </div>
                                       `;
            }

            document.getElementById("form-content").innerHTML += `

                        <span class="alert alert-success invisible" role="alert">
                          correct answer
                        </span>
                        <span class="alert alert-danger invisible" role="alert">
                            incorrect answer
                        </span>

                        <div class="mb-3 px-2" style="background:#F5F6F6">
                         <p><strong>Q${idx + 1}.</strong> ${item.question}</p>
                        <div class="py-2" id="question${idx}">
                        </div>
                        </div>
                                   `;

            document.getElementById(`question${idx}`).innerHTML += tem

        });
    });

}

$("#add-exam-form").on("submit", function (event) {
        event.preventDefault();
        let data = [];

        $.get(`/seeker/assessment-exam/api/${id}`, function (response) {
            const numberOfQuestions = response.length;
            let markByQuestion = 100.0 / numberOfQuestions;
            let totalScore = 0;
            for (let i = 0; i < response.length; i++) {
                radios = document.getElementsByName(`radioName${i}`);
                let name = '';
                for (let j = 0; j < radios.length; j++) {
                    if (radios[j].checked) {
                        name = radios[j].value
                    }
                }
                item = {
                    id: 1,
                    name: name
                }

                data.push(item)
            }
            for (let index = 0; index < response.length; index++) {
                let isCorrect = false;
                for (let idx = 0; idx < data.length; idx++) {
                    if (response[index].answer === data[idx].name) {
                        isCorrect = true;
                    }
                }
                if (isCorrect) {
                    totalScore += markByQuestion;
                }
            }

            getAssessmentResult(totalScore, response[0].assessmentLineId)
        });


        function getAssessmentResult(score, assessmentLineId) {
            const result = {
                id: 0,
                score: parseFloat(score).toFixed(2),
                assessmentLines: assessmentLineId,
                outOf: 100,
                testDate: new Date().toJSON().slice(0, 10)
            }

            fetch('/seeker/assessment-exam', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(result)
            })
                .then(data => {
                    console.log('Success:', data);
                    if (result.score >= 50) {
                        swal("Success!", `Passed Successfully: ${result.score}/100`, "success").then(function (){
                            window.location=`/seeker/assessment-result/${result.assessmentLines}`;
                        });

                    } else if (result.score > 35) {
                        swal("Average!", `Have not Passed ${result.score}/100`, "warning").then(function (){
                            window.location=`/seeker/assessment-result/${result.assessmentLines}`;
                        });
                    } else {
    //                    fetch(`/seeker/assessment-result/${result.assessmentLines}`)
                        swal("Failed!", `Not Good ${result.score}/100`, "error").then(function (){
                            window.location=`/seeker/assessment-result/${result.assessmentLines}`;
                        })
                        ;

                    }

                })
                .catch((error) => {
                    console.error('Error:', error);
                });

        }






    });
function submitAssessment(){

        let data = [];

        $.get(`/seeker/assessment-exam/api/${id}`, function (response) {
            const numberOfQuestions = response.length;
            let markByQuestion = 100.0 / numberOfQuestions;
            let totalScore = 0;
            for (let i = 0; i < response.length; i++) {
                radios = document.getElementsByName(`radioName${i}`);
                let name = '';
                for (let j = 0; j < radios.length; j++) {
                    if (radios[j].checked) {
                        name = radios[j].value
                    }
                }
                item = {
                    id: 1,
                    name: name
                }

                data.push(item)
            }
            for (let index = 0; index < response.length; index++) {
                let isCorrect = false;
                for (let idx = 0; idx < data.length; idx++) {
                    if (response[index].answer === data[idx].name) {
                        isCorrect = true;
                    }
                }
                if (isCorrect) {
                    totalScore += markByQuestion;
                }
            }

            getAssessmentResult(totalScore, response[0].assessmentLineId)
        });


        function getAssessmentResult(score, assessmentLineId) {
            const result = {
                id: 0,
                score: Math.round(score) ,
                assessmentLines: assessmentLineId,
                outOf: 100,
                testDate: new Date().toJSON().slice(0, 10)
            }


        }
}
function displayAssessmentCountDown(duration){
        // Set the date we're counting down to
        var countDownDate = new Date();
                countDownDate.setTime(Date.now() + duration * 60 * 1000);
        // Update the count down every 1 second
        var x = setInterval(function() {


            // Update the count down every 1 second

              // Get today's date and time
              var now = new Date().getTime();

              // Find the distance between now and the count down date
              var distance = countDownDate - now;

              // Time calculations for days, hours, minutes and seconds

              var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
              var seconds = Math.floor((distance % (1000 * 60)) / 1000);

              // Output the result in an element with id="demo"
              document.getElementById("demo").innerHTML = minutes + "m " + seconds + "s ";

                if(minutes < 10){
                    document.getElementById("demo").style.backgroundColor = "yellow";
                     document.getElementById("demo").style.color = "black";
                }

                if(minutes < 5){
                                           document.getElementById("demo").style.backgroundColor = "red";
                                           document.getElementById("demo").style.color = "white";
                 }
              // If the count down is over, write some text
              if (distance < 0) {
                clearInterval(x);
                document.getElementById("demo").style.backgroundColor = "red";
                document.getElementById("demo").style.color = "white";
                document.getElementById("demo").innerHTML = "EXPIRED";
                submitAssessment();
              }
            }, 1000);

    }
