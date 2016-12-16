package net.is_bg.ltf;


import java.io.File;
import java.io.IOException;




public class ContextParamLoader<T> {
	
	//names of the params that go in server xml!!!!
	private static String updateDaemonAllow = "updateDaemonAllow";
	private static String updateDaemonDelayMin = "updateDaemonDelayMin";
	private static String numberOfThreads = "numberOfThreads";
	private static String applibdir = "applibdir";
	private static String appdownloaddir = "appdownloaddir";
	private static String loginGod = "loginGod";
	private static String showId = "showId";
	private static String updateVersion = "updateVersion";
	private static String updateAllow = "updateAllow";
	private static String updateCenterServers = "updateCenterServers";
	
	//UPDATE CENTER
	private static String updateCenterKeystoreFile = "updateCenterKeystoreFile";
	private static String updateCenterKeystorePass = "updateCenterKeystorePass";
	private static String updateCenterKeyAlias = "updateCenterKeyAlias";
	private static String updateCenterKeyPass = "updateCenterKeyPass";
	private static String updateCenterSocketProtocol = "updateCenterSocketProtocol";
	private static String updateCenterStoreType = "updateCenterStoreType";
	private static String updateCenterApplication =  "updateCenterApplication";
	
	//whether to log sql statements booleans
	private static String logSelect = "logSelect";
	private static String logUpdate = "logUpdate";
	private static String logInsert = "logInsert";
	private static String logDelete = "logDelete";
	private static String clearSessions = "clearSessions";
	private static String enableLog = "enableLog";
	
	//says whether to execute scripts when application is deployed or not!!!
	private static String executescript = "executescript";
	

	//help center parameters
	private static  String support_phone = "support_phone";
	private static  String support_mobile = "support_mobile";
	private static  String support_url = "support_url";
	
	//transaction queue
	private static String  transactionQueueMaxSize = "transactionQueueMaxSize";
	
	private static String getCurrentfileDir(){
		try {
			return new File(".").getCanonicalFile().getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	
	public enum CONTEXTPARAMS{
		
		//task scheduler params
		UPDATE_DAEMON_ALLOW(updateDaemonAllow, ContextParamLoader.getParam(updateDaemonAllow, Boolean.class, false), Boolean.class),
		UPDATE_DAEMON_DELAY_MINUTES(updateDaemonDelayMin,  ContextParamLoader.getParam(updateDaemonDelayMin, Long.class, 1l),  Long.class),
		UPDATE_DAEMON_NUMBER_OF_THREADS(numberOfThreads, ContextParamLoader.getParam(numberOfThreads,  Integer.class, 10), Integer.class),
		
		LOGIN_GOD(loginGod, ContextParamLoader.getParam(loginGod,  Boolean.class, false), Boolean.class),
		SHOW_ID(showId, ContextParamLoader.getParam(showId,  Boolean.class, false), Boolean.class),
		ENABLE_LOG(enableLog, ContextParamLoader.getParam(enableLog,  Boolean.class, true), Boolean.class),
		
		UPDATE_APP_VERSION_IN_DATABSE(updateVersion, ContextParamLoader.getParam(updateVersion,  Boolean.class, false), Boolean.class),
		
		/**a comma separated values of update center server in config in format protocol:serverip:port - example   http:localhost:8080, http:someothrerhost:port */
		UPDATE_CENTER_PROTOCOL_SERVER_PORT(updateCenterServers,  ContextParamLoader.getParam(updateCenterServers, String.class, "http:localhost:8080"), String.class),
		EXECUTE_SCRIPT(executescript, ContextParamLoader.getParam(executescript,  Boolean.class, false), Boolean.class),
		
		//application update params
		APPLICATION_LIB_DIR(applibdir,  ContextParamLoader.getParam(applibdir, String.class, getCurrentfileDir() + File.separator + "lib"),  String.class),
		APPLICATION_DOWNLOAD_DIR(appdownloaddir,  ContextParamLoader.getParam(appdownloaddir, String.class, getCurrentfileDir()), String.class),
		
		//update center context params
		UPDATE_ALLOW(updateAllow, ContextParamLoader.getParam(updateAllow,  Boolean.class, false), Boolean.class),
		UPDATE_CENTER_KEYSTORE_FILE(updateCenterKeystoreFile,  ContextParamLoader.getParam(updateCenterKeystoreFile, String.class, "keystore\\updatecenter.keystore"), String.class),
		UPDATE_CENTER_KEYSTORE_PASSWORD(updateCenterKeystorePass,  ContextParamLoader.getParam(updateCenterKeystorePass, String.class, "KeyParola123"), String.class),
		UPDATE_CENTER_PRIVATEKEY_ALIAS(updateCenterKeyAlias,  ContextParamLoader.getParam(updateCenterKeyAlias, String.class, "ltf_wsclient"), String.class),
		UPDATE_CENTER_PRIVATEKEY_PASSWORD(updateCenterKeyPass,  ContextParamLoader.getParam(updateCenterKeyPass, String.class, "KeyParola123"), String.class),
		
		UPDATE_CENTER_PROTOCOL(updateCenterSocketProtocol,  ContextParamLoader.getParam(updateCenterSocketProtocol, String.class, "ssl"), String.class),
		UPDATE_CENTER_KEYSTORETYPE(updateCenterStoreType,  ContextParamLoader.getParam(updateCenterStoreType, String.class, "jks"), String.class),
		UPDATE_CENTER_APPLICATION(updateCenterApplication,  ContextParamLoader.getParam(updateCenterApplication, String.class, "ltf"), String.class),
		
		CLEAR_SESSIONS(clearSessions,  ContextParamLoader.getParam(clearSessions, Boolean.class, true), Boolean.class),
		
		//log statements params
		LOG_SELECT(logSelect, ContextParamLoader.getParam(logSelect,  Boolean.class, true), Boolean.class),
		LOG_INSERT(logInsert, ContextParamLoader.getParam(logInsert,  Boolean.class, true), Boolean.class),
		LOG_UPDATE(logUpdate, ContextParamLoader.getParam(logUpdate,  Boolean.class, true), Boolean.class),
		LOG_DELETE(logDelete, ContextParamLoader.getParam(logDelete,  Boolean.class, true), Boolean.class),
		
		//help center Params
		SUPPORT_PHONE(support_phone, ContextParamLoader.getParam(support_phone,  String.class, null), String.class),
		SUPPORT_MOBILE(support_mobile, ContextParamLoader.getParam(support_mobile,  String.class, null), String.class),
		SUPPORT_URL(support_url, ContextParamLoader.getParam(support_url,  String.class, null), String.class),
		
		TRANSACTIONQUEUE_MAX_SIZE(transactionQueueMaxSize, ContextParamLoader.getParam(transactionQueueMaxSize, Integer.class, 100), Integer.class), 
		DEFAULT_JDBC_RESOURCE_NAME("", "", String.class);
		

		<T> CONTEXTPARAMS(String name, T defaultValue,  Class<T> c){
			this.name = name;
			this.clazz = c;
			this.value = defaultValue;
		};
		
		String name;
		Class clazz;
		Object value;
		
		public String getName() {
			return name;
		}
		public Class getClazz() {
			return clazz;
		}
		
		
		public Object getValue(){
			return value;
		}
		
		public static void printParams(){
			CONTEXTPARAMS[] arr = CONTEXTPARAMS.values();
			for(int i =0 ; i< arr.length ; i++){
				System.out.println(arr[i].getName() + "=" + arr[i].getValue());
			}
		}
		
		public static  String toZtring() {
			// TODO Auto-generated method stub
			CONTEXTPARAMS[] arr = values();
			StringBuilder b = new  StringBuilder();
			for(int i =0 ; i< arr.length ; i++){
				b.append(arr[i].getName() + "=" + arr[i].getValue());
				b.append("<\br>");
			}
			return b.toString();
		}
	}
	
	
	
	
	


	@SuppressWarnings("unchecked")
	public static <T> T getParam(String name, Class<T> type) {
		try{
			Object o = ApplicationGlobals.getApplicationGlobals().getContext().lookup(name);
			return (T)o;
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getParam(String name, Class<T> type, T defaultRes) {
		try{
			Object o = ApplicationGlobals.getApplicationGlobals().getContext().lookup(name);
			return (T)o;
		}catch (Exception e) {
			// TODO: handle exception
			return defaultRes;
		}
	}
	
}
