package it.project.chat.data.proxy;

import java.util.List;
import java.util.Optional;

import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;

public class UserProxy extends User {
	///  da fare la ricerca della lista 
	
	private Integer versione = 0;

	@Override
	public Integer getVersione() {
		// TODO Auto-generated method stub
		return versione;
	}

	@Override
	public void setVersione(Integer id) {
		// TODO Auto-generated method stub
		this.versione = id;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<List<User>> listContact() throws BusinessException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
