package net.is_bg.ltf.treeutil;


import java.io.Serializable;
import java.util.List;

/***
 * A class Tree node ATD!!!
 * @author lubo
 *
 * @param <T>
 */
public interface ITreeNode<K, T>  extends Serializable {
	public K getKey();									  		 //the tree node key
	public void setKey(K id);								     //the tree node key
	public ITreeNode<K, T> getParentNode();                      //pointer to parent node
	public void setParentNode(ITreeNode<K, T> parentNode);       //pointer to parent node
	public List<ITreeNode<K, T>> getChildren();                  //list with children nodes
	public void setChildren(List<ITreeNode<K, T>> children);     //list with children nodes
	public T getData();                                       	 //the data stored in this node
	public void setData(T data);                                 //the data stored in this node
}
