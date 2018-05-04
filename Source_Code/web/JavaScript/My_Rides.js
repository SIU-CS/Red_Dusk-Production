function newRide() {
    var i;
    var str = document.getElementById("invisibleInput").innerHTML;
    var holder = "";
    var count = 0;
    var itemList = [];
    var buttonCount = 0;

    for (var i = 0; i < str.length; i++)
    {
        if (str.charAt(i) == ",")
        {
            count += 1;
            itemList.push(holder);
            holder = "";
        } else
        {
            holder += str.charAt(i);
        }
    }
    holder += str.charAt(i);
    itemList.push(holder);
    count = (itemList.length / 2);

    for (i = 0; i < count; i++) {
        buttonCount += 1;
        var table = document.getElementById("ridesTable");
        var row = table.insertRow(-1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var chkbox = document.createElement('input');
        chkbox.type = "radio";
        chkbox.id = "chk-" + buttonCount + "";
        chkbox.name = "chk";
        chkbox.value= itemList[0];
        cell1.innerHTML = itemList.shift();
        cell2.innerHTML = itemList.shift();
        cell3.appendChild(chkbox);
        cell3.style.textAlign = "right";
    }
}

function load() {
    var radios = document.getElementsByName('chk');

    for (var i = 0, length = radios.length; i < length; i++)
    {
        if (radios[i].checked)
        {
            // do whatever you want with the checked radio
            var form = document.getElementById("fileForm");
            var hiddenElement = document.getElementById("selectedFile");
            hiddenElement.value = radios[i].value;
            form.submit();
            
            // only one radio can be logically checked, don't check the rest
            break;
        }
    }
}



