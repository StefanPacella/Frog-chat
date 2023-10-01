function sendPicture() {
	var id = document.getElementById("iduser").getAttribute("data-value");
	var formData = new FormData(inputPicture);
	$.ajax({
		type: "PUT",
		url: getBaseUrl() + "rest/users/" + id + "/picture",
		data: formData,
		cache: false,
		contentType: false,
		processData: false,
		success: function(result) {
			//	okPopup("Operazione conclusa");
			//	addselect.ok();
			window.location.href = getBaseUrl() + "chat";
		},
		error: function(result, status) {
			///	nokPopup("Errore nella connesione");
			//// devi mettere un errore a schermo se l'utente non si è loggato per
		}
	});
	//	okPopup("Il file è stato invitato al server");
}