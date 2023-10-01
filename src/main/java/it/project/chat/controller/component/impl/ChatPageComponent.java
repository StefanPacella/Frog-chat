package it.project.chat.controller.component.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import freemarker.template.TemplateException;
import it.project.chat.framework.template.OutputStreamCatch;

public class ChatPageComponent extends OutputStreamCatch {

	private String html = "";
	private Map<Object, Object> map = new HashMap<>();

	public ChatPageComponent(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		html = catturaOutputStream;
	}
	
	public void setListContacts(String setListContact) {
		///map.put("listConctact", setListContact);
	}
	
	public void makeFileHtml(ServletContext servletContext) {
		try {
			this.init("chat.html", servletContext, map);
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
		}
	}

	public String getHtml() {
		return html;
	}
}
