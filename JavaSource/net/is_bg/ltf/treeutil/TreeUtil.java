package net.is_bg.ltf.treeutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Trees traversing algorithms!!!
 * @author lubo
 *
 */
public class TreeUtil {
	
	/**Traverses a tree by DFS algorithm!!!
	 * @param <K>
	 * @param <T>
	 * 
	 * @param node - the current  node
	 * @param trcallback  - what is to be done forward & backward
	 */
	public static <K, T> void traverseTreeDfs(ITreeNode<K,T> node, ITraverseTreeCallBack<K, T> trcallback){
		if(trcallback != null)trcallback.OnForward(node);
		
		if(node.getChildren().size()> 0){
			for(ITreeNode<K,T> child : node.getChildren()){
				traverseTreeDfs(child, trcallback);
				if(trcallback != null) trcallback.OnReturnFromRecursion(node);//act on the directory
			}
		}
		if(trcallback != null) trcallback.OnReturnFromRecursion(node);  //act on the file
	}
	
	
	/***
	 * Traverses a tree by BFS algorithm!!! Returns a map with the level numbers & a List of tree nodes contained in each level!!!!
	 * @param <T>
	 * @param <K,T>
	 * @return
	 */
	public static <K, T> Map<Long, List<ITreeNode<K, T>>>   traverseTreeBfs(ITreeNode<K,T> node){
		Map<Long, List<ITreeNode<K, T>>> levelMap = new HashMap<Long, List<ITreeNode<K,T>>>();
		Map<K, Long> idLevelMap = new HashMap<K, Long>();
		Map<K, ITreeNode<K, T>> idElementMap = new HashMap<K, ITreeNode<K, T>>();
		List<ITreeNode<K, T>> l = new ArrayList<ITreeNode<K,T>>();
		int topIndex = 0, currentIndex = 0;
		l.add(node);
		idLevelMap.put(node.getKey(), 0l);
		long maxLevel = 0;
		
		while(currentIndex <= topIndex){
			long currentElementLevel = idLevelMap.get(l.get(currentIndex).getKey());
			maxLevel = currentElementLevel  > maxLevel ? currentElementLevel : maxLevel;
			for(ITreeNode<K, T> element : l.get(currentIndex).getChildren()){    //get children for current element
				l.add(element); 
				idElementMap.put(element.getKey(), element);
				idLevelMap.put(element.getKey(), currentElementLevel+1);
				topIndex++;               					 	 				 //add to list increase top
			}
			currentIndex++;                                                      //goto next element
		}
		
		//init level map lists
		for(long i =0; i <= maxLevel; i++){
			levelMap.put(i, new ArrayList<ITreeNode<K, T>>());
		}
		
		//add node to level map
		for(ITreeNode<K, T> n : l){
			Long elementLevel = idLevelMap.get(n.getKey());
			if(elementLevel!=null) levelMap.get(elementLevel).add(idElementMap.get(n.getKey()));
		}
		idLevelMap  = null;
		l = null;
		idElementMap = null;
		return levelMap;
	}
	
	
}
