package net.is_bg.ltf.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

/**
 * A class that manages HttpSessions in the application!!!
 * @author Lubo
 *
 */
public class ApplicationSessionManager  {

    private static Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();        

    /**
     * Adds session to the session map!!!
     * @param session
     */
    public static void addSession(HttpSession session) {
    	if(session == null) return;
        sessions.put(session.getId(), session);
    }

    /**
     * Destroys a session & removes it from session map!!!
     * @param session
     */
    public static void destroySession(HttpSession session) {
    	if(session == null) return;
    	synchronized (sessions) {
    		HttpSession s =  sessions.remove(session.getId());
    		if(s!= null) s.invalidate();
		}
    }

    /**
     * Destroys a session by id!!!
     * @param sessionId
     */
    public static void  destroySession(String sessionId) {
    	synchronized (sessions) {
    		HttpSession session = sessions.get(sessionId);
    		destroySession(session);
    	}
    }
    
    /**
     * Just removes session from session map!!!
     */
    public static HttpSession removeSessionFromMap(HttpSession session){
    	if(session == null) return null;
    	return removeSessionFromMap(session.getId());
    	
    }
    
    
    /**
     * Just removes session from session map!!!
     */
    public static HttpSession removeSessionFromMap(String id){
    	synchronized (sessions) {
    		HttpSession session = getSession(id);
    		sessions.remove(id);
    		return session;
		}
    }
    
    public static HttpSession getSession(String id){
    	return sessions.get(id);
    }

}
