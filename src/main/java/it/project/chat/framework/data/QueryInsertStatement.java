package it.project.chat.framework.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryInsertStatement<T> implements SendQuery {

	private Integer lastID;
	private String query;

	public QueryInsertStatement(String query) {
		// TODO Auto-generated constructor stub
		this.query = query;
	}

	@Override
	public void query(Connection connection) throws BusinessException {
		// TODO Auto-generated method stub
		try (PreparedStatement preparedStatement = connection.prepareStatement(query,
				Statement.RETURN_GENERATED_KEYS)) {

			setVal(preparedStatement);

			preparedStatement.executeUpdate();

			setID(preparedStatement);

		} catch (SQLException ex) {
			throw new BusinessException("Errore di esecuzione della query", ex);
		}
	}

	public void setVal(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub
	}

	public void setID(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub
		ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
			lastID = rs.getInt(1);
		}
	}

	public Integer getLastID() {
		return lastID;
	}

}
