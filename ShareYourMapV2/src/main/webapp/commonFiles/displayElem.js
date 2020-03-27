function getServerData(url, success){
    $.ajax({
    	type:'GET',
        dataType: "json",
        url: url
    }).done(success);
}

//---------------------		Automatic actions		---------------------		

//Initialize the initial map display in the page (center on the EIDD)
$(function(){
	var map = L.map('map').setView([48.8266496,2.3826648], 20); // LIGNE 14

    var osmLayer = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', { // LIGNE 16
        attribution: '© OpenStreetMap contributors',
    });
    map.addLayer(osmLayer);
});


//---------------------		Actions on click		---------------------	

/*
 * Center a map 
 * @param {float} x		The longitude value to center the map
 * @param {float} y		The latitude value to center the map
 */
function centerMap(x,y){
	var map = L.map('map').setView([x,y], 20);
}

/*
 * Send the request to have the map with a certain id
 * @param {long} id		The id of the wanted map
 */
function displayMap(id){
	getServerData("/ws/maps/"+id,mapDetails);
}

/*
 * Display information of the current map in the page with different templates
 * @param {Map} result		The current map
 */
function mapDetails(result){
	var uid = {"uid":1};
	var dataEdit = $.extend(uid,result);
	
	var map_name = _.template($('#mapHeader').html());
	$("#currentMap").html(map_name(result));
	
	var fav_template = _.template($('#listFavs').html());
	var listFavs = result['locations'];
	_.each(listFavs, function(location) {
		fav_name_type = fav_template(location);
		$("#favsList").html(fav_name_type);
	});
	
	var editMap = _.template($('editMapTemplate').html());
	editHTML = editMap(dataEdit);
	$("#viewMap").html(editMap);
}


/*
 * Send the request to have the favs with a certain id
 * @param {long} id		The id of the wanted favorite
 */
function displayFav(id){
	getServerData("/ws/viewmap/viewlocation/1/1/1",favDetails);							// !! A changer !!
}

/*
 * Display information of the current favs in the page with the 'favDetails' template
 * @param {Location} result		The current favorite
 */
function favDetails(result){
	document.getElementById("viewFav").style.display = "block";
	var location_template = _.template($('#favDetails').html());
	detail = location_template(result);
	$("#viewFav").html(detail);
}


//Close fav's information page
function closeFav(){
	document.getElementById("viewFav").style.display = "none";
}
