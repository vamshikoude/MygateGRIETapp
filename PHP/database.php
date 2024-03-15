<?php

$con = mysqli_connect("localhost","ctcorphy_mgate","mygate#12345","ctcorphy_mgate");

// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }
  
?>