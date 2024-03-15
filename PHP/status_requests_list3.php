<?php

include 'database.php';


  

 
 $sql1 ="select *from reasons where faculty_status='accepted' and hod_status='wait'";
  
 $resultt = mysqli_query($con, $sql1);
 
if (mysqli_num_rows($resultt) > 0) {

$response["details"] = array();
       
    while ($row1 = mysqli_fetch_array($resultt)) {
            
            $pnames["req_id"] = $row1["req_id"];
            
            
              $pnames["reason"]  = $row1["reason"];
              
              
              $pnames["date"] =$row1["date"];
             
             
            $pnames["userid"] = $row1["rollno"];
            
            
            $pnames["faculty_status"] = $row1["faculty_status"];
             
            
            $pnames["hod_status"] = $row1["hod_status"];
             
                 
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