var urlx;
var owner;
const second30 = 30000;
function polling() {
	const t = this;
	var url = urlx ;
	var xhr = new XMLHttpRequest();
	xhr.open('GET', url, true);
	xhr.responseType = 'json';
	xhr.onload = function() {
		if (xhr.response != null && xhr.response.length > 0) {
			var o = new Object();
			o.data = xhr.response;
			o.owner = t.owner;
			t.postMessage(o);
		}
	};
	xhr.send();
	setTimeout(polling, second30);
}

self.addEventListener("message", function(event) {
	this.urlx = event.data.url;
	this.owner = event.data.owner;
	polling();
});