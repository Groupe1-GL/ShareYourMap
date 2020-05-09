/* HOMEPAGE script
 * Functions use to display elements on the home page
 * 
 * @link src/main/webapp/
 */

//----------------------	Server functions	---------------------
/**
 * Send the PUT request ot the server
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
 * Send the POST request ot the server
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
// Display div for sign up and hide the connection div
$("#new").click(function (){
	document.getElementById("newUser").style.display = "block";
	document.getElementById("connection").style.display = "none";
});

// Display div for connection and hide the sign in div
$("#connect").click(function (){
	document.getElementById("newUser").style.display = "none";
	document.getElementById("connection").style.display = "block";
});


//---------------------		Actions on click		---------------------	
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


$("#sign_in").click(function (){
	username = document.getElementById("connect_username").value;
	psw = document.getElementById("connect_passwd").value;
	user = {"id":0, "name":username,"password":psw};
	postServerData(`/ws/users`,user,connect);
});


function connect(result){
	var res = result.split('&');
	if(res.length <= 1){
		alert(result);
	}
	else{
		alert(res[0]);
		location.replace(res[1]);
	}
}