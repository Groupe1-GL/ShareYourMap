/* HOMEPAGE script
 * Functions use to display elements on the home page
 * 
 * @link src/main/webapp/
 */

//----------------------	Server functions	---------------------
/**
 * Sends the PUT request to the server
 * @param {string} 	 url		The url of the request
 * @param {JSON} user			The user 
 * @param {void} success 		The callback function
 */
function putServerData(url,user,success){
    $.ajax({
    	type: 'PUT',
		dataType: "text",
		contentType: "application/json",
		data: JSON.stringify(user),
        url: url
    }).done(success);
}

/**
 * Sends the POST request to the server
 * @param {string} 	 url		The url of the request
 * @param {JSON} user			The user 
 * @param {void} success 		The callback function
 */
function postServerData(url,user,success){
    $.ajax({
    	type: 'POST',
		dataType: "text",
		contentType: "application/json",
		data: JSON.stringify(user),
        url: url
    }).done(success);
}


//----------------------	Visual effects	-------------------------
/*
 * Displays div for sign up and hide the connection div
 */
$("#new").click(function (){
	document.getElementById("newUser").style.display = "block";
	document.getElementById("connection").style.display = "none";
});

/**
 * Displays div for connection and hide the sign in div
 */ 
$("#connect").click(function (){
	document.getElementById("newUser").style.display = "none";
	document.getElementById("connection").style.display = "block";
});


//---------------------		Actions on click		---------------------
/**
 * Sends the request to create a user with a username and a password
 * Verifies if the username isn't already used
 */ 	
$("#sign_up").click(function (){
	username = document.getElementById("new_username").value;
	psw = document.getElementById("new_passwd").value;
	cpsw = document.getElementById("new_cpasswd").value;
	if(psw!=cpsw){
		alert("Passwords don't match");
	}
	else{
		user = {"id":0, "name":username,"password":psw};
		putServerData(`/ws/users`,user,connect);
	}
});

/**
 * Sends the request to connect a user with its username and its password
 */
$("#sign_in").click(function (){
	username = document.getElementById("connect_username").value;
	psw = document.getElementById("connect_passwd").value;
	user = {"id":0, "name":username,"password":psw};
	postServerData(`/ws/users`,user,connect);
});

/**
 * Switchs to the personal page if the connection is validated or displays the error message
 * If the connection is validated, the result will be: path to the personal page
 * Else, it will be: a message
 * @param {String} result	The response for a connection or a subscription
 */
function connect(result){
	// Check the type of response 
	if(result.split('/')[0] != 'viewmap'){
		alert(result);
	}
	else{
		location.replace(result);
	}
}