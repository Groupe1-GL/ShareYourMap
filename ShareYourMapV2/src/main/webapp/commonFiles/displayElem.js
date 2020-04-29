/* DISPLAY ELEMENTS
 * Functions to display maps and locations details
 */

//----------------------	Visual effects	-------------------------
$("#toggle").click(showNav);

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

  
// ---------------------		Global variables	---------------------
var current_loc_x = null;
var current_loc_y = null;

var map = L.map('map');
var current_map = null;
var current_fav = null;
var markers = L.layerGroup();
var heartLoc = L.icon({
    iconUrl: "../resources/heart_localisation.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});
var heartEvent = L.icon({
    iconUrl: "../resources/heart_event.png",
	iconSize:     [45, 45],
	iconAnchor:   [21, 42],
});
var router_elem = null;
var options = {
	enableHighAccuracy: true,
	timeout: 5000,
	maximumAge: 0
  };


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

// Init user id
var url =  window.location.href;
var urlParams = new URLSearchParams(window.location.search);
var current_user_id = urlParams.get('uid');

// Initialize the initial map display in the page (center on the EIDD)

map.setView([48.8266496,2.3826648], 20);
var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png');
map.addLayer(osmLayer);
map.on('dblclick', addFav);

// Find the current location of the user
navigator.geolocation.getCurrentPosition(success, error, options);

function success(pos){
	var crd = pos.coords;
	current_loc_x = crd.latitude;
	current_loc_y = crd.longitude;
}

function error(err) {
	console.warn(`ERREUR (${err.code}): ${err.message}`);
  }

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
	var x_min = 180;
	var y_min = 90;
	var x_max = -180;
	var y_max = -90;
	const list_length = list_locations.length;
	if(list_length == 1){
		centerMapView(list_locations[0]['position']['x'],list_locations[0]['position']['x'],20);
		return;
	}
	_.each(list_locations, function(location) {
		x_max = Math.max(x_max, location['position']['x']);
		x_min = Math.min(x_min, location['position']['x']);
		y_max = Math.max(y_max, location['position']['y']);
		y_min = Math.min(y_min, location['position']['y']);
	});
	var dist_y = Math.abs(y_max - y_min);
	var dist_x = Math.abs(x_max - x_min);
	x_mean = (x_max + x_min)/2;
	y_mean = (y_max + y_min)/2;
	var z_x = Math.floor((Math.log(360/dist_x))/Math.log(2));
	var z_y = Math.floor((Math.log(180/dist_y))/Math.log(2));
	var z = Math.min(z_x,z_y,20);
	centerMapView(x_mean,y_mean,z+2);
}

/*
 * On double click on the map, open the element to create a new fav at this address
 */
function addFav(e) {
	if(current_map != null){
		createNewFav(e.latlng.lat,e.latlng.lng);
	}
}

/*
 * Center the map on the user's position
 * @param pos
 */
function centerMe(){
	centerMapView(current_loc_x,current_loc_y,20);
}


/*
 * Send the request to have the map with a certain id
 * @param {string} id		The id of the wanted map
 */
function displayMap(id){
	getServerData(`/ws/maps/${id}`,mapDetails);
}

/*
 * Display information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){
	current_map = result;
	markers.clearLayers();
	
	var map_name = _.template("<%= name %>");
	$("#currentMap").html(map_name(result));

	var fav = document.getElementById("newFav");
	if(fav!=null){
		fav.style.display = "block";
	}

	var listFavs = result['locations'];
	findCenter(listFavs);
	fillFavList(listFavs);
}

/*
 * Add favorite to the list  of favorites
 */
function fillFavList(favs){
	markers.clearLayers();
	
	document.getElementById('favsList').innerHTML = "";
	var fav_template = _.template($('#listFavs').html());

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
		marker.on('mouseover',popUpFav.bind(null,location['name'],location['posistion']));
		markers.addLayer(marker);
	});
	map.addLayer(markers);
}


/*
 * Display the name of a favorite when on mouseover
 */
function popUpFav(name,pos) {
    popup
        .setLatLng([pos['x'],pos['y']])
        .setContent(name)
        .openOn(map);
}

/*
 * Get the location for an certain id
 * And display information of this favs in the page with the 'favDetails' template
 * @param {long} id		The id of the wanted favorite
 */
function displayFav(id){
	
	if (router_elem != null){
		router_elem.remove();
	}

	var uid = {"uid":current_user_id};
	var mid = {"mid":current_map['id']};
	
	_.each(current_map['locations'], function(location) {
		if(location['id']==id){
			current_fav = location;
			document.getElementById("viewFav").style.display = "block";
			var location_template = _.template($('#favDetails').html());
			var loc_info = $.extend(uid,mid,location);
			detail = location_template(loc_info);
			$("#viewFav").html(detail);

			$("#msgs").html("");
			var msgs_template = _.template("<li><%=txt></li>");
			_.each(location['message'], function(msg) {
				$("#msgs").append(msgs_template({"txt":msg}));
			});

			$("#pics").html("");
			var pics_template = _.template("<li><%=pix></li>");
			_.each(location['pictures'], function(pix) {
				$("#pics").append(pics_template({"pix":pix}));
			});
			
			centerMapView(parseFloat(location['position']['x'])-0.0002,parseFloat(location['position']['y'])+0.0008,20);
		}
	});				
}

/*
 * Display the div in which the user can add a picture 
 */
function addPix(){
	document.getElementById("addPix").style.display = "block";
}

/*
 * Display the div in which the user can add a msg
 */
function addMsg(){
	document.getElementById("addMsg").style.display = "block";
}

// Close all element before open an other
function closeAll() {
	var divClose = document.getElementsByClassName("closable");
	for (const key in divClose) {
		//document.getElementById(key.id).style.display = "none";
	}
}

/*
 * Display different means of transport available for the user
 */
function howToGo(){
	if (document.getElementById("meanTransp").style.display == "block"){
		document.getElementById("meanTransp").style.display = "none";
	}
	else{
		document.getElementById("meanTransp").style.display = "block";
	}
}

/*
 * Display different means of transport available for the user
 */
function howToGoFull(){
	if (document.getElementById("meanTranspFull").style.display == "block"){
		document.getElementById("meanTranspFull").style.display = "none";
	}
	else{
		document.getElementById("meanTranspFull").style.display = "block";
	}
}

/*
 * Display the element in which a find an itinerary to the location
 * @param {float} x		The longitude value to the location
 * @param {float} y		The latitude value to the location
 */
function goTo(x,y,mean){
	
	if (router_elem != null && router_elem.remove() == this){
		return;
	}
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
	
	router_elem.addTo(map);
}


// Close element
function closeElement(id){
	document.getElementById(id).style.display = "none";
}