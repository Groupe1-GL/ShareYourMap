/* HOMEPAGE script
 * Functions use to display elements on the home page
 * 
 * @link src/main/webapp/
 */

//----------------------	Server functions	---------------------
/**
 * Send the PUT request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function putServerData(url,user,success){
    $.ajax({
    	type: 'PUT',
		dataType: "json",
		data: user,
        url: url
    }).done(success);
}

/**
 * Send the POST request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function postServerData(url,user,success){
    $.ajax({
    	type: 'POST',
		dataType: "json",
		data: user,
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
	username = document.getElementById("username").value;
	psw = document.getElementById("passwd").value;
	cpsw = document.getElementById("cpasswd").value;
	user = {"name":username,"passwrd":psw,"cpasswrd":cpsw};
	putServerData(`/ws/users`,user,navig);
});


$("#sign_in").click(function (){
	username = document.getElementById("username").value;
	psw = document.getElementById("passwd").value;
	user = {"name":username,"passwrd":psw};
	postServerData(`/ws/users`,user,navig);
});


function navig(result){
	if(result.hasOwnProperty('sign')){
		window.alert(result['sign']);
	}
	window.location.replace(result['url']);
}
