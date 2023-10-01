package it.project.chat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.TemplateException;
import it.project.chat.controller.component.FactoryComponent;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;

public class LoginController extends HttpServlet {

	private FactoryComponent factoryComponent;
	private static final long serialVersionUID = 1L;
	private Map<Object, Object> map = new HashMap<>();

	//// http://localhost:8080/Lamiaprimaprova/login
	public LoginController() {
		// TODO Auto-generated constructor stub
		factoryComponent = FactoryComponent.instance;
	}

	private void process(HttpServletResponse rr, HttpServletRequest rq) {
		ServletContext servletContext = this.getServletContext();
		try {
			map.put("maincontent", factoryComponent.getLoginComponent(servletContext));
			factoryComponent.getFrameComponent(servletContext, map, rq, rr);
		} catch (TemplateException | IOException e) {
			
		}
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rr) {
		process(rr, rq);
	}

	@Override
	public void doPost(HttpServletRequest rq, HttpServletResponse rr) {
		String nickname = rq.getParameter("email");
		String password = rq.getParameter("password");
		try {
			Cookie cookie = AuthenticationMaster.INSTACE.loginCookie(nickname, password);
			rr.addCookie(cookie);
			try {
				rr.sendRedirect(BaseUrl.URLCHAT);
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		} catch (AuthenticationException e) {
			try {
				rr.sendRedirect(BaseUrl.URLLOGIN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
	}

}
