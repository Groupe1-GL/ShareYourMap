function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url
    }).done(success);
}

//		---------------------		Automatic actions		---------------------		

// Return list of public maps
$(function(){
	getServerData("/ws/searchmap",getMapsList);
	//getServerData("/ws/maps",getMapsList);
});


function getMapsList(result){
	var id_template = _.template($('#listPublicMap').html());
	_.each(result, function(map) {
		map_id = id_template(map);
		$('#mapsList').append(map_id);
    });
}


//		---------------------		Actions on click		---------------------		

// Return list of maps with "map_name" (searchBar value) in their name 
$(function(){
	$("#searchMap").click(function(){
		var map_name = document.getElementById("searchBar").value;
		if (map_name !=  null && map_name != ""){
			//getServerData("/ws/maps/"+map_name,getMapsList)
		}
	});
});


// Display the map with a certain id on click
function displayMap(id){
	getServerData("/ws/viewmap/1/"+id,getFavInMap);
	//getServerData("/ws/maps/"+id,getFavInMap);
}


function getFavInMap(result){
	var name_template = _.template($('#listLocation').html());
	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		location_name = name_template(location);
		$("#favsList").append(location_name);
    });
}


// Display location's information for a certain id on click
function displayLocation(id){
	getServerData("/ws/viewmap/viewlocation/1/1/1",getLocationDetails);
}

function getLocationDetails(result){
	document.getElementById("locationPage").style.display = "block";
	var location_template = _.template($('#locationDetails').html());
	detail = location_template(result);
	$("#locationPage").append(detail);
}


//Close location's information page
function closeLocation(){
	document.getElementById("locationPage").style.display = "none";
}

