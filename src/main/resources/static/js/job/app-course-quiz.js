var id = document.getElementById("course-id").value;

fetchQuestionsAndOptions("/seeker/course-exam/api/"+id);

function fetchQuestionsAndOptions(url) {
    //        let main = document.getElementById("form-content");
    var markup = '';
    $.get(url, function (data) {
                   console.log(data);
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

    $.get(`/seeker/course-exam/api/${id}`, function (response) {
//        console.log(response);
        const numberOfQuestions = response.length;
        let markByQuestion = 100.0 / numberOfQuestions;
//        console.log(markByQuestion)
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

//           console.log(totalScore)
        getQuizResult(totalScore, response[0].courseId)
    });


    function getQuizResult(score, assessmentLineId) {
//        console.log(assessmentLineId)
        const result = {
            id: 0,
            score: parseFloat(score).toFixed(2),
            assessmentLines: assessmentLineId,
            outOf: 100,
            testDate: new Date().toJSON().slice(0, 10)
        }

        console.log(result);
        fetch('/seeker/course-quiz', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(result)
        })
            .then(data => {
                console.log('Success:', data);
                if (result.score >= 50) {
//                    fetch(`/seeker/assessment-result/${result.assessmentLines}`)
                    swal("Success!", `Passed Successfully: ${result.score}/100`, "success").then(function (){
                        window.location=`/candidate-dashboard`;
                    });

                } else if (result.score > 35) {
//                    fetch(`/seeker/assessment-result/${result.assessmentLines}`)
                    swal("Average!", `Have not Passed ${result.score}/100`, "warning").then(function (){
                        window.location=`/candidate-dashboard`;
                    });
                } else {
//                    fetch(`/seeker/assessment-result/${result.assessmentLines}`)
                    swal("Failed!", `Not Good ${result.score}/100`, "error").then(function (){
                        window.location=`/candidate-dashboard`;
                    });
                }

            })
            .catch((error) => {
                console.error('Error:', error);
            });

    }


    function showAlertResult() {

    }

});


