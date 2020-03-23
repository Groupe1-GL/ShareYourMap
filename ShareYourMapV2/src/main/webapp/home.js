function getServerData(url, success){
    $.ajax({
    	type: 'GET',
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
	getServerData("/ws/users",getUsers)
});


function getUsers(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#usersList").append(html);
}


$(function(){
	$("#addUser").click(function(){
		putServerData("/ws/users",getUsers)
	});
});

