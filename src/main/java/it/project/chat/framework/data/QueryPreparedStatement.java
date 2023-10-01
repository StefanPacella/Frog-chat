package it.project.chat.framework.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class QueryPreparedStatement<T> implements SendQuery {

	private String query;
	private T data = null;

	public QueryPreparedStatement(String query) {
		this.query = query;
		// TODO Auto-generated constructor stub
	}

	public void query(Connection connection) throws BusinessException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			setVal(preparedStatement);

			preparedStatement.execute();

			risultati(preparedStatement);

		} catch (SQLException ex) {
			throw new BusinessException("Errore di esecuzione della query", ex);
		}
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}


	public void setVal(PreparedStatement preparedStatement) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void risultati(PreparedStatement preparedStatement) throws SQLException {

	}

}
