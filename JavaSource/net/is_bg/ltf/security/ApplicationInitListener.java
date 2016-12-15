package net.is_bg.ltf.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import token.SharedToken;
import net.is_bg.ltf.ServiceLocator;





public class ApplicationInitListener implements ServletContextListener {
	private DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	//protected Log LOG = LogFactory.getLog(this.getClass());
	
	public ApplicationInitListener() {
	
	}

	public void contextInitialized(ServletContextEvent contextEvent) {
		
		//set locale
    	Locale.setDefault( new Locale("bg", "BG"));
    	System.out.println(Locale.getDefault());
    	
    	SharedToken token =	SharedToken.getInstance(TimeUnit.HOURS.toMillis(10));
    	System.out.println(token.getCreateTimeDate());
    	System.out.println(token.getEndTimeDate());
    	
    	/*try {
			byte [] enc = TokenUtils.encryptToken(token, new NullEncoderFactory("mykey"));
			byte [] dec = TokenUtils.decryptToken(enc, new SimpleOffsetDecoderFactory("mykey"));
			try {
				token = (SharedToken)TokenUtils.deserialize(dec , dec.length);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	
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
