package it.project.chat.controller.component.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import freemarker.template.TemplateException;
import it.project.chat.controller.BaseUrl;
import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;
import it.project.chat.framework.template.OutputStreamCatch;

public class MenuProfileComponent extends OutputStreamCatch {

	private String html = "";
	private Map<Object, Object> map = new HashMap<>();

	public MenuProfileComponent(String dir) {
		super(dir);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setOutput(String catturaOutputStream) {
		// TODO Auto-generated method stub
		this.html = catturaOutputStream;
	}

	public String getHtml() {
		return html;
	}

	public void creaIlFileHtml(ServletContext servletContext, HttpServletRequest rq) {
		try {
			String email = AuthenticationMaster.INSTACE.validateRequest(rq);
			Optional<User> u = UserDao.INSTACE.querySelectConCondizioneSullaChiave(new String[] { email });
			map.put("nickname", u.get().getNickname());
			map.put("picture", u.get().getPicture());
			map.put("id", u.get().getId());
			map.put("menuprofile_chat", BaseUrl.URLCHAT);
			map.put("menuprofile_edit", BaseUrl.URLEDIT);
			map.put("menuprofile_editpictureprofile", BaseUrl.URLEDITPICTURE);
			map.put("menuprofile_logout", BaseUrl.URLLOGOUT);

			this.init("menuprofile.html", servletContext, map);
		} catch (TemplateException | IOException | BusinessException | NoSuchElementException e) {
			// TODO Auto-generated catch block
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
		}
	}

}
