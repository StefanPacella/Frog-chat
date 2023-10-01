package it.project.chat.rest.resources;

import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import it.project.chat.data.dao.ContactDao;
import it.project.chat.data.domainmodel.Contact;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.AuthenticationMaster;
import it.project.chat.framework.sicurezza.Logged;

@Path("contacts")
public class ContactsResource {

	@Logged
	@POST
	@Consumes("application/json")
	public Response newContact(Contact c, @Context HttpServletRequest request) {
		try {
			AuthenticationMaster.INSTACE.validateRequest(request, c.getIdUser());
			checkAttributes(c);
			ContactDao.INSTACE.aggiungiUnNuovoContatto(c);
			return Response.ok().build();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			return Response.serverError().build();
		} catch (AuthenticationException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	private void checkAttributes(Contact c) throws BusinessException, AuthenticationException, NoSuchElementException {
		c.getUserContact().get();
	}
}
