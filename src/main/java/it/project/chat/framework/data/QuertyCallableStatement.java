package it.project.chat.framework.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class QuertyCallableStatement<T> implements SendQuery {

	private String query;
	private T data;

	public QuertyCallableStatement(String query) {
		this.query = query;
		// TODO Auto-generated constructor stub
	}

	public void query(Connection connection) throws BusinessException {
		try (CallableStatement callableStatement = connection.prepareCall(query)) {

			setVal(callableStatement);

			callableStatement.execute();

			risultati(callableStatement);

		} catch (SQLException ex) {
			throw new BusinessException("Errore nell'esecuzione della query", ex);
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
