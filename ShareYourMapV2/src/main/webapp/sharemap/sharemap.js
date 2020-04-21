var current_user_id = 1;
var current_map_id = 1;

displayMap(1);
/*
 * Display the element in which a user can create a fav
 * @param {int} uid		the id of the current user
 * @param {float} x		The longitude value of the new favorite
 * @param {float} y		The latitude value of the new favorite
 */
function createNewFav(x,y){
	var details = {"uid":current_user_id, "mid":current_map_id, "x":x, "y":y};
	document.getElementById("viewFav").style.display = "block";
	var newFav = _.template($('#newFavTemplate').html());
	$("#viewFav").html(newFav(details));
	if (x==0 && y==0) {
		document.getElementById("address_input").style.display = "block";
	}
}