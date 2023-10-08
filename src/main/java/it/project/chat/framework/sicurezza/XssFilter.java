package it.project.chat.framework.sicurezza;

import org.owasp.encoder.Encode;

public class XssFilter {
	
	public XssFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public String sanitize(String input) {
		input = Encode.forHtmlAttribute(input);
		return input;
	}

}
