package net.is_bg.ltf.login;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import net.is_bg.ltf.db.common.StoredProcedure;

public class EncryptionKeyForUserKeyStoredFunction extends StoredProcedure {
	private String userKey;
	private String encryptionKey;
	
	public EncryptionKeyForUserKeyStoredFunction(String userKey){
		this.userKey = userKey;
	}
	
	private static final String FUN_NAME = "{call userencryptionkey(?,?)}";
	 

	@Override
	protected String getProcedureName() {
		// TODO Auto-generated method stub
		return FUN_NAME;
	}

	@Override
	protected void setParameters(CallableStatement arg0) throws SQLException {
		// TODO Auto-generated method stub
		arg0.setString(1, userKey);
		arg0.registerOutParameter(2, Types.VARCHAR);
	}
	
	@Override
	protected void retrieveResult(CallableStatement callableStatement)
			throws SQLException {
		// TODO Auto-generated method stub
		encryptionKey= callableStatement.getString(2);
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}
	
	

}
