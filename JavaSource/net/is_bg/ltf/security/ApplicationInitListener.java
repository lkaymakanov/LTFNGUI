package net.is_bg.ltf.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.is_bg.ltf.ServiceLocator;


/*import net.is_bg.common.AppUtil;
import net.is_bg.common.ContextParams.RESOURCES;
import net.is_bg.common.hibernate.util.HibernateUtil;
import net.is_bg.common.util.FileUtil;


import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import taskscheduler.Frequency;
import taskscheduler.ScheduledTask;
import taskscheduler.TaskUtils;
import taskscheduler.ThreadPoolTaskScheduler;*/


public class ApplicationInitListener implements ServletContextListener {
	private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	//protected Log LOG = LogFactory.getLog(this.getClass());
	
	public ApplicationInitListener() {
	
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		
		//set locale
    	Locale.setDefault( new Locale("bg", "BG"));
    	System.out.println(Locale.getDefault());
    	
    	//create service locator & add it to application map...
    	ServiceLocator.getServicelocator();
		
		System.out.println("Context '" + contextEvent.getServletContext().getContextPath() + "' initialized at " + formatter.format(new Date()) + ".");
	}

	public void contextDestroyed(ServletContextEvent contextEvent) {
		//shut down hibernate connections
		//HibernateUtil.close();
		System.out.println("Context '" + contextEvent.getServletContext().getContextPath() + "' destroyed at " + formatter.format(new Date()) + ".");
	}

}
