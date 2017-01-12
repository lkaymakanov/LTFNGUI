package net.is_bg.ltf.login;

import net.is_bg.ltf.db.common.AbstractMainDao;
import net.is_bg.ltf.db.common.interfaces.IConnectionFactory;

public class LoginDao extends AbstractMainDao {

	/**
     * 
     */
    private static final long serialVersionUID = 1308409886230318013L;

	public LoginDao(IConnectionFactory factory) {
		super(factory);
	}

	public LoginResult loginResult(String username, String password, String dbResourceName) {
		LoginStoredFunction loginFunction = new LoginStoredFunction(username, password);
		execute(loginFunction, dbResourceName);
		LoginResult result = loginFunction.getResult();
		CurrUserSelect currUserSelect = new CurrUserSelect(result.getCurUser().getId());
		execute(currUserSelect, dbResourceName);
		result.setCurUser(currUserSelect.getUser());
		return result;
	}
	
	
	public String getEncryptionKey(String userKey, String defdbCon){
		EncryptionKeyForUserKeyStoredFunction f = new EncryptionKeyForUserKeyStoredFunction(userKey);
		execute(f, defdbCon);
		return f.getEncryptionKey();
	}
	
	public LoginResult loginResult(String username, String password) {
		return loginResult(username,  password, null);
	}
}
