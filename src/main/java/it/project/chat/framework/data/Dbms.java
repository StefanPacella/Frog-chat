package it.project.chat.framework.data;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Dbms {

	public Dbms() {
		// TODO Auto-generated constructor stub
	}

	private void logException(SQLException e) {
		Throwable cause = e.getCause();
		System.err.println("ERRORE: " + e.getMessage());
		if (cause != null) {
			if (cause instanceof SQLException) {
				System.err.println("* SQLState: " + ((SQLException) cause).getSQLState());
				System.err.println("* Codice errore DBMS: " + ((SQLException) cause).getErrorCode());
				System.err.println("* Messaggio errore DBMS: " + ((SQLException) cause).getMessage());
			} else {
				System.err.println("* Causa: " + cause.getMessage());
			}
		}
	}

	private Connection connect() throws BusinessException, SQLException {
		try {
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup("java:comp/env/jdbc/dbmsChat");
			return ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			throw new BusinessException("Problemi nella connessione al database");
		}
	}

	public <T> void makeConnectionAndQuery(SendQuery query) throws BusinessException {
		Connection c = null;
		try {
			c = connect();
			try {
				c.setAutoCommit(false);
			} catch (SQLException ex) {
				close(c);
				throw new SQLException("Problemi di gestione della transazione", ex);
			}

			try {
				query.query(c);
			} catch (BusinessException e) {
				// TODO: handle exception
				close(c);
				throw new BusinessException("Errore di esecuzione della query", e);
			}

			try {
				c.commit();
				close(c);
			} catch (SQLException ex) {
				throw new SQLException("Problemi di gestione della transazione", ex);
			}
		} catch (SQLException ex) {
			if (ex.getSQLState() != null && ex.getSQLState().equals("45000")) {
				close(c);
				throw new BusinessException(ex.getMessage());
			}
			logException(ex);
			try {
				if (c != null) {
					c.rollback();
					close(c);
				}
			} catch (SQLException ex1) {
				logException(new SQLException("Problemi di rollback sulla connessione", ex1));
			}
		}
	}

	private void close(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logException(new SQLException("problema nella chiusura della connesione", e));
		}
	}

}
