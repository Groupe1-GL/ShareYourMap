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

function postServerData(url){
    $.ajax({
    	type: 'POST',
        dataType: "json",
        url: url
    });
}


function postServerDataCallBack(url, success){
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
    });
}


//---------------------		Automatic actions		---------------------		


// Return current registered user
$(function(){
	var user = "1";
	getServerData("/ws/viewmap/"+user,getUser)
	//getServerData("/users/"+user,getUser);
});


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

// Display the initial map
$(function(){
	var map = L.map('map').setView([48.8266496,2.3826648], 20); // LIGNE 14

    var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', { // LIGNE 16
        attribution: '© OpenStreetMap contributors',
    });
    map.addLayer(osmLayer);
});


//---------------------		Actions on click		---------------------	

// Re center a map
function centerMap(x,y){
	var map = L.map('map').setView([x,y], 20);
}

//Return list of location with "location_name" (searchBar value) in their name 
$(function(){
	$("#searchFav").click(function(){
		var location_name = document.getElementById("searchBar").value;
		//getServerData("/ws/maps/"+select_map+"/location"+location_name,getLocationsList);
		getServerData("/ws/searchmap/search/"+location_name,getLocationsList);
	});
});


function getLocationsList(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#locationsList").html(html);
}


// Create a new map
function createNewMap(){
	document.getElementById("viewMap").style.display = "block";
	var createMap = _.template($('#newMapTemplate').html());
	$("#viewMap").html(createMap());
}

//Close map's information page
function closeMap(){
	document.getElementById("viewMap").style.display = "none";
}

// Delete a map from user's list of map
function deleteMap(id){
		var user = "1";
		var map = id;
		//deleteServerData("/users/"+user+"/maps/"+map,deleteMapToUser);
		deleteServerData("/ws/viewmap/"+user+"/"+map);
		history.go(0);
}


//Display the map with a certain id on click
function displayMap(id){
	getServerData("/ws/viewmap/1/1",getFavInMap);
	//getServerData("/ws/maps/"+id,getFavInMap);
}


function getFavInMap(result){
	var uid = {"uid":1};
	var dataEdit = $.extend(uid,result);
	
	var map_name = _.template($('#mapHeader').html());
	$("#currentMap").html(map_name(result));
	
	var loc_name_template = _.template($('#listLocation').html());
	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		location_name = loc_name_template(location);
		$("#favsList").html(location_name);
	});
	
	var editMap = _.template($('editMapTemplate').html());
	editHTML = editMap(dataEdit);
	$("#viewMap").html(editMap);
}


// Add a new location to the current map
$(function(){
	$("#addLocation").click(function(){
		putServerData("/ws/users/1/maps/1/location",putAddLocation);
	});
});

function putAddLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddLocation").html(html);
}


//Display location's information for a certain id on click
function displayLocation(id){
	getServerData("/ws/viewmap/viewlocation/1/1/1",getLocationDetails);
}

function getLocationDetails(result){
	document.getElementById("locationPage").style.display = "block";
	var location_template = _.template($('#locationDetails').html());
	detail = location_template(result);
	$("#locationPage").html(detail);
}


//Close location's information page
function closeLocation(){
	document.getElementById("locationPage").style.display = "none";
}


// Edit a location
$(function(){
	$("#editLocation").click(function(){
		var user = "1";
		var map = "1";
		var location = "1";
		postServerDataCallBack("/ws/users/"+user+"/maps/"+map+"/location/"+location,postEditLocation);
	});
});

function postEditLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resEditLocation").html(html);
}

// Delete a location
$(function(){
	$("#deleteLocation").click(function(){
		var user = "1";
		var map = "1";
		var location = "1";
		deleteServerData("/ws/users/"+user+"/maps/"+map+"/location/"+location);
		history.go(0);
	});
});
