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
import it.project.chat.data.dao.UserDao;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;

public class EditController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FactoryComponent factoryComponent;
	private Map<Object, Object> map = new HashMap<>();

	public EditController() {
		// TODO Auto-generated constructor stub
		factoryComponent = FactoryComponent.instance;
	}

	private void process(HttpServletResponse rr, HttpServletRequest rq) throws IOException {
		ServletContext servletContext = this.getServletContext();
		try {
			String email = AuthenticationMaster.INSTACE.validateRequest(rq);
			String u = UserDao.INSTACE.querySelectConCondizioneSullaChiave(new String[] { email }).get().getNickname();
			map.put("nicknameedit", u);
			String html = factoryComponent.getComponentGenericoHtml(servletContext, "edit.html", map);
		
			map = new HashMap<>();
			map.put("maincontent", html);
			factoryComponent.getFrameComponent(servletContext, map, rq, rr);
		} catch (TemplateException | IOException e) {

		} catch (AuthenticationException | BusinessException e) {
			// TODO Auto-generated catch block
			rr.sendRedirect(BaseUrl.URLLOGIN);
		}
	}

	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rr) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(rq);
			process(rr, rq);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			try {
				rr.sendRedirect(BaseUrl.URLLOGIN);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
			}
		}
	}
}
