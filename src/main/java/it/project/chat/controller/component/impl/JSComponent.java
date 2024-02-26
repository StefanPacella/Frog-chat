package it.project.chat.controller.component.impl;

import java.util.Optional;

import it.project.chat.framework.template.OutputStreamCatch;
import it.project.chat.controller.BaseUrl;

public class JSComponent extends OutputStreamCatch {

	private Optional<String> html = Optional.empty();
	private final static String URLREST = BaseUrl.URLCHAT + "rest/";
	private String restElemento = "";

	public JSComponent(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.html = Optional.of(catturaOutputStream);
	}

	public Optional<String> getHtml() {
		return html;
	}

	public String getRestElemento() {
		return restElemento;
	}

	public void setRestElemento(String restElemento) {
		this.restElemento = restElemento;
	}
	
	public String getIndirizzoRest() {
		return "\"" + URLREST + restElemento + "\"";
	}

}
