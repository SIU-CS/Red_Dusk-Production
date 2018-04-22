<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/Create_Account_Style_Sheet.css">
        <script src="JavaScript/Password_Requirements.js" type="text/javascript" charset="utf-8"></script>
        <title>Create Account</title>
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

        <p/>
        <p/>
        <p/>
        <p/>
        <p/>

        <div class="container">
            <!-- Change out action for actual create account page/function -->
            <form action="/action_page.php">
                <h2>Create New Account</h2>
                <label for="usrname">First Name</label>
                <input type="text" id="first" name="first" required>

                <label for="usrname">Last Name</label>
                <input type="text" id="last" name="last" required>

                <label for="usrname">Email</label>
                <input type="text" id="usrname" name="usrname" required>

                <label for="psw">Password</label>
                <input type="password" id="psw" name="psw" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" title="Must contain at least one number and one uppercase and lowercase letter, one special character, and must be between 7-30 characters" required>

                <input type="submit" value="Submit">
            </form>
        </div>

        <div id="message">
            <h3>Password must contain the following:</h3>
            <p id="letter" class="invalid">A <b>lowercase</b> letter</p>
            <p id="capital" class="invalid">A <b>capital (uppercase)</b> letter</p>
            <p id="number" class="invalid">A <b>number</b></p>
            <p id="special" class="invalid">A <b>special character</b></p>
            <p id="length" class="invalid">Length <b>must be between 7-30 characters</b></p>
        </div>


    </body>
</html>
