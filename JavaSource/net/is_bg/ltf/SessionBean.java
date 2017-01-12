package net.is_bg.ltf;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import authenticate.controller.AuthenticationEncryptionUtils;


import token.ITokenData;
import token.TokenUtils;
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



	public static SessionBean attachSessionDataToLoggedUser(User curUser, DBUrlAttributes attrib) throws IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, SecurityException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
		// TODO Auto-generated method stub
		ValueExpression exp = AppUtil.createValueExpression("#{sessionBean}", SessionBean.class);
		SessionBean sb = (SessionBean) exp.getValue(AppUtil.getFacesContext().getELContext());
		HttpServletRequest request = AppUtil.getRequest();
		TokenDataBuilder tokenDataBuilder = new  TokenDataBuilder();
		tokenDataBuilder.setRequestIp(AppUtil.getIpAdddress(request));
		tokenDataBuilder.setTokenId(request.getSession().getId());
		tokenDataBuilder.setTokenSessionId(request.getSession().getId());
		tokenDataBuilder.setUserId(curUser.getId() + "");
		
		
		//generate token
		ITokenData tdata = tokenDataBuilder.build();
		
		//encrypt token with user key
		tokenDataBuilder = new TokenDataBuilder();
		String userKey = curUser.getOther();
		tokenDataBuilder.setUserId(userKey);
		String userEncryptionKey = ApplicationGlobals.getApplicationGlobals().getServiceLocator().getLoginDao().getEncryptionKey(userKey, attrib.defDbCon);
		byte [] data = AuthenticationEncryptionUtils.getEncoderFactory(userEncryptionKey).getEncoder().encode(TokenUtils.serialize(tdata));
		tokenDataBuilder.setAdditionalData(data);
		sb.tokenData = tokenDataBuilder.build();
		
		ObjectMapper om = new ObjectMapper();
		String s = om.convertValue(sb.tokenData.getAdditionalData(), String.class);
		
		/*//convert to bytes 
		byte [] b = om.convertValue(s, byte[].class);
		AuthenticationEncryptionUtils.getDecoderFactory(userEncryptionKey).getDecoder().decode(b);
		ITokenData ittt =	TokenUtils.deserialize(b, b.length, ITokenData.class);*/
		
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
