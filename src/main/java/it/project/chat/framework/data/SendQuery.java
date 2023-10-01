package it.project.chat.framework.data;

import java.sql.Connection;

public interface SendQuery {

	public void query(Connection connection) throws BusinessException;

}
