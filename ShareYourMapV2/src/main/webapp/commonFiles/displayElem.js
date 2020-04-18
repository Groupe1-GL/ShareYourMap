/* DISPLAY ELEMENTS
 * Functions to display maps and locations details
 */

//----------------------	Visual effects	-------------------------
$("#toggle").click(showNav);

function showNav() {
    if( document.getElementById("nav_bar").style.transform == "translateX(0px)"){
        document.getElementById("nav_bar").style.transform = "translateX(-340px)";
		document.getElementById("toggle").style.left = "0";
		document.getElementById("toggle").style.backgroundImage = "url(\"../resources/burger.png\")";
		document.getElementById("currentMap").style.display = "block";
    }
    else {
        document.getElementById("nav_bar").style.transform = "translateX(0px)";
        document.getElementById("toggle").style.left = "22%";
		document.getElementById("currentMap").style.display = "none";
		document.getElementById("toggle").style.backgroundImage = "url(\"../resources/croix.png\")";
    }
}

  

//----------------------	Global variables	-------------------------

var map = L.map('map');
var current_user_id = 1;
var current_map_id = 1;
var current_fav_id = 1;
var markers = L.layerGroup();
var heartLoc = L.icon({
    iconUrl: "../resources/heart_localisation.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});
var router_elem = null;

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
function centerMapView(x,y,z){
	map.setView([x,y], z);
}

/*
 * Find the center of the cluser of locations
 * @param {List<Location>} list_locations		The list of locations 
 */
function findCenter(list_locations){
	var x_mean = 0;
	var y_mean = 0;
	var x_min, y_min = 180;
	var x_max, y_max = -180;
	var z = 20;
	const list_length = list_locations.length;
	if(list_length == 1){
		centerMapView(list_locations[0]['position']['x'],list_locations[0]['position']['x'],20);
		return;
	}
	_.each(list_locations, function(location) {
		x_mean = x_mean + location['position']['x'];
		y_mean = y_mean + location['position']['y'];
		x_max = Math.max(x_max, location['position']['x']);
		x_min = Math.min(x_min, location['position']['x']);
		y_max = Math.max(y_max, location['position']['y']);
		y_min = Math.min(y_min, location['position']['y']);
	});
	x_mean /= list_length;
	y_mean /= list_length;
	var z_x = Math.abs(360/(x_max - x_min));
	var z_y = Math.abs(360/(y_max - y_min));
	//z = Math.min(z_x,z_y);
	centerMapView(x_mean,y_mean,z);
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

	var fav = document.getElementById("newFav");
	if(fav!=null){
		fav.style.display = "block";
	}
	
	document.getElementById('favsList').innerHTML = "";
	var fav_template = _.template($('#listFavs').html());

	var listFavs = result['locations'];
	findCenter(listFavs);
	_.each(listFavs, function(location) {
		fav_name_type = fav_template(location);
		$("#favsList").append(fav_name_type);
		var marker = L.marker([location['position']['x'],location['position']['y']],{icon:heartLoc});
		marker.on('click',displayFav.bind(null,parseInt(location['id'])));
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
	if (router_elem != null){
		router_elem.remove();
	}
	_.each(result['locations'], function(location) {
		if(location['id']==current_fav_id){
			document.getElementById("viewFav").style.display = "block";
			var location_template = _.template($('#favDetails').html());
			detail = location_template(location);
			$("#viewFav").html(detail);
			centerMapView(parseFloat(location['position']['x'])-0.0002,parseFloat(location['position']['y'])+0.0008,20);
		}
	});
}

/*
 * Display the element in which a find an itinerary to the location
 * @param {float} x		The longitude value to the location
 * @param {float} y		The latitude value to the location
 */
function goTo(x,y){
	if (router_elem != null && router_elem.remove() == this){
		return;
	}
	router_elem = L.Routing.control({
		lineOptions: {
			styles: [{color: '#e74c4d', opacity: 1, weight: 2}]
		},
		waypoints: [
			null,
			L.latLng(x, y)
		],
		routeWhileDragging: true,
			
		router: new L.Routing.osrmv1({
			language: 'eng',
			profile: 'car', // car, bike, foot
		}),
		geocoder: L.Control.Geocoder.nominatim()
	});
	router_elem.addTo(map);
}


// Close element
function closeElement(id){
	document.getElementById(id).style.display = "none";
}