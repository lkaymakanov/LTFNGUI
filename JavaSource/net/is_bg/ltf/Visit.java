package net.is_bg.ltf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.is_bg.ltf.db.common.interfaces.visit.IVisit;
import net.is_bg.ltfn.commons.old.models.user.User;


// TODO: Auto-generated Javadoc
/**
 * The Class Visit.
 */
public class Visit implements IVisit, Serializable {
	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5446112242686411092L;
	
	/** The help mode. */
	private boolean helpMode = false;
	
	/** The cur user. */
	private User curUser;
	
	/** The Allowed list. */
	private List<String> AllowedList = new ArrayList<String>();
	
	/** The Prev page. */
	private String PrevPage;
	
	
	/** The db conns. */
	private List<SelectItem> dbConns = new ArrayList<SelectItem>();
	
	/** The def db conn. */
	private String defDbConn = "jdbc/ltf";
	
	/** The def db conn read only. */
	private String defDbConnReadOnly = "jdbc-ro/ltf";
	
	/** The tns. */
	private String tns;
	
	
	//Дата на плащане и ЕИН на субекта при безкасово плащане,
	//транзакция и вид платежен документ,
	//селектирана банкова сметка или посредник
	/** The payment date. */
	private Date paymentDate;
	
	/** The tax subject idn. */
	private String taxSubjectIdn = "";
	
	/** The sel transaction. */
	private String selTransaction;
	
	/** The sel pay document. */
	private String selPayDocument;
	
	
	
	/** The remote addr. */
	private String remoteAddr;
	
	/** The remote host. */
	private String remoteHost;
	
	/** The remote port. */
	private int remotePort;
	
	/** The remote user. */
	private String remoteUser;
	
	/** The transaction no. */
	private long transactionNo;
	
	
	/** The db type. */
	private DB_TYPE dbType = DB_TYPE.PGR;   //postgre default
	
	/** The support phone. */
	private String supportPhone;
	
	/** The support mobile. */
	private String supportMobile;
	
	/** The support url. */
	private String supportURL;
	
	/** The query max count. */
	private int queryMaxCount;
	
	/** The query page count. */
	private int queryPageCount;
	
	private BigDecimal suma;
	
	
	
	/**
	 * Instantiates a new visit.
	 *
	 * @param Dbname the dbname
	 */
	public Visit(String Dbname){
		if(Dbname == null) return;
		if(Dbname.equals("PostgreSQL"))  	dbType = DB_TYPE.PGR;
		if(Dbname.equals("Oracle"))  	dbType = DB_TYPE.ORCL;
	}
	

/*	*//**
	 * Gets the menu roles list.
	 *
	 * @return the menu roles list
	 *//*
	public List<MenuNode> getMenuRolesList() {
		return MenuRolesList;
	}
	
	*//**
	 * Sets the menu roles list.
	 *
	 * @param menuRolesList the new menu roles list
	 *//*
	public void setMenuRolesList(List<MenuNode> menuRolesList) {
		MenuRolesList = menuRolesList;
	}
	*/
	/**
	 * Gets the prev page.
	 *
	 * @return the prev page
	 */
	public String getPrevPage() {
		return PrevPage;
	}
	
	/**
	 * Sets the prev page.
	 *
	 * @param prevPage the new prev page
	 */
	public void setPrevPage(String prevPage) {
		PrevPage = prevPage;
	}
	
	/**
	 * Adds the allowed page.
	 *
	 * @param alowedPage the alowed page
	 */
	public void addAllowedPage(String alowedPage) {
		AllowedList.add(alowedPage);
	}
	
	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	public String getLocale() {
		return FacesContext.getCurrentInstance().getViewRoot().getLocale().toString();
	}
	
	/**
	 * Sets the locale.
	 *
	 * @param locale the new locale
	 */
	public void setLocale(String locale) {
		FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(locale));
	}
	
	/**
	 * Gets the allowed list.
	 *
	 * @return the allowed list
	 */
	public List<String> getAllowedList() {
		return AllowedList;
	}
	
	/**
	 * Sets the allowed list.
	 *
	 * @param allowedList the new allowed list
	 */
	public void setAllowedList(List<String> allowedList) {
		AllowedList = allowedList;
	}
	
	
	/**
	 * Gets the payment date.
	 *
	 * @return the payment date
	 */
	public Date getPaymentDate() {
	    return paymentDate;
	}
	
	/**
	 * Sets the payment date.
	 *
	 * @param paymentDate the new payment date
	 */
	public void setPaymentDate(Date paymentDate) {
	    this.paymentDate = paymentDate;
	}
	
	/**
	 * Gets the tax subject idn.
	 *
	 * @return the tax subject idn
	 */
	public String getTaxSubjectIdn() {
	    return taxSubjectIdn;
	}
	
	/**
	 * Sets the tax subject idn.
	 *
	 * @param taxSubjectIdn the new tax subject idn
	 */
	public void setTaxSubjectIdn(String taxSubjectIdn) {
	    this.taxSubjectIdn = taxSubjectIdn;
	}
	
	/**
	 * Gets the sel transaction.
	 *
	 * @return the sel transaction
	 */
	public String getSelTransaction() {
	    return selTransaction;
	}
	
	/**
	 * Sets the sel transaction.
	 *
	 * @param selTransaction the new sel transaction
	 */
	public void setSelTransaction(String selTransaction) {
	    this.selTransaction = selTransaction;
	}
	
	/**
	 * Gets the sel pay document.
	 *
	 * @return the sel pay document
	 */
	public String getSelPayDocument() {
	    return selPayDocument;
	}
	
	/**
	 * Sets the sel pay document.
	 *
	 * @param selPayDocument the new sel pay document
	 */
	public void setSelPayDocument(String selPayDocument) {
	    this.selPayDocument = selPayDocument;
	}
	
	
	/**
	 * Gets the remote addr.
	 *
	 * @return the remote addr
	 */
	public String getRemoteAddr() {
		return remoteAddr;
	}
	
	/**
	 * Sets the remote addr.
	 *
	 * @param remoteAddr the new remote addr
	 */
	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}
	
	/**
	 * Gets the remote host.
	 *
	 * @return the remote host
	 */
	public String getRemoteHost() {
		return remoteHost;
	}
	
	/**
	 * Sets the remote host.
	 *
	 * @param remoteHost the new remote host
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}
	
	/**
	 * Gets the remote port.
	 *
	 * @return the remote port
	 */
	public int getRemotePort() {
		return remotePort;
	}
	
	/**
	 * Sets the remote port.
	 *
	 * @param remotePort the new remote port
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	
	/**
	 * Gets the remote user.
	 *
	 * @return the remote user
	 */
	public String getRemoteUser() {
		return remoteUser;
	}
	
	/**
	 * Sets the remote user.
	 *
	 * @param remoteUser the new remote user
	 */
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}
	
	/**
	 * Checks if is help mode.
	 *
	 * @return true, if is help mode
	 */
	public boolean isHelpMode() {
		return helpMode;
	}
	
	/**
	 * Sets the help mode.
	 *
	 * @param helpMode the new help mode
	 */
	public void setHelpMode(boolean helpMode) {
		this.helpMode = helpMode;
	}
	
	/**
	 * Gets the def db conn.
	 *
	 * @return the def db conn
	 */
	public String getDefDbConn() {
		return defDbConn;
	}
	
	/**
	 * Sets the def db conn.
	 *
	 * @param defDbConn the new def db conn
	 */
	public void setDefDbConn(String defDbConn) {
		this.defDbConn = defDbConn;
	}
	
	/**
	 * Gets the db conns.
	 *
	 * @return the db conns
	 */
	public List<SelectItem> getDbConns() {
		return dbConns;
	}

	/**
	 * Sets the db conns.
	 *
	 * @param dbConns the new db conns
	 */
	public void setDbConns(List<SelectItem> dbConns) {
		this.dbConns = dbConns;
	}
	
	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.visit.IVisit#getTns()
	 */
	public String getTns() {
		return tns;
	}
	
	/**
	 * Sets the tns.
	 *
	 * @param tns the new tns
	 */
	public void setTns(String tns) {
		this.tns = tns;
	}
	
	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.visit.IVisit#getTransactionNo()
	 */
	public long getTransactionNo() {
		return transactionNo;
	}


	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.visit.IVisit#setTransactionNo(long)
	 */
	public void setTransactionNo(long transactionNo) {
		this.transactionNo = transactionNo;
	}
	
	
	/**
	 * Gets the support phone.
	 *
	 * @return the support phone
	 */
	public String getSupportPhone() {
		return supportPhone;
	}
	
	/**
	 * Sets the support phone.
	 *
	 * @param supportPhone the new support phone
	 */
	public void setSupportPhone(String supportPhone) {
		this.supportPhone = supportPhone;
	}
	
	/**
	 * Gets the support mobile.
	 *
	 * @return the support mobile
	 */
	public String getSupportMobile() {
		return supportMobile;
	}
	
	/**
	 * Sets the support mobile.
	 *
	 * @param supportMobile the new support mobile
	 */
	public void setSupportMobile(String supportMobile) {
		this.supportMobile = supportMobile;
	}
	
	/**
	 * Gets the support url.
	 *
	 * @return the support url
	 */
	public String getSupportURL() {
		return supportURL;
	}
	
	/**
	 * Sets the support url.
	 *
	 * @param supportURL the new support url
	 */
	public void setSupportURL(String supportURL) {
		this.supportURL = supportURL;
	}
	
	/**
	 * Gets the def db conn read only.
	 *
	 * @return the def db conn read only
	 */
	public String getDefDbConnReadOnly() {
		return defDbConnReadOnly;
	}
	
	/**
	 * Sets the def db conn read only.
	 *
	 * @param defDbConnReadOnly the new def db conn read only
	 */
	public void setDefDbConnReadOnly(String defDbConnReadOnly) {
		this.defDbConnReadOnly = defDbConnReadOnly;
	}
	
	/**
	 * Gets the query max count.
	 *
	 * @return the query max count
	 */
	public int getQueryMaxCount() {
		return queryMaxCount;
	}
	
	/**
	 * Sets the query max count.
	 *
	 * @param queryMaxCount the new query max count
	 */
	public void setQueryMaxCount(int queryMaxCount) {
		this.queryMaxCount = queryMaxCount;
	}
	
	/**
	 * Gets the query page count.
	 *
	 * @return the query page count
	 */
	public int getQueryPageCount() {
		return queryPageCount;
	}
	
	/**
	 * Sets the query page count.
	 *
	 * @param queryPageCount the new query page count
	 */
	public void setQueryPageCount(int queryPageCount) {
		this.queryPageCount = queryPageCount;
	} 

	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.visit.IVisit#getDbType()
	 */
	public DB_TYPE getDbType() {
		// TODO Auto-generated method stub
		return dbType;
	}


	/* (non-Javadoc)
	 * @see net.is_bg.ltf.db.common.interfaces.visit.IVisit#getFullName()
	 */
	public String getFullName() {
		// TODO Auto-generated method stub
		if(curUser != null) return curUser.getFullName();
		return null;
	}


	public BigDecimal getSuma() {
		return suma;
	}


	public void setSuma(BigDecimal suma) {
		this.suma = suma;
	}


	

	public long getVisitId() {
		// TODO Auto-generated method stub
		return (curUser!= null) ? curUser.getId() : 0;
	}


	public User getCurUser() {
		// TODO Auto-generated method stub
		return curUser;
	}
	
	
}