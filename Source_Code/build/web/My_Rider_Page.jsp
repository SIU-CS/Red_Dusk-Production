<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
<title>Create Account Page</title>
<link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/my_rides_style_sheet.css">
<script src="JavaScript/MyRides.js"></script>
</head>
<body onload="newRide()">

<nav>
  <ul>
    <li>
      <a href="index.html">Home</a>
    </li>
	<li>
      <a href="https://github.com/SIU-CS/Red_Dusk-Production">Our Github</a>
    </li>
	<h1>EASY RIDER</h1>
    <li>
      <a href="MyAccountPage.html">My Account</a>
    </li>
    <li>
      <a href="MyRidesPage.html">My Rides</a>
    </li>
  </ul>
</nav>

<div class="container">
<table id="ridesTable">
  <tr id="top" class="fixed-side">
    <td>File Name</td>
    <td>Date Modified</td>
	<td id="buttons"><button onclick="">Select All</button><button onclick="">Uncheck All</button><button onclick="">Load File</button><button onclick="">Delete Selected</button></td>
  </tr>
</table>

<button onclick="newRide()" class="fixed-side">Try it</button>
	
</div>


</body>
</html>
