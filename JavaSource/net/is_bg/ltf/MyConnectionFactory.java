package net.is_bg.ltf;

import java.sql.Connection;

import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;

public class MyConnectionFactory implements IConnectionFactoryX{
	
	private ConnectionProperties pr;
	DataSourceConnectionFactoryDrManager drmanger ;
	
	public MyConnectionFactory(String url, String user, String pass){
		pr = new ConnectionProperties();
		pr.setDriver( "org.postgresql.Driver");
		pr.setUrl(url);
		pr.setUser(user);
		pr.setPass(pass);
		drmanger = new DataSourceConnectionFactoryDrManager(pr);
	}
	

	@Override
	public Connection getConnection() {
		// TODO Auto-generated method stub
		return drmanger.getConnection();
	}

	@Override
	public Connection getConnection(String arg0) {
		// TODO Auto-generated method stub
		return drmanger.getConnection();
	}

}
