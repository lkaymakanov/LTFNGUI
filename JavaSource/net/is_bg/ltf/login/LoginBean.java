package net.is_bg.ltf.login;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import net.is_bg.ltf.AppConstants;
import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ConnectionLoader;
import net.is_bg.ltf.ConnectionLoader.DBUrlAttributes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import authenticate.AuthenticationException;
import authenticate.AuthenticationUtils;

@RequestScoped
@ManagedBean
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 4263455566447280588L;
	private static final Log LOG = LogFactory.getLog(LoginBean.class);
	private final static ResourceBundle MSG_LOGIN = ResourceBundle.getBundle(AppConstants.MSG_LOGIN);
	private String username;
	private String password;
	private String errMsg;
	//private Boolean isCertVerified;
	private boolean secured = false;
	private ConnectionLoader connectionLoader;
	private List<SelectItem> dbConns;
	private Map<Integer, DBUrlAttributes> mapConnection;
	private int  defDbConn = 0;  // "jdbc/ltf";
	
	public final static Map<String, String> ERROR_MAP = new HashMap<String, String>();
	{
		ERROR_MAP.put("userLess3", MSG_LOGIN.getString("userLess3") + AppConstants.MODAL_NEW_LINE);
		ERROR_MAP.put("userOver60", MSG_LOGIN.getString("userOver60") + AppConstants.MODAL_NEW_LINE);
		ERROR_MAP.put("passLess3", MSG_LOGIN.getString("passLess3") + AppConstants.MODAL_NEW_LINE);
		ERROR_MAP.put("passOver60", MSG_LOGIN.getString("passOver60") + AppConstants.MODAL_NEW_LINE);
		ERROR_MAP.put("err.no.connection.with.db", MSG_LOGIN.getString("err.no.connection.with.db") + AppConstants.MODAL_NEW_LINE);
		ERROR_MAP.put("err.no.tns", MSG_LOGIN.getString("err.no.tns"));
	};
	
	

	
	public LoginBean() {
		
		initConn();
		System.out.println("Login Bean initialized....");
	}
	
	public void initConn(){
		connectionLoader = ConnectionLoader.getConnectionLoader();
		mapConnection = connectionLoader.getMapConnection();
		dbConns = connectionLoader.getDbConns();
		defDbConn = connectionLoader.getDefDbConn();
	}
	
	
	
	
	//-----------------------------------------------------------------------------
	public String login() throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
		HttpServletRequest request = (HttpServletRequest) AppUtil.getFacesContext().getExternalContext().getRequest();
		//login кодиран case sensitive !!!
	    LoginResult loginResult = null;
		try {
		    loginResult = AuthenticationUtils.userPassAuthentication(username, password, defDbConn, ERROR_MAP);
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			loginResult = (LoginResult)e.getData();
			//modalDialog.setErrMsg(MSG_LOGIN.getString(loginResult.getStatus()));
			return null;
		}

		AuthenticationUtils.logUser(request,  loginResult.getCurUser(), defDbConn);
		return AppConstants.SUCCESS_OUTCOME;
	}
	
	
	// getters & setters ----------------------------------------------------------------
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public boolean isSecured() {
		return secured;
	}

	public void setSecured(boolean secured) {
		this.secured = secured;
	}

	public List<SelectItem> getDbConns() {
		return dbConns;
	}

	public void setDbConns(List<SelectItem> dbConns) {
		this.dbConns = dbConns;
	}
	
	public int getDefDbConn() {
		return defDbConn;
	}
	
	
	
	public void setDefDbConn(int defDbConn) {
		this.defDbConn = defDbConn;
	}


}