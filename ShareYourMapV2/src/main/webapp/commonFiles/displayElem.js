/* DISPLAY ELEMENTS
 * Functions to display maps and locations details
 */

//----------------------	Global variables	-------------------------

var map = L.map('map');
var popup = new L.Popup();
var current_user_id = 1;
var current_map_id = 6;
var current_fav_id = 1;

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
$(function(){
	map.setView([48.8266496,2.3826648], 20);
    var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', { // LIGNE 16
        attribution: '© OpenStreetMap contributors',
    });
    map.addLayer(osmLayer);
    map.on('click', viewFav);
    map.on('dblclick', addFav);
});

function viewFav(e) {
	 popup
     .setLatLng(e.latlng)
     .setContent("You clicked the map at " + e.latlng.toString())
     .openOn(map);
    //displayFav(0);
}

function addFav(e) {
	createFav(e.latlng.lat,e.latlng.lng);
}

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
 * Send the request to have the map with a certain id
 * @param {long} id		The id of the wanted map
 */
function displayMap(id){
	current_map_id = id;
	getServerData("/ws/maps/"+id,mapDetails);
}

/*
 * Display information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){	
	var map_name = _.template($('#mapHeader').html());
	$("#currentMap").html(map_name(result));
	
	document.getElementById('favsList').innerHTML = "";
	var fav_template = _.template($('#listFavs').html());
	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		fav_name_type = fav_template(location);
		$("#favsList").append(fav_name_type);
	});
}


/*
 * Send the request to have the current map and search for the fav with a certain id
 * @param {long} id		The id of the wanted favorite
 */
function displayFav(id){
	current_fav_id = id;
	getServerData("/ws/maps/"+current_map_id,favDetails);						
}

/*
 * Get the location for an certain id
 * And display information of this favs in the page with the 'favDetails' template
 * @param {Map} result	The current map
 * @param {long} id		The id of the wanted favorite
 */
function favDetails(result){
	_.each(result['locations'], function(location) {
		if(location['id']==current_fav_id){
			document.getElementById("viewFav").style.display = "block";
			var location_template = _.template($('#favDetails').html());
			detail = location_template(location);
			$("#viewFav").html(detail);
			centerMap(parseFloat(location['position']['x']),parseFloat(location['position']['y']));
		}
	});
	return null;
}

/*
 * Display the element in which a find an itinerary to the location
 * @param {float} x		The longitude value to the location
 * @param {float} y		The latitude value to the location
 */
function itinerary(x,y){
	
}
	
//Close fav's information page
function closeFav(){
	document.getElementById("viewFav").style.display = "none";
}
