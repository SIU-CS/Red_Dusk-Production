<!DOCTYPE html>
<html lang="en-US">
    <head>
        <title>Login Page</title>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/Login_Style_Sheet.css">
    </head>

    <body>
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
        <div id="mainContent">
            <div id="loginDiv">
                <form action="Login_Servlet" method="POST">
                    <h2>Login</h2>
                    <table>
                        <tr> <td colspan="2"> <input type="text" name="username" placeholder="username"> </td></tr>
                        <tr> <td colspan="2"> <input type="text" id="input" name="password" placeholder="password"> </td></tr>
                        <tr> <td> <input type="submit" name="loginBtn" value="Enter" class="btn" 
                                         onclick="function () {
                                                     hash = sha256(document.getElementById('input').value);
                                                 }"> </td> 
                            <td> <a href = "Forgot_Password.html">Forgot Password</a></td></tr>
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