/** VIEWMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/viewmap
 */

//---------------------		Global variable	---------------------
var user_favs = [];
var user_psw;
 

//----------------------	Visual effects	-------------------------
$(".mapPerso").hover(function(){
	$("#option").css("diplay", "inline");
	});


//----------------------	Server functions	---------------------

/**
 * Send the GET request ot the server
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
 * Send the DELETE request ot the server
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
 * Send the PUT request ot the server
 * @param {string} 	 url			The url of the request
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

// Return current registered user
$(function(){
	getServerData(`/ws/users/${current_user_id}`,getUser);
});

/**
 * Display user information in the current page with different templates
 * @param {JSON: User}		the current user
 */
function getUser(result){	

	var user_name = _.template($('#header_user').html());	
	$("#userName").html(user_name(result));
	user_name = _.template('<%= name%>\'s maps')
	$("#pageName").html(user_name(result));
	
	user_psw = result['password'];
	
	var user_edit = _.template($('#editUserTemplate').html());
	$("#editUser").html(user_edit(result));

	document.getElementById('mapList').innerHTML = "";
	var id_template = _.template($('#listMap').html());
	var maps = result['maps'];
	
	_.each(maps, function(map) {
		map_id = id_template(map);
		$('#mapList').append(map_id);

		_.each(map['locations'], function(fav) {
			user_favs.push(fav);
		});
	});
	
	var user_temp = _.template($('#navigate').html());
	$("#publicmap").html(user_temp({"uid":result['id']}));
}

//---------------------		Actions on click		---------------------	

$("#userName").click(function(){
	if (document.getElementById("editUser").style.display != "block"){
		document.getElementById("editUser").style.display = "block";
	}
	else{
		document.getElementById("editUser").style.display = "none";
	}
});

/**
 * Display the element to edit user's password
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


function editUser(){
	var opsw = document.getElementById("opsw").value;
	var npsw = document.getElementById("npsw").value;
	var cpsw = document.getElementById("cpsw").value;
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
		user = {"password":npsw};
		postServerData(`/ws/users/${current_user_id}`,user,navig);
	}
}


/**
 * Delete a user
 */
function deleteUser(){
	deleteServerData(`/ws/users/${current_user_id}`,bye);
}

/**
 * Change backgroung color of the current map
 * @param {*} id	id of the currentMap
 */
function editMapColor(id){
	var c_map = document.getElementById(id);
	var maps = document.getElementById('mapList').getElementsByClassName('my_maps');

	_.each(maps, function(map) {
		map.style.backgroundColor = "#090935";
		map.style.color = "white";
		map.onmouseover = function(){
			if (this != c_map) {
				this.style.backgroundColor ="#e74c4d";  this.style.color = "black";
			}
		};
        map.onmouseout = function(){
			if (this != c_map) {
				this.style.backgroundColor ="#090935";  this.style.color = "white";
			}
		};;
	});

	c_map.style.backgroundColor = "#e74c4d";
	c_map.style.color = "black";
}


/**
 * Display the element in which a user can create a map
 * @param {int} uid		the id of the current user
 */
$("#newMap").click(function (){
	var user_id = {"uid":current_user_id};
	document.getElementById("viewMap").style.display = "block";
	var createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").html(createMap(user_id));
});


// Send the request to create a map
function createMap(){
	var name = document.getElementById("map_name").value;
	putServerData(`/ws/maps/${name}/user/${current_user_id}`,refresh);
}

/** 
 * Send the request to get the map to edit
 * @param {int} mid		the id of the current map
 */
function divEditMap(mid){
	getServerData(`/ws/maps/${mid}`,showEditMap);
}

/**
 * Display the element in which a user can edit a map
 * @param {Map} result		The map to edit
 */
function showEditMap(result){
	var uid = {"uid":current_user_id};
	var editDetails = $.extend(uid,result);
	
	document.getElementById("viewMap").style.display = "block";
	var editMap = _.template($('#editMapTemplate').html());
	$("#viewMap").html(editMap(editDetails));
}


/**
 * Send the request to update map's information
 * @param {int} mid 
 */
function editMap(mid){
	var name = document.getElementById("edit_name").value;
	var access = document.getElementById("edit_access").checked;
	var map = {"id":mid, "name":name, "access":access};
	postServerData(`/ws/maps/user/${current_user_id}`,map,refresh);
}


/**
 * Display the element in which a user can share a map
 * @param {int} mid		the id of the current map
 */
function shareMap(mid,sid){
	var link = {"link": "localhost:8080/ws/map?id="+mid+"&shared-id="+sid};
	document.getElementById("shareMap").style.display = "block";
	var sharingLink = _.template($('#shareMapTemplate').html());
	$("#shareMap").html(sharingLink(link));
}

/**
 * Copy sharing link 
 */
function copyLink() {
	var copyText = document.getElementById("shareLink");
	copyText.select();
	alert("Sharing link copied !");
	document.execCommand("copy");
  }

/**
 * Delete a map from user's list of map
 * @param {int} mid		the id of the map
 */
function deleteMap(mid){
	deleteServerData(`/ws/maps/${mid}/user/${current_user_id}`,refresh);
}

/**
 * Display the element to create an event
 */
function displayEvent(){
	var checkBox = document.getElementById("event");
	if(checkBox.checked){
		document.getElementById("eventElem").style.display = "block";
	}
	else{
		document.getElementById("eventElem").style.display = "none";
		document.getElementById("start").value = null;
		document.getElementById("end").value = null;
	}
}


/**
 * Display the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createNewFav(x,y){
	var details = {"uid":current_user_id, "mid":current_map['id'], "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	var newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
}


/**
 * Send the request to create a new favorite
 */
function newFav(x,y){
	var name = document.getElementById("new_name").value;
	var description = document.getElementById("new_description").value;
	var label = document.getElementById("new_label").value;
	var checkBox = document.getElementById("event");
	var fav = {"name":name, "description":description, "label":label, "position":{"x":x, "y":y}};
	if(checkBox.checked){
		var start = document.getElementById("new_start").value;
		var end = document.getElementById("new_end").value;
		fav = {"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
	}
	putServerData2(`/ws/location/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
}


/**
 * Display the element in which a user can edit a fav
 * @param {Map} result		The map to edit
 */
function showEditFav(){
	var uid = {"uid":current_user_id};
	var mid = {"mid":current_map['id']};

	_.each(current_map['locations'], function(location) {
		if(location['id']==current_fav['id']){
			var editDetails = $.extend(uid,mid,location);
			var editFav = _.template($('#editFavTemplate').html());
			$("#viewFav").html(editFav(editDetails));
		}
	});	
}

function editFav(id,event){
	var name = document.getElementById("edit_name").value;
	var description = document.getElementById("edit_description").value;
	var label = document.getElementById("edit_label").value;	
	var fav = {"name":name, "description":description, "label":label};
	if(event){
		var start = document.getElementById("new_start").value;
		var end = document.getElementById("new_end").value;
		fav = {"name":name, "description":description, "label":label, "start":start, "end":end};
	}
	postServerData(`/ws/location/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig)
}


/**
 * Filter the list of favorites regarding a research
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


// Delete a location
function deleteFav(lid){
	deleteServerData(`/ws/location/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
}

function navig(result){
	alert(result);
	location.reload();
}


// Refresh 
function refresh(result){
	history.go(0);
}