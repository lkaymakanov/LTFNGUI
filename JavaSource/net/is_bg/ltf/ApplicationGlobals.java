package net.is_bg.ltf;

import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;


public class ApplicationGlobals {
	
	private static ApplicationGlobals gl;
	private long startTime;
	private  Context initContext = null; 
	
	static{
		init();
	}

	private static  void init(){
		if(gl != null) return;
		synchronized (ApplicationGlobals.class) {
			if(gl == null) gl = new ApplicationGlobals();
			gl.startTime = new Date().getTime();
		}
	}
	
	public static ApplicationGlobals getApplicationGlobals(){
		return gl;
	}

	public long getStartTime() {
		return startTime;
	}

	
	/**Initializes & returns the java context*/
	public  Context getContext(){
		if(initContext != null) return initContext;
		try{
			initContext = (Context)new InitialContext().lookup("java:/comp/env");
			return initContext;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
