package net.is_bg.ltf;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.is_bg.ltf.ConnectionLoader.DBUrlAttributes;
import net.is_bg.ltfn.commons.old.models.user.User;

@SessionScoped
@ManagedBean
public class SessionBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8340410909113766529L;
	private Visit visit;
	

	public Visit getVisit() {
		return visit;
	}

	

	public static SessionBean attachSessionDataToLoggedUser(User curUser, DBUrlAttributes attrib) {
		// TODO Auto-generated method stub
		/*if()
		AppUtil.getse*/
		return null;
	}
}
