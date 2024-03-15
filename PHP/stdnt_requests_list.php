<?php

include 'database.php';

 
 $sql1 ="select *from student where status='wait'";
  
 $resultt = mysqli_query($con, $sql1);
 
if (mysqli_num_rows($resultt) > 0) {

$response["details"] = array();
       
    while ($row1 = mysqli_fetch_array($resultt)) {
            
            $pnames["name"] = $row1["stdnt_name"];
            
            
              $pnames["contact"]  = $row1["mobile"];
             
             
            $pnames["userid"] = $row1["stdnt_rollno"];
             
             
            
             $pnames["email"] =$row1["email"];
             
                 
            array_push($response["details"], $pnames);
         
         }
         
      
 $response["success"] = 1;
        
        $response["message"] = "Success.";
           
        // echoing JSON response
        echo json_encode($response);

  
}  
else {

 $response["success"] =0;

 
        $response["message"] = "No Record Found.";
      
         echo json_encode($response);
   // echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
mysqli_close($con);  ?>