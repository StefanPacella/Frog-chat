package it.project.chat.framework.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class DaoAbstractQueryDbms<T extends DataItem> {

	private DaoAbstract<T> daoAbstract;

	public DaoAbstractQueryDbms(DaoAbstract<T> daoAbstract) {
		// TODO Auto-generated constructor stub
		this.daoAbstract = daoAbstract;
	}

	public QueryPreparedStatement<T> querySelectConid(final Integer id) throws BusinessException {
		QueryPreparedStatement<T> q = new QueryPreparedStatement<T>(
				"select * from " + daoAbstract.getEntita() + " where id = ? ;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, id);
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				T t = daoAbstract.risultatiSelect(preparedStatement);
				this.setData(t);
			}
		};
		return q;
	}

	public QueryPreparedStatement<T> querySelectConCondizioneSullaChiaveNelDB(final String[] valore) throws BusinessException {
		QueryPreparedStatement<T> q = new QueryPreparedStatement<T>(creaLaStringaDellaQuerySelect()) {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				for (int i = 0; i < valore.length; i++) {
					preparedStatement.setString(i + 1, valore[i]);
				}
			}

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				T t = daoAbstract.risultatiSelect(preparedStatement);
				this.setData(t);
			}
		};
		return q;
	}

	public QueryPreparedStatement<List<T>> getLista() throws BusinessException {
		QueryPreparedStatement<List<T>> q = new QueryPreparedStatement<List<T>>(
				"select * from " + daoAbstract.getEntita() + "  ;") {

			@Override
			public void risultati(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub

				List<T> l = new ArrayList<>();
				try (ResultSet rs = preparedStatement.getResultSet()) {
					while (rs.next()) {
						l.add(daoAbstract.make(rs));
					}
					this.setData(l);
				}

				this.setData(l);
			}
		};
		return q;
	}

	public QueryPreparedStatement<T> eliminaConId(final Integer id) throws BusinessException {
		QueryPreparedStatement<T> q = new QueryPreparedStatement<T>(
				"delite * from " + daoAbstract.getEntita() + " where id = ? ;") {

			@Override
			public void setVal(PreparedStatement preparedStatement) throws SQLException {
				// TODO Auto-generated method stub
				preparedStatement.setInt(1, id);
			}

		};
		return q;
	}

	private String creaLaStringaDellaQuerySelect() {
		String s = "select * from " + daoAbstract.getEntita() + " where ";
		for (int i = 0; i < daoAbstract.getAttributoChiaveDellaSelect().length; i++) {
			s = s + " " + daoAbstract.getAttributoChiaveDellaSelect()[i] + " = ?  ";
			if (i < daoAbstract.getAttributoChiaveDellaSelect().length - 1) {
				s = s + " and ";
			}
		}
		return s + " ; ";
	}

}
