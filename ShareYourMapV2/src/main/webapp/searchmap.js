function getServerDataTxt(url, success){
    $.ajax({
        dataType: "text",
        url: url
    }).done(success);
}

function getServerData(url, success){
    $.ajax({
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
	getServerData("/ws/searchmap",getMapsList);
});

function getMapsList(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#mapsList").append(html); 
}


$(function(){
	getServerData("/ws/searchmap/1",getFavInMap);
});


function getFavInMap(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#favsList").append(html);
}


$(function(){
	$("#addMap").click(function(){
		putServerData("/ws/searchmap",putAddMap);
	});
});


function putAddMap(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#resAddMap").append(html);
}
