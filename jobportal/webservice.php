<?php
header("Content-Type:application/json");

if (isset($_GET['username']) && $_GET['username']!="") {
	include 'config.php';
	$order_id = $_GET['username'];
	$result = mysqli_query(
	$conn,
	"SELECT * FROM `users` WHERE user_type='Job Seeker'");
	if(mysqli_num_rows($result)>0){
	$row = mysqli_fetch_array($result);
    $username = $row['username'];
	$firstname = $row['firstname'];
	$lastname = $row['lastname'];
	$email = $row['email'];
    $phone = $row['phone'];
	response($order_id, $amount, $response_code,$response_desc);
	mysqli_close($con);
	}else{
		response(NULL, NULL, 200,"No Record Found");
		}
}else{
	response(NULL, NULL, 400,"Invalid Request");
	}

function response($firstname,$lastname,$email,$phone){
    $response['username'] = $username;
	$response['firstname'] = $firstname;
	$response['lastname'] = $lastname;
	$response['email'] = $email;
	$response['phone'] = $phone;
	
	$json_response = json_encode($response);
	echo $json_response;
}
?>