var checkPass = function() {
  if (document.getElementById('pass').value ==
    document.getElementById('passconfirm').value && document.getElementById('pass').value != "") {
    document.getElementById('message2').style.color = 'green';
    document.getElementById('message2').innerHTML = 'matching';
  } else {
    document.getElementById('message2').style.color = 'red';
    document.getElementById('message2').innerHTML = 'passwords must match';
  }
  if (document.getElementById('email').value ==
    document.getElementById('emailconfirm').value && document.getElementById('email').value != "") {
    document.getElementById('message1').style.color = 'green';
    document.getElementById('message1').innerHTML = 'matching';
  } else {
    document.getElementById('message1').style.color = 'red';
    document.getElementById('message1').innerHTML = 'emails must match';
  }
  if (document.getElementById('message1').style.color ==
	document.getElementById('message2').style.color) {
	document.getElementById('submit').disabled = false;
  } else {
	  document.getElementById('submit').disabled = true;
  }
}