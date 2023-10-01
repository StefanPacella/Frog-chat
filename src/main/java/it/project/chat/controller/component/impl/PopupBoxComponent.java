package it.project.chat.controller.component.impl;

import it.project.chat.framework.template.OutputStreamCatch;

public class PopupBoxComponent extends OutputStreamCatch {
	
	private String popupboxOutputStream = "";

	public PopupBoxComponent(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.popupboxOutputStream = catturaOutputStream;
	}

	public String getPopupboxOutputStream() {
		return popupboxOutputStream;
	}

}
