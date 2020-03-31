/* HOMEPAGE script
 * Functions use to display elements on the home page
 * 
 * @link src/main/webapp/
 */

// Display div for sign up and hide the connection div
function newUser(){
	document.getElementById("newUser").style.display = "block";
	document.getElementById("connection").style.display = "none";
}

// Display div for connection and hide the sign in div
function connect(){
	document.getElementById("newUser").style.display = "none";
	document.getElementById("connection").style.display = "block";
}

