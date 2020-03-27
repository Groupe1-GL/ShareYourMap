function getServerData(url, success){
    $.ajax({
    	type:'GET',
        dataType: "json",
        url: url
    }).done(success);
}

function deleteServerData(url,success){
    $.ajax({
    	type: 'DELETE',
        dataType: "json",
        url: url
    }).done(success);
}


//---------------------		Automatic actions		---------------------		


// Return current registered user
$(function(){
	var user = "1";
	getServerData("/ws/users/"+user,getUser);
});



/*
 * Display user information in the current page with different templates
 * @param {JSON: User}		the current user
 */
function getUser(result){
	var user_name = _.template("<h2><%=name%></h2>");	
	$("#userName").append(user_name(result));

	var id_template = _.template($('#listMap').html());
	var maps = result['maps'];
	_.each(maps, function(map) {
		map_id = id_template(map);
		$('#mapList').html(map_id);
	 });
/*	
	var createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").append(createMap(result));*/
}


//---------------------		Actions on click		---------------------	


/*
 * Display the element in which a user can create a map
 * @param {int} uid		the id of the current user
 */
function createNewMap(uid){
	user_id = {"uid": uid};
	document.getElementById("viewMap").style.display = "block";
	var createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").html(createMap(user_id));
}

// Close map's information element
function closeMap(){
	document.getElementById("viewMap").style.display = "none";
}

/*
 * Display the element in which a user can edit a map
 * @param {int} uid		the id of the current user
 * @param {int} mid		the id of the current map
 */
function editMap(uid,mid){
	editDetails = {"uid":uid,"mid":mid};
	document.getElementById("viewMap").style.display = "block";
	var editMap = _.template($('#editMapTemplate').html());
	$("#viewMap").html(editMap(editDetails));
}

/*
 * Delete a map from user's list of map
 * @param {int} uid		the id of the current user
 * @param {int} mid		the id of the current map
 */
function deleteMap(uid,mid){
	deleteServerData("/ws/users/"+uid+"/maps/"+mid,refresh);
}

/*
 * Display the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 */
function createFav(id){
	document.getElementById("viewFav").style.display = "block";
	var editMap = _.template($('#newFavTemplate').html());
	$("#viewFav").html(editMap());
}

// Close fav's information page
function closeFav(){
	document.getElementById("viewFav").style.display = "none";
}

// Edit a location


// Delete a location
function deleteFav(uid,mid,lid){
	deleteServerData("/ws/users/"+uid+"/maps/"+mid+"/location/1",refresh);
}

// Refresh 
function refresh(result){
	history.go(0);
}