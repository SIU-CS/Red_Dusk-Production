function loadFunction(){
    var i=1;
    //test if user is logged in or not, if yes keep log out text the same
    if(i==1)//if not logged in, change log out button to say log in
    {
        document.getElementById("logButton").innerHTML = "Log In";
    }
}

function logFunction(){
        if(document.getElementById("logButton").innerHTML == "Log In")
        {
            window.open("index.html");
        }
        else if(document.getElementById("logButton").innerHTML == "Log Out")
        {
            if (confirm('Are you sure you want to leave the page? Any unsaved progess will be lost!')) 
            {
                //Log the user out
            }
            else{
                //do nothing
            }
        }
}


