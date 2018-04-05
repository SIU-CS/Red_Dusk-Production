
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="JavaScript/FileReaderLogic.js"></script>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/index_style_sheet.css">
        <title>Home Page</title>
    </head>
    <body>

        <h1>Easy Rider</h1>

        <p> Github: <a href="https://github.com/SIU-CS/Red_Dusk-Production">
                https://github.com/SIU-CS/Red_Dusk-Production</a><br/>
        </p>

        <hr>

        <form action="Interpreter_Servlet" method="POST">
            <table style="width:100%">
                <tr>
                    <th>Code</th>
                    <th>Output</th>
                </tr>
                <tr style="width:100%">
                    <td><textarea id="filecontents" class="scrollabletextbox" name = "r_input" 
                                  rows="35" placeholder="Enter Code Here...">${r_input}</textarea></td>
                    <td><textarea readonly id="box2" class="scrollabletextbox" style="width:100%" 
                                  rows="35" placeholder="Output Appears Here...">${r_output}</textarea></td> 
                </tr>
                <tr style="width:100%"> 
                    <td> <input type ="submit" value="Submit"> </td>
                </tr>
                <tr style="width:100%">
                    <td> 
                        Choose local file to upload
                        <input type="file" id="txtfiletoread"/>
                    </td>
                    <td></td>
                </tr>
                <tr style="width:100%">
                    <td>
                        Choose file saved to your account
                        <select>
                            <option value="volvo">File1.R</option>
                            <option value="saab">File2.R</option>
                            <option value="mercedes">File3.R</option>
                            <option value="audi">File4.R</option>
                        </select>
                    </td>
                    <td></td>
                </tr>
            </table>
        </form>
    </body>
</html>