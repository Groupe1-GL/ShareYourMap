/* PUBLICMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/publicmap
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

// Send the request to have the list of public maps
$(function(){
	getServerData("/ws/maps",displayMapsList);
});

/*
 * Display public maps in a list of button based on the 'listPublicMap' template
 * @param {List<Map>}	The list of public map stocked in the database
 */
function displayMapsList(result){
	document.getElementById('mapList').innerHTML = "";
	var map_template = _.template($('#listPublicMap').html());
	_.each(result, function(map) {
		if(map['access']=="1"){
			map_display = map_template(map);
			$('#mapList').append(map_display);
		}
    });
}

/*		---------------------		Actions on click		---------------------		*/

// Send the request to have the list of maps with "map_name" (searchBar value) in their name 
$(function(){
	$("#searchMap").click(function(){
		var map_name = document.getElementById("searchBar").value;
		if (map_name !=  null && map_name != ""){
			getServerData("/ws/mapsearch/?search="+map_name,displayMapsList)
		}
	});
});
