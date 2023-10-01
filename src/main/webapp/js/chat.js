function newMessage() {
	var today = new Date();
	const date = today.getDate() + '/' + (today.getMonth() + 1) + '/' + today.getFullYear();
	const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

	$.ajax({
		type: "POST",
		url: getBaseUrl() + "rest/messages",
		data: JSON.stringify({
			idusersender: document.getElementById("iduser").getAttribute("data-value"),
			iduserreceiver: contactSelect,
			text: document.getElementById("idTextArea").value,
			dataM: date,
			timeM: time,
			hasbeenread: false
		}),
		contentType: "application/json",
		success: function(result) {
			var e = new Object();
			e.userSender = new Object();
			e.userSender.picture = document.getElementById("pictureUser").src;
			e.userSender.nickname = document.getElementById("nicknameUser").innerHTML;
			e.date = date;
			e.time = time;
			e.text = document.getElementById("idTextArea").value;
			addMyNewMessage(e);
			document.getElementById("idTextArea").value = "";
			scrolloToButtom();
		},
		error: function(result, status) {

		}
	});
}


var timeCurrentOldestMessage;
var dateCurrentOldestMessage;
var threadPollingMessage;

function getTheLastTenPosts() {
	if (threadPollingMessage != null) {
		threadPollingMessage.terminate();
	}
	threadPollingMessage = makeTheLongPollingMessageRun();
	const myId = document.getElementById("iduser").getAttribute("data-value");
	$.ajax({
		type: "GET",
		url: getBaseUrl() + "rest/messages/getTheLastTenMessages?" + "user=" + document.getElementById("iduser").getAttribute("data-value") + "&contact_user=" + contactSelect,
		contentType: "application/json",
		success: function(result) {
			document.getElementById("idTextArea").value = "";
			cleanMessagesList();
			result = sortFromOldestToNewest(result);
			var i = 0;
			for (const node of result) {
				if (i == 0) {
					timeCurrentOldestMessage = node.time;
					dateCurrentOldestMessage = node.date;
				}
				i++;
				if (node.userSender.id == myId) {
					addMyNewMessage(node);
				}
				else {
					addOtherNewMessage(node);
				}
			}
			scrolloToButtom();
		},
		error: function(result, status) {

		}
	});
}

function makeTheLongPollingMessageRun() {
	var m = new Worker("js/longpolling.js");
	const myId = document.getElementById("iduser").getAttribute("data-value");
	var url = getBaseUrl() + "rest/messages/getTheNewOnes?" + "user=" + myId + "&contact_user=" + contactSelect;
	var owner = 'chat';
	var send = { url, owner };
	m.postMessage(send);
	m.addEventListener("message", function(event) {
		if (event.data.owner == 'chat') {
			var result = event.data.data;
			result = sortFromOldestToNewest(result);
			for (const node of result) {
				if (node.userSender.id == myId) {
					addMyNewMessage(node);
				}
				else {
					addOtherNewMessage(node);
				}
			}
			scrolloToButtom();
		}
	});
	return m;
}


function scrolloToButtom() {
	const e = document.getElementById("scrollchat");
	e.scrollTo(0, e.scrollHeight);
}


function getMessagesFromTimeX() {
	const myId = document.getElementById("iduser").getAttribute("data-value");
	var url = getBaseUrl() + "rest/messages/getTheLastTenMessagesBeforeTimeX?" + "user=" + document.getElementById("iduser").getAttribute("data-value") + "&contact_user=" + contactSelect;
	url = url + "&date=" + dateCurrentOldestMessage + "&time=" + timeCurrentOldestMessage;
	$.ajax({
		type: "GET",
		url: url,
		contentType: "application/json",
		success: function(result) {
			result = sortFromNewestToOldest(result);
			for (const node of result) {
				timeCurrentOldestMessage = node.time;
				dateCurrentOldestMessage = node.date;
				if (node.userSender.id == myId) {
					addMyMessageBeforeOthers(node);
				}
				else {
					addOtherMessageBeforeOthers(node);
				}
			}
		},
		error: function(result, status) {

		}
	});
}


const scrollchat = document.getElementById("scrollchat");
scrollchat.addEventListener("scroll", () => {
	if (scrollchat.scrollTop == 0) {
		getMessagesFromTimeX();
	}
});


function sortFromOldestToNewest(array) {
	var length = array.length;
	for (var i = 0; i < length; i++) {
		var index = i;
		for (var j = i; j < length; j++) {
			if (getTimeOfMessage(array[index]) > getTimeOfMessage(array[j])) {
				index = j;
			}
		}
		swap(index, i, array);
	}
	return array;
}

function sortFromNewestToOldest(array) {
	var length = array.length;
	for (var i = 0; i < length; i++) {
		var index = i;
		for (var j = i; j < length; j++) {
			if (getTimeOfMessage(array[index]) < getTimeOfMessage(array[j])) {
				index = j;
			}
		}
		swap(index, i, array);
	}
	return array;
}

function swap(index, i, array) {
	var temp = array[index];
	array[index] = array[i];
	array[i] = temp;
}

function getTimeOfMessage(message) {
	var myDate = message.date;
	myDate = myDate.split("/");
	var d = new Date(myDate[2], myDate[1] - 1, myDate[0]);
	let [hours, minutes, seconds] = message.time.split(':');
	d.setHours(+hours);
	d.setMinutes(minutes);
	d.setSeconds(seconds);
	return d.getTime() / 1000;
}
