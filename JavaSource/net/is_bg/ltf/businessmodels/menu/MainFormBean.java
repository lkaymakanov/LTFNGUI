package net.is_bg.ltf.businessmodels.menu;

import java.io.Serializable;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.http.HttpServletRequest;

import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ServiceLocator;
import net.is_bg.ltf.SessionBean;
import net.is_bg.ltf.tree.menu.HtmlMenuConstructor;

import org.richfaces.component.UIToolbar;

@ManagedBean
@RequestScoped
public class MainFormBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7832968142097013919L;
	private UIToolbar menu =  new UIToolbar();
	private boolean menuGenerated = false;
	private String pathToView = "http://dir.bg";
	private String remoteIp = "http://10.240.110.201:8080/LTFPG";
	
	public MainFormBean(){
		SessionBean sb = AppUtil.getSessionBeanFromFacesContext();
		if(sb == null ||  sb.getVisit() == null || sb.getVisit().getCurUser() == null) return;
		
		HttpServletRequest req = AppUtil.getRequest();
		String sessionId = req.getSession().getId();
		String uri = req.getRequestURI().toString();
		uri = uri.replace(req.getContextPath(), "");   //remove context
		String tokenSuffix = "?token=" + sessionId;
		pathToView = remoteIp + uri + tokenSuffix;
		System.out.println("MainFormBean Created...");
	}
	
	private void menuGenerate(){
		 SessionBean sb = AppUtil.getSessionBeanFromFacesContext();
		 if(sb == null ||  sb.getVisit() == null || sb.getVisit().getCurUser() == null) return;
		 MenuSql sql = new MenuSql(sb.getVisit().getCurUser().getId());
		 ServiceLocator.getServicelocator().getMenuDao().execute(sql);
		 List<MenuNode> lmenu =  sql.getUnorderedMenuList();
		 menu = HtmlMenuConstructor.createMenuToolbar(lmenu);
		 menuGenerated = true;
	}

	public UIToolbar getMenu() {
		if(!menuGenerated) menuGenerate();
		return menu;
	}

	public void setMenu(UIToolbar menu) {
		this.menu = menu;
	}

	public String getPathToView() {
		return pathToView;
	}
	
	public String link(String l){
		System.out.println(l);
		return l;
	}
	
	
}
