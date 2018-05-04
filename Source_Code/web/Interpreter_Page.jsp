<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <script src="JavaScript/File_Reader.js" type="text/javascript" charset="utf-8"></script>
        <script src="JavaScript/R_Script_Downloader.js" type="text/javascript" charset="utf-8"></script>
        <script src="JavaScript/Tab_Manager.js" type="text/javascript" charset="utf-8"></script>
        <script src="JavaScript/ace/ace.js" type="text/javascript" charset="utf-8"></script>
        <script src="JavaScript/Image_Viewer.js" type="text/javascript" charset="utf-8"></script>
        <script src="JavaScript/Empty_Textbox_Checker.js" type="text/javascript" charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/Interpreter_Style_Sheet.css">

        <title>Code Interpreter</title>
    </head>
    <body>
        
        <nav>
            <ul>
                <li>
                    <a href="Login_Page.jsp">Home</a>
                </li>
                <li>
                    <a href="https://github.com/SIU-CS/Red_Dusk-Production%22%3E">Our Github</a>
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
         
        <div id="tab_container">
            <button class="tablink" onclick="openPage('editorMain', this, '#4CAF50')" id="defaultOpen">Editor</button>
            <button class="tablink" onclick="openPage('csvMain', this, '#4CAF50')">CSV</button>
            <button class="tablink" onclick="openPage('graphMain', this, '#4CAF50')">Graphics</button>
        </div>

        <div id="editorMain" class="tabcontent">
            <form id="main_content" method="POST">
                <div id="btn_container">
                    <input type="text" name="filename" id="filenameTxt" value=${filename}>
                    
                    <input type="image" src="./Images/runner_image.png" id="runnerBtn" class="imageBtn" name="submit" 
                           onclick="return empty();" title="Run Project" formaction="Interpreter_Servlet"/>
                    
                    <input type="image" src="./Images/save_images.png" id="accountSaverBtn" name="accountSave" class="imageBtn"
                           title="Save to Account" formaction="File_Save_Servlet"/>

                    <img src="./Images/download_image.png" id="rSaverBtn" class="imageBtn" name="rSave" 
                           title="Download Script" onclick="saveTextAsFile()">

                    <img src="./Images/upload_image.png" id="visiblefiletoreadBtn" class="imageBtn"
                           title="Upload Script" onclick="document.getElementById('txtfiletoread').click()"/>

                    <input type="file" accept=".r,.txt,.R" id="txtfiletoread" class="invisibleBtn"/>

                </div>

                <div id="editor_container"><div id="editor">${r_input}</div> <textarea name="editor" id="code_input">${r_input}</textarea></div>
                <div id="output_container"><textarea name="output" id="code_output" readonly>${r_output}</textarea></div>
            </form>
        </div>
            
        <div id="csvMain" class="tabcontent">
        </div>

        <div id="graphMain" class="tabcontent">

            <c:forEach var="picture" items="${pictureList}">
                <object data="Image_Servlet?pictureItem=${picture}"/>
            </c:forEach>
        </div>
    </body>

    <!--Start With Editor Tab Open-->
    <script>
        document.getElementById("defaultOpen").click();
    </script>

    <!--Ace Code Editor Initialization-->
    <script>
        ace.require("ace/ext/language_tools");
        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
        editor.session.setMode("ace/mode/r");
        editor.resize();
        editor.getSession().on("change", function () {
            document.getElementById("code_input").innerHTML = editor.getSession().getValue();
        });

    </script>
</html>

