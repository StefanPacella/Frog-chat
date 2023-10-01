package it.project.chat.data.proxy;

import java.util.Optional;

import it.project.chat.data.dao.UserDao;
import it.project.chat.data.domainmodel.Message;
import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.GetEntity;

public class MessageProxy extends Message {

	private Integer versione = 0;
	private GetEntity<User> getUserSender;
	private GetEntity<User> getUserReceiver;

	public MessageProxy() {
		// TODO Auto-generated constructor stub
		initGetEntity();
	}

	@Override
	public Integer getVersione() {
		// TODO Auto-generated method stub
		return versione;
	}

	@Override
	public void setVersione(Integer versione) {
		// TODO Auto-generated method stub
		this.versione = versione;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<User> getUserSender() throws BusinessException {
		// TODO Auto-generated method stub
		return getUserSender.get(this.getIdusersender());
	}

	@Override
	public Optional<User> getUserReceiver() throws BusinessException {
		// TODO Auto-generated method stub
		return getUserReceiver.get(this.getIdusersender());
	}

	private void initGetEntity() {
		getUserSender = new GetEntity<User>(UserDao.INSTACE);
		getUserReceiver = new GetEntity<User>(UserDao.INSTACE);
	}

}
