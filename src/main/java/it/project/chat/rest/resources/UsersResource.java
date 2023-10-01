package it.project.chat.rest.resources;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import it.project.chat.data.dao.ContactDao;
import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.PictureUtility;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;
import it.project.chat.framework.sicurezza.Logged;
import it.project.chat.framework.sicurezza.XssFilter;

@Path("users")
public class UsersResource {

	private XssFilter xssFilter = new XssFilter();

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doLogin(@Context UriInfo uriinfo, @FormParam("email") String email,
			@FormParam("password") String password) {
		try {
			String c = AuthenticationMaster.INSTACE.loginStringToken(email, password);
			NewCookie newCookie = new NewCookie("token", c);
			return Response.ok(c).cookie(newCookie).build();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	@Logged
	@DELETE
	@Path("/logout")
	public Response doLogout(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		try {
			AuthenticationMaster.INSTACE.logout(request);
			AuthenticationMaster.INSTACE.logoutDeleteTokenfromCookie(response);
			return Response.noContent().build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@POST
	@Consumes("application/json")
	public Response newUser(User user) {
		try {
			UserDao.INSTACE.aggiungiUnNuovoUtente(user);
			return Response.ok().build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@PUT
	@Path("{user: [a-zA-Z0-9]+ }")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateNickNameAndPassword(@Context HttpServletRequest request, @PathParam("user") String user,
			@FormParam("nickname") String nickname, @FormParam("oldpassword") String oldPassword,
			@FormParam("newpassword") String newPassword) {
		try {
			nickname = xssFilter.sanitize(nickname);
			String email = AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			AuthenticationMaster.INSTACE.valideteRequest(email, oldPassword);
			UserDao.INSTACE.updateNickNamePassword(email, newPassword, nickname);
			return Response.ok().build();
		} catch (AuthenticationException | NumberFormatException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@PUT
	@Path("{user: [a-zA-Z0-9]+ }/picture")
	@Consumes("multipart/form-data")
	public Response updatePicture(@Context HttpServletRequest request, @PathParam("user") String user) {
		try {
			String email = AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			PictureUtility pictureUtility = new PictureUtility();
			String p = pictureUtility.saveTheImg(request, email);
			UserDao.INSTACE.updateProfilePicture(email, p);
			return Response.ok().build();
		} catch (AuthenticationException | NumberFormatException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@GET
	@Path("{user: [a-zA-Z0-9]+ }/listContact")
	@Produces("application/json")
	public Response getUserContacts(@Context HttpServletRequest request, @PathParam("user") String user) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			List<User> l = ContactDao.INSTACE.listOfContacts(Integer.parseInt(user)).stream().map(x -> {
				try {
					return x.getUserContact().get();
				} catch (BusinessException | NoSuchElementException e) {
					// TODO Auto-generated catch block
					return null;
				}
			}).filter(x -> x != null).collect(Collectors.toList());
			return Response.ok(l).build();
		} catch (AuthenticationException | NumberFormatException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@GET
	@Path("{user: [a-zA-Z0-9]+ }/getListUsersWhoWroteToTheUser")
	@Produces("application/json")
	public Response getListUsersWhoWroteToTheUser(@Context HttpServletRequest request, @PathParam("user") String user) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			// UserDao.INSTACE;
			List<User> l = UserDao.INSTACE.getListUsersWhoWroteToTheUser(Integer.parseInt(user));
			return Response.ok(l).build();
		} catch (AuthenticationException | NumberFormatException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@GET
	@Path("search/{user: [a-zA-Z0-9@.!#$%&'*+-/=?^_`{|}~;]+ }")
	@Produces("application/json")
	public Response searchUser(@PathParam("user") String s) {
		try {
			List<User> l = UserDao.INSTACE.searchUser(s);
			return Response.ok(l).build();
		} catch (NumberFormatException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

}
