<?php
// array for JSON response
include 'database.php';

$response = array();

// Create connection


// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
  
 
   $reqid =$_POST["reqid"];
   

   
 
$sql = "update reasons set faculty_status='rejected' where req_id='$reqid'";


if (mysqli_query($con, $sql)) {
        // successfully inserted into database
        $response["success"] = 1;
    
        $response["message"] = "Rejected Successfully.";
      
  

        // echoing JSON response
        echo json_encode($response);

   // echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);
}


mysqli_close($con);  ?>