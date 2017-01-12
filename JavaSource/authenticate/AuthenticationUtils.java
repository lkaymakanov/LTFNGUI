package authenticate;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ConnectionLoader;
import net.is_bg.ltf.ConnectionLoader.DBUrlAttributes;
import net.is_bg.ltf.SessionBean;
import net.is_bg.ltf.login.LoginResult;
import net.is_bg.ltfn.commons.old.models.user.User;
import token.TokenConstants;
import authenticate.AuthenticationException.AuthenticationExceptionBuilder;

import com.cc.rest.client.enumerators.IPARAM;
import com.cc.rest.client.enumerators.IREST_PATH;

/**
 * Authentication Utils !!!
 * @author lubo
 *
 */
public class AuthenticationUtils {
	

	
	
	/**
	 * Gets a user password based authentication!!!
	 * @param user
	 * @param pass
	 * @param dbConnName
	 * @return
	 */
	private static IAuthentication<LoginResult>  getUserPassAuthenticator(String user, String pass, String dbConnName){
		return new UserPassAuthenticationFactory(user, pass, dbConnName).getAuthentication();
	}
	

	
	/***
	 * Check if user is logged!!!
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws IOException
	 */
	public static UserNavigationPage checkifLogged(HttpServletRequest httpServletRequest) throws IOException{
		return new HpptServletRequestAuthentication(httpServletRequest).checkiflogged();
	}
	
	
	/***
	 * Authentication based on user & password!!!
	 * @param username
	 * @param password
	 * @param errorMap 
	 * @param dbCon
	 * @return
	 * @throws AuthenticationException
	 */
	public static LoginResult userPassAuthentication(String username, String password, int dbIndex, Map<String, String> errorMap) throws AuthenticationException{
		boolean processLogin = true;
		String errMsg = "";
		LoginResult loginResult;
		DBUrlAttributes attrib =  ConnectionLoader.getConnectionLoader().getMapConnection().get(dbIndex);

		if (username == null || username.length() < 3) {
			errMsg += errorMap.get("userLess3");
			processLogin = false;
		} else if (username.length() > 60) {
			errMsg += errorMap.get("userOver60");
		}

		if (password == null || password.length() < 3) {
			errMsg += errorMap.get("passLess3");
		} else if (password.length() > 60) {
			errMsg += errorMap.get("passOver60");
			//focus = "mainForm:password";
			processLogin = false;
		}
		
		if (attrib == null) {
			errMsg += errorMap.get("err.no.connection.with.db");
			processLogin = false;
		}
		

		if (!processLogin) {
			 AuthenticationExceptionBuilder<LoginResult> bd = new AuthenticationExceptionBuilder<LoginResult>();
			 loginResult = new LoginResult(false, errMsg, null);
			 bd.setData(loginResult);
			 AuthenticationException e =  bd.build();
			 throw e;
		}
		
	
		
		//проверка дали е избран TNS
		if (attrib.tmpTns.equals("-1")){
			 errMsg+=errorMap.get(("err.no.tns"));
			 AuthenticationExceptionBuilder<LoginResult> bd = new AuthenticationExceptionBuilder<LoginResult>();
			 loginResult = new LoginResult(false, errMsg, null);
			 bd.setData(loginResult);
			 AuthenticationException e =  bd.build();
			 throw e;
		}
		
		//проверка дали има налична база данни
		if (attrib.tmpTns.equals("@::")){
			 errMsg+=errorMap.get(("err.no.connection.with.db"));
			 AuthenticationExceptionBuilder<LoginResult> bd = new AuthenticationExceptionBuilder<LoginResult>();
			 loginResult = new LoginResult(false, errMsg, null);
			 bd.setData(loginResult);
			 AuthenticationException e =  bd.build();
			 throw e;
		}
		IAuthentication<LoginResult> auth = getUserPassAuthenticator(username, password, attrib.defDbCon);
		return auth.authenticate();
	}
	

	
	/**The main path pointing to update center jersey servlet*/
	private final static IREST_PATH MAIN_PATH = new  IREST_PATH() {
		@Override
		public String getPath() {
			// TODO Auto-generated method stub
			return TokenConstants.TOKEN_DATA_PATH;
		}
	}; 
	
	private final static IPARAM TOKEN_PARAM  = new IPARAM() {
		@Override
		public String getStringValue() {
			// TODO Auto-generated method stub
			return TokenConstants.TOKEN_ID_PARAM_NAME;
		}
	};
	
	
	/***
	 * Logs user & attach data to user session!!!
	 * @param httpServletRequest
	 * @param curUser
	 * @param attrib
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static SessionBean logUser(HttpServletRequest httpServletRequest, User curUser, int dbIndex) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException{
		SessionBean sb = AppUtil.getSessionBeanFromSession(httpServletRequest.getSession());
		if(sb.getVisit() != null && sb.getVisit().getCurUser()!=null)  throw new RuntimeException("Session data is already attached to user...");
		DBUrlAttributes attrib =   ConnectionLoader.getConnectionLoader().getMapConnection().get(dbIndex);
		sb =  SessionBean.attachSessionDataToLoggedUser(curUser, attrib);
		
		//insert into session table
		/*Session s = new  Session();
		s.setUser(sb.getVisit().getCurUser());
		String ipAddress = AppUtil.getIpAdddress(httpServletRequest);
		String sessionId = httpServletRequest.getSession().getId();
		s.setIpaddress(ipAddress);
		s.setSessionappid(sessionId);
		s.setCreatetime(new Date());
		ApplicationGlobals.getApplicationGlobals().getLocator().getSessionDao().insertSession(s);
		
		//allow access to session page
		sb.getVisit().addAllowedPage("/pages/session.jsf");
		
		//increment users
		ApplicationGlobals.getApplicationGlobals().incrementUsers();*/
		
		return sb;
	}
}
