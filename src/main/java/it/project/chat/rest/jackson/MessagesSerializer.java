package it.project.chat.rest.jackson;

import java.io.IOException;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.UtilityDateandTime;
import it.project.chat.framework.data.BusinessException;

public class MessagesSerializer extends JsonSerializer<Message> {

	private UtilityDateandTime utilityDateandTime = new UtilityDateandTime();
	
	@Override
	public void serialize(Message value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// TODO Auto-generated method stub
		gen.writeStartObject();
		gen.writeObjectField("text", value.getText());
		gen.writeObjectField("hasbeenread", value.isHasbeenread());
		gen.writeObjectField("id", value.getId());
		
		try {
			String d = utilityDateandTime.convertDateForUser(value.getData());
			gen.writeObjectField("date", d);
			utilityDateandTime.checkTheTimeFormat(value.getTime());
			gen.writeObjectField("time", value.getTime());
		} catch (ParseException | BusinessException e) {
			// TODO Auto-generated catch block
			throw new IOException("date or time are not valide");
		}
		
		
		
		try {
			gen.writeObjectFieldStart("userSender");
			User userSender = value.getUserSender().get();
			gen.writeObjectField("id", userSender.getId());
			gen.writeObjectField("nickname", userSender.getNickname());
			gen.writeObjectField("email", userSender.getEmail());
			gen.writeObjectField("picture", userSender.getPicture());
			gen.writeEndObject();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new IOException("system error ");
		}
		
		try {
			gen.writeObjectFieldStart("userReceiver");
			User userReceiver = value.getUserReceiver().get();
			gen.writeObjectField("id", userReceiver.getId());
			gen.writeObjectField("nickname", userReceiver.getNickname());
			gen.writeObjectField("email", userReceiver.getEmail());
			gen.writeObjectField("picture", userReceiver.getPicture());
			gen.writeEndObject();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new IOException("system error ");
		}
		
		
		
		
		gen.writeEndObject();
	}

}
