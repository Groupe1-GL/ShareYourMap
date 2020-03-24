function getServerData(url, success){
    $.ajax({
    	type: 'GET',
        dataType: "json",
        url: url
    }).done(success);
}

$(function(){
	$('#sign_in').click(function(){
		getServerData("/ws/users",validConnection);
	});
});


function validConnection(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	var passwd = document.getElementById('psw').value;
	var email = document.getElementById('mail').value;
	for (const us in result) {
		//if(us['password']===passwd && us['email']===email){
			document.location='publicmap.html';
			break;
		//}
	}
}

function newUser(){
	document.getElementById("newUser").style.display = "block";
	document.getElementById("connection").style.display = "none";
}

function connect(){
	document.getElementById("newUser").style.display = "none";
	document.getElementById("connection").style.display = "block";
}

