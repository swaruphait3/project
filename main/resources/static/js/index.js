console.log("this is script file");

const toggleSidebar = () => {
  if ($(".sidebar").is(":visible")) {
    //true
    //band karna hai
    $(".sidebar").css("display", "none");
    $(".content").css("margin-left", "0%");
  } else {
    //false
    //show karna hai
    $(".sidebar").css("display", "block");
    $(".content").css("margin-left", "20%");
  }
};

//display image outside issue view.....................

const displayImage = () =>{
Swal.fire({
  title: 'Sweet!',
  text: 'Modal with a custom image.',
  imageUrl: 'https://unsplash.it/400/200',
  imageWidth: 400,
  imageHeight: 200,
  imageAlt: 'Custom image',
})
}

//activate swal button..................

const activeUser = () =>{
Swal.fire({
  title: 'Are you sure?',
  text: "You won't be able to revert this!",
  icon: 'warning',
  showCancelButton: true,
  confirmButtonColor: '#3085d6',
  cancelButtonColor: '#d33',
  confirmButtonText: 'Yes, delete it!'
}).then((result) => {
  if (result.isConfirmed) {
    Swal.fire(
      'Deleted!',
      'Your file has been deleted.',
      'success'
    )
  }
})
}
//***********************


//searching.............
const search = () =>{
// console.log("searching...");
let query=$("#search-input").val();

if(query == ""){
    $(".search_result").hide();
}else{
    //search
    console.log(query);
   //sending rquest to server
   let url=`http://localhost:8000/support/search/${query}`;

   fetch(url)
   .then((response) => {
    return response.json();
   })
   .then((data) => {
    //data.........
    console.log(data);

    
    let text=`<div class='list-group'>`;
    data.forEach((users) => {
text+= `<a href='#' class='list-group-item list-group-action'> ${users.name} </a>`;
    });

    text+=`</div>`;

$(".search_result").html(text);
$(".search_result").show();
   });
}
}

//solution search

const searchSolution = () =>{
// console.log("searching...");
let query=$("#search-input").val();

if(query == ""){
    $(".search_result").hide();
}else{
    //search
    $(".card-body").hide();
    console.log(query);
   //sending rquest to server
   let url=`http://localhost:8000/support/solutionSearch/${query}`;

   fetch(url)
   .then((response) => {
    return response.json();
   })
   .then((data) => {
    //data.........
    console.log(data);
        let text=`<div class='list-group'>`;
    data.forEach((soloutions) => {
text+= `<a href='#' class='text-left list-group-item list-group-action'> ${soloutions.details} </a>
<p  class='text-left list-group-item list-group-action'>${soloutions.solution}</p>
`;
    });

    text+=`</div>`;

$(".search_result").html(text);
$(".search_result").show();
   });
}
}

//***********FAQ
$(document).ready(function(){
  $("#myInput").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#myTable tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
  });
});