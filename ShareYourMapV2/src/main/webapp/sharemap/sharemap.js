
if(urlParams.has('id')){
	var current_user_id;
	var id = urlParams.get('id');
	displayMap(id);
}

//----------------------	Server functions	---------------------

/**
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

/**
 * Send the DELETE request ot the server
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
 * Send the PUT request ot the server
 * @param {string} 	 url			The url of the request
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
 * Send the PUT request ot the server
 * @param {string} 	 url			The url of the request
 * @param {void} success 		The callback function
 */
function putServerData(url,success){
    $.ajax({
    	type: 'PUT',
        dataType: "json",
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


//----------------------	Action functions	---------------------
/**
 * Display the element to create an event
 */
function displayEvent(){
	var checkBox = document.getElementById("event");
	if(checkBox.checked){
		document.getElementById("eventElem").style.display = "block";
	}
	else{
		document.getElementById("eventElem").style.display = "none";
		document.getElementById("start").value = null;
		document.getElementById("end").value = null;
	}
}


/**
 * Display the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createNewFav(x,y){
	var details = {"uid":current_user_id, "mid":current_map['id'], "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	var newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
}


/**
 * Send the request to create a new favorite
 */
function newFav(x,y){
	var name = document.getElementById("new_name").value;
	var description = document.getElementById("new_description").value;
	var label = document.getElementById("new_label").value;
	var checkBox = document.getElementById("event");
	var fav = {"name":name, "description":description, "label":label, "position":{"x":x, "y":y}};
	if(checkBox.checked){
		var start = document.getElementById("new_start").value;
		var end = document.getElementById("new_end").value;
		fav = {"name":name, "description":description, "label":label, "position":{"x":x, "y":y}, "start":start, "end":end};
	}
	putServerData2(`/ws/location/map/${current_map['id']}/user/${current_user_id}`,fav,refresh);
}


/**
 * Display the element in which a user can edit a fav
 * @param {Map} result		The map to edit
 */
function showEditFav(){
	var uid = {"uid":current_user_id};
	var mid = {"mid":current_map['id']};

	_.each(current_map['locations'], function(location) {
		if(location['id']==current_fav['id']){
			var editDetails = $.extend(uid,mid,location);
			var editFav = _.template($('#editFavTemplate').html());
			$("#viewFav").html(editFav(editDetails));
		}
	});	
}

function editFav(id,event){
	var name = document.getElementById("edit_name").value;
	var description = document.getElementById("edit_description").value;
	var label = document.getElementById("edit_label").value;	
	var fav = {"name":name, "description":description, "label":label};
	if(event){
		var start = document.getElementById("new_start").value;
		var end = document.getElementById("new_end").value;
		fav = {"name":name, "description":description, "label":label, "start":start, "end":end};
	}
	postServerData(`/ws/location/${id}/map/${current_map['id']}/user/${current_user_id}`,fav,navig)
}


/**
 * Filter the list of favorites regarding a research
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


// Delete a location
function deleteFav(lid){
	deleteServerData(`/ws/location/${lid}/map/${current_map['id']}/user/${current_user_id}`,refresh);
}

function navig(result){
	alert(result);
	location.reload();
}


// Refresh 
function refresh(result){
	history.go(0);
}
