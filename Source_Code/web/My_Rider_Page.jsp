<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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

        <div class="container">
            <table id="ridesTable">
                <tr>
                    <td>Row1 cell1</td>
                    <td>Row1 cell2</td>
                </tr>
                <tr>
                    <td>Row2 cell1</td>
                    <td>Row2 cell2</td>
                </tr>
                <tr>
                    <td>Row3 cell1</td>
                    <td>Row3 cell2</td>
                </tr>
            </table>

            <button onclick="newRide()">Try it</button>

        </div>

    </body>
</html>
