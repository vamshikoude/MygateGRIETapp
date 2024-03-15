<?php

include 'database.php';
$response = array();



// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
  
 
    $unm=$_POST['unm'];
    
    $pwd=$_POST['pwd'];
 

   
$sql = "select *from faculty where fact_id='$unm' and passwrd='$pwd' and status='accepted'";


$result = mysqli_query($con, $sql);

if (mysqli_num_rows($result) > 0) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "LoginSuccessfully.";

        // echoing JSON response
        echo json_encode($response);

   // echo "New record created successfully";
} else {
 $response["success"] = 0;
        $response["message"] = "Login Fail.";
             echo json_encode($response);
  
}

mysqli_close($con);  ?>