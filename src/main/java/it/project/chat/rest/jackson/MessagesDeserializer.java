package it.project.chat.rest.jackson;

import java.io.IOException;
import java.text.ParseException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.proxy.MessageProxy;
import it.project.chat.framework.UtilityDateandTime;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.XssFilter;

public class MessagesDeserializer extends JsonDeserializer<Message> {

	private UtilityDateandTime utilityDateandTime = new UtilityDateandTime();
	private XssFilter xssFilter = new XssFilter();

	@Override
	public Message deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		Message r = new MessageProxy();

		JsonNode node = p.getCodec().readTree(p);

		if (node.has("id")) {
			r.setId(node.get("id").asInt());
		}

		if (node.has("dataM")) {
			r.setData(node.get("dataM").asText());
			String date = "";
			try {
				date = utilityDateandTime.convertDateForDbms(node.get("dataM").asText());
				r.setData(date);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new IOException("date isn't valide");
			}

		}

		if (node.has("timeM")) {
			try {
				utilityDateandTime.checkTheTimeFormat(node.get("timeM").asText());
				r.setTime(node.get("timeM").asText());
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				throw new IOException("time isn't valide");
			}
		}

		if (node.has("hasbeenread")) {
			r.setHasbeenread(node.get("hasbeenread").asBoolean());
		}
		if (node.has("iduserreceiver")) {
			r.setIduserreceiver(node.get("iduserreceiver").asInt());
		}
		if (node.has("idusersender")) {
			r.setIdusersender(node.get("idusersender").asInt());
		}
		if (node.has("text")) {
			String text = xssFilter.sanitize(node.get("text").asText());
			r.setText(text);
		}
		if (node.has("version")) {
			r.setVersione(node.get("version").asInt());
		} else {
			r.setVersione(0);
		}

		return r;
	}

}
