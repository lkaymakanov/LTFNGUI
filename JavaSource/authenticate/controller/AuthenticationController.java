package authenticate.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import token.TokenConstants;
import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.SessionBean;
import net.is_bg.ltf.security.ApplicationSessionManager;

@Path("/token")
public class AuthenticationController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/data")
	public TokenDataUserData getTokenData(@Context UriInfo info) throws IOException{
		List<String> s = info.getQueryParameters().get(TokenConstants.TOKEN_ID_PARAM_NAME);
		String tokenId = (s == null || s.size() < 1 ? null : s.get(0));
		SessionBean sb = AppUtil.getSessionBeanFromSession(ApplicationSessionManager.getSession(tokenId));
		TokenDataUserData dk = new TokenDataUserData();
		dk.setUserKey(sb.getVisit().getCurUser().getOther());
		dk.setDefDbCon(sb.getVisit().getDefDbConn());
		//(TokenUtils.serialize(sb.getTokenData()));
		dk.setTokenData(sb.getTokenData());
		return dk;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/verify")
	public Boolean verifyToken(@Context UriInfo info){
		List<String> s = info.getQueryParameters().get(TokenConstants.TOKEN_ID_PARAM_NAME);
		String [] a  =  info.getPath(true).split("/");
		String tokenId = (s == null || s.size() < 1 ? null  : s.get(0));
		HttpSession session = ApplicationSessionManager.getSession(tokenId);
		if(session== null) return false;
		SessionBean sb = AppUtil.getSessionBeanFromSession(session);
		if(sb == null || sb.getTokenData() == null)  return false;
		return true;
	}
	
}
