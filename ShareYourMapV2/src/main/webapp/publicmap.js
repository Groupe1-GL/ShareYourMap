/* PUBLICMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/publicmap.html 
 */

/*
 * Send the GET request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function getServerData(url, success){
    $.ajax({
        dataType: "json",
        url: url
    }).done(success);
}

/*		---------------------		Automatic actions		---------------------		*/	

// Initialize the initial map display in the page (center on the EIDD)
$(function(){
	var map = L.map('map').setView([48.8266496,2.3826648], 20)

    var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
    });
    map.addLayer(osmLayer);
});

// Send the request to have the list of public maps
$(function(){
	getServerData("/ws/searchmap",displayMapsList);
	//getServerData("/ws/maps",displayMapsList);
});


/*
 * Display maps in a list of button based on the 'listPublicMap' template
 * @param {List<Map>}	The list of public map stocked in the database
 */
function displayMapsList(result){
	var id_template = _.template($('#listPublicMap').html());
	_.each(result, function(map) {
		map_id = id_template(map);
		$('#mapsList').html(map_id);
    });
}


/*		---------------------		Actions on click		---------------------		*/

/*
 * Center a map 
 * @param {float} x		The longitude value to center the map
 * @param {float} y		The latitude value to center the map
 */
function centerMap(x,y){
	var map = L.map('map').setView([x,y], 20);
}

// Send the request to have the list of maps with "map_name" (searchBar value) in their name 
$(function(){
	$("#searchMap").click(function(){
		var map_name = document.getElementById("searchBar").value;
		if (map_name !=  null && map_name != ""){
			//getServerData("/ws/maps/"+map_name,getMapsList)
		}
	});
});


/*
 * Send the request to have the map with a certain id
 * @param {long} id		The id of the wanted map
 */
function displayMap(id){
	getServerData("/ws/viewmap/1/1",mapDetails);
	//getServerData("/ws/maps/"+id,mapDetails);
}

/*
 * Display information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){
	var map_name = _.template("<h2><%= name %></h2>");
	$("#currentMap").html(map_name(result));
	
	var name_template = _.template($('#listLocation').html());
	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		location_name = name_template(location);
		$("#favsList").html(location_name);
    });
}


/*
 * Send the request to have the favs with a certain id
 * @param {long} id		The id of the wanted favorite
 */
function displayLocation(id){
	getServerData("/ws/viewmap/viewlocation/1/1/1",locationDetails);
}

/*
 * Display information of the current favs in the page with the 'locationDetails' template
 * @param {Location} result		The current favorite
 */
function locationDetails(result){
	document.getElementById("locationPage").style.display = "block";
	var location_template = _.template($('#locationDetails').html());
	detail = location_template(result);
	$("#locationPage").html(detail);
}

/* -------------------------------- Display location elements  -------------------------------- */

//Close location's information page
function closeLocation(){
	document.getElementById("locationPage").style.display = "none";
}

