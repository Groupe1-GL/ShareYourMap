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


function callDone(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#result").append(html);
}


function callDone3(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#result2").append(html);
}
function callDone2(result){
	var templateExample = _.template($('#templateExample').html());

	var html = templateExample({
		"attribute":JSON.stringify(result)
	});

	$("#result2").append(html);
}

$(function(){
	$("#users").click(function(){
		getServerData("/ws/users",callDone);
	});
});


$(function(){
	$("#addUser").click(function(){
		putServerData("/ws/users",callDone);
	});
});

$(function(){
	$("#maps").click(function(){
		getServerData("/ws/maps",callDone2);
	});
});

$(function(){
	$("#locations").click(function(){
		getServerData('/ws/maps/'+id,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		putServerData('/ws/maps/'+id,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		getServerData('/ws/viewmap/'+id,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		postServerData('/ws/viewmap/'+uid+'/'+mid,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		getServerData('/ws/viewmap/'+uid+'/'+mid,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		deleteServerData('/ws/viewmap/'+uid+'/'+mid,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		putServerData('/ws/viewmap/'+uid+'/'+mid,callDone3);
	});
});

$(function(){
	$("#locations").click(function(){
		getServerData('/ws/viewlocation/'+uid+'/'+mid+'/'+lid,callDone3);
	});
});
$(function(){
	$("#locations").click(function(){
		deleteServerData('/ws/viewlocation/'+uid+'/'+mid+'/'+lid,callDone3);
	});
});