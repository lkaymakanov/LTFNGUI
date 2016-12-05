package net.is_bg.ltf.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * When the user session timedout, ({@link #sessionDestroyed(HttpSessionEvent)}
 * )method will be invoked. This method will make necessary cleanups (logging
 * out user, updating db and audit logs, etc...) As a result; after this method,
 * we will be in a clear and stable state. So nothing left to think about
 * because session expired, user can do nothing after this point.
 * 
 * Thanks to hturksoy
 **/

public class SecurityListener implements HttpSessionListener {
	protected Log LOG = LogFactory.getLog(this.getClass());
	private static final DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");
	
	
	
	public SecurityListener() {
		
	}

	public void sessionCreated(HttpSessionEvent event) {
		//add session to session map
		HttpSession session  = event.getSession();
		ApplicationSessionManager.addSession(event.getSession());
		//session.setAttribute(AppConstants.SESSION_DATA_BEAN, new SessionDataBean());
		LOG.debug("Session " + event.getSession().getId() + " created at " + formatter.format(new Date()) + ".");
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		// get the destroying session...
		HttpSession session = event.getSession();
		//remove from session map
		ApplicationSessionManager.removeSessionFromMap(session);
		LOG.debug("Session " + session.getId() + " destroyed at " + formatter.format(new Date()) + ". Logging out user...");
		
		//delete session
		//AppUtil.deleteSession(session.getId());
		/** 
		 * nobody can reach user data after this point because session is
		 * invalidated already. So, get the user data from session and save its
		 * logout information before losing it. User's redirection to the
		 * timeout page will be handled by the SessionTimeoutFilter.
		 */

		// Only if needed
		try {
			//sessionService.delete(session.getId());
			prepareLogoutInfoAndLogoutActiveUser(session);
		} catch (Exception e) {
			LOG.debug("Error while logging out at session destroyed : " + e.getMessage());
		}
	}
	


	/**
	 * Clean your logout operations.
	 */
	public void prepareLogoutInfoAndLogoutActiveUser(HttpSession httpSession) {
		// Only if needed
	}
}
