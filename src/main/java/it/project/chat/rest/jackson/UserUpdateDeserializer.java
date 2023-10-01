package it.project.chat.rest.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import it.project.chat.rest.model.UserUpdate;

public class UserUpdateDeserializer extends JsonDeserializer<UserUpdate> {

	@Override
	public UserUpdate deserialize(JsonParser p, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		UserUpdate u = new UserUpdate();

		JsonNode node = p.getCodec().readTree(p);

		if (node.has("newpassword")) {
			u.setNewPassword(node.get("newpassword").asText());
		}
		if (node.has("oldpassword")) {
			u.setOldPassword(node.get("oldpassword").asText());
		}
		if (node.has("nickname")) {
			u.setNickName(node.get("nickname").asText());
		}

		return u;
	}

}
