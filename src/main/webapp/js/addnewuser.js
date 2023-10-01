function sendNewUser() {
	$.ajax({
		type: "POST",
		url: getBaseUrl() + "rest/users",
		data: JSON.stringify({
			nickname: document.getElementById("nicknameid").value,
			email: document.getElementById("emailid").value,
			password: document.getElementById("passwordid").value
		}),
		contentType: "application/json",
		success: function(result) {
			window.location.href = getBaseUrl() + "login";
		},
		error: function(result, status) {
			///nokPopup("Errore");
		}
	});
}