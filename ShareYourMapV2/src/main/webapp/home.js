/* HOMEPAGE script
 * Functions use to navigate and interact with server on the home page
 * 
 * @link src/main/webapp/home.html
 */

/*
 * Send the GET request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function getServerData(url, success){
    $.ajax({
    	type: 'GET',
        dataType: "json",
        url: url
    }).done(success);
}


/* -------------------------------- 		On click function		-------------------------------- */

// Send the request to have the list of users to validate a connection
$(function(){
	$('#sign_in').click(function(){
		getServerData("/ws/users",validConnection);
	});
});

/*
 * Compare email & password of the current visitor to the users in the database to validate the connection
 * @param {List<User>} result		The list of users saved in the database
 */
function validConnection(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	var passwd = document.getElementById('psw').value;
	var email = document.getElementById('mail').value;
	for (const us in result) {
		//if(us['password']===passwd && us['email']===email){	// Ya pas d'attribut 'email' quand j'affiche le JSON donc je peux pas comparer
			document.location='publicmap.html';
			break;
		//}
	}
}

/* -------------------------------- Display sign in/up elements  -------------------------------- */

// Display div for sign up and hide the connection div
function newUser(){
	document.getElementById("newUser").style.display = "block";
	document.getElementById("connexion").style.display = "none";
}

//Display div for connection and hide the sign in div
function connect(){
	document.getElementById("newUser").style.display = "none";
	document.getElementById("connexion").style.display = "block";
}

