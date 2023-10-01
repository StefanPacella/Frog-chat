package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import it.project.chat.data.domainmodel.Contact;
import it.project.chat.data.proxy.ContactProxy;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DaoAbstract;
import it.project.chat.framework.data.DataException;

public class ContactDao extends DaoAbstract<Contact> {

	public static ContactDao INSTACE = new ContactDao();
	private ContactQueryDbms contactQueryDbms;

	public ContactDao() {
		super("contact", new String[] { "id_user", "id_user_contact" });
		// TODO Auto-generated constructor stub
		contactQueryDbms = new ContactQueryDbms(this);
	}

	@Override
	public void initImpl() throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroyImpl() throws DataException {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<Contact> querySelectConCondizioneSullaChiave(String[] valore) throws BusinessException {
		// TODO Auto-generated method stub
		return this.getCache().get(x -> {
			try {
				return x.getIdUser().equals(convertStringToInteger(valore[0]))
						&& x.getIdUserContact().equals(convertStringToInteger(valore[1]));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}, querySelectConCondizioneSullaChiaveNelDB(valore));
	}

	public List<Contact> queryGetListUserById(Integer iduser) throws BusinessException {
		// TODO Auto-generated method stub
		return this.getCache().getList("listcontact" + iduser, x -> x.getIdUser().equals(iduser),
				contactQueryDbms.queryGetListUserById(iduser));
	}

	public void aggiungiUnNuovoContatto(Contact c) throws BusinessException {
		this.checkIfElementIsAlreadyPresentInMemoryAndThenAdd(
				new String[] { c.getIdUser().toString(), c.getIdUserContact().toString() }, c);
	}

	private int convertStringToInteger(String id) throws Exception {
		return Integer.parseInt(id);
	}

	@Override
	public Contact risultatiSelect(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub
		try (ResultSet rs = preparedStatement.getResultSet()) {
			if (rs.next()) {
				Contact c = make(rs);
				return c;
			}
		}
		return null;
	}

	@Override
	public Contact make(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		Contact c = new ContactProxy();
		c.setIdUser(rs.getInt("id_user"));
		c.setIdUserContact(rs.getInt("id_user_contact"));
		c.setId(rs.getInt("id"));
		c.setVersione(rs.getInt("version"));
		return c;
	}

	@Override
	protected void methodAddInCacheAndDbms(Contact o) throws BusinessException {
		// TODO Auto-generated method stub
		this.getCache().add(
				x -> x.getIdUser().equals(o.getIdUser()) && x.getIdUserContact().equals(o.getIdUserContact()), o,
				contactQueryDbms.aggiungiUnNuovoContatto(o));
	}
	
	public List<Contact> listOfContacts(Integer id) throws BusinessException {
		return getCache().getList("listOfContacts" + id, x -> {
			try {
				return x.getUser().get().getId().equals(id);
			} catch (BusinessException | NoSuchElementException e) {
				// TODO Auto-generated catch block
				return false;
			}
		}, contactQueryDbms.listOfContacts(id));
	}

}
