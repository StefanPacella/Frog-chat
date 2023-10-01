/**
 * 
 */
var list = document.getElementById("listmessages");

function addMyNewMessage(value) {
	list.appendChild(makeMyMessage(value));
}

function addOtherNewMessage(value) {
	list.appendChild(makeOtherMessage(value));
}


function addMyMessageBeforeOthers(value) {
	var firstC = list.firstElementChild;
	list.insertBefore(makeMyMessage(value), firstC);
}

function addOtherMessageBeforeOthers(value) {
	var firstC = list.firstElementChild;
	list.insertBefore(makeOtherMessage(value), firstC);
}

function makeMyMessage(value) {
	const userNumberElementTemplate = document.getElementById('templatemymmessage');
	const elementClone = userNumberElementTemplate.content.cloneNode(true)
	elementClone.getElementById('mypictureurl').src = value.userSender.picture;
	elementClone.getElementById('myname').innerHTML = value.userSender.nickname;
	elementClone.getElementById('mylasttime').innerHTML = value.date + " " + value.time;
	elementClone.getElementById('mytext').innerHTML = value.text;
	return elementClone;
}


function makeOtherMessage(value) {
	const userNumberElementTemplate = document.getElementById('templateothermessage');
	const elementClone = userNumberElementTemplate.content.cloneNode(true)
	elementClone.getElementById('otherpictureurl').src = value.userSender.picture;
	elementClone.getElementById('othername').innerHTML = value.userSender.nickname;
	elementClone.getElementById('otherlasttime').innerHTML = value.date + " " + value.time;
	elementClone.getElementById('othertext').innerHTML = value.text;
	return elementClone;
}

function cleanMessagesList() {
	const list = document.getElementById("listmessages");
	while (list.hasChildNodes()) {
		if (list.firstChild.value != "template")
			list.removeChild(list.firstChild);
	}
}


