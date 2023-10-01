package it.project.chat.controller.component.impl;

import it.project.chat.framework.template.OutputStreamCatch;

public class LoginComponent extends OutputStreamCatch {
	
	private String loginOutputStream = "";

	public LoginComponent(String path ) {
		// TODO Auto-generated constructor stub
		super(path);
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		loginOutputStream = catturaOutputStream;
	}

	public String getLoginOutputStream() {
		return loginOutputStream;
	}
}
