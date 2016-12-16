package net.is_bg.ltf.login;

import net.is_bg.ltfn.commons.old.models.user.User;




public class LoginResult {
	private boolean succeeded;
	private String status;
	private User curUser;

	public LoginResult(boolean succeeded, 
	                   String status, 
	                   User curUser) {
		super();
		this.succeeded = succeeded;
		this.status = status;
		this.curUser = curUser;
	}


	// getters & setters ---------------------------------------------------------------
	public boolean isSucceeded() {
		return succeeded;
	}
	public String getStatus() {
		return status;
	}
	public User getCurUser() {
		return curUser;
	}


	public void setCurUser(User curUser) {
	    this.curUser = curUser;
	}
}
