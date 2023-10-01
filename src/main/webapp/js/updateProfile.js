function updateUser() {
	var id = document.getElementById("iduser").getAttribute("data-value");

	$.ajax({
		type: "PUT",
		url: getBaseUrl() + "rest/users/" + id,
		data: {
			oldpassword: document.getElementById("passwordidold").value,
			newpassword: document.getElementById("passwordidnew").value,
			nickname: document.getElementById("nicknameid").value
		},
		success: function() {
			window.location.href = getBaseUrl() + "chat";
		},
		error: function(result, status) {
			///	nokPopup("Error");
		}
	});
}