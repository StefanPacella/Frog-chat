package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import it.project.chat.data.domainmodel.User;
import it.project.chat.data.proxy.UserProxy;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.DaoAbstract;
import it.project.chat.framework.data.DataException;
import it.project.chat.framework.sicurezza.AuthenticationException;
import it.project.chat.framework.sicurezza.Hash256;

public class UserDao extends DaoAbstract<User> {

	public static final UserDao INSTACE = new UserDao();

	private UserQueryDbms userQueryDbms;
	private Hash256 hash256 = new Hash256();

	private UserDao() {
		super("user", new String[] { "email" });
		// TODO Auto-generated constructor stub
		userQueryDbms = new UserQueryDbms(this);
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
	public Optional<User> querySelectConCondizioneSullaChiave(String[] valore) throws BusinessException {
		// TODO Auto-generated method stub
		return this.getCache().get(x -> x.getEmail().equals(valore[0]),
				querySelectConCondizioneSullaChiaveNelDB(valore));
	}

	@Override
	public User risultatiSelect(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub
		try (ResultSet rs = preparedStatement.getResultSet()) {
			if (rs.next()) {
				User r = make(rs);
				return r;
			}
		}
		return null;
	}

	@Override
	public User make(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		UserProxy userProxy = new UserProxy();
		userProxy.setEmail(rs.getString("email"));
		userProxy.setId(rs.getInt("id"));
		userProxy.setNickname(rs.getString("nickname"));
		userProxy.setPassword(rs.getString("password"));
		userProxy.setPicture(rs.getString("picture"));
		userProxy.setVersione(rs.getInt("version"));
		return userProxy;
	}

	public void aggiungiUnNuovoUtente(User o) throws BusinessException {
		o.setPassword(hash256.getSHA256SecurePassword(o.getPassword(), o.getEmail()));
		this.checkIfElementIsAlreadyPresentInMemoryAndThenAdd(new String[] { o.getEmail() }, o);
	}

	@Override
	protected void methodAddInCacheAndDbms(User o) throws BusinessException {
		// TODO Auto-generated method stub
		getCache().add(x -> x.getEmail().equals(o.getEmail()), o, userQueryDbms.aggiungiUnNuovoUtente(o));
	}

	public void updateProfilePicture(String email, String path) throws BusinessException {
		try {
			User u = this.querySelectConCondizioneSullaChiave(new String[] { email }).get();
			u.setPicture(path);
			getCache().update(x -> x.getEmail().equals(email), u, userQueryDbms.updateProfilePictureQuery(u),
					querySelectConCondizioneSullaChiaveNelDB(new String[] { email }));
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new BusinessException("error");
		}
	}

	public void updateNickNamePassword(String email, String newPassword, String nickName) throws BusinessException {
		try {
			User u = this.querySelectConCondizioneSullaChiave(new String[] { email }).get();
			u.setPassword(hash256.getSHA256SecurePassword(newPassword, email));
			u.setNickname(nickName);
			getCache().update(x -> x.getEmail().equals(email), u, userQueryDbms.updateNickNameAndPassword(u),
					querySelectConCondizioneSullaChiaveNelDB(new String[] { email }));
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new BusinessException("error");
		}
	}

	public User login(String email, String password) throws AuthenticationException {
		try {
			User u = querySelectConCondizioneSullaChiave(new String[] { email }).get();
			if (u.getPassword().equals(hash256.getSHA256SecurePassword(password, email)))
				return u;
			else
				throw new AuthenticationException("password or email not correct");
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException("password or email not correct");
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			throw new AuthenticationException("error");
		}
	}

	public List<User> searchUser(String s) throws BusinessException {
		return getCache().getList("searchUser" + s, x -> searchUserPredicate(x, s), userQueryDbms.searchUser(s));
	}

	private boolean searchUserPredicate(User u, String s) {
		return u.getEmail().toLowerCase().contains(s.toLowerCase())
				|| u.getNickname().toLowerCase().contains(s.toLowerCase());
	}

	public List<User> getListUsersWhoWroteToTheUser(Integer idUser) throws BusinessException {
		return MessageDao.INSTANCE.getListUsersWhoWroteToTheUserReceiver(idUser);
	}
	
}
