package net.is_bg.ltf;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.model.SelectItem;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.is_bg.ltf.db.common.JDBCException;

/**
 * Used to load  connections described in server.xml!
 * @author lubo
 *
 */
public class ConnectionLoader {
	public final static String DB_CONN_RESOURCE = "jdbc";
	private List<SelectItem> dbConns = new ArrayList<SelectItem>();
	private Map<Integer, DBUrlAttributes> mapConnection = new HashMap<Integer, DBUrlAttributes>();
	private int  defDbConn = 0;  // "jdbc/ltf";
	
	private static ConnectionLoader connectionLoader;
	
	
	private  ConnectionLoader() {
		// TODO Auto-generated constructor stub
		loadConnections();
	}
	
	
	public static ConnectionLoader getConnectionLoader(){
		if(connectionLoader != null) return connectionLoader;
		synchronized (ConnectionLoader.class) {
			if(connectionLoader == null) connectionLoader = new ConnectionLoader();
		}
		return connectionLoader;
	}
	


	private void loadConnections(){
		Context initContext;
		try {
			initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			NamingEnumeration<NameClassPair> connList = envContext.list(DB_CONN_RESOURCE);
			
			
			DBUrlAttributes attrib = null;
			int i = 0;
			while (connList.hasMoreElements()) {
				
				NameClassPair pair = connList.nextElement();
				String tmpStr = DB_CONN_RESOURCE + "/" + pair.getName();
			   
				attrib = new DBUrlAttributes();
				try {
					DataSource dataSource = (DataSource) envContext.lookup(tmpStr);
					Connection conn = dataSource.getConnection();
					DatabaseMetaData ds = conn.getMetaData();
					attrib =  DBUrlAttributes.ParseDBUrl(ds.getURL());
					attrib.tmpUsername = ds.getUserName().toUpperCase();
					attrib.tmpDbName = ds.getDatabaseProductName();
					attrib.tmpName = pair.getName();
					attrib.defDbCon = tmpStr;
					attrib.tmpTns = attrib.tmpUsername + "@" + attrib.tmpIP + ":" + attrib.tmpPort + ":" + attrib.tmpSID;
				
					System.out.println( "DataBase URL: " + ds.getURL());
					System.out.println( "DataBase Productname: " + ds.getDatabaseProductName());
					
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				
				//add attributes to map
				mapConnection.put(i, attrib);
				
				dbConns.add(new SelectItem(i, attrib.tmpTns));
				i++;
			}
			
			if (dbConns.size() > 0) defDbConn = (Integer)dbConns.get(0).getValue();
		} catch (NamingException e) {
			throw new JDBCException(e);
		}
	}
	
	

	public int getDefDbConn() {
		return defDbConn;
	}
	

	public Set<Integer> getConnectionKeys(){
		return mapConnection.keySet();
	}
	
	
	public List<SelectItem> getDbConns() {
		return dbConns;
	}


	public Map<Integer, DBUrlAttributes> getMapConnection() {
		return mapConnection;
	}




	public static class DBUrlAttributes implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -2011969777074895016L;
		public String tmpUrl = "";
		public String defDbCon = "";
		//String[] tmpArr = "";
		public String tmpIP = "";
		public String tmpPort = "";
		public String tmpSID = "";
		public String tmpUsername = "";
		public String tmpDbName = "";
		public String tmpTns = "";
		public String tmpName = "";
		
		private static DBUrlAttributes ParseDBUrl(String dbUrl){
			DBUrlAttributes attrr = new DBUrlAttributes();
			
			if(dbUrl == null)  return attrr;
			
			//replace all slashes with @
			dbUrl = dbUrl.replaceAll("/", "@");
			if(dbUrl == null)  return attrr;
			
			//replace all semicolon with @
			dbUrl = dbUrl.replaceAll(":", "@");
			if(dbUrl == null)  return attrr;
			
			//split by double @
			String [] arr = dbUrl.split("@@");
			if(arr == null || arr.length < 2 || arr[1] == null) return attrr;
			
			
			//AbstractManagedBean.DebugLog(arr[1]);
			
			//now split by @
			String [] at = arr[1].split("@");
			
			if(at == null || at.length < 3)  return attrr;
			
			//now fill attributes take the last 3 strrings
			if(at[0] != null )attrr.tmpIP = at[at.length-3];
			if(at[1] != null )attrr.tmpPort = at[at.length-2];
			if(at[2] != null )attrr.tmpSID = at[at.length-1];
			
			return attrr;
		}
	}
}
