<!DOCTYPE html>
<html lang="en-US">
    <head>
        <title>Login Page</title>
        <script src="JavaScript/Logout.js"></script>
        <script src="JavaScript/Error_Handler.js" type="text/javascript" charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/Login_Style_Sheet.css">
    </head>

    <body onload="hideError();">
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

        <div id="mainContent">
            <div id="loginDiv" class="container">
                <form action="Login_Servlet" method="POST">
                    <h2>Login</h2>
                    <table>
                        <tr> <td colspan="2"> <input type="email" name="username" placeholder="email"> </td></tr>
                        <tr> <td colspan="2"> <input type="password" id="input" name="password" placeholder="password"> </td></tr>
                        <tr> <td> <input type="submit" name="loginBtn" class="colorBtn" value="Enter" class="btn" 
                                         onclick="function () {
                                                     hash = sha256(document.getElementById('input').value);
                                                 }"> </td> 
                            <td> <a href = "Forgot_Password.html" id="forgotPass">Forgot Password</a></td></tr>
                    </table>
                </form>

            </div>

            <div id="otherDiv" class="container">
                <h2>New User</h2>
                <form action="Login_Servlet" method="POST">
                    <table>
                        <tr> <td> <input type="submit" name="guestBtn" value="Continue as Guest" class="btn"> </td> </tr>
                        <tr> <td> <input type="button" name="accountBtn" class="colorBtn" value="Create Account" class="btn" 
                                         onclick="window.location.href = 'Create_Account_Page.jsp'"> </td> </tr>
                    </table>
                </form>
            </div>
        </div>

        <div class="alert" id="errorDiv" style="visibility: hidden;">
            <span class="closebtn" onclick="this.parentElement.style.display = 'none';">&times;</span> 
            ${error}
        </div>
        <p id="errorPara" style="visibility: hidden">${error}</p
    </body>
</html>

