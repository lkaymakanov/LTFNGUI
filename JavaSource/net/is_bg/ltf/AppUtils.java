package net.is_bg.ltf;

import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

public class AppUtils {

	/**
	 * Returns the faces instance!!!
	 * @return
	 */
	public static FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	/**
	 * Returns the application!!!
	 * @return
	 */
	public static Application getApplication(){
		return FacesContext.getCurrentInstance().getApplication();
	}
	
	/**
	 * Returns the expression factory!!!
	 * @return
	 */
	public static ExpressionFactory getExpressionFactory()   {
		return FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
	}

}
