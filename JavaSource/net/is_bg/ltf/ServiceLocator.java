package net.is_bg.ltf;

import net.is_bg.ltf.businessmodels.menu.MenuDao;
import net.is_bg.ltf.db.common.DBConfig;
import net.is_bg.ltf.db.common.impl.logging.LogFactorySystemOut;
import net.is_bg.ltf.db.common.impl.timer.ElapsedTimer;
import net.is_bg.ltf.db.common.impl.visit.VisitEmpty;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactoryX;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimer;
import net.is_bg.ltf.db.common.interfaces.timer.IElaplsedTimerFactory;
import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltf.db.common.interfaces.visit.IVisitFactory;
import net.is_bg.ltf.login.LoginDao;

public class ServiceLocator {
	
	private  MenuDao menuDao;
	private LoginDao loginDao;
	private final static ServiceLocator serviceLocator = new ServiceLocator();
	private IConnectionFactoryX cf;
	

	private  ServiceLocator(){
		cf = // new DataSourceConnectionFactoryDrManagerX("jdbc:postgresql://10.240.110.70:5432/pdv", "pdv", "pdv");
		new DataSourceConnectionFactory();
		DBConfig.initDBConfig(new LogFactorySystemOut(), 
				new IVisitFactory() {
					@Override
					public IVisit getVist() {
						// TODO Auto-generated method stub
						return new VisitEmpty();
					}
					}, cf, new IElaplsedTimerFactory() {
					
					@Override
					public IElaplsedTimer getElapsedTimer() {
						// TODO Auto-generated method stub
						return new ElapsedTimer();
					}
				});
		initDaos(DBConfig.getConnectionFactory());
	}
	
	private void initDaos(IConnectionFactory factory){
		menuDao = new MenuDao(factory);
		loginDao = new LoginDao(factory);
	}
	
	public static ServiceLocator getServicelocator() {
		return serviceLocator;
	}

	public MenuDao getMenuDao() {
		return menuDao;
	}

	public LoginDao getLoginDao() {
		// TODO Auto-generated method stub
		return loginDao;
	}
	
	
}
