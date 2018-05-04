<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
    <head>
        <title>Create Account Page</title>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/My_Rides_Style_Sheet.css">
        <script src="JavaScript/My_Rides.js"></script>
    </head>
    <body onload="newRide()">
        <nav>
            <ul>
                <li>
                    <a href="Login_Page.jsp">Home</a>
                </li>
                <li>
                    <a href="https://github.com/SIU-CS/Red_Dusk-Production">Our Github</a>
                </li>
                <h1>EASY RIDER</h1>
                <li>
                    <a href="Interpreter_Page.jsp">Interpreter</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/My_Rides_Servlet">My Rides</a>
                </li>
            </ul>
        </nav>

        <section class="container">
            <div class="left">
                <h2>Profile Information</h2>
                <table id="">
                    <tr id="profile1">
                        <td>First</td>
                        <td>Last</td>
                    </tr>
                    <tr id="profile1">
                        <td colspan=2>Email</td>
                    </tr>
                    <tr id="profile1">
                        <td colspan=2>Reset Password Link</td>
                    </tr>
                </table>


            </div>
            <div id="right" class="right">
                <h2>My Rides</h2>
                <form action="Load_File_Servlet" method="GET" id="fileForm">
                    <table id="ridesTable">
                        <tr id="top" class="fixed-side">
                            <td><a href="${pageContext.request.contextPath}/My_Rides_Servlet?sort=alpha">File Name</a></td>
                            <td><a href="${pageContext.request.contextPath}/My_Rides_Servlet?sort=date">Date Modified</a></td>
                            <td id="buttons"><input type="button" value ="Load" onclick="load()"/><button onclick="deleted()">Delete</button></td>
                        <input type="hidden" name="selectedFile" id="selectedFile"/>
                        </tr>
                    </table>
                </form>
            </div>
        </section>

        <p id="invisibleInput">${file_list}</p>

    </body>
</html>
