package it.project.chat.controller.component.impl;

import it.project.chat.framework.template.OutputStreamCatch;

public class RegistrazioneComponent extends OutputStreamCatch {

	private String registrazioneOutputStream = "";

	public RegistrazioneComponent(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.registrazioneOutputStream = catturaOutputStream;
	}

	public String getRegistrazioneOutputStream() {
		return registrazioneOutputStream;
	}

}
