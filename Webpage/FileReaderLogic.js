window.onload = function () { 
 //Check the support for the File API support 
 if (window.File && window.FileReader && window.FileList && window.Blob) {
    var fileSelected = document.getElementById('txtfiletoread');
    fileSelected.addEventListener('change', function (e) { 
         //Set the extension for the file 
         var fileExtension = "application/octet-stream"; 
         //Get the file object 
         var fileTobeRead = fileSelected.files[0];
        //Check of the extension match 
             //Initialize the FileReader object to read the 2file 
             var fileReader = new FileReader(); 
             fileReader.onload = function (e) { 
                 var fileContents = document.getElementById('filecontents'); 
                 fileContents.innerText = fileReader.result; 
             } 
             fileReader.readAsText(fileTobeRead); 
    }, false);
} 
 else { 
     alert("Files are not supported"); 
 } 
 }
