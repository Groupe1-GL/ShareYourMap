function getServerDataTxt(url, success){
    $.ajax({
        dataType: "text",
        url: url
    }).done(success);
}

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
			getServerDataTxt("/ws/searchmap/search/"+map_name,getMapsList);
		}
	});
});


// Return list of locations for the selected map
$(function(){
	$("#map").click(function(){
		var select_map = document.getElementById("map").value;
		getServerData("/ws/viewmap/1/1"+select_map,getFavInMap)
		//getServerData("/ws/maps/"+select_map+"/location",getFavInMap)
	});
});


function getFavInMap(result){
	var id_template = _.template($('#listLocation').html());
	_.each(result, function(location) {
		location_id = id_template(location);
		$("#favsList").append(location_id);
    });
}
