function getServerDataTxt(url, success){
    $.ajax({
    	type: 'GET',
        dataType: "text",
        url: url
    }).done(success);
}

function getServerDataLambda(url, success){
    $.ajax({
    	type: 'GET',
        url: url
    }).done(success);
}

function getServerData(url, success){
    $.ajax({
    	type:'GET',
        dataType: "json",
        url: url
    }).done(success);
}

function putServerData(url, success){
    $.ajax({
    	type: 'PUT',
        dataType: "json",
        url: url
    }).done(success);
}

function postServerData(url, success){
    $.ajax({
    	type: 'POST',
        dataType: "json",
        url: url
    }).done(success);
}

function deleteServerData(url, success){
    $.ajax({
    	type: 'DELETE',
        dataType: "json",
        url: url
    }).done(success);
}


$(function(){
	getServerDataLambda("/ws/viewmap/1",getUser);
});


function getUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#userName").append(html);
}

$(function(){
	$("#createMap").click(function(){
		postServerData("/ws/viewmap/1",postAddMapToUser);
	});
});

function postAddMapToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddMap").append(html);
}


$(function(){
	$("#deleteMap").click(function(){
		deleteServerData("/ws/viewmap/1/1",deleteMapToUser);
	});
});

function deleteMapToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resDeleteMap").append(html);
}


$(function(){
	$("#addLocation").click(function(){
		putServerData("/ws/viewmap/viewlocation/1/1",putAddLocation);
	});
});

function putAddLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddLocation").append(html);
}


$(function(){
	getServerData("/ws/viewmap/viewlocation/1/1",getLocationsList);
});


function getLocationsList(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#locationsList").append(html);
}


$(function(){
	$("#editLocation").click(function(){
		postServerData("/ws/viewmap/viewlocation/1/1/1",postEditLocation);
	});
});

function postEditLocation(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resEditLocation").append(html);
}


$(function(){
	$("#deleteLocation").click(function(){
		deleteServerData("/ws/viewmap/viewlocation/1/1/1",deleteLocationToUser);
	});
});

function deleteLocationToUser(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resDeleteLocation").append(html);
}