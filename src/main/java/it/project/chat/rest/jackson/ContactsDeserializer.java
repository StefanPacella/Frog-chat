package it.project.chat.rest.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import it.project.chat.data.domainmodel.Contact;
import it.project.chat.data.proxy.ContactProxy;

public class ContactsDeserializer extends JsonDeserializer<Contact> {

	@Override
	public Contact deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		Contact c = new ContactProxy();

		JsonNode node = p.getCodec().readTree(p);

		if (node.has("id_user")) {
			c.setIdUser(node.get("id_user").asInt());
		}

		if (node.has("id_user_contact")) {
			c.setIdUserContact(node.get("id_user_contact").asInt());
		}
		return c;
	}

}
