package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.project.chat.data.domainmodel.Message;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.QueryInsertStatement;
import it.project.chat.framework.data.QueryPreparedStatement;

public class MessageQueryDbms {

	private MessageDao messageDao;

	public MessageQueryDbms(MessageDao messageDao) {
		// TODO Auto-generated constructor stub
		this.messageDao = messageDao;
	}

	public QueryInsertStatement<Message> aggiungiUnNuovoMessaggio(final Message r) throws BusinessException {
		QueryInsertStatement<Message> q = new QueryInsertStatement<Message>(
				"INSERT INTO `dbmsChat`.`message` (`text`, `dataM`, `timeM`, `idusersender`, `iduserreceiver`, `hasbeenread`) VALUES ( ? ,  ? ,  ? ,  ? ,  ? ,  ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, r.getText());
				preparedStatement.setString(2, r.getData());
				preparedStatement.setString(3, r.getTime());
				preparedStatement.setInt(4, r.getIdusersender());
				preparedStatement.setInt(5, r.getIduserreceiver());
				preparedStatement.setBoolean(6, r.isHasbeenread());
			}

		};
		return q;
	}

	public QueryPreparedStatement<List<Message>> getTheLatestMessagesFromTimex(String user, String contactUser,
			String fromDate, String fromTime) {

		String qS = "select * from ( ";
		qS = qS + " select * from `message` `m` ";
		qS = qS + " where ";
		qS = qS + "(";
		qS = qS + " ( (`m`.iduserreceiver = ? and `m`.idusersender = ? ) or (`m`.idusersender = ? and `m`.iduserreceiver = ? ) ) ";
		qS = qS + " AND ";
		qS = qS + " (  ( DATEDIFF(  STR_TO_DATE( ? , '%Y-%m-%d') , `m`.dataM ) > 0 ) OR ( ( DATEDIFF( STR_TO_DATE( ? , '%Y-%m-%d') , `m`.dataM ) >= 0 ) and ( TIMEDIFF ( STR_TO_DATE( ? ,'%H:%i:%s') ,  `m`.timeM ) > 0 )))  ";
		qS = qS + ")";
		qS = qS + " ORDER BY ADDTIME( `m`.dataM , `m`.timeM ) desc  ";
		qS = qS + " ) `me`  LIMIT 10 ; ";

		QueryPreparedStatement<List<Message>> q = new QueryPreparedStatement<List<Message>>(qS) {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stu

				preparedStatement.setString(1, user);
				preparedStatement.setString(2, contactUser);
				preparedStatement.setString(3, user);
				preparedStatement.setString(4, contactUser);
				preparedStatement.setString(5, fromDate);
				preparedStatement.setString(6, fromDate);
				preparedStatement.setString(7, fromTime);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Message> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Message a = messageDao.make(rs);
						l.add(a);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

	public QueryPreparedStatement<List<Message>> getTheLastTenPosts(String user, String contactUser) {
		String qS = "select * from ( ";
		qS = qS + " select * from `message` `m` where (( `m`.iduserreceiver = ? and `m`.idusersender = ? ) or (`m`.idusersender = ? and `m`.iduserreceiver = ? ) ) ";
		qS = qS + " ORDER BY ADDTIME( `m`.dataM , `m`.timeM ) desc";
		qS = qS + " ) `me` LIMIT 10 ;	 ";
		QueryPreparedStatement<List<Message>> q = new QueryPreparedStatement<List<Message>>(qS) {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, user);
				preparedStatement.setString(2, contactUser);
				preparedStatement.setString(3, user);
				preparedStatement.setString(4, contactUser);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Message> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Message a = messageDao.make(rs);
						l.add(a);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

	public QueryPreparedStatement<List<Message>> getTheNewOnes(String user, String contactUser) {
		String qS = "select * from ( ";
		qS = qS + " select * from `message` `m` where ( ( `m`.iduserreceiver = ? and `m`.idusersender = ? ) and `m`.hasbeenread = false ) ";
		qS = qS + " ORDER BY ADDTIME( `m`.dataM , `m`.timeM ) desc";
		qS = qS + " ) `me` LIMIT 10 ;	 ";
		QueryPreparedStatement<List<Message>> q = new QueryPreparedStatement<List<Message>>(qS) {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setString(1, user);
				preparedStatement.setString(2, contactUser);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Message> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Message a = messageDao.make(rs);
						l.add(a);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

	public QueryInsertStatement<Message> updateAllHasbeenread(final Integer iduser) throws BusinessException {
		QueryInsertStatement<Message> q = new QueryInsertStatement<Message>(
				"UPDATE `message` `m1` SET `hasbeenread` = true , `version` = `version` + 1 WHERE (`iduserreceiver` = ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, iduser);
			}

		};
		return q;
	}

	public QueryInsertStatement<Message> updateHasbeenread(final Integer id) throws BusinessException {
		QueryInsertStatement<Message> q = new QueryInsertStatement<Message>(
				"UPDATE `message` `m1` SET `hasbeenread` = true , `version` = `version` + 1 WHERE (`id` = ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, id);
			}

		};
		return q;
	}

	public QueryPreparedStatement<List<Message>> getListUsersWhoWroteToTheUser(Integer idUser)
			throws BusinessException {
		QueryPreparedStatement<List<Message>> q = new QueryPreparedStatement<List<Message>>(
				"SELECT * FROM dbmsChat.message where ( iduserreceiver = ? and  hasbeenread = false  ) ;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, idUser);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Message> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Message e = messageDao.make(rs);
						l.add(e);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

}
