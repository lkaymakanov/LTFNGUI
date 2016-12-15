package net.is_bg.ltf.tree.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.context.FacesContext;

import org.richfaces.component.UIDropDownMenu;
import org.richfaces.component.UIMenuGroup;
import org.richfaces.component.UIMenuItem;
import org.richfaces.component.UIToolbar;

import net.is_bg.ltf.AppUtil.Icons;
import net.is_bg.ltf.businessmodels.menu.MenuNode;
import net.is_bg.ltf.treeutil.ITreeNode;
import net.is_bg.ltf.treeutil.TreeUtil;

/***
 * Creates a richfaces menu dynamically
 * @author lubo
 *
 */
public class HtmlMenuConstructor {

	private static class MenuDecorator {
		public MenuDecorator(ITreeNode<Integer, MenuNode> node){
			this.node = node;
		}
		ITreeNode<Integer, MenuNode> node;
		List<MenuDecorator>  children = new ArrayList<MenuDecorator>();
		UIComponent thisNodeHtml;
		UIComponent thisNodeHtmlParent;
	}
	
	private static void createMenuChild(MenuDecorator node){
		FacesContext ctx = FacesContext.getCurrentInstance();
       
		UIComponent parent = node.thisNodeHtmlParent;
		if(node.children.size() > 0){
			//link to sub menu
			//UIMenuGroup menuSubGroup = new UIMenuGroup();
			UIMenuGroup menuSubGroup = (UIMenuGroup) ctx.getApplication().createComponent(ctx, UIMenuGroup.COMPONENT_TYPE,  "org.richfaces.MenuGroupRenderer");
			//UIToolbar 
			//HtmlMenuGroup menuSubGroup = new HtmlMenuGroup();
			menuSubGroup.setLabel(node.node.getData().getMenuName());
			menuSubGroup.setId("mnu" + Integer.toString(node.node.getData().getMenuRoleId()));
			// иконка
			if (node.node.getData().getIconId() != null) menuSubGroup.setIcon(Icons.getIconUrl(node.node.getData().getIconId()));
			node.thisNodeHtml = menuSubGroup;
			parent.getChildren().add(menuSubGroup);
		}else{
			//no children ----> leaf , menu end point
			////HtmlMenuItem menuItem = new HtmlMenuItem();
			 UIMenuItem menuItem = (UIMenuItem) ctx.getApplication().createComponent(ctx, UIMenuItem.COMPONENT_TYPE, "org.richfaces.MenuItemRenderer");
			//menuItem.setStyleClass("toolBarItem");
			//menuItem.setSubmitMode("ajax");
			menuItem.setLabel(node.node.getData().getMenuName());
			menuItem.setId("mnu__" + Integer.toString(node.node.getData().getMenuRoleId()));
			if (node.node.getData().getIconId() != null) menuItem.setIcon(Icons.getIconUrl(node.node.getData().getIconId()));
			//addActionListener(menuItem, "#{mainFormBean.menuItemClick}");
		    menuItem.setData(node.node.getData().getHref());
			//menuItem.setReRender("ibody");
			// иконка
			//if (node.node.getData().getIconId() != null) menuItem.setIcon(Icons.getIconUrl(node.node.getData().getIconId()));
			node.thisNodeHtml = menuItem;
			parent.getChildren().add(menuItem);
		}
	}
	
	private static void createMenuRoot(UIComponent parent, MenuDecorator node){
		FacesContext ctx = FacesContext.getCurrentInstance();
		UIDropDownMenu dropMenu = (UIDropDownMenu) ctx.getApplication()
	            .createComponent(ctx, UIDropDownMenu.COMPONENT_TYPE,
	            "org.richfaces.DropDownMenuRenderer");
		//dropMenu.setValue(childNode.getMenuName());
		//dropMenu.setId("mnu" + Integer.toString(childNode.getMenuRoleId()));

		// картинката
		HtmlGraphicImage image = new HtmlGraphicImage();
		if (node.node.getData().getIconId() != null) image.setUrl(Icons.getIconUrl(node.node.getData().getIconId()));
		image.setStyleClass("menuIcon");
		// надписа
		HtmlOutputText text = new HtmlOutputText();
		text.setValue(node.node.getData().getMenuName());
		// бутончето
		HtmlPanelGroup panelGroup = new HtmlPanelGroup();
		//if (childNode.getIconId() != null) panelGroup.getChildren().add(image);
		panelGroup.getChildren().add(text);
		// сглобяваме
		dropMenu.getFacets().put("label", panelGroup);
		parent.getChildren().add(dropMenu);
		node.thisNodeHtml = dropMenu;
	}
	
	
	

	public static UIToolbar createMenuToolbar(List<MenuNode> lmenu){
		ITreeNode<Integer, MenuNode> menuTree = MenuTreeConstructor.createMenuTree(lmenu);
		UIToolbar menuBar;
		FacesContext ctx = FacesContext.getCurrentInstance();
		menuBar = (UIToolbar) ctx.getApplication()
		            .createComponent(ctx, UIToolbar.COMPONENT_TYPE, 
		            "org.richfaces.ToolbarRenderer");
		Map<Long,  List<ITreeNode<Integer, MenuNode>>> levelMap  = TreeUtil.traverseTreeBfs(menuTree);
		Map<Long, List<MenuDecorator>>  levelDecoratedMap = new HashMap<Long, List<HtmlMenuConstructor.MenuDecorator>>();
		Map<Integer, MenuDecorator>  parentChildMap = new HashMap<Integer, MenuDecorator>();
		MenuDecorator root = new MenuDecorator(null);
		
		for(Long k:levelMap.keySet()){
			List<MenuDecorator> l = new ArrayList<MenuDecorator>();
			levelDecoratedMap.put(k, l);
			for(ITreeNode<Integer, MenuNode> node : levelMap.get(k)){
				if(node == null) continue;
				MenuDecorator dec = new MenuDecorator(node);
				l.add(dec);
				parentChildMap.put(node.getKey(), dec);         //the id of the wrapped node & the wrapped node
			}
		}
		//loop through decorated
		for(Long k:levelDecoratedMap.keySet()){
			for(MenuDecorator node : levelDecoratedMap.get(k)){
				//get parent node from map
				MenuDecorator parent = node.node.getParentNode() == null ? null : parentChildMap.get(node.node.getParentNode().getKey());
				if(parent !=null) {
					parent.children.add(node);
				}else{   //add to root
					root.children.add(node);
				}
			}
		}
		
		//here we end up with MenuDecorator tree
		root.thisNodeHtml = menuBar;   //set menu as root
		
		//loop through decorated again & create html tree!!!
		for(Long k:levelDecoratedMap.keySet()){
			for(MenuDecorator node : levelDecoratedMap.get(k)){
				if(node.node.getParentNode() == null) {
					node.thisNodeHtmlParent = menuBar;
					createMenuRoot(menuBar, node);
				}else{
					createMenuChild(node);
				}
				//set UIComponent parent  to children nodes
				setParentToChildren(node);
			}
		}
		return menuBar;
	}
	
	private static void setParentToChildren(MenuDecorator d){
		for(MenuDecorator dd: d.children){
			dd.thisNodeHtmlParent = d.thisNodeHtml;
		}
	}
}

