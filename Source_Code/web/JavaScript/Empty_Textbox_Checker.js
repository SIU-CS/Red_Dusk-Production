function empty() {
    var x;
    x = document.getElementById("code_input").value;
    
    if (!x.replace(/\s/g, '').length) {
        return false;
    };
}

