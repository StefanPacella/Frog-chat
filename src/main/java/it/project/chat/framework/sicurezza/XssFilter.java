package it.project.chat.framework.sicurezza;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class XssFilter {
	
	public XssFilter() {
		// TODO Auto-generated constructor stub
	}
	
	public String sanitize(String input) {
		PolicyFactory policyFactory = new HtmlPolicyBuilder().allowStandardUrlProtocols().allowStyling()
				.allowCommonBlockElements().allowCommonInlineFormattingElements().toFactory();
		return policyFactory.sanitize(input);
	}

}
