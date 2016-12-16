package net.is_bg.ltf;


import java.sql.Connection;
import java.sql.SQLException;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import net.is_bg.ltf.ContextParamLoader.CONTEXTPARAMS;
import net.is_bg.ltf.db.common.JDBCException;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;

//TODO: Auto-generated Javadoc
/**
* A factory for creating DataSourceConnection objects.
*/
public class DataSourceConnectionFactory implements IConnectionFactoryX {
	
	/** The Constant DATASOURCE_NAME. */
	public Connection getConnection() {
		Visit visit = null;
		if(FacesContext.getCurrentInstance() != null){
			SessionBean sb = AppUtil.getSessionBeanFromFacesContext();
			visit = (sb == null ? null : sb.getVisit());
		}
		String dataSourceName = visit == null ? (String)CONTEXTPARAMS.DEFAULT_JDBC_RESOURCE_NAME.getValue() : visit.getDefDbConn();
		return getConnection(dataSourceName);
	}

	
	@Override
	public Connection getConnection(String dataSourceName) {
		// TODO Auto-generated method stub
		try {
			DataSource ds = null;
			ds = (DataSource) ApplicationGlobals.getApplicationGlobals().getContext().lookup(dataSourceName);
			Connection conn = ds.getConnection();
			return conn;
		} catch (NamingException e) {
			throw new JDBCException(e);
		} catch (SQLException e) {
			throw new JDBCException(e);
		}
	}
	
}