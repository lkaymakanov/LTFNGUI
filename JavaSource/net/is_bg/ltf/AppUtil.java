package net.is_bg.ltf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.is_bg.ltf.security.Sha512;

public class AppUtil {

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
	
	/**
     * Converts Object to bytes!
     * @param o Object to convert to bytes
     * @return byte array of object
     * @throws IOException 
     */
	 public static byte[] serialize(Object o) throws IOException  {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os;
			os = new ObjectOutputStream(out);
			os.writeObject(o);
			return out.toByteArray();
	 }

    /**
     * Converts byte array to Object!
     * 
     * @param data
     *            Byte array to be converted to Object
     * @param size
     *            The size of array in bytes
     * @return Converted Object - Null if fails
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object deserialize(byte[] data, int size) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, size);
		ObjectInputStream is;
		is = new ObjectInputStream(in);
		return is.readObject();
    }
    
    /**
     * Converts byte array to Object!
     * 
     * @param data
     *            Byte array to be converted to Object
     *            The size of array in bytes
     * @return Converted Object - Null if fails
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream in = new ByteArrayInputStream(data, 0, data.length);
		ObjectInputStream is;
		is = new ObjectInputStream(in);
		return is.readObject();
    }
	
    /***
     * Restores  an object from a File!
     * @param f
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object  deserialize (File f) throws IOException, ClassNotFoundException{
    	FileInputStream fin = new FileInputStream(f);
    	byte b [] = new byte[(int)f.length()]; 
    	fin.read(b, 0, b.length);
    	Object o = deserialize(b, b.length);
    	fin.close();
    	return o;
    }
	
    /**
	 * Returns IDN with stars for last 4 chars
	 */
	public static String getIdnStars(String idn){
		 if (idn.toString().length() > 6)
				return idn.toString().substring(0, 6) + idn.toString().substring(6).replaceAll(".", "*");
			    else
			  return idn.toString();
	}
	
	
	public static boolean restrictChar(String text){
		if(text.contains("\'") || text.contains("|")){
			return true;
		}else{
			return false;
		}
	}
	
	public static Object getValueForExpression(String exp){
		ExpressionFactory expFactory = FacesContext.getCurrentInstance().getApplication().getExpressionFactory();
		ELContext expContext = FacesContext.getCurrentInstance().getELContext();
		ValueExpression valExp = expFactory.createValueExpression(expContext, exp, Object.class);
		return valExp.getValue(expContext);
	}
	

	
	public static ExternalContext getExternalContext(){
		if(getFacesContext() == null) return null;
		return getFacesContext().getExternalContext();
	}
	
	
	public static HttpServletRequest getRequest(){
		if(getExternalContext() == null) return null;
		return (HttpServletRequest)getExternalContext().getRequest();
	}
	
	
	public static HttpServletResponse getResponse(){
		if(getExternalContext() == null) return null;
		return (HttpServletResponse)getExternalContext().getResponse();
	}
	

	/**
	 * Returns the session
	 * @param create
	 * @return
	 */
	public static HttpSession getSession(boolean create){
		if(getRequest() == null) return null;
		return  (HttpSession) getRequest().getSession(create);
	}
	
	
	/**
	 * Returns the application session map.
	 * @return
	 */
	public static Map<String, Object> getSessionMap(){
		if(getExternalContext() == null) return null;
		return getExternalContext().getSessionMap();
	}
	
	/**
	 * Call  StackTrace to print methods' frame where it's  invoked and where it' s located in source file.
	 *
	 * @param level the level
	 * @level parameter level shows how many levels down the stack to be printed. If no param all methods on stack are printed!
	 */
	public static String StackTrace(int... level){
		StackTraceElement[] threadstacktrace =  Thread.currentThread().getStackTrace();
		String msg= "";
		if(threadstacktrace == null ) return msg;
		int l;
		 //show everything if no param
		if(level == null || level.length == 0)  l = threadstacktrace.length;  
		else {
			//else take the least of length and in parameter level
			l = threadstacktrace.length < level[0] ? threadstacktrace.length : level[0];
		}
		
		//print  stack trace
		if(threadstacktrace != null) {
			for(int i = 0; i < l; i++){
				if(threadstacktrace[i] == null)  continue;
				
				//get each stack element
				StackTraceElement el = threadstacktrace[i];
				
				//message to show
				msg  = String.format("classaname = %s, method name = %s, filename = %s, lineNumber = %d", el.getClassName(), el.getMethodName(), el.getFileName(), el.getLineNumber());
				
				System.err.println(msg);
			}
	     }
		
		return  msg;
	}
	
	 public static ValueExpression createValueExpression(String valueExpression,
			 Class<?> valueType) {
    	return getApplication() == null ? null :
	  	 getApplication().getExpressionFactory().createValueExpression(getFacesContext().getELContext(),
						     valueExpression,
						     valueType);
     }
	 
	 
	/* public static SessionBean getSessionBeanForSessionId(String sessionId){
		 HttpSession session =	ApplicationSessionManager.getSession(sessionId);
		 if(session == null) return null;
		 SessionBean sb = (SessionBean)session.getAttribute(AppConstants.SESSION_BEAN);
		 return sb;
	 }*/
		
	 public static SessionBean getSessionBeanFromSession(HttpSession session){
		 if(session == null) return null;
		 SessionBean sb = (SessionBean)session.getAttribute(AppConstants.SESSION_BEAN);
		 return sb;
	 }
	 
	 
	public static SessionBean getSessionBeanFromFacesContext(){
		     if(getFacesContext() == null) return null;
		     if(getFacesContext().getExternalContext() == null) return null;
			 SessionBean sb = (SessionBean) getFacesContext().getExternalContext().getSessionMap().get(AppConstants.SESSION_BEAN);
			 return sb;
	}
	 
		
	public static void processRequestParams(Map<String, String[]> map){
	}
		
	public static String passwordGenerator(int lengt) {
		return passwordGenerator(lengt, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}
	
	
	public static String passwordGenerator(int lengt, String alphabet) {
		String password = "";
		for (int i = 0; i < lengt; i++) {
			int rand = (int) (Math.random() * alphabet.length());
			password += alphabet.charAt(rand);
		}
		return password;
	}
	
	public static  String getSha512(String input){
		return new  Sha512().digest(input);
	}

    
	// ------------------------------------------------------------------------------------
	public static boolean checkLnc(String lnc){
		int[] tegla = { 21, 19, 17, 13, 11, 9, 7, 3, 1 };
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			try {
				sum += Integer.valueOf(lnc.substring(i, i + 1)) * tegla[i];
			} catch (Exception e) {
				return false;
			}
		}
		int control;
		try {
			control = Integer.valueOf(lnc.substring(9, 10));
		} catch (Exception e) {
			return false;
		}
		if (control == (sum % 10))
			return true;
		
		return false;
	}
	
	// ------------------------------------------------------------------------------------
	public static boolean checkEgn(String idn) {
		int[] tegla = { 2, 4, 8, 5, 10, 9, 7, 3, 6 };
		int sum = 0;
		for (int i = 0; i < 9; i++) {
			try {
				sum += Integer.valueOf(idn.substring(i, i + 1)) * tegla[i];
			} catch (Exception e) {
				return false;
			}
		}
		int control;
		try {
			control = Integer.valueOf(idn.substring(9, 10));
		} catch (Exception e) {
			return false;
		}
		if (control == (sum % 11) % 10)
			return true;
		return false;
	}

	// ------------------------------------------------------------------------------------
	public static boolean checkBulstat(String bulstat) {
		int sum = 0;
		boolean flag = false;
		for (int i = 1; i < 9; i++) {
			try {
				sum += Integer.valueOf(bulstat.substring(i - 1, i)) * i;
			} catch (Exception e) {
				return false;
			}
		}
		int control;
		try {
			control = Integer.valueOf(bulstat.substring(8, 9));
		} catch (Exception e) {
			return false;
		}
		if (sum % 11 != 10) {
			if (control == sum % 11)
				flag = true;
		} else {
			sum = 0;
			for (int i = 3; i < 11; i++) {
				sum += Integer.valueOf(bulstat.substring(i - 3, i - 2)) * i;
			}
			if (control == sum % 11 % 10)
				flag = true;
		}
		if (flag && bulstat.length() == 9)
			return flag;
		if (flag && bulstat.length() == 13) {
			int[] arr1 = { 2, 7, 3, 5 };
			int[] arr2 = { 4, 9, 5, 7 };
			sum = 0;
			for (int i = 8; i < 12; i++) {
				try {
					sum += Integer.valueOf(bulstat.substring(i, i + 1))
							* arr1[i - 8];
				} catch (Exception e) {
					return false;
				}
			}
			try {
				control = Integer.valueOf(bulstat.substring(12, 13));
			} catch (Exception e) {
				return false;
			}
			if (sum % 11 != 10) {
				if (control == sum % 11)
					return true;
			} else {
				sum = 0;
				for (int i = 8; i < 12; i++) {
					sum += Integer.valueOf(bulstat.substring(i, i + 1)) * arr2[i - 8];
				}
				if (control == sum % 11 % 10)
					return true;
			}
		}
		return false;
	}
	//=---------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Check if number is between from & to boundaries including the boundaries themselves!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberInRangeIncludingBounds(T  number, T from, T to){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(from);
			int compareToTo = n.compareTo(to);
			if(compareToFrom == 0 || compareToTo ==0)  return true;  //equals one of the boundaries
			
			if(compareToFrom < 0){  //less than from
				return false;
			}
			
			if(compareToTo > 0){  //more than to
				return false;
			}
			return true;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number );
	}
	
	
	/**
	 * Check if number is between from & to boundaries excluding the boundaries themselves!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberInRangeExcludingBounds(T  number, T from, T to){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(from);
			int compareToTo = n.compareTo(to);
			if(compareToFrom == 0 || compareToTo ==0)  return false;  //equals one of the boundaries
			
			if(compareToFrom < 0){  //less than from
				return false;
			}
			
			if(compareToTo > 0){  //more than to
				return false;
			}
			return true;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	/***
	 * Checks if  a class is subclass to a given class!!!
	 * @param classTocheck
	 * @param baseClass
	 * @return
	 */
	public static boolean isSubClass(Class classTocheck, Class baseClass){
		try{
			classTocheck.asSubclass(baseClass);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	/**
	 * Check if number is less than eq!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberLessThan(T  number, T eq){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(eq);
			
			if(compareToFrom < 0){  //less than eq
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	
	/**
	 * Check if number is less than or equal to eq!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberLessThanOrEqual(T  number, T eq){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(eq);
			
			if(compareToFrom <=0){  //less than or eq
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	
	/**
	 * Check if number is more than eq!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberMoreThan(T  number, T eq){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(eq);
			
			if(compareToFrom > 0){  //more than eq
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	
	/**
	 * Check if number is more  than or equal eq!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberMoreThanOrEqual(T  number, T eq){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(eq);
			
			if(compareToFrom >=0){  //more than or eq
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	
	/**
	 * Check if number is equal to eq!
	 * @param number
	 * @param from
	 * @param to
	 * @return
	 */
	public static <T extends Number> boolean  testNumberEqual(T  number, T eq){
		if(number instanceof Comparable){
			Comparable<Number> n = (Comparable<Number>)number;
			int compareToFrom = n.compareTo(eq);
			
			if(compareToFrom == 0){  //more than eq
				return true;
			}
			return false;
		}
		throw new IllegalArgumentException("No comparable number passed to testNumberInRange " + number);
	}
	
	
	public static int getCurrentYear(){
		return new Date().getYear() + 1900; 
	}
	
	public static <T> boolean checkForOneNotNullElement(List<T> l){
		if(l != null && l.size()> 0 && l.get(0)!= null) return true;
		return false;
	}
	
	public static <T> T getFirstElement(List<T> l){
		return l.get(0);
	}
	


	

	/**
	 * get the ip address of the incoming request!!!
	 * @param request
	 * @return
	 */
	public static String getIpAddress(){
		return getIpAdddress(getRequest());
	}
	
	
	/**
	 * get the ip address of the incoming request!!!
	 * @param request
	 * @return
	 */
	public static String getIpAdddress(HttpServletRequest request){
		  String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   return ipAddress;
	}

	public static byte[] readFileContent(File file){
		return readFileContent(file, 1024*1024);
	}
	
	private static class ByteArray {
		ByteArray(byte []b, int size){
			this.b = b;
			this.size = size;
		}
	
		byte [] b;
		int size;
	}
	
	public static byte[] readFileContent(File file ,int bufferSize){
		// contain bytes read from file
	      //Vector fileBytes = new Vector();
		 List<ByteArray>  barrayList = new ArrayList<ByteArray>();
		 byte [] buffer = new byte [bufferSize];
		 int size = 0;
		 
	      // read contents from file 
	      try {
	         FileInputStream in = new FileInputStream( file );
	         // read bytes from stream.
	         int blength;
	        
	         while ((blength = in.read(buffer))!=-1) {
	        	 barrayList.add(new ByteArray(buffer, blength));
	        	 size+=blength;
	            //contents = ( byte )in.read();
	            //fileBytes.add( new Byte( contents ) );
	         }
	           
	         in.close();
	      } 
	      // handle IOException
	      catch ( IOException exception ) {
	         exception.printStackTrace();
	      }
	      
	      // create byte array from contents in Vector fileBytes
	      byte[] fileContent = new byte[ size];
	      
	      int offset = 0;
	      for ( int i = 0; i < barrayList.size(); i++ ) {
	    	  ByteArray b = barrayList.get(i);
	    	  
	    	  for(int j=0; j < b.size; j++){
	    		  fileContent[offset+j] = b.b[j];
	    	  }
	    	  offset+=b.size;
	      }
	      return fileContent;
	}
	
	/**Initializes & returns the java context*/
	public static Context getContext(){
		return ApplicationGlobals.getApplicationGlobals().getContext();
	}
	
	/**
	 * 
	 * @param beanName
	 * @param type
	 * @return
	 */
	public static <T> T  getBean(String beanName, Class<T> type){
		if(getFacesContext()== null) return null;
		ELContext elContext = getFacesContext().getELContext();
		if(elContext == null) return null;
		return  (T) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, beanName);
	}
	
	/***
	 * Manage icon paths!!!
	 * @author lubo
	 *
	 */
	public static class Icons {
		private static String  iconFolder = "images/icons/silk";
		private static Integer iconSize = 16;
		private static String  iconExtention = ".png";
		
		public static  String getIconUrl(String iconFolder, Integer iconSize, String iconName){
			String iconUrl = "/" + iconFolder + "/" + iconSize.toString() + "x" + iconSize.toString() + "/" + iconName + iconExtention;
			return iconUrl;
		}
		
		public static String getIconUrl(Integer iconSize, String iconName){
			String iconUrl = "/" + iconFolder + "/" + iconSize.toString() + "x" + iconSize.toString() + "/" + iconName + iconExtention;
			return iconUrl;
		}
		
		public static String getIconUrl(String iconName){
			String iconUrl = "/" + iconFolder + "/" + iconSize.toString() + "x" + iconSize + "/" + iconName + iconExtention;
			return iconUrl;
		}
	}

}
