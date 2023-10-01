package it.project.chat.rest.resources;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import it.project.chat.data.dao.ContactDao;
import it.project.chat.data.dao.MessageDao;
import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.Contact;
import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.domainmodel.User;
import it.project.chat.data.proxy.ContactProxy;
import it.project.chat.framework.UtilityDateandTime;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;
import it.project.chat.framework.sicurezza.Logged;

@Path("messages")
public class MessagesResource {

	private UtilityDateandTime utilityDateandTime = new UtilityDateandTime();

	@Logged
	@POST
	@Consumes("application/json")
	public Response newMessage(Message m, @Context HttpServletRequest request) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, m.getUserSender().get().getId());
			checkAttributes(m);
			MessageDao.INSTANCE.addNewMessage(m);
			addNewContact(m.getIdusersender(), m.getIduserreceiver());
			return Response.ok().build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		} catch (AuthenticationException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private void checkAttributes(Message m) throws BusinessException, AuthenticationException, NoSuchElementException {
		if (m.getUserReceiver().get().getId() == 0) {
			throw new AuthenticationException("");
		}
	}

	private void addNewContact(Integer idusersender, Integer iduserreceiver) throws BusinessException {
		try {
			ContactDao.INSTACE
					.querySelectConCondizioneSullaChiave(new String[] { idusersender + "", iduserreceiver + "" }).get();
		} catch (NoSuchElementException e) {
			Contact c = new ContactProxy();
			c.setIdUser(idusersender);
			c.setIdUserContact(iduserreceiver);
			ContactDao.INSTACE.aggiungiUnNuovoContatto(c);
		}

		try {
			ContactDao.INSTACE
					.querySelectConCondizioneSullaChiave(new String[] { iduserreceiver + "", idusersender + "" }).get();
		} catch (NoSuchElementException e) {
			Contact c = new ContactProxy();
			c.setIdUser(iduserreceiver);
			c.setIdUserContact(idusersender);
			ContactDao.INSTACE.aggiungiUnNuovoContatto(c);
		}
	}

	@Logged
	@GET
	@Path("getTheLastTenMessagesBeforeTimeX")
	@Produces("application/json")
	public Response getTheLastTenMessagesBeforeTimeX(@Context HttpServletRequest request,
			@QueryParam("user") String user, @QueryParam("contact_user") String contactUser,
			@QueryParam("date") String fromDate, @QueryParam("time") String fromTime) throws IOException {
		List<Message> l = new ArrayList<>();
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			fromDate = utilityDateandTime.convertDateForDbms(fromDate);
			utilityDateandTime.checkTheTimeFormat(fromTime);
			l = MessageDao.INSTANCE.getTheLatestMessagesFromTimex(user, contactUser, fromDate, fromTime);
			return Response.ok(l).build();
		} catch (NumberFormatException | AuthenticationException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (NoSuchElementException | ParseException e) {
			// TODO: handle exception
			return Response.status(404).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@GET
	@Path("getTheLastTenMessages")
	@Produces("application/json")
	public Response getTheLastTenMessages(@Context HttpServletRequest request, @QueryParam("user") String user,
			@QueryParam("contact_user") String contactUser) throws IOException {
		List<Message> l = new ArrayList<>();
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			l = MessageDao.INSTANCE.getTheLastTenPosts(user, contactUser);
			MessageDao.INSTANCE.updateAllHasbeenreadThatHaveBeenSentByTheOtherUser(Integer.parseInt(user));
			return Response.ok(l).build();
		} catch (NumberFormatException | AuthenticationException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return Response.status(404).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@GET
	@Path("getTheNewOnes")
	@Produces("application/json")
	public Response getTheNewOnes(@Context HttpServletRequest request, @QueryParam("user") String user,
			@QueryParam("contact_user") String contactUser) throws IOException {
		List<Message> l = new ArrayList<>();
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, Integer.parseInt(user));
			l = MessageDao.INSTANCE.getTheNewOnes(user, contactUser);
			MessageDao.INSTANCE.updateAllHasbeenreadThatHaveBeenSentByTheOtherUser(Integer.parseInt(user));
			return Response.ok(l).build();
		} catch (NumberFormatException | AuthenticationException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return Response.status(404).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

	@Logged
	@PUT
	@Path("{message: [a-zA-Z0-9]+ }/hasbeenread")
	public Response updateHasbeenread(@Context HttpServletRequest request, @PathParam("message") String message) {
		try {
			Message m = MessageDao.INSTANCE.selectConId(Integer.parseInt(message)).get();
			String email = AuthenticationMaster.INSTACE.validateRequest(request);
			User u = UserDao.INSTACE.querySelectConCondizioneSullaChiave(new String[] { email }).get();
			if(!m.getIduserreceiver().equals(u.getId()))
				return Response.status(Response.Status.UNAUTHORIZED).build();
			MessageDao.INSTANCE.updateHasbeenread(Integer.parseInt(message));
			return Response.ok().build();
		} catch (NumberFormatException | NoSuchElementException | AuthenticationException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		}
	}

}
