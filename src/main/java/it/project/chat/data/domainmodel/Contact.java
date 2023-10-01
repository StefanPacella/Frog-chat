package it.project.chat.data.domainmodel;

import java.util.Optional;

import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DataItem;

public abstract class Contact implements DataItem {

	private Integer id;
	private Integer idUser;
	private Integer idUserContact;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Integer getIdUserContact() {
		return idUserContact;
	}

	public void setIdUserContact(Integer idUserContact) {
		this.idUserContact = idUserContact;
	}

	public abstract Optional<User> getUser() throws BusinessException;

	public abstract Optional<User> getUserContact() throws BusinessException;

}
