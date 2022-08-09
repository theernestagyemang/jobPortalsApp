<?php 

include 'config.php';

error_reporting(0);

session_start();

if (isset($_SESSION['username'])) {
    header("Location: index.php");
}

if (isset($_POST['submit'])) {
	$username = $_POST['username'];
	$firstname = $_POST['firstname'];
	$lastname =$_POST['lastname'];
	$email = $_POST['email'];
	$phone = $_POST['phone'];
	$dob = $_POST['dob'];
	$user_type = $_POST['user_type'];
	$permitted_chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	$apiKey = substr(str_shuffle($permitted_chars), 0, 30);
	$password = md5($_POST['password']);
	$cpassword = md5($_POST['cpassword']);
	

	if ($password == $cpassword) {
		$sql = "SELECT * FROM users WHERE email='$email'";
		$result = mysqli_query($conn, $sql);
		if (!$result->num_rows > 0) {
			$sql = "INSERT INTO users (username, firstname, lastname, email, phone, dob, user_type, apiKey, password)
					VALUES ('$username', '$firstname', '$lastname', '$email', '$phone', '$dob', '$user_type', '$apiKey', '$password')";
			$result = mysqli_query($conn, $sql);
			if ($result) {
				echo "<script>alert('Wow! User Registration Completed.')</script>";
				$username = "";
				$firstname = "";
				$lastname = "";
				$email = "";
				$phone = "";
				$dob = "";
				$user_type = "";
				$apiKey = "";
				$_POST['password'] = "";
				$_POST['cpassword'] = "";
			} else {
				echo "<script>alert('Woops! Something Wrong Went.')</script>";
			}
		} else {
			echo "<script>alert('Woops! Email Already Exists.')</script>";
		}
		
	} else {
		echo "<script>alert('Password Not Matched.')</script>";
	}
}

?>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

	<link rel="stylesheet" type="text/css" href="style.css">

	<title>Register Form - Pure Coding</title>
</head>
<body>
	<div class="container">
		<form action="" method="POST" class="login-email">
            <p class="login-text" style="font-size: 2rem; font-weight: 800;">Register</p>
			<div class="input-group">
				<input type="text" placeholder="Username" name="username" value="<?php echo $username; ?>" required>
			</div>
			<div class="input-group">
				<input type="text" placeholder="First Name" name="firstname" value="<?php echo $firstname; ?>" required>
			</div>
			<div class="input-group">
				<input type="text" placeholder="Last Name" name="lastname" value="<?php echo $lastname; ?>" required>
			</div>		
			<div class="input-group">
				<input type="email" placeholder="Email" name="email" value="<?php echo $email; ?>" required>
			</div>
			<div class="input-group">
				<input type="tel" placeholder="Phone" name="phone" value="<?php echo $phone; ?>" required>
			</div>
			<div class="input-group">
				<input type="date" placeholder="Date of Birth" name="dob" value="<?php echo $dob; ?>" required>
			</div>
			<div class="input-group">
				<select class="input-group" name="user_type" id="user_type" required>
  					<option value="please select" disabled selected hidden>Please Select User Type</option>
					<option value="Job Seeker" <?php if ($_GET['user_type'] == 'Job Seeker') { echo 'selected'; } ?>>Job Seeker</option>
					<option value="Employer" <?php if ($_GET['user_type'] == 'Employer') { echo 'selected'; } ?>>Employer</option>
				</select>
            </div>
			<div class="input-group">
				<input type="password" placeholder="Password" name="password" value="<?php echo $_POST['password']; ?>" required>
            </div>
            <div class="input-group">
				<input type="password" placeholder="Confirm Password" name="cpassword" value="<?php echo $_POST['cpassword']; ?>" required>
			</div>
			<div class="input-group">
				<button name="submit" class="btn">Register</button>
			</div>
			<p class="login-register-text">Have an account? <a href="index.php">Login Here</a>.</p>
		</form>
	</div>
</body>
</html>