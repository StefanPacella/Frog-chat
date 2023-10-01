function okToSearch() {
	var s = document.getElementById("textareaSearch").value;
	$.ajax({
		type: "GET",
		url: getBaseUrl() + "rest/users/search/" + s,
		contentType: "application/json",
		success: function(list) {
			cleanConctactList();
			if (list != undefined) {
				list.forEach(x => addNewConctactinTheList(x, false));
			}
		},
		error: function(result, status) {
			///	nokPopup("Errore nella connesione");
		}
	});
}

var badge;
const eventCleanColorConctactList = new Event("eventCleanColorConctactList");
const eventColorConctactListNewMessage = new Event("eventColorConctactListNewMessage");
var contactSelect = 0;
var listConctats = new Array();
var listConctatsNewMessage = new Array();

function addNewConctactinTheList(value, isFavorite) {
	const list = document.getElementById("listcontactul");
	const userNumberElementTemplate = document.getElementById('listcontacttemplate');
	const elementClone = userNumberElementTemplate.content.cloneNode(true);
	elementClone.querySelector('img').src = value["picture"];
	elementClone.getElementById('contactname').innerHTML = value["nickname"];
	elementClone.getElementById('contacttext').innerHTML = value["email"];
	const id_user_Contact = value["id"];
	elementClone.getElementById('buttonIdAddToFavourites').addEventListener('click', () => {
		addNewFavorite(id_user_Contact);
	});
	setColorOfCard(elementClone);
	badge = makeBadge(elementClone);

	setAttribute(elementClone, id_user_Contact);

	if (isFavorite) {
		elementClone.getElementById('iconFavoriteid').style.display = "none";
	}

	list.appendChild(elementClone);
}

function setAttribute(elementClone, iduser) {
	var badgeRefence = elementClone.getElementById('templateBadge');
	badgeRefence.style.display = "none";
	var card = elementClone.getElementById('id-list-group-item')
	card.idUserConctact = iduser;
	card.badge = badgeRefence;
}


function setColorOfCard(elementClone) {
	var card = elementClone.getElementById('id-list-group-item');

	listConctats.push(card);

	card.addEventListener('click', () => {
		card.classList.remove("list-group-item-notselect");
		card.classList.remove("list-group-item-newmessage");
		card.classList.add("list-group-item-select");
		contactSelect = card.idUserConctact;
		card.badge.style.display = "none";
		dispachEvent(eventCleanColorConctactList);
		getTheLastTenPosts();
	});
	card.addEventListener('eventCleanColorConctactList', () => {
		if (card.idUserConctact != contactSelect) {
			card.classList.remove("list-group-item-select");
			card.classList.remove("list-group-item-newmessage");
			card.classList.add("list-group-item-notselect");
		}
	});
	card.addEventListener('eventColorConctactListNewMessage', () => {
		for (const node of listConctatsNewMessage) {
			if (node.id == card.idUserConctact) {
				card.badge.style.display = "block";
			}
		}
	});
}


function makeBadge(elementClone) {
	var badge = elementClone.getElementById("templateBadge");
	var c = badge.cloneNode(true);
	return c;
}

makeTheLongPollingContactRun();

function makeTheLongPollingContactRun() {
	var m = new Worker("js/longpolling.js");
	const myId = document.getElementById("iduser").getAttribute("data-value");
	var url = getBaseUrl() + "rest/users/" + myId + "/getListUsersWhoWroteToTheUser";
	var owner = 'contat';
	var send = { url, owner };
	m.postMessage(send);
	m.addEventListener("message", function(event) {
		console.log(event.data.owner);
		if (event.data.owner == 'contat') {
			listConctatsNewMessage = event.data.data;
			dispachEvent(eventColorConctactListNewMessage);
		}
	});
	return m;
}

function cleanConctactList() {
	const list = document.getElementById("listcontactul");
	while (list.hasChildNodes()) {
		if (list.firstChild.value != "template")
			list.removeChild(list.firstChild);
	}
}

function dispachEvent(eventCleanColorConctactList) {
	for (const node of listConctats) {
		if (node.value != "template") {
			node.dispatchEvent(eventCleanColorConctactList);
		}
	}
}


function loadTheFavoriteContact() {
	if (document.getElementById("iduser") != null) {
		var id = document.getElementById("iduser").getAttribute("data-value");
		$.ajax({
			type: "GET",
			url: getBaseUrl() + "rest/users/" + id + "/listContact",
			contentType: "application/json",
			success: function(list) {
				cleanConctactList();
				if (list != undefined) {
					list.forEach(x => addNewConctactinTheList(x, true));
				}
			},
			error: function(result, status) {
				///	nokPopup("Errore nella connesione");
			}
		});
	}
}

loadTheFavoriteContact();


var input = document.getElementById("textareaSearch");

input.addEventListener("keypress", function(event) {
	if (event.key === "Enter") {
		okToSearch();
	}
});

input.addEventListener("input", function() {
	var textareaSearch = document.getElementById("textareaSearch").value;
	if(textareaSearch == ''){
		cleanConctactList();
		loadTheFavoriteContact();
	}
});


function addNewFavorite(idUserContact) {
	$.ajax({
		type: "POST",
		url: getBaseUrl() + "rest/contacts",
		data: JSON.stringify({
			id_user_contact: idUserContact,
			id_user: document.getElementById("iduser").getAttribute("data-value")
		}),
		contentType: "application/json",
		success: function(result) {
			//	window.location.href = getBaseUrl() + "login";
			cleanConctactList();
			loadTheFavoriteContact();
		},
		error: function(result, status) {
			///nokPopup("Errore");
		}
	});
}
