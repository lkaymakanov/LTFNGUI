package net.is_bg.ltf;

import java.util.Date;


public class ApplicationGlobals {
	
	private static ApplicationGlobals gl;
	private long startTime;
	
	static{
		init();
	}

	private static  void init(){
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
	
}
