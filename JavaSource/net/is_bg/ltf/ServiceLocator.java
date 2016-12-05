package net.is_bg.ltf;

import java.sql.Connection;

import net.is_bg.ltf.businessmodels.menu.MenuDao;
import net.is_bg.ltf.db.common.ConnectionProperties;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.impl.DataSourceConnectionFactoryDrManager;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;

public class ServiceLocator {
	
	private  MenuDao menuDao;
	private final static ServiceLocator serviceLocator = new ServiceLocator();
	
	
	private ConnectionProperties pr = new ConnectionProperties();
	private IConnectionFactoryX cex = new IConnectionFactoryX() {
		
		@Override
		public Connection getConnection() {
			// TODO Auto-generated method stub
			return new DataSourceConnectionFactoryDrManager(pr).getConnection();
		}
		
		@Override
		public Connection getConnection(String arg0) {
			// TODO Auto-generated method stub
			return new DataSourceConnectionFactoryDrManager(pr).getConnection();
		}
	};
	{
		pr.setDriver( "org.postgresql.Driver");
		pr.setUrl("jdbc:postgresql://10.240.110.70:5432/pdv");
		pr.setUser("pdv");
		pr.setPass("pdv");
	}

	private  ServiceLocator(){
		DBConfig.initDBConfig(new LogFactorySystemOut(), 
				new IVisitFactory() {
					@Override
					public IVisit getVist() {
						// TODO Auto-generated method stub
						return new VisitEmpty();
					}
					}, cex, new IElaplsedTimerFactory() {
					
					@Override
					public IElaplsedTimer getElapsedTimer() {
						// TODO Auto-generated method stub
						return new ElapsedTimer();
					}
				});
		initDaos(DBConfig.getConnectionFactory());
	}
	
	private void initDaos(IConnectionFactory connectionFactory){
		menuDao = new MenuDao(connectionFactory);
	}
	
	public static ServiceLocator getServicelocator() {
		return serviceLocator;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}
	
}
