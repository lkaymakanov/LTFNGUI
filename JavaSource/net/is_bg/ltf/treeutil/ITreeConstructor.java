package net.is_bg.ltf.treeutil;

/**
 * An interface for constructing trees!!!
 * @author lubo
 *
 * @param <K>
 * @param <T>
 */
public interface ITreeConstructor<K, T> {
	public ITreeNode<K, T>  constructTrree();
}
