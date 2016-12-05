package net.is_bg.ltf.treeutil;


public interface ITraverseTreeCallBack<K,T> {
		
		public void OnForward(ITreeNode<K, T> node);
		public void OnReturnFromRecursion(ITreeNode<K, T> node);

}
