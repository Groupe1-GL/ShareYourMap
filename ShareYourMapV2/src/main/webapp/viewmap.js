function getServerDataTxt(url, success){
    $.ajax({
    	type: 'GET',
        dataType: "text",
        url: url
    }).done(success);
}

function getServerDataLambda(url, success){
    $.ajax({
    	type: 'GET',
        url: url
    }).done(success);
}

function getServerData(url, success){
    $.ajax({
    	type:'GET',
        dataType: "json",
        url: url
    }).done(success);
}

function putServerData(url, success){
    $.ajax({
    	type: 'PUT',
        dataType: "json",
        url: url
    }).done(success);
}

function postServerData(url, success){
    $.ajax({
    	type: 'POST',
        dataType: "json",
        url: url
    }).done(success);
}

function deleteServerData(url, success){
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
	//getServerDataLambda("/users/"+user,getUser);
	getServerDataLambda("/ws/viewmap/"+user,getUser);
});


function getUser(result){
	var user_name = _.template("<%=userInfo%>");
	var user_maps = _.template("<%=userID%>");
	$("#userName").append(user_name(result));
	

	var id_template = _.template($('#listMap').html());
	var maps = result['maps'];
	_.each(maps, function(map) {
		map_id = id_template(map);
		$('#mapsList').append(map_id);
	 });
	
}


//---------------------		Actions on click		---------------------	

//Return list of location with "location_name" (searchBar value) in their name 
$(function(){
	$("#searchFav").click(function(){
		var location_name = document.getElementById("searchBar").value;
		
		//getServerData("/ws/maps/"+select_map+"/location"+location_name,getLocationsList);
		getServerDataTxt("/ws/searchmap/search/"+location_name,getLocationsList);
	});
});


function getLocationsList(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#locationsList").append(html);
}


//Return list of locations for the selected map
$(function(){
	$("#1").click(function(){
		var select_map = "1";
		getServerData("/ws/viewmap/1/1"+select_map,getFavInMap)
		//getServerData("/ws/maps/"+select_map+"/location",getFavInMap)
	});
});


function getFavInMap(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#locationsList").append(html);
}


// Create a new map
$(function(){
	$("#createMap").click(function(){
		//putServerData("/ws/maps",postAddMapToUser)
		postServerData("/ws/viewmap/1",postAddMapToUser);
	});
});

function postAddMapToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddMap").append(html);
}

// Delete a map from user's list of map
$(function(){
	$("#deleteMap").click(function(){
		var user = "1";
		var map = "1";
		//deleteServerData("/users/"+user+"/maps/"+map,deleteMapToUser);
		deleteServerData("/ws/viewmap/"+user+"/"+map,deleteMapToUser);
	});
});

function deleteMapToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resDeleteMap").append(html);
}


// Add a new location to the current map
$(function(){
	$("#addLocation").click(function(){
		//putServerData("/ws/location",putAddLocation);
		putServerData("/ws/viewmap/viewlocation/1/1",putAddLocation);
	});
});

function putAddLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddLocation").append(html);
}


// Edit a location
$(function(){
	$("#editLocation").click(function(){
		var location = "1";
		//postServerData("/ws/location/"+location,postEditLocation);
		postServerData("/ws/viewmap/viewlocation/1/1/"+location,postEditLocation);
	});
});

function postEditLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resEditLocation").append(html);
}

// Delete a location
$(function(){
	$("#deleteLocation").click(function(){
		var user = "1";
		var map = "1";
		var location = "1";
		//deleteServerData("/ws/users/"+user+"/maps/"+map+"/location/"+location,deleteLocationToUser);
		deleteServerData("/ws/viewmap/viewlocation/1/1/1",deleteLocationToUser);
	});
});

function deleteLocationToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resDeleteLocation").append(html);
}