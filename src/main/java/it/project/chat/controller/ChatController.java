package it.project.chat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.TemplateException;
import it.project.chat.controller.component.FactoryComponent;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;

public class ChatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactoryComponent factoryComponent;
	private Map<Object, Object> map = new HashMap<>();

	/////http://localhost:8080/chat/chat
	public ChatController() {
		// TODO Auto-generated constructor stub
		factoryComponent = FactoryComponent.instance;
	}

	private void process(HttpServletResponse rr, HttpServletRequest rq) {
		ServletContext servletContext = this.getServletContext();
		try {
			map.put("maincontent", factoryComponent.getComponentChatPage(servletContext));
			factoryComponent.getFrameComponent(servletContext, map, rq, rr);
		} catch (TemplateException | IOException e) {
			// TODO Auto-generated catch block
		}
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rr) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(rq);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			try {
				rr.sendRedirect(BaseUrl.URLLOGIN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
		process(rr, rq);
	}
}
