package it.project.chat.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LogoutController() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * weak logout - don't use this
	 * build your own
	 */
	
	@Override
	public void doGet(HttpServletRequest rq, HttpServletResponse rr) {
		Cookie cookie = new Cookie("token", "");
		rr.addCookie(cookie);
		try {
			rr.sendRedirect(BaseUrl.URLLOGIN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}

}