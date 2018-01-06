$('document').ready(function() {
});

//DELETE NOTES
function deleteNote(id) {
	console.log(id);
	alert(id);
	$.ajax({
		type : 'DELETE',
		url : "deleteNote/" + id,
	});

}

//EDIT NOTE
function editNote(id) {
	console.log(id);
	alert(id);
	$.ajax({
		type : 'POST',
		url : "editNote/" + id,
	});

}

//SIDE BAR TOGGLE
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
/*function archiveNote(id) {

 alert("AAAAA");

 console.log(id);
 alert(id);
 jQuery.ajax({
 type : 'POST',
 url : "user/archiveNote/" + id,
 dataType: 'json',
 success : function(jsondata) {  
 console.log(jsondata); 
 alert(jsondata['name']);
 },
 error : function() {
 console.log("Error");
 }
 });

 }*/

//ARCHIVE NOTES
function archiveNote(id) {
	console.log(id);
	alert(id);
	$.ajax({
		type : 'POST',
		url : "archiveNote/" + id,
	});

}

//TRASH NOTE
function trashNote(id) {

	alert("BBBB");

	console.log(id);
	alert(id);
	jQuery.ajax({
		type : 'PUT',
		url : "trashNote/" + id,
		dataType : 'json',
		success : function(jsondata) {
			console.log(jsondata);
			alert(jsondata['name']);
		},
		error : function() {
			console.log("Error");
		}
	});

}

//SOCIAL SHARE
function facebookShare(title, description) {
	console.log(title);
	console.log('inside fbAsyncInit');
	FB.init({
		appId : '1145672222233815',
		status : true,
		cookie : true,
		xfbml : true,
		version : 'v2.4'
	});

	FB.ui({
		method : 'share_open_graph',
		action_type : 'og.likes',
		action_properties : JSON.stringify({
			object : {
				'og:title' : title,
				'og:description' : description
			}
		})
	}, function(response) {
		if (response && !response.error_message) {
			alert('Posting completed.');
		} else {
			alert('Error while posting.');
		}
	});
};

//MAKE A COPY
function makeCopyNote(id) {

	alert("makeCopyNote");

	console.log(id);
	alert(id);
	jQuery.ajax({
		type : 'PUT',
		url : "user/makeCopyNote/" + id,
		dataType : 'json',
		success : function(jsondata) {
			console.log(jsondata);
			alert(jsondata['name']);
		},
		error : function() {
			console.log("Error");
		}
	});

}

//IMAGE UPLOAD
 var imageSrc = "";

$scope.$on("fileProgress", function(e, progress) {
	$scope.progress = progress.loaded / progress.total;
});

$scope.openImageUploader = function(type) {
	$scope.type = type;
	$('#imageuploader').trigger('click');
}

$scope.changeProfile = function(user) {

	var a = homeService.changeProfile(user);

	a.then(function(response) {

	}, function(response) {

	});
}

$scope.removeImage = function() {
	$scope.AddNoteBox = false;
	$scope.addimg = undefined;
}

$scope.type = {};
$scope.type.noteImage = '';

$scope.$watch('imageSrc', function(newimg, oldimg) {
	if ($scope.imageSrc != '') {
		if ($scope.type === 'input') {
			$scope.addimg = $scope.imageSrc;
		} else if ($scope.type === 'user') {
			$scope.User.profileImage = $scope.imageSrc;
			$scope.changeProfile($scope.User);
		} else {
			$scope.type.noteImage = $scope.imageSrc;
			$scope.updateNote($scope.type);
		}
	}
});