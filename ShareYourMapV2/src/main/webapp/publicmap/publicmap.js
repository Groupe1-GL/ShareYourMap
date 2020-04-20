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
	current_map_id=1;
	map_now = {"current_map":current_map_id};
	document.getElementById('mapList').innerHTML = "";
	var map_template = _.template($('#listPublicMap').html());
	_.each(result, function(map) {
		if(map['access']=="1"){
			sum = $.extend(map, map_now);
			map_display = map_template(sum);
			$('#mapList').append(map_display);
		}
    });
}

/*
 * Filter the list of maps regarding a research
 */
$(document).ready(function(){
	$("#searchBar").on("keyup", function() {
		var value = $(this).val().toLowerCase();
	  	$("#mapList li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});