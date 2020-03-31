/* VIEWMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/viewmap
 */

//---------------------		Global variables	---------------------
var current_user_id = 1;
var current_map_id = 6;
var current_fav_id = 1;

//----------------------	Server functions	---------------------

/*
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

/*
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


//-------------------		Automatic actions		-----------------		


// Return current registered user
$(function(){
	getServerData("/ws/users/"+current_user_id,getUser);
});


/*
 * Display user information in the current page with different templates
 * @param {JSON: User}		the current user
 */
function getUser(result){
	var user_name = _.template("<h2><%=name%></h2>");	
	$("#userName").html(user_name(result));

	document.getElementById('mapList').innerHTML = "";
	var id_template = _.template($('#listMap').html());
	var maps = result['maps'];
	_.each(maps, function(map) {
		map_id = id_template(map);
		$('#mapList').append(map_id);
	 });
}


//---------------------		Actions on click		---------------------	

/*
 * Display the element in which a user can create a map
 * @param {int} uid		the id of the current user
 */
function createNewMap(){
	user_id = {"uid":current_user_id};
	document.getElementById("viewMap").style.display = "block";
	var createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").html(createMap(user_id));
}

// Close map's information element
function closeMap(){
	document.getElementById("viewMap").style.display = "none";
}

/* 
 * Send the request to get the map to edit
 * @param {int} mid		the id of the current map
 */
function editMap(mid){
	getServerData("/ws/maps/6",showEditMap);
}

/*
 * Display the element in which a user can edit a map
 * @param {Map} result		The map to edit
 */
function showEditMap(result){
	uid = {"uid":current_user_id};
	editDetails = $.extend(uid,result);
	
	document.getElementById("viewMap").style.display = "block";
	var editMap = _.template($('#editMapTemplate').html());
	$("#viewMap").html(editMap(editDetails));
}


/*
 * Display the element in which a user can share a map
 * @param {int} mid		the id of the current map
 */
function shareMap(mid){
	
}

/*
 * Delete a map from user's list of map
 * @param {int} mid		the id of the map
 */
function deleteMap(mid){
	deleteServerData("/ws/users/"+current_user_id+"/maps/"+mid,refresh);
}

/*
 * Display the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createFav(x,y){
	var details = {"uid":current_user_id, "mid":current_map_id, "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	var newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
}

// Close fav's information page
function closeFav(){
	document.getElementById("viewFav").style.display = "none";
}

// Edit a location


// Delete a location
function deleteFav(lid){
	deleteServerData("/ws/users/"+current_user_id+"/maps/"+current_map_id+"/location/"+lid,refresh);
}

// Refresh 
function refresh(result){
	history.go(0);
}