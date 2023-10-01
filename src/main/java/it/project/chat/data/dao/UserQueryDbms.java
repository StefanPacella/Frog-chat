package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.project.chat.data.domainmodel.User;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.QueryInsertStatement;
import it.project.chat.framework.data.QueryPreparedStatement;

public class UserQueryDbms {

	private UserDao userDao;

	public UserQueryDbms(UserDao userDao) {
		// TODO Auto-generated constructor stub
		this.userDao = userDao;
	}

	public QueryInsertStatement<User> aggiungiUnNuovoUtente(final User r) throws BusinessException {
		QueryInsertStatement<User> q = new QueryInsertStatement<User>(
				"INSERT INTO `user` (`nickname`, `password`, `email`, `picture` ) VALUES ( ? ,  ? ,  ? ,  ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, r.getNickname());
				preparedStatement.setString(2, r.getPassword());
				preparedStatement.setString(3, r.getEmail());
				preparedStatement.setString(4, r.getPicture());
			}

		};
		return q;
	}

	public QueryInsertStatement<User> updateProfilePictureQuery(final User user) throws BusinessException {
		QueryInsertStatement<User> q = new QueryInsertStatement<User>(
				"UPDATE `user` SET `picture` = ?, `version` = ?  WHERE (`email` = ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, user.getPicture());
				preparedStatement.setInt(2, user.getVersione());
				preparedStatement.setString(3, user.getEmail());
			}

		};
		return q;
	}

	public QueryInsertStatement<User> updateNickNameAndPassword(final User user) throws BusinessException {
		QueryInsertStatement<User> q = new QueryInsertStatement<User>(
				"UPDATE `user` SET `nickname` = ? , `password` = ? , `version` = ?  WHERE ( `email` = ? ) ;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, user.getNickname());
				preparedStatement.setString(2, user.getPassword());
				preparedStatement.setInt(3, user.getVersione());
				preparedStatement.setString(4, user.getEmail());
			}

		};
		return q;
	}

	public QueryPreparedStatement<List<User>> searchUser(String s) throws BusinessException {
		QueryPreparedStatement<List<User>> q = new QueryPreparedStatement<List<User>>(
				"SELECT * FROM `user` `u` WHERE (`u`.email LIKE ? or `u`.nickname LIKE ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, '%' + s + '%');
				preparedStatement.setString(2, '%' + s + '%');
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<User> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						User e = userDao.make(rs);
						l.add(e);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

}
