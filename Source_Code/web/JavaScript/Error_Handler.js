
function hideError() {
    var htmlstring = document.getElementById("errorPara").innerHTML;
    htmlstring = (htmlstring.trim) ? htmlstring.trim() : htmlstring.replace(/^\s+/, '');

    if (htmlstring !== '') {
        var errordiv = document.getElementById("errorDiv");
        errordiv.style.visibility = 'visible';
    }else{
        var errordiv = document.getElementById("errorDiv");
        errordiv.style.visibility = 'hidden';
    }
}

