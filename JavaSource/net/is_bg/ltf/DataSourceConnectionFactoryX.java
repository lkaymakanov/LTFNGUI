package net.is_bg.ltf;
import java.sql.Connection;



import net.is_bg.ltf.ContextParamLoader.CONTEXTPARAMS;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;


public class DataSourceConnectionFactoryX extends  DataSourceConnectionFactory implements IConnectionFactoryX{
	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return this.getConnection(CONTEXTPARAMS.DEFAULT_JDBC_RESOURCE_NAME.getValue().toString());
	}

}
