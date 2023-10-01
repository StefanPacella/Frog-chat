package it.project.chat.rest.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import it.project.chat.data.domainmodel.User;

public class UserSerializer extends JsonSerializer<User> {

	@Override
	public void serialize(User value, JsonGenerator jgen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		jgen.writeStartObject();
		jgen.writeObjectField("id", value.getId());
		jgen.writeObjectField("nickname", value.getNickname());
		jgen.writeObjectField("email", value.getEmail());
		jgen.writeObjectField("picture", value.getPicture());
		jgen.writeObjectField("version", value.getVersione());
		jgen.writeEndObject();
	}

}
