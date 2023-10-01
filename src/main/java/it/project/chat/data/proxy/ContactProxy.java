package it.project.chat.data.proxy;

import java.util.Optional;

import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.Contact;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.GetEntity;

public class ContactProxy extends Contact {

	private Integer versione = 0;
	private GetEntity<User> getUser;
	private GetEntity<User> getUserContact;

	public ContactProxy() {
		// TODO Auto-generated constructor stub
		initGetEntity();
	}
	
	
	@Override
	public Integer getVersione() {
		// TODO Auto-generated method stub
		return versione;
	}

	@Override
	public void setVersione(Integer id) {
		// TODO Auto-generated method stub
		versione = id;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public Optional<User> getUser() throws BusinessException {
		// TODO Auto-generated method stub
		return getUser.get(this.getIdUser());
	}

	@Override
	public Optional<User> getUserContact() throws BusinessException {
		// TODO Auto-generated method stub
		return getUserContact.get(this.getIdUserContact());
	}
	
	private void initGetEntity() {
		getUser = new GetEntity<User>(UserDao.INSTACE);
		getUserContact = new GetEntity<User>(UserDao.INSTACE);
	}

}
