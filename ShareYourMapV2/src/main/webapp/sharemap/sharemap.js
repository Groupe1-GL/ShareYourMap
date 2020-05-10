/** SHAREMAP
 * Functions to navigate in the page with informations about a shared map
 * @link src/main/webapp/sharemap
 */


//----------------------	Server functions	---------------------

/**
 * Sends the DELETE request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function deleteServerData(url,success){
    $.ajax({
    	type: 'DELETE',
        dataType: "json",
        url: url
    }).done(success);
}

/**
 * Sends the PUT request ot the server
 * @param {string} 	 url		The url of the request
 * @param {JSON}	data		The data to send
 * @param {void} success 		The callback function
 */
function postServerData(url,data,success){
    $.ajax({
    	type: 'POST',
		dataType: "text",
		contentType: "application/json",
		data: JSON.stringify(data),
        url: url
    }).done(success);
}

/**
 * Send the PUT request ot the server with a data
 * @param {string} 	 url			The url of the request
 * @param {JSON} data 			The data to send
 * @param {void} success 		The callback function
 */
function putServerData2(url,data,success){
    $.ajax({
    	type: 'PUT',
		dataType: "json",
		contentType: "application/json",
		data: JSON.stringify(data),
		processData: false,
        url: url
    }).done(success);
}

//-------------------		Automatic actions		-----------------		
// Sends the request to get the shared map
if(urlParams.has('id')){
	var current_user_id;
	var id = urlParams.get('id');
	displayMap(id);
}

//---------------------		Actions on click		---------------------	
/**
 * Displays the element to create an event
 */
function displayEvent(){
	const checkBox = document.getElementById("event");
	if(checkBox.checked){
		document.getElementById("eventElem").style.display = "block";
	}
	else{
		document.getElementById("eventElem").style.display = "none";
	}
}

/**
 * Displays the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createNewFav(x,y){
	const details = {"uid":current_user_id, "mid":current_map['id'], "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	const newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
}

/**
 * Sends the request to create a new favorite
 * @param {float} x 	The latitude of the location
 * @param {float} y 	The lonngitude of the location
 */
function newFav(x,y){
	const name = document.getElementById("new_name").value;
	const description = document.getElementById("new_description").value;
	const label = document.getElementById("new_label").value;
	const checkBox = document.getElementById("event");
	var fav = {"name":name, "description":description, "label":label, "position":{"x":x, "y":y}};

	// Adds informations for a event to the request
	if(checkBox.checked){
		const startIn = document.getElementById("new_start").value;
		const endIn = document.getElementById("new_end").value;
		var start = startIn.split(" ");
		var end = endIn.split(" ");

		// Different formats for Firefox and Chrome/Edge
		if(start[1] != null && end[1] != null){
			const startTime = start[1].split(":")[0]+"-"+start[1].split(":")[1]+"-"+start[1].split(":")[2];
			const endTime = end[1].split(":")[0]+"-"+end[1].split(":")[1]+"-"+end[1].split(":")[2];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start[0]+"-"+startTime, "end":end[0]+"-"+endTime};
		}
		else{
			start = startIn.split('T')[0]+"-"+startIn.split('T')[1].split(':')[0]+"-"+startIn.split('T')[1].split(':')[1];
			end = endIn.split('T')[0]+"-"+endIn.split('T')[1].split(':')[0]+"-"+endIn.split('T')[1].split(':')[1];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
		}
		// Sends the request to create a event
		putServerData2(`/ws/event/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
	}
	else{
		// Sends the request to create a location
		putServerData2(`/ws/location/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
	}
}

/**
 * Displays the element in which a user can edit a fav
 * @param {Map} result		The map to edit
 */
function showEditFav(){
	var uid = {"uid":current_user_id};
	var mid = {"mid":current_map['id']};
	var editDetails = $.extend(uid,mid,current_fav);
	var editFav = _.template($('#editFavTemplate').html());
	$("#viewFav").html(editFav(editDetails));
}

/**
 * Sends the request to edit a favorite
 * @param {int} id 
 * @param {boolean} event 
 */
function editFav(id,event){
	var name = document.getElementById("edit_name").value;
	var description = document.getElementById("edit_description").value;
	var label = document.getElementById("edit_label").value;	
	var fav = {"name":name, "description":description, "label":label};

	// Adds informations for a event to the request
	if(event){
		var startIn = document.getElementById("new_start").value;
		var endIn = document.getElementById("new_end").value;
		var start = startIn.split(" ");
		var end = endIn.split(" ");	

		// Different formats for Firefox and Chrome/Edge
		if(start[1] != null && end[1] != null){
			var startTime = start[1].split(":")[0]+"-"+start[1].split(":")[1]+"-"+start[1].split(":")[2];
			var endTime = end[1].split(":")[0]+"-"+end[1].split(":")[1]+"-"+end[1].split(":")[2];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start[0]+"-"+startTime, "end":end[0]+"-"+endTime};
		}
		else{
			start = startIn.split('T')[0]+"-"+startIn.split('T')[1].split(':')[0]+"-"+startIn.split('T')[1].split(':')[1];
			end = endIn.split('T')[0]+"-"+endIn.split('T')[1].split(':')[0]+"-"+endIn.split('T')[1].split(':')[1];
			fav = {"id":0,"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
		}
		// Sends the request to edit a location
		postServerData(`/ws/event/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig);
	}
	else{
		// Sends the request to edit a location
		postServerData(`/ws/location/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig);
	}
}

/**
 * Deletes a location
 * @param {int} lid		The id of the map to delete
 */
function deleteFav(lid){
	// Searchs the map to  delete in the list of favorite of the current map
	_.each(current_map['locations'], function(location) {
		if(location['id']==lid){
			if(location['event']==1){
				deleteServerData(`/ws/event/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
			}
			else{
				deleteServerData(`/ws/location/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
			}
		}
	});
}

/**
 * Filters the list of favorites regarding a research
 */
$(document).ready(function(){
	$("#searchBar").on("keyup", function() {
		var value = $(this).val().toLowerCase();
		fillFavList(user_favs);
	 	$("#favsList li").filter(function() {
			$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	  	});
	});
});

/**
 * Displays the response of the request and refreshs the page
 * @param {String} result 
 */
function navig(result){
	alert(result);
	location.reload();
}

/**
 * Refreshs the page
 */
 function refresh(result){
	history.go(0);
}