/** DISPLAY ELEMENTS
 * Functions to display maps and locations details
 */

  
// ---------------------		Global variables	---------------------
// Variables to locate the user
var current_loc_x = null;
var current_loc_y = null;

// Variables link to diplaying the leaflet map
var map = L.map('map');
var markers = L.layerGroup();
var heartLoc = L.icon({
    iconUrl: "../resources/heart_localisation.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});

// Variables link to diplaying the leaflet markers
var heartEvent = L.icon({
    iconUrl: "../resources/heart_event.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});

// Variables link to diplaying the leaflet router
var router_elem = null;
var options = {
	enableHighAccuracy: true,
	timeout: 5000,
	maximumAge: 0
  };

// Variables to save the current map and the current favorite
var current_map = null;
var current_fav = null;

//----------------------	Server functions		-------------------------

/**
 * Sends the GET request ot the server
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

/**
 * Sends the POST request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function postServerData2(url,success){
    $.ajax({
    	type: 'POST',
		dataType: "json",
        url: url
    }).done(success);
}


//---------------------		Automatic actions		---------------------		

/**
 * Init user id with the query element in the URL
 */ 
var urlParams = new URLSearchParams(window.location.search);
if(urlParams.has('uid')){
	var current_user_id = urlParams.get('uid');
}

/**
 * Initialises the initial map display in the page (center on the EIDD)
 */ 
map.setView([48.8266496,2.3826648], 15);
var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png');
map.addLayer(osmLayer);
map.on('dblclick', addFav);

/**
 * Finds the current location of the user
 */
navigator.geolocation.getCurrentPosition(success, error, options);

// Saves the current position of the user
function success(pos){
	const crd = pos.coords;
	current_loc_x = crd.latitude;
	current_loc_y = crd.longitude;
}

function error(err) {
	console.warn(`ERREUR (${err.code}): ${err.message}`);
}

//---------------------		Actions on click		---------------------	

/**
 * Centers a map 
 * @param {float} x		The longitude value to center the map
 * @param {float} y		The latitude value to center the map
 * @param {int} z		The zoom level (between 0 and 20)
 */
function centerMapView(x,y,z){
	map.setView([x,y], z);
}

/**
 * Finds the center of the cluser of locations
 * @param {List<Location>} list_locations		The list of locations 
 */
function findCenter(list_locations){
	var x_min = 180;
	var y_min = 90;
	var x_max = -180;
	var y_max = -90;
	const list_length = list_locations.length;

	// If only one location is in the list, centers the map on this  location
	if(list_length == 1){
		centerMapView(list_locations[0]['position']['x'],list_locations[0]['position']['y'],16);
		return;
	}

	// Calculates the center of the x and y coordinates
	_.each(list_locations, function(location) {
		x_max = Math.max(x_max, location['position']['x']);
		x_min = Math.min(x_min, location['position']['x']);
		y_max = Math.max(y_max, location['position']['y']);
		y_min = Math.min(y_min, location['position']['y']);
	});
	const dist_y = Math.abs(y_max - y_min);
	const dist_x = Math.abs(x_max - x_min);
	const x_mean = (x_max + x_min)/2;
	const y_mean = (y_max + y_min)/2;

	// Calculates the zoom level to have a view on all locations
	const z_x = Math.floor((Math.log(360/dist_x))/Math.log(2));
	const z_y = Math.floor((Math.log(180/dist_y))/Math.log(2));
	const z = Math.min(z_x,z_y,20);

	// Centers the map regarding these informations
	centerMapView(x_mean,y_mean,z+2);
}

/**
 * If double click on the map, opens the element to create a new fav at this address
 * @param {*} e		The event which trigged the function
 */
function addFav(e) {
	if(current_map != null){
		createNewFav(e.latlng.lat,e.latlng.lng);
	}
}

/**
 * Centers the map on the user's position
 */
function centerMe(){
	centerMapView(current_loc_x,current_loc_y,20);
}

/**
 * Sends the request to have the map with a certain id
 * @param {string} id		The id of the wanted map
 */
function displayMap(id){
	getServerData(`/ws/maps/${id}`,mapDetails);
}

/**
 * Displays information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){
	// Sets the current_map to the result
	current_map = result;

	// Change the background color of the current map
	editMapColor(result['id']);

	// Fills the element which displays the name of the current map
	var map_name = _.template("<%= name %>");
	$("#currentMap").html(map_name(result));

	// Fills the favorites list with the map's favorites and center the map regarding the cluster of favorites
	const listFavs = result['locations'];
	findCenter(listFavs);
	fillFavList(listFavs);
}

/**
 * Adds favorites to the list  of favorites
 */
function fillFavList(favs){
	// Clears the set of markers on the map and the favorites list
	markers.clearLayers();
	document.getElementById('favsList').innerHTML = "";

	const fav_template = _.template($('#listFavs').html());

	// For every favorites fill the template, adds an marker to the group of markers
	_.each(favs, function(location) {
		fav_name_type = fav_template(location);
		$("#favsList").append(fav_name_type);
		if(location['event']==1){
			var marker = L.marker([location['position']['x'],location['position']['y']],{icon:heartEvent});
		}
		else{
			var marker = L.marker([location['position']['x'],location['position']['y']],{icon:heartLoc});
		}
		marker.on('click',displayFav.bind(null,parseInt(location['id'])));
		markers.addLayer(marker);
	});

	// Adds the group of markers on the map
	map.addLayer(markers);
}

/**
 * Gets the location for an certain id
 * And displays information of this favs in the page with the 'favDetails' template
 * @param {long} id		The id of the wanted favorite
 */
function displayFav(id){
	// Clears the router element
	if (router_elem != null){
		router_elem.remove();
	}

	const uid = {"uid":current_user_id};
	const mid = {"mid":current_map['id']};
	
	// Seachs the current favorite in the list of favorites of the current map
	_.each(current_map['locations'], function(location) {
		if(location['id']==id){
			current_fav = location;

			// Displays the favorites details on the page
			document.getElementById("viewFav").style.display = "block";
			const location_template = _.template($('#favDetails').html());
			const loc_info = $.extend(uid,mid,location);
			detail = location_template(loc_info);
			$("#viewFav").html(detail);

			// Uses a special template for messages and pictures
			$("#msgs").html("");
			const msgs_template = _.template("\"<%=txt%>\"<br>");
			_.each(location['messages'], function(msg) {
				$("#msgs").append(msgs_template({"txt":msg}));
			});

			$("#pics").html("");
			const pics_template = _.template("<%=pix%><br>");
			_.each(location['pictures'], function(pix) {
				$("#pics").append(pics_template({"pix":pix}));
			});
			
			// Centers the view on the location
			centerMapView(parseFloat(location['position']['x'])-0.0002,parseFloat(location['position']['y'])+0.0008,20);
		}
	});				
}

/**
 * Displays the div in which the user can add a picture 
 */
function addPix(){
	document.getElementById("addPix").style.display = "block";
}

/**
 * Sends the request to add a picture to a location
 */
function sendPix(){
	const picture = document.getElementById("pix").value;
	postServerData2(`/ws/feed-img/location/${current_fav['id']}/map/${current_map['id']}/user/${current_user_id}`,picture,refresh);
}

/**
 * Displays the div in which the user can add a msg
 */
function addMsg(){
	document.getElementById("addMsg").style.display = "block";
}

/**
 * Sends the request to add a message to a location
 */
function sendMsg(){
	const msg = document.getElementById("message").value;
	postServerData2(`/ws/feed/${msg}/location/${current_fav['id']}/map/${current_map['id']}/user/${current_user_id}`,refresh);
}

/**
 * Displays different means of transport available for the user to go to the current location
 */
function howToGo(){
	if (document.getElementById("meanTransp").style.display == "block"){
		document.getElementById("meanTransp").style.display = "none";
	}
	else{
		document.getElementById("meanTransp").style.display = "block";
	}
}

/**
 * Displays different means of transport available for the user to go from A to B (without parameter)
 */
function howToGoFull(){
	if (document.getElementById("meanTranspFull").style.display == "block"){
		document.getElementById("meanTranspFull").style.display = "none";
	}
	else{
		document.getElementById("meanTranspFull").style.display = "block";
	}
}

/**
 * Displays or clears the element in which a user can find an itinerary to the location
 * @param {float} x		The longitude value to the location
 * @param {float} y		The latitude value to the location
 * @param {String} mean The mean of transport to go to the location
 */
function goTo(x,y,mean){
	
	// Clears the element if already displays
	if (router_elem != null && router_elem.remove() == this){
		return;
	}

	// Adds information in the router element (line style, coordinates of locations, ,language and mean of transport)
	router_elem = L.Routing.control({
		lineOptions: {
			styles: [{color: '#e74c4d', opacity: 1, weight: 2}]
		},
		waypoints: [
			L.latLng(current_loc_x, current_loc_y),
			L.latLng(x, y)
		],
			
		router: new L.Routing.osrmv1({
			language: 'eng',
			profile: mean, // car, bike, foot
		}),
		geocoder: L.Control.Geocoder.nominatim()
	});
	
	// Adds the router element to the map
	router_elem.addTo(map);
}


/**
 * Closes an element (div)
 * @param {int} id		The id of the element
 */ 
function closeElement(id){
	document.getElementById(id).style.display = "none";
}


/**
 * Refreshs the page
 */
function refresh(result){
	history.go(0);
}

/**
 * Displays the "Good bye ^^" element when the user leave the website
 */
function bye(){
	alert("Good bye ^^");
	location.replace("../home.html");
}

//----------------------	Visual effects	-------------------------
$("#toggle").click(showNav);

// Hides or show the navigation bar and the div with the current map name
function showNav() {
    if( document.getElementById("nav_bar").style.transform == "translateX(0px)"){
        document.getElementById("nav_bar").style.transform = "translateX(-348px)";
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
