function newRide() {
	var i;
	for (i = 0; i < 5; i++) { 
		var table = document.getElementById("ridesTable");
		var row = table.insertRow(-1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var chkbox = document.createElement('input');
		chkbox.type = "checkbox";
		chkbox.id = "chk" ;
		chkbox.name = "chk" ;
		cell1.innerHTML = "NEW CELL1";
		cell2.innerHTML = "NEW CELL2";
		cell3.appendChild(chkbox)
		cell3.style.textAlign = "right";
	}
}
