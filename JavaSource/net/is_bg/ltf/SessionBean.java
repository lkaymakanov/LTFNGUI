package net.is_bg.ltf;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;

import token.ITokenData;
import token.TokenData.TokenDataBuilder;
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
	private ITokenData tokenData; 
	

	public Visit getVisit() {
		return visit;
	}

	

	public ITokenData getTokenData() {
		return tokenData;
	}



	public static SessionBean attachSessionDataToLoggedUser(User curUser, DBUrlAttributes attrib) {
		// TODO Auto-generated method stub
		ValueExpression exp = AppUtil.createValueExpression("#{sessionBean}", SessionBean.class);
		SessionBean sb = (SessionBean) exp.getValue(AppUtil.getFacesContext().getELContext());
		HttpServletRequest request = AppUtil.getRequest();
		TokenDataBuilder tokenDataBuilder = new  TokenDataBuilder();
		tokenDataBuilder.setRequestIp(AppUtil.getIpAdddress(request));
		tokenDataBuilder.setTokenId(request.getSession().getId());
		tokenDataBuilder.setTokenSessionId(request.getSession().getId());
		tokenDataBuilder.setUserId(curUser.getId() + "");
		
		sb.tokenData = tokenDataBuilder.build();
		
		Visit tmpVisit = new Visit(attrib.tmpDbName);
		tmpVisit.setTns(attrib.tmpTns);
		tmpVisit.setDefDbConn(attrib.defDbCon);
		tmpVisit.setDefDbConnReadOnly(attrib.defDbCon);
		tmpVisit.setCurUser(curUser);
	
		tmpVisit.setRemoteAddr(request.getRemoteAddr());
		tmpVisit.setRemoteHost(request.getRemoteHost());
		tmpVisit.setRemotePort(request.getRemotePort());
		tmpVisit.setRemoteUser(request.getRemoteUser());
		
		//read headers
		Map<String, String> headers = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String string = (String) headerNames.nextElement();
			headers.put(string, request.getHeader(string));
		}
		//tmpVisit.getVisitAdditionals().setUserAgent(headers.get("user-agent"));
		
		sb.visit = tmpVisit;
		return sb;
	}
}
