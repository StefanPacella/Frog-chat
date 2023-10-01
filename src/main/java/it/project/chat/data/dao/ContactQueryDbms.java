package it.project.chat.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.project.chat.data.domainmodel.Contact;
import it.project.chat.framework.data.BusinessException;
import it.project.chat.framework.data.QueryInsertStatement;
import it.project.chat.framework.data.QueryPreparedStatement;

public class ContactQueryDbms {
	
	private ContactDao contactDao;
	
	public ContactQueryDbms(ContactDao contactDao) {
		// TODO Auto-generated constructor stub
		this.contactDao = contactDao;
	}

	public QueryInsertStatement<Contact> aggiungiUnNuovoContatto(final Contact c) throws BusinessException {
		QueryInsertStatement<Contact> q = new QueryInsertStatement<Contact>(
				"INSERT INTO `contact` (`id_user`, `id_user_contact`) VALUES ( ? , ? );") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, c.getIdUser());
				preparedStatement.setInt(2, c.getIdUserContact());
			}

		};
		return q;
	}
	
	public QueryPreparedStatement<List<Contact>> queryGetListUserById(Integer iduser) throws BusinessException {
		QueryPreparedStatement<List<Contact>> q = new QueryPreparedStatement<List<Contact>>(
				"SELECT * FROM contact c where c.id_user = ?;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, iduser);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Contact> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Contact e = contactDao.make(rs);
						l.add(e);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}
	
	public QueryPreparedStatement<List<Contact>> listOfContacts(Integer id) throws BusinessException {
		QueryPreparedStatement<List<Contact>> q = new QueryPreparedStatement<List<Contact>>(
				"SELECT * FROM contact where id_user = ? ;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, id);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				List<Contact> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						Contact e = contactDao.make(rs);
						l.add(e);
					}
					this.setData(l);
				}
			}
		};
		return q;
	}

}
