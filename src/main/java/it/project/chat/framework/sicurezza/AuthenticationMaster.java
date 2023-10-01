package it.project.chat.framework.sicurezza;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import javax.annotation.Priority;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;

@Provider
@Logged
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationMaster implements ContainerRequestFilter {

	private List<String> blacklist = new ArrayList<String>();

	private SecretKey jwtKey;

	public static AuthenticationMaster INSTACE = new AuthenticationMaster();

	public AuthenticationMaster() {
		// TODO Auto-generated constructor stub
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSha256");
			jwtKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException ex) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
		}
	}

	public Cookie loginCookie(String email, String password) throws AuthenticationException {
		User u = UserDao.INSTACE.login(email, password);
		String token = createToken(u);
		return new Cookie("token", token);
	}

	public String loginStringToken(String email, String password) throws AuthenticationException {
		User u = UserDao.INSTACE.login(email, password);
		return createToken(u);
	}

	public void logoutDeleteTokenfromCookie(HttpServletResponse rr) {
		Cookie cookie = new Cookie("token", "");
		rr.addCookie(cookie);
	}

	public void logout(HttpServletRequest rr) {
		Cookie[] list = rr.getCookies();
		Optional<Cookie> cookies = Stream.of(list).filter(x -> x.getName().equals("token")).findFirst();
		try {
			logout(cookies.get().getValue());
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
	}

	public void logout(String token) {
		/////////////
		/////// there are several ways to implement this method
		/////// it depends on your needs
		/////////////
	}

	public void valideteRequest(String email, String password) throws AuthenticationException {
		UserDao.INSTACE.login(email, password);
	}

	public String validateRequest(HttpServletRequest requestContext, Integer id) throws AuthenticationException {
		if (id == null) {
			throw new AuthenticationException("");
		}
		try {
			String email = validateRequest(requestContext);
			User u = UserDao.INSTACE.querySelectConCondizioneSullaChiave(new String[] { email }).get();
			if (!u.getId().equals(id)) {
				throw new AuthenticationException("");
			}
			return email;
		} catch (BusinessException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException("");
		}
	}

	public void validateRequest(HttpServletRequest requestContext, String email) throws AuthenticationException {
		if (!validateRequest(requestContext).equals(email))
			throw new AuthenticationException("");
	}

	public String validateRequest(HttpServletRequest requestContext) throws AuthenticationException {
		try {
			Cookie[] list = requestContext.getCookies();
			Optional<Cookie> cookies = Stream.of(list).filter(x -> x.getName().equals("token")).findFirst();
			return validateRequest(cookies.get().getValue());
		} catch (NoSuchElementException | NullPointerException e) {
			// TODO: handle exception
			throw new AuthenticationException("");
		}
	}

	public void validateRequest(ContainerRequestContext containerRequestContext) throws AuthenticationException {
		try {
			Map<String, javax.ws.rs.core.Cookie> list = containerRequestContext.getCookies();
			String token = list.get("token").getValue();
			validateRequest(token);
		} catch (NoSuchElementException | NullPointerException e) {
			// TODO: handle exception
			throw new AuthenticationException("");
		}
	}

	public String validateRequest(String token) throws AuthenticationException {
		try {
			Jws<Claims> jwsc = Jwts.parserBuilder().setSigningKey(INSTACE.getJwtKey()).build().parseClaimsJws(token);
			String email = jwsc.getBody().getSubject();
			if (email == null)
				throw new AuthenticationException("");
			else
				return email;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException("");
		}
	}

	private String createToken(User u) throws AuthenticationException {
		String token = Jwts.builder().setSubject(u.getEmail()).setIssuedAt(new Date()).addClaims(makeClaims(u))
				.setExpiration(
						Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()))
				.signWith(jwtKey).compact();
		return token;
	}

	private Claims makeClaims(User u) {
		Claims claims = Jwts.claims().setSubject(u.getEmail());
		claims.put("id", u.getId() + "");
		claims.put("nickname", u.getNickname());
		return claims;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// TODO Auto-generated method stub
		try {
			validateRequest(requestContext);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

	public SecretKey getJwtKey() {
		return jwtKey;
	}

}
