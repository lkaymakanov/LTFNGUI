package net.is_bg.ltf;


import java.sql.Connection;

import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;

public class DataSourceConnectionFactoryDrManagerX extends DataSourceConnectionFactoryDrManager implements IConnectionFactoryX{
	
	public DataSourceConnectionFactoryDrManagerX(String url, String user, String pass){
		this(getConnectionProp(url, user, pass));
	}

	public DataSourceConnectionFactoryDrManagerX(ConnectionProperties pr) {
		super(pr);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection getConnection(String arg0) {
		// TODO Auto-generated method stub
		return getConnection();
	}
	
	private static ConnectionProperties getConnectionProp(String url, String user, String pass){
		ConnectionProperties pr = new ConnectionProperties();
		pr.setDriver("org.postgresql.Driver");
		pr.setUrl(url);
		pr.setUser(user);
		pr.setPass(pass);
		return pr;
	}
}
