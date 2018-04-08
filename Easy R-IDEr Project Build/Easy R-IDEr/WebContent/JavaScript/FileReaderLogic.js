window.onload = function () {
    //Check the support for the File API support 
    if (window.File && window.FileReader && window.FileList && window.Blob) {
        var fileSelected = document.getElementById('txtfiletoread');
        fileSelected.addEventListener('change', function (e) {
            //Set the extension for the file 
            var fileExtension = /text.*/;
            //Get the file object 
            var fileTobeRead = fileSelected.files[0];
            //Initialize the FileReader object to read the 2file 
            var fileReader = new FileReader();
            fileReader.onload = function (e) {
                var editor = ace.edit("editor");
                document.getElementById("code_input").innerHTML = editor.setValue(fileReader.result);
            }
            fileReader.readAsText(fileTobeRead);

        }, false);
    } else {
        alert("Files are not supported");
    }
}