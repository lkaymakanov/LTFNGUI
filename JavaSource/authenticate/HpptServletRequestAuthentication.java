package authenticate;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import token.TokenConstants.AUTHENTICATION_TYPE;
import net.is_bg.ltf.AppConstants;
import net.is_bg.ltf.SessionBean;
import net.is_bg.ltf.Visit;
import net.is_bg.ltfn.commons.old.models.user.User;

class HpptServletRequestAuthentication {
	
	private static final String LOGIN_PAGE = "pages/login.jsf";
	private static final String MAIN_FORM_PAGE = "pages/mainform.jsf";
	private static final String EMPTY_PAGE = "/pages/empty.jsf";
	private HttpServletRequest httpServletRequest;

	
	HpptServletRequestAuthentication(HttpServletRequest httpServletRequest){
		this.httpServletRequest = httpServletRequest;
	}
	
	

	/**
	 * Returns login page base on authentication scheme
	 * @param t
	 * @return
	 */
	private  String getLoginPage(AUTHENTICATION_TYPE t){
		if(t == AUTHENTICATION_TYPE.USERPASS) return LOGIN_PAGE;
		return null;
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	private  Visit getVisitFromHttpServletRequest(){
		if(httpServletRequest == null) return null;
		SessionBean sb = (SessionBean)httpServletRequest.getSession(true).getAttribute(AppConstants.SESSION_BEAN);
		return sb == null ? null :sb.getVisit();
	}
	
	
	
	/**
	 * 
	 */
	UserNavigationPage checkiflogged() throws IOException{
		Visit v = getVisitFromHttpServletRequest();
		User u = null;
		if(v!= null && v.getCurUser()!=null) u = v.getCurUser();
		UserNavigationPage userNav = new UserNavigationPage();
		
		userNav.user = u;
		if(u == null){
			//user is not logged and page is not login page - navigate to login page!!!!
			if(!httpServletRequest.getRequestURL().toString().contains(getLoginPage(AUTHENTICATION_TYPE.USERPASS))){
				userNav.navPage = getLoginPage(AUTHENTICATION_TYPE.USERPASS);
				//httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + LOGIN_PAGE);
				//return  false;
			}
		}else{
			//there is user logged & we opened login -- goto main form
			if(httpServletRequest.getRequestURL().toString().contains(getLoginPage(AUTHENTICATION_TYPE.USERPASS))){
				userNav.navPage = MAIN_FORM_PAGE;
				//httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + MAIN_FORM_PAGE);
				//return true;
			}
		}
		return userNav;
	}
	
/*	*//**
	 * Token based authentication
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws IOException
	 *//*
	@SuppressWarnings("unused")
	void tokenAuthentication() throws IOException{
		new TokenAuthenticationFactory(httpServletRequest).
		//System.out.println("TOKEN:" +  getRequestParam("token"));
		//System.out.println("USERID:" +  getRequestParam( "uid"));
	}*/

	
	
	
}
