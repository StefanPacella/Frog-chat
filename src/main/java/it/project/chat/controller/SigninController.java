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

public class SigninController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private FactoryComponent factoryComponent;
	private Map<Object, Object> map = new HashMap<>();

	public SigninController() {
		// TODO Auto-generated constructor stub
		factoryComponent = FactoryComponent.instance;
	}

	private void process(HttpServletResponse rr, HttpServletRequest rq) {
		ServletContext servletContext = this.getServletContext();
		try {
			Map<Object, Object> x = new HashMap<>();
			x.put("sigin_login", BaseUrl.URLLOGIN);
			map.put("maincontent", factoryComponent.getComponentGenericoHtml(servletContext, "sigin.html", x));
			factoryComponent.getFrameComponent(servletContext, map, rq, rr);
		} catch (TemplateException | IOException e) {

		}
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rr) {
		process(rr, rq);
	}
}
