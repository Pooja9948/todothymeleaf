$('document').ready(function() {
});

function deleteNote(id) {
	console.log(id);
	alert(id);
	$.ajax({
		type : 'DELETE',
		url : "user/deleteNote/" + id,
	});

}
function editNote(id) {
	console.log(id);
	alert(id);
	$.ajax({
		type : 'POST',
		url : "user/editNote/" + id,
	});

}
function toggleNav() {
	var sideNav = document.getElementById("sideNavContent").style.width;
	if (sideNav == "0px") {
		openNav();
	} else {
		closeNav();
	}
}

function openNav() {

	if (screen.width > 1000) {
		document.getElementById("sideNavContent").style.width = "250px";
		document.getElementById("noteContainer").style.marginLeft = "300px";
	} else
		document.getElementById("sideNavContent").style.width = "250px";
}

function closeNav() {
	document.getElementById("sideNavContent").style.width = "0px";
	document.getElementById("noteContainer").style.marginLeft = "0px";
}
function archiveNote(id) {
	
	alert("AAAAA");
	
	console.log(id);
	alert(id);
	jQuery.ajax({
		type : 'PUT',
		url : "user/archiveNote/"+ id,
		dataType: 'json',
		success : function(jsondata) {  
            console.log(jsondata); 
            alert(jsondata['name']);
        },
        error : function() {
            console.log("Error");
        }
	});

}
function trashNote(id) {
	
	alert("BBBB");
	
	console.log(id);
	alert(id);
	jQuery.ajax({
		type : 'PUT',
		url : "user/trashNote/"+ id,
		dataType: 'json',
		success : function(jsondata) {  
            console.log(jsondata); 
            alert(jsondata['name']);
        },
        error : function() {
            console.log("Error");
        }
	});

}