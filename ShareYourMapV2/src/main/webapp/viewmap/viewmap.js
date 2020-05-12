/** VIEWMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/viewmap
 */

//---------------------		Global variable	---------------------
// User's favorites
var user_favs = [];

// User password
var user_psw;
 

//----------------------	Visual effects	-------------------------
// Hover effect to display map's options 
$(".mapPerso").hover(function(){
	$("#option").css("diplay", "inline");
	});


//----------------------	Server functions	---------------------

/**
 * Sends the GET request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function getServerData(url, success){
    $.ajax({
    	type:'GET',
        dataType: "json",
        url: url
    }).done(success);
}

/**
 * Sends the DELETE request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function deleteServerData(url,success){
    $.ajax({
    	type: 'DELETE',
        dataType: "json",
        url: url
    }).done(success);
}

/**
 * Sends the PUT request ot the server
 * @param {string} 	 url		The url of the request
 * @param {JSON}	data		The data to send
 * @param {void} success 		The callback function
 */
function postServerData(url,data,success){
    $.ajax({
    	type: 'POST',
		dataType: "text",
		contentType: "application/json",
		data: JSON.stringify(data),
        url: url
    }).done(success);
}

/**
 * Send the PUT request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function putServerData(url,success){
    $.ajax({
    	type: 'PUT',
        dataType: "json",
        url: url
    }).done(success);
}

/**
 * Send the PUT request ot the server with a data
 * @param {string} 	 url			The url of the request
 * @param {JSON} data 			The data to send
 * @param {void} success 		The callback function
 */
function putServerData2(url,data,success){
    $.ajax({
    	type: 'PUT',
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		processData: false,
        url: url
    }).done(success);
}

//-------------------		Automatic actions		-----------------		

// Sends the request to get the current registered user
$(function(){
	getServerData(`/ws/users/${current_user_id}`,getUser);
});

/**
 * Displays user's informations in the current page with different templates
 * @param {JSON: User}		the current user
 */
function getUser(result){	

	// Displays the name of the user
	var user_name = _.template($('#header_user').html());	
	$("#userName").html(user_name(result));
	user_name = _.template('<%= name%>\'s maps')
	$("#pageName").html(user_name(result));
	
	// Saves the user password
	user_psw = result['password'];
	
	// Fills the div in which the user will be able to change its password
	const user_edit = _.template($('#editUserTemplate').html());
	$("#editUser").html(user_edit(result));

	// Fills the list of maps with user's
	document.getElementById('mapList').innerHTML = "";
	const id_template = _.template($('#listMap').html());
	const maps = result['maps'];
	
	_.each(maps, function(map) {
		map_id = id_template(map);
		$('#mapList').append(map_id);

		_.each(map['locations'], function(fav) {
			user_favs.push(fav);
		});
	});
	
	// Fills the element to navigate between pages
	const user_temp = _.template($('#navigate').html());
	$("#publicmap").html(user_temp({"uid":result['id']}));
}

//---------------------		Actions on click		---------------------	
/**
 * Displays the element to edit the user
 */
$("#userName").click(function(){
	if (document.getElementById("editUser").style.display != "block"){
		document.getElementById("editUser").style.display = "block";
	}
	else{
		document.getElementById("editUser").style.display = "none";
	}
});

/**
 * Displays the element to edit user's password
 */
function psw(){
	var checkBox = document.getElementById("editUserCheck");
	if(checkBox.checked){
		document.getElementById("editMpd").style.display = "block";
	}
	else{
		document.getElementById("editMpd").style.display = "none";
		document.getElementById("start").value = null;
		document.getElementById("end").value = null;
	}
}

/**
 * Send the requeste to edit user's password
 * Verifies if the past password is correct and if the new passwords match
 */
function editUser(){
	const opsw = document.getElementById("opsw").value;
	const npsw = document.getElementById("npsw").value;
	const cpsw = document.getElementById("cpsw").value;
	if(opsw != user_psw){
		alert("Wrong old password");
	}
	else if(npsw==""){
		alert("Password can't be empty")
	}
	else if(npsw != cpsw){
		alert("New passwords don't match");
	}
	else{
		user = {"id":0, "password":npsw};
		postServerData(`/ws/users/${current_user_id}`,user,navig);
	}
}


/**
 * Deletes a user
 */
function deleteUser(){
	deleteServerData(`/ws/users/${current_user_id}`,bye);
}

/**
 * Changes backgroung color of the current map and sets the hover effect on others maps
 * @param {int} id	id of the currentMap
 */
function editMapColor(id){
	const c_map = document.getElementById(id);
	const maps = document.getElementById('mapList').getElementsByClassName('my_maps');

	_.each(maps, function(map) {
		map.style.backgroundColor = "white";
		map.onmouseover = function(){
			if (this != c_map) {
				this.style.backgroundColor ="#e74c4d";
			}
		};
        map.onmouseout = function(){
			if (this != c_map) {
				this.style.backgroundColor ="white";
			}
		};;
	});
	c_map.style.backgroundColor = "#e74c4d";
}


/**
 * Displays the element in which a user can create a map
 * @param {int} uid		The id of the current user
 */
$("#newMap").click(function (){
	const user_id = {"uid":current_user_id};
	document.getElementById("viewMap").style.display = "block";
	const createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").html(createMap(user_id));
});


/**
 * Send the request to create a map
 */ 
function createMap(){
	const name = document.getElementById("map_name").value;
	putServerData(`/ws/maps/${name}/user/${current_user_id}`,refresh);
}

/** 
 * Sends the request to get the map to edit
 * @param {int} mid		the id of the current map
 */
function divEditMap(mid){
	getServerData(`/ws/maps/${mid}`,showEditMap);
}

/**
 * Displays the element in which a user can edit a map
 * @param {Map} result		The map to edit
 */
function showEditMap(result){
	const uid = {"uid":current_user_id};
	const editDetails = $.extend(uid,result);
	
	document.getElementById("viewMap").style.display = "block";
	const editMap = _.template($('#editMapTemplate').html());
	$("#viewMap").html(editMap(editDetails));
}


/**
 * Sends the request to update map's information
 * @param {int} mid 
 */
function editMap(mid){
	const name = document.getElementById("edit_name").value;
	const access = document.getElementById("edit_access").checked;
	const map = {"id":mid, "name":name, "access":access};
	postServerData(`/ws/maps/user/${current_user_id}`,map,refresh);
}


/**
 * Displays the element in which a user can share a map
 * @param {int} mid		the id of the current map
 */
function shareMap(mid,sid){
	const link = {"link": "localhost:8080/ws/map?id="+mid+"&shared-id="+sid+"&uid="+current_user_id};
	document.getElementById("shareMap").style.display = "block";
	const sharingLink = _.template($('#shareMapTemplate').html());
	$("#shareMap").html(sharingLink(link));
}

/**
 * Copies sharing link 
 */
function copyLink() {
	const copyText = document.getElementById("shareLink");
	copyText.select();
	alert("Sharing link copied !");
	document.execCommand("copy");
  }

/**
 * Deletes a map from user's list of map
 * @param {int} mid		the id of the map
 */
function deleteMap(mid){
	deleteServerData(`/ws/maps/${mid}/user/${current_user_id}`,refresh);
}

/**
 * Displays the element to create an event
 */
function displayEvent(){
	const checkBox = document.getElementById("event");
	if(checkBox.checked){
		document.getElementById("eventElem").style.display = "block";
	}
	else{
		document.getElementById("eventElem").style.display = "none";
	}
}

/**
 * Displays the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createNewFav(x,y){
	const details = {"uid":current_user_id, "mid":current_map['id'], "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	const newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
}

/**
 * Sends the request to create a new favorite
 * @param {float} x 	The latitude of the location
 * @param {float} y 	The lonngitude of the location
 */
function newFav(x,y){
	const name = document.getElementById("new_name").value;
	const description = document.getElementById("new_description").value;
	const label = document.getElementById("new_label").value;
	const checkBox = document.getElementById("event");
	var fav = {"id":0, "name":name, "description":description, "label":label, "position":{"x":x, "y":y}};

	// Adds informations for a event to the request
	if(checkBox.checked){
		const startIn = document.getElementById("new_start").value;
		const endIn = document.getElementById("new_end").value;
		var start = startIn.split(" ");
		var end = endIn.split(" ");

		// Different formats for Firefox and Chrome/Edge
		if(start[1] != null && end[1] != null){
			const startTime = start[1].split(":")[0]+"-"+start[1].split(":")[1]+"-"+start[1].split(":")[2];
			const endTime = end[1].split(":")[0]+"-"+end[1].split(":")[1]+"-"+end[1].split(":")[2];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start[0]+"-"+startTime, "end":end[0]+"-"+endTime};
		}
		else{
			start = startIn.split('T')[0]+"-"+startIn.split('T')[1].split(':')[0]+"-"+startIn.split('T')[1].split(':')[1];
			end = endIn.split('T')[0]+"-"+endIn.split('T')[1].split(':')[0]+"-"+endIn.split('T')[1].split(':')[1];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
		}
		// Sends the request to create a event
		putServerData2(`/ws/event/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
	}
	else{
		// Sends the request to create a location
		putServerData2(`/ws/location/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
	}
}

/**
 * Displays the element in which a user can edit a fav
 * @param {Map} result		The map to edit
 */
function showEditFav(){
	var uid = {"uid":current_user_id};
	var mid = {"mid":current_map['id']};
	var editDetails = $.extend(uid,mid,current_fav);
	var editFav = _.template($('#editFavTemplate').html());
	$("#viewFav").html(editFav(editDetails));
}

/**
 * Sends the request to edit a favorite
 * @param {int} id 
 * @param {boolean} event 
 */
function editFav(id,event){
	var name = document.getElementById("edit_name").value;
	var description = document.getElementById("edit_description").value;
	var label = document.getElementById("edit_label").value;	
	var fav = {"id":0, "name":name, "description":description, "label":label};

	// Adds informations for a event to the request
	if(event){
		var startIn = document.getElementById("new_start").value;
		var endIn = document.getElementById("new_end").value;
		var start = startIn.split(" ");
		var end = endIn.split(" ");	

		// Different formats for Firefox and Chrome/Edge
		if(start[1] != null && end[1] != null){
			var startTime = start[1].split(":")[0]+"-"+start[1].split(":")[1]+"-"+start[1].split(":")[2];
			var endTime = end[1].split(":")[0]+"-"+end[1].split(":")[1]+"-"+end[1].split(":")[2];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start[0]+"-"+startTime, "end":end[0]+"-"+endTime};
		}
		else{
			start = startIn.split('T')[0]+"-"+startIn.split('T')[1].split(':')[0]+"-"+startIn.split('T')[1].split(':')[1];
			end = endIn.split('T')[0]+"-"+endIn.split('T')[1].split(':')[0]+"-"+endIn.split('T')[1].split(':')[1];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
		}
		// Sends the request to edit a location
		postServerData(`/ws/event/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig);
	}
	else{
		// Sends the request to edit a location
		postServerData(`/ws/location/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig);
	}
}

/**
 * Deletes a location
 * @param {int} lid		The id of the map to delete
 */
function deleteFav(lid){
	// Searchs the map to  delete in the list of favorite of the current map
	_.each(current_map['locations'], function(location) {
		if(location['id']==lid){
			if(location['event']==1){
				deleteServerData(`/ws/event/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
			}
			else{
				deleteServerData(`/ws/location/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
			}
		}
	});
}

/**
 * Filters the list of favorites regarding a research
 */
$(document).ready(function(){
	$("#searchBar").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		fillFavList(user_favs);
	 	$("#favsList li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	  	});
	});
});


/**
 * Displays the response of the request and refreshs the page
 * @param {String} result 
 */
function navig(result){
	alert(result);
	location.reload();
}


/**
 * Refreshs the page
 */
 function refresh(result){
	history.go(0);
}