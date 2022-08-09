<?php 

$server = "localhost";
$user = "root";
$pass = "ao holdings";
$database = "login";

$conn = mysqli_connect($server, $user, $pass, $database);

if (!$conn) {
    die("<script>alert('Connection Failed.')</script>");
}

?>