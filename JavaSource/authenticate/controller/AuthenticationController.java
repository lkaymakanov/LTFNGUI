package authenticate.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/token")
public class AuthenticationController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/data")
	public String getString(){
		return "TOKEN_DATA";
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/verify")
	public String verifyToken(){
		return "TOKEN_VERIFY";
	}
	
	
}
