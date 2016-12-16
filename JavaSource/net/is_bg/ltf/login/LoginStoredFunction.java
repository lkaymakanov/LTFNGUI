package net.is_bg.ltf.login;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.is_bg.ltf.db.common.StoredProcedure;
import net.is_bg.ltfn.commons.old.models.user.User;

public class LoginStoredFunction extends StoredProcedure {
    private static final String USER_FUN_NAME = "{call USER_VER2(?,?,?,?)}";
    private LoginResult result;
    private String username;
    private String password;

    // private User user = new User();

    public LoginStoredFunction(String username, String password) {
	super();
	this.username = username;
	this.password = password;
    }

    @Override
    protected String getProcedureName() {
	return USER_FUN_NAME;
    }

    @Override
    protected void retrieveResult(CallableStatement callableStatement) throws SQLException {
	User curUser = new User();
	curUser.setId(callableStatement.getLong(4));
	result = new LoginResult(callableStatement.getString(3).equals(SUCCESS), callableStatement.getString(3), curUser);
    }

    @Override
    protected void setParameters(CallableStatement callableStatement) throws SQLException {
	callableStatement.setString(1, username);
	callableStatement.setString(2, password);
	callableStatement.registerOutParameter(3, Types.VARCHAR);
	callableStatement.registerOutParameter(4, Types.BIGINT);
    }

    public LoginResult getResult() {
	return result;
    }

}
