<?php
// array for JSON response
include 'database.php';

$response = array();

// Create connection


// Check connection
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
  
 
   $reason=$_POST["reason"];
   
   $unm =$_POST["unm"];
   


$sql = "INSERT INTO reasons(reason,date,faculty_status,hod_status,rollno) VALUES('$reason',now(),'wait','wait','$unm')";

if (mysqli_query($con, $sql)) {
        
        $response["success"] = 1;
    
        $response["message"] = "done.";
      
        // echoing JSON response
        echo json_encode($response);

} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($con);

}

mysqli_close($con);  ?>