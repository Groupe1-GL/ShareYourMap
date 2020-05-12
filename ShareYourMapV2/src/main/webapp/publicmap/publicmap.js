/* PUBLICMAP
 * Functions to navigate in the page collecting all the public maps
 * @link src/main/webapp/publicmap
 */

/**
 * Sends the GET request ot the server
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

/**
 * Fills the navigation button with the current user id
 * And sends the request to have the list of public maps
 */ 
$(function(){
	var user = _.template($('#navigate').html());
	$("#viewmap").html(user({"uid":current_user_id}));

	getServerData("/ws/maps",displayMapsList);
});

/**
 * Displays public maps in a list of button based on the 'listPublicMap' template
 * Filters maps based on the 'access' atribute (access == true if the map is public)
 * @param {List<Map>} result	The list of map stocked in the database
 */
function displayMapsList(result){

	document.getElementById('mapList').innerHTML = "";
	var map_template = _.template($('#listPublicMap').html());
	_.each(result, function(map) {
		if(map['access']){
			map_display = map_template(map);
			$('#mapList').append(map_display);
		}
    });
}

/**
 * Changes backgroung color of the current map and sets the hover effect on others maps
 * @param {int} id	id of the currentMap
 */
function editMapColor(id){
	var c_map = document.getElementById(id);
	var maps = document.getElementById('mapList').getElementsByClassName('public_maps');

	_.each(maps, function(map) {
		map.style.backgroundColor = "white";
		map.onmouseover = function(){
			if (this != c_map) {
				this.style.backgroundColor ="#F7DD48";
			}
		};
        map.onmouseout = function(){
			if (this != c_map) {
				this.style.backgroundColor ="white";
			}
		};;
	});
	c_map.style.backgroundColor = "#F7DD48";
	c_map.style.color = "black";
}

/**
 * Filter the list of maps regarding a research (String)
 */
$(document).ready(function(){
	$("#searchBar").on("keyup", function() {
		var value = $(this).val().toLowerCase();
	  	$("#mapList li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
		});
	});
});