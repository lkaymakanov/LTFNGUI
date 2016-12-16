package net.is_bg.ltf.security;

import java.io.IOException;

import javax.ejb.SessionBean;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import net.is_bg.ltf.AppUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SecurityFilter implements Filter {
	
	protected Log LOG = LogFactory.getLog(this.getClass());
	//private AllowedResources defaultResources = new AllowedResources();

	public void init(FilterConfig filterConfig) throws ServletException {
		//nothing
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if ((request instanceof HttpServletRequest)	&& (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;
			
			String url = httpServletRequest.getRequestURL().toString();
			String context = httpServletRequest.getContextPath();
			String uri = httpServletRequest.getRequestURI().toString();
			
			System.err.println("entered in SecurityFilter do filter.............");
			System.out.println("Page is " + url);
			System.out.println("Context  is " + context);
			System.out.println("Uri  is " + uri);
			
			Object o = AppUtil.getSessionBeanFromSession(httpServletRequest.getSession());
			System.out.println("Session Bean = " + o);
			
			/*
			Enumeration headerNames = httpServletRequest.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				System.out.print("<br/>Header Name: <em>" + headerName);
				String headerValue = httpServletRequest.getHeader(headerName);
				System.out.print("</em>, Header Value: <em>" + headerValue);
				System.out.println("</em>");
			}*/

			
			//skip params
			/*if(uri.contains(".jsf")) uri = uri.split(".jsf")[0] + ".jsf";
			Visit v = AppUtil.getVisitFromHttpServletRequest(httpServletRequest);
			User u = null;
			if(v!= null && v.getCurUser()!=null) u = v.getCurUser();
			
			//there is user logged 
			if(u!=null){
				//there is user logged & we opened login -- goto main form
				if(url.contains(RESOURCES.LOGIN_PAGE.URL())){
					httpServletResponse.sendRedirect(RESOURCES.MAIN_FORM_PAGE.URL());
					return;
				}
				//AppUtil.processRequestParams(AppUtil.getRequestParameters(httpServletRequest), v);
			}
			//no user logged check for page is allowed
			if (!isPageAlowed(uri, httpServletRequest)) {
				String restrictedpage = RESOURCES.LOGIN_PAGE.URL();
				LOG.error("Page " + httpServletRequest.getRequestURI() + " is forbidden!");
				if(isSessionInvalid(httpServletRequest)){  //session is not valid 
					LOG.debug("Redirecting to no-authorized page: " + restrictedpage);  //login page
				}
				else{
					if(u != null) restrictedpage = RESOURCES.RESTRICTED_PAGE.URL();   //goto restricted page  if user is logged
					LOG.debug("Redirecting to no-authorized page: " +  restrictedpage);   // not allowed page but user is  logged on
				}
				
				//if(isSessionInvalid(httpServletRequest)) httpServletRequest.getSession().invalidate();
				httpServletResponse.sendRedirect(restrictedpage + RESOURCES.REASON_FORBIDDEN.URL());
				return;
			}*/
				
			
			
			

			// is session expire control required for this request?
			if (isSessionControlRequiredForThisResource(httpServletRequest)) {

				// is session invalid?
				if (isSessionInvalid(httpServletRequest)) {
					/*String timeoutUrl =  RESOURCES.TIMEOUT_PAGE.URL();
					LOG.debug("Session is invalid! Redirecting to timeout page: " + timeoutUrl);
					httpServletResponse.sendRedirect(timeoutUrl + RESOURCES.REASON_EXPIRED.URL());*/
					return;
				}
			}
						
			
			
			try {
				filterChain.doFilter(request, response);
			} catch (Exception e) {

				if (e instanceof ServletException) {
					if (httpServletRequest.getRequestURI().contains("/pages/help")) {
						LOG.error("Page " + httpServletRequest.getRequestURI() + " is missing!");
						//LOG.debug("Redirecting to missed-help page: " +  RESOURCES.NO_HELP_PAGE.URL());
						
						//httpServletResponse.sendRedirect( RESOURCES.NO_HELP_PAGE.URL());
					} else {
						//LOG.debug("Redirecting to error page: " +  RESOURCES.ERROR_PAGE.URL());
						//throw new javax. javax.faces.FacesException(e.getCause());
/*						
						LOG.error("Page " + httpServletRequest.getRequestURI() + " is missing!");
						LOG.debug("Redirecting to no-authorized page: " + httpServletRequest.getContextPath() + "/" + RESTRICTED_PAGE);
						
						httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + RESTRICTED_PAGE + REASON_FORBIDDEN);
						httpServletRequest.getSession().invalidate();
*/						
					}
				} else {
					//LOG.debug("Redirecting to error page: " +  RESOURCES.ERROR_PAGE.URL());
					
					//rise exception to error page
					//throw new javax.faces.FacesException(e.getCause());
//					httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/" + ERROR_PAGE);
				}
				
				return;
			}
		}
	}
	

	/*
	 * session shouldn't be checked for some pages. For example: for timeout
	 * page.. Since we're redirecting to timeout page from this filter, if we
	 * don't disable session control for it, filter will again redirect to it
	 * and this will be result with an infinite loop...
	 */
	private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
		String requestPath = httpServletRequest.getRequestURI();
		return false;// !requestPath.contains(RESOURCES.TIMEOUT_PAGE.URL());
	}

	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null) && !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}
	
	public boolean isPageAlowed(String uri, HttpServletRequest httpServletRequest) {
		/*String requestPath = httpServletRequest.getRequestURI();
		requestPath = requestPath.split(";")[0];
		requestPath = StringUtils.cutFaceUrl(requestPath);*/
		//String uri = httpServletRequest.getRequestURI().toString();
		
		//check in default non login pages
		/*if(defaultResources.isAllowedResource(uri)) return true;
		
		//get the visit from request
		Visit v = AppUtil.getVisitFromHttpServletRequest(httpServletRequest);
		
		if(v == null ) return false;
		
		//files servlet request
		if(uri.contains(AppConstants.FILE_SERVLET_PATH_PREFIX)){
			return true;
		}*/
		
		//check the visit set of pages if visit is not null otherwise return false
		return true;//v.isAllowedResource(StringUtils.cutFaceUrl(uri)) || (v.isAllowedResource(uri)); 
	}
	
	
	

	public void destroy() {

	}

}
