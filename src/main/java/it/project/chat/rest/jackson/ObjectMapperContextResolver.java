
package it.project.chat.rest.jackson;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it.project.chat.data.domainmodel.Contact;
import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.domainmodel.User;
import it.project.chat.rest.model.UserUpdate;


@Provider
public class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {

	private final ObjectMapper mapper;

	public ObjectMapperContextResolver() {
		this.mapper = createObjectMapper();
	}

	@Override
	public ObjectMapper getContext(Class<?> type) {
		return mapper;
	}

	private ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		SimpleModule customSerializer = new SimpleModule("CustomSerializersModule");
		
		customSerializer.addDeserializer(Message.class, new MessagesDeserializer());
		customSerializer.addSerializer(Message.class, new MessagesSerializer());
		
		customSerializer.addDeserializer(User.class, new UserDeserializer());
		customSerializer.addSerializer(User.class, new UserSerializer());

		customSerializer.addDeserializer(Contact.class, new ContactsDeserializer());

		mapper.registerModule(customSerializer);

		return mapper;
	}
}
