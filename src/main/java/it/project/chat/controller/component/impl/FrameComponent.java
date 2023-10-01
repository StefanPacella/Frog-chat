package it.project.chat.controller.component.impl;

import it.project.chat.framework.template.OutputStreamCatch;

public class FrameComponent extends OutputStreamCatch {

	private String frame = "";

	public FrameComponent(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.frame = catturaOutputStream;
	}

	public String getFrame() {
		return frame;
	}
	
}
