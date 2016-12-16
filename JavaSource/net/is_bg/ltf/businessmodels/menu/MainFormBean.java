package net.is_bg.ltf.businessmodels.menu;

import java.io.Serializable;
import java.util.List;
import net.is_bg.ltf.AppUtil;
import net.is_bg.ltf.ServiceLocator;
import net.is_bg.ltf.SessionBean;
import net.is_bg.ltf.tree.menu.HtmlMenuConstructor;

import org.richfaces.component.UIToolbar;


public class MainFormBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7832968142097013919L;
	private UIToolbar menu = new UIToolbar();
	
	public MainFormBean(){
		
	}
	
	private void menuGenerate(){
		 SessionBean sb = AppUtil.getSessionBeanFromFacesContext();
		 MenuSql sql = new MenuSql(sb.getVisit().getCurUser().getId());
		 ServiceLocator.getServicelocator().getMenuDao().execute(sql);
		 List<MenuNode> lmenu =  sql.getUnorderedMenuList();
		 menu = HtmlMenuConstructor.createMenuToolbar(lmenu);
	}

	public UIToolbar getMenu() {
		if(menu == null) menuGenerate();
		return menu;
	}

	public void setMenu(UIToolbar menu) {
		this.menu = menu;
	}
	
	
}
