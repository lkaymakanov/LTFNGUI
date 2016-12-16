package authenticate;

import net.is_bg.ltf.ApplicationGlobals;
import net.is_bg.ltf.login.LoginResult;
import net.is_bg.ltf.security.Sha512;
import authenticate.AuthenticationException;
import authenticate.AuthenticationException.AuthenticationExceptionBuilder;
import authenticate.IAuthentication;
import authenticate.IAuthenticationFactory;
import authenticate.UserPassAuthentication;

/***
 * A user password AuthenticationFactory!!!
 * @author lubo
 *
 */
class UserPassAuthenticationFactory implements IAuthenticationFactory<LoginResult> {
	
	private UserPassAuthentication<LoginResult> up;
	
	public UserPassAuthenticationFactory(String user, String pass, String dbConn){
		up = new UserPassAuthenticationInner(user, pass, dbConn);
	}

	@Override
	public IAuthentication<LoginResult> getAuthentication() {
		// TODO Auto-generated method stub
		return up;
	}
	
	static class UserPassAuthenticationInner extends UserPassAuthentication<LoginResult>{
		private String dbConnection;
		public UserPassAuthenticationInner(String user, String pass, String dbConnection) {
			super(user, pass);
			this.dbConnection = dbConnection;
		}

		@Override
		public LoginResult authenticate() throws AuthenticationException {
			// TODO Auto-generated method stub
			//here check user pass
			LoginResult loginResult = ApplicationGlobals.getApplicationGlobals().getServiceLocator().getLoginDao().loginResult(getCredentials().getUser(), 
					new Sha512().digest(getCredentials().getPass()), dbConnection);
			
			if (!loginResult.isSucceeded()) {
				 AuthenticationExceptionBuilder<LoginResult> bd = new AuthenticationExceptionBuilder<LoginResult>();
				 bd.setData(loginResult);
				 AuthenticationException e =  bd.build();
				 throw e;
			}
			return loginResult;
		}
	}
	
}
