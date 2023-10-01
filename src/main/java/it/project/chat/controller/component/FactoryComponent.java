package it.project.chat.controller.component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.TemplateException;
import it.project.chat.controller.BaseUrl;
import it.project.chat.controller.component.impl.ChatPageComponent;
import it.project.chat.controller.component.impl.ComponentGenericoHtml;
import it.project.chat.controller.component.impl.JSComponent;
import it.project.chat.controller.component.impl.LoginComponent;
import it.project.chat.controller.component.impl.MenuProfileComponent;
import it.project.chat.controller.component.impl.RegistrazioneComponent;
import it.project.chat.framework.template.TemplateResult;

public class FactoryComponent {

	public static FactoryComponent instance = new FactoryComponent();

	private final String dir = "html";

	private FactoryComponent() {

	}

	public String getLoginComponent(ServletContext servletContext) throws TemplateException, IOException {
		try (LoginComponent o = new LoginComponent(dir)) {
			Map<Object, Object> m = new HashMap<Object, Object>();
			m.put("urllogin_signin", BaseUrl.URLSIGNIN);
			o.init("login.html", servletContext, m);
			return o.getLoginOutputStream();
		}
	}

	public void getFrameComponent(ServletContext servletContext, Map<Object, Object> dati, HttpServletRequest rq,
			HttpServletResponse rr) throws TemplateException, IOException {
		TemplateResult templateResult = new TemplateResult(dir, servletContext);
		templateResult.init();
		templateResult.setupServletResponseTextHtml();
		dati.put("urlframechat", BaseUrl.URLCHAT);
		dati.put("urlframebase", BaseUrl.URLBASE);
		dati.put("menuprofile", this.getMenuProfile(servletContext, rq));
		dati.forEach((x, y) -> templateResult.aggiungiDato(x, y));
		templateResult.make(rr.getWriter(), "frame.html");
	}

	public String getMenuProfile(ServletContext servletContext, HttpServletRequest rq) throws IOException {
		try (MenuProfileComponent b = new MenuProfileComponent(dir)) {
			b.creaIlFileHtml(servletContext, rq);
			return b.getHtml();
		}
	}

	public String getRegistrazioneComponent(ServletContext servletContext) throws TemplateException, IOException {
		try (RegistrazioneComponent f = new RegistrazioneComponent(dir)) {
			f.init("registrazione.html", servletContext);
			return f.getRegistrazioneOutputStream();
		}
	}

	public String getComponentGenericoHtml(ServletContext servletContext, String nome)
			throws TemplateException, IOException {
		try (ComponentGenericoHtml f = new ComponentGenericoHtml(dir)) {
			f.init(nome, servletContext);
			return f.getHtml();
		}
	}

	public String getComponentGenericoHtml(ServletContext servletContext, String nome, Map<Object, Object> dati)
			throws TemplateException, IOException {
		try (ComponentGenericoHtml f = new ComponentGenericoHtml(dir)) {
			f.init(nome, servletContext, dati);
			return f.getHtml();
		}
	}

	public String getComponentChatPage(ServletContext servletContext) throws TemplateException, IOException {
		try (ChatPageComponent f = new ChatPageComponent(dir)) {
			f.makeFileHtml(servletContext);
			return f.getHtml();
		}
	}

	public String getGetJavaScript(ServletContext servletContext, String filejs, String rest)
			throws TemplateException, IOException {
		try (JSComponent f = new JSComponent(dir)) {
			f.setRestElemento(rest);
			Map<String, String> map = new HashMap<>();
			map.put("url", f.getIndirizzoRest());
			f.initJS(filejs, servletContext, map);
			return f.getHtml().get();
		}
	}

}
