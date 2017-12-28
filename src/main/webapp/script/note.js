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
