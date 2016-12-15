package authenticate.controller;

import com.cc.rest.jersey.CCRestResources;

public class AuthenticationResources extends CCRestResources{
	public static final String CONTROLLER_PACKAGE = "authenticate.controller";
	
	public AuthenticationResources(){
		packages(CONTROLLER_PACKAGE);
	}
	
}
