/* DISPLAY ELEMENTS
 * Functions to display maps and locations details
 */

//----------------------	Global variables	-------------------------

var map = L.map('map');
var current_user_id = 1;
var current_map_id = "dezf";
var current_fav_id = 1;
var markers = L.layerGroup();
var heartLoc = L.icon({
    iconUrl: "../resources/heart_localisation.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});

//----------------------	Server function		-------------------------

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

//---------------------		Automatic actions		---------------------		

// Initialize the initial map display in the page (center on the EIDD)

map.setView([48.8266496,2.3826648], 20);
var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png');
map.addLayer(osmLayer);
map.on('dblclick', addFav);

//---------------------		Actions on click		---------------------	

/*
 * Center a map 
 * @param {float} x		The longitude value to center the map
 * @param {float} y		The latitude value to center the map
 */
function centerMap(x,y){
	map.setView([x,y], 20);
}

/*
 * On double click on the map, open the element to create a new fav at this address
 */
function addFav(e) {
	createNewFav(e.latlng.lat,e.latlng.lng);
}

/*
 * Send the request to have the map with a certain id
 * @param {string} id		The id of the wanted map
 */
function displayMap(id){
	current_map_id = id;
	getServerData(`/ws/maps/${id}`,mapDetails);
}

/*
 * Display information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){
	markers.clearLayers();
	
	var map_name = _.template($('#mapHeader').html());
	$("#currentMap").html(map_name(result));
	
	document.getElementById('favsList').innerHTML = "";
	var fav_template = _.template($('#listFavs').html());

	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		fav_name_type = fav_template(location);
		$("#favsList").append(fav_name_type);
		var marker = L.marker([location['position']['x'],location['position']['y']],{icon:heartLoc});
		//marker.on('click',displayFav(parseInt(location['id'])));
		markers.addLayer(marker);
	});
	map.addLayer(markers);
}


/*
 * Send the request to have the current map and search for the fav with a certain id
 * @param {long} id		The id of the wanted favorite
 */
function displayFav(id){
	current_fav_id = id;
	getServerData(`/ws/maps/${current_map_id}`,favDetails);						
}

// Close all element before open an other
function closeAll() {
	var divClose = document.getElementsByClassName("closable");
	for (const key in divClose) {
		//document.getElementById(key.id).style.display = "none";
	}
}

/*
 * Get the location for an certain id
 * And display information of this favs in the page with the 'favDetails' template
 * @param {Map} result	The current map
 * @param {long} id		The id of the wanted favorite
 */
function favDetails(result){
	closeAll();
	_.each(result['locations'], function(location) {
		if(location['id']==current_fav_id){
			document.getElementById("viewFav").style.display = "block";
			var location_template = _.template($('#favDetails').html());
			detail = location_template(location);
			$("#viewFav").html(detail);
			centerMap(parseFloat(location['position']['x']),parseFloat(location['position']['y']));
		}
	});
}

/*
 * Display the element in which a find an itinerary to the location
 * @param {float} x		The longitude value to the location
 * @param {float} y		The latitude value to the location
 */
function itinerary(x,y){
	
}
	
// Close element
function closeElement(id){
	document.getElementById(id).style.display = "none";
}

