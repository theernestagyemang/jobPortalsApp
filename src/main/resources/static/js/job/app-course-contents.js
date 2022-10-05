  getVideos();
  function getVideos() {
        const id = document.getElementById("course-id").value;
        console.log(id);
        $.get(`/seeker/course-detail/api/${id}`, function (videos) {
            console.log(videos)
            displayVideo(videos)
        });
    }

  function displayVideo(data){
        console.log("you click")
        for(let i=0; i<data.length; i++){
            const url=data[i].url;
            const name = data[i].name;
            document.getElementById(`video${data[i].id}`).addEventListener("click",()=>{
               document.getElementById("media").innerHTML = `
                    <div >
                          <iframe allowfullscreen class="embed-responsive-item"
                              frameborder="0"
                              height="500"
                              src="${url}"
                              title="${name}"
                              width="750">
                          </iframe>
                    </div>
               `;
            })
        }

  }