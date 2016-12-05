package net.is_bg.ltf.tree.menu;

import java.util.ArrayList;
import java.util.List;

import net.is_bg.ltf.businessmodels.menu.MenuNode;
import net.is_bg.ltf.db.common.interfaces.IAbstractModel;
import net.is_bg.ltf.treeutil.ITreeNode;

/***
 * Each menu tree node!!!
 * @author lubo
 *
 */
class MenuTreeNode implements ITreeNode<Integer, MenuNode>, IAbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1593958055811024025L;
	private List<ITreeNode<Integer, MenuNode>> children = new ArrayList<ITreeNode<Integer, MenuNode>>();
	private ITreeNode<Integer, MenuNode> parent;
	private MenuNode data;
	private int key;

	@Override
	public Integer getKey() {
		// TODO Auto-generated method stub
		return key;
	}

	@Override
	public void setKey(Integer id) {
		// TODO Auto-generated method stub
		this.key = id;
	}

	@Override
	public ITreeNode<Integer, MenuNode> getParentNode() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParentNode(ITreeNode<Integer, MenuNode> parentNode) {
		// TODO Auto-generated method stub
		this.parent = parentNode;
	}

	@Override
	public List<ITreeNode<Integer, MenuNode>> getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

	@Override
	public void setChildren(List<ITreeNode<Integer, MenuNode>> children) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MenuNode getData() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public void setData(MenuNode data) {
		// TODO Auto-generated method stub
		this.data = data;
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return key;
	}

}
