var ok = null;
function okPopup(messaggio) {
	document.getElementById("messaggiopopup").innerHTML = messaggio;
	document.getElementById('mostrapop').click();
	ok = true;
}

function nokPopup(messaggio) {
	document.getElementById("messaggiopopup").innerHTML = messaggio;
	document.getElementById('mostrapop').click();
}


function ricaricaLaPagina() {
	if (ok != null) {
		location.reload();
	}
}