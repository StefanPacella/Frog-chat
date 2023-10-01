package it.project.chat.controller.component.impl;

import it.project.chat.framework.template.OutputStreamCatch;

public class ComponentGenericoHtml extends OutputStreamCatch {

	private String html = "";

	public ComponentGenericoHtml(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.html = catturaOutputStream;
	}

	public String getHtml() {
		return html;
	}

}
