
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="JavaScript/FileReaderLogic.js"></script>
        <script src="JavaScript/ace/ace.js" type="text/javascript" charset="utf-8"></script>
        <link rel="stylesheet" type="text/css" href="Cascading_Style_Sheets/code_input_style_sheet.css">
        <title>Home Page</title>
    </head>
    <body>

        <form id="main_content" action="Interpreter_Servlet" method="POST">
            <div id="btn_container"><input type="submit" id="runnerBtn" name="submit" value="Run"></div>
            <div id="editor_container"><div id="editor">${r_input}</div> <textarea name="editor" id="code_input"></textarea></div>
            <div id="output_container"><textarea id="code_output" readonly>${r_output}</textarea></div>
        </form>
    </body>

    <!--Ace Code Editor Initialization-->
    <script>
        var editor = ace.edit("editor");
        editor.setTheme("ace/theme/twilight");
        editor.session.setMode("ace/mode/r");
        editor.resize();
        var textarea = $('textarea[name="editor"]');
        editor.getSession().on("change", function () {
            textarea.val(editor.getSession().getValue());
        });
        
    </script>
</html>

