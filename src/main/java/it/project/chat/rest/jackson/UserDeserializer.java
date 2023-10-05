package it.project.chat.rest.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import it.project.chat.data.domainmodel.User;
import it.project.chat.data.proxy.UserProxy;
import it.project.chat.framework.sicurezza.XssFilter;

public class UserDeserializer extends JsonDeserializer<User> {

	private XssFilter xssFilter = new XssFilter();
	
	@Override
	public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		User r = new UserProxy();

		JsonNode node = p.getCodec().readTree(p);

		if (node.has("id")) {
			r.setId(node.get("id").asInt());
		}
		if (node.has("nickname")) {
			String nickname = xssFilter.sanitize(node.get("nickname").asText());
			r.setNickname(nickname);
		}
		if (node.has("email")) {
			String email = xssFilter.sanitize(node.get("email").asText());
			r.setEmail(email);
		}
		if (node.has("password")) {
			r.setPassword(node.get("password").asText());
		}
		if (node.has("picture")) {
			r.setPicture(node.get("picture").asText());
		} else {
			r.setPicture(PictureUtility.DEFAULTPICTUREPROFILE);
		}

		if (node.has("version")) {
			r.setVersione(node.get("version").asInt());
		} else {
			r.setVersione(0);
		}

		return r;
	}

}
