<!DOCTYPE html>
<html lang="en-US">
    <head>
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/login_style_sheet.css">
    </head>

    <body>
        <h1>EASY RIDER</h1>
        <div id="mainContent">
            <div id="loginDiv">
                <form action="Login_Servlet" method="POST">
                    <h2>Login</h2>
                    <table>
                        <tr> <td> <input type="text" name="username" placeholder="username"> </td></tr>
                        <tr> <td> <input type="password" id="input" name="password" placeholder="password"> </td></tr>
                        <tr> <td> <input type="submit" name="loginBtn" value="Enter" class="btn" 
                                         onclick="function () {
                                                     hash = sha256(document.getElementById('input').value);
                                                 }"> </td> </tr> 
                        <tr> <td id ="errorMessage">  ${error_output} </td> </tr>
                        <tr> <td> <a href = "Forgot_Password.html">Forgot Password</a></td></tr>
                    </table>
                </form>

            </div>

            <div id="otherDiv">
                <h2>New User</h2>
                <form action="Login_Servlet" method="POST">
                    <table>
                        <tr> <td> <input type="submit" name="guestBtn" value="Continue as Guest" class="btn"> </td> </tr>
                        <tr> <td> <input type="button" name="accountBtn" value="Create Account" class="btn" 
                                         onclick="window.location.href = 'Create_Account_Page.jsp'"> </td> </tr>
                    </table>
                </form>
            </div>
        </div>
    </body>
</html>