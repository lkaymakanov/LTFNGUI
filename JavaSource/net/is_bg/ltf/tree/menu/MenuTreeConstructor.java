package net.is_bg.ltf.tree.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.is_bg.ltf.businessmodels.menu.MenuNode;
import net.is_bg.ltf.treeutil.ITreeConstructor;
import net.is_bg.ltf.treeutil.ITreeNode;

/***
 * Creates a menu Tree!!!
 * @author lubo
 *
 */
public class MenuTreeConstructor implements ITreeConstructor<Integer, MenuNode> {
	private MenuTreeNode tree = new MenuTreeNode();
	private Map<Integer, MenuTreeNode>  menunodeMap = new HashMap<Integer, MenuTreeNode>();
	private List<MenuTreeNode> menunodeList =  new ArrayList<MenuTreeNode>();
	
	private  MenuTreeConstructor(List<MenuNode> menu){
		for(MenuNode m : menu){
			MenuTreeNode node = new MenuTreeNode();
			node.setKey(m.getMenuRoleId());
			node.setData(m);
			menunodeList.add(node);
			menunodeMap.put(m.getMenuRoleId(), node);
		}
	}

	@Override
	public ITreeNode<Integer, MenuNode> constructTrree() {
		// TODO Auto-generated method stub
		//now attach children to parents
		for(int i=0 ; i < menunodeList.size(); i++){
			MenuTreeNode current = menunodeList.get(i);
			MenuNode data = current.getData();
			MenuTreeNode parent  =  menunodeMap.get((int)data.getParentId());
			if(parent == null) {
				//attach to root
				tree.getChildren().add(current);
			}else{
				//attach to parent node
				current.setParentNode(parent);
				parent.getChildren().add(current);
			}
		}
		return tree;
	}
	
	
	public static ITreeNode<Integer, MenuNode> createMenuTree(List<MenuNode> menu){
		return new MenuTreeConstructor(menu).constructTrree(); 
	}
	
	
}
