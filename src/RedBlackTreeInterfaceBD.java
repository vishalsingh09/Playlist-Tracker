

public interface RedBlackTreeInterfaceBD<T extends Comparable<T>> extends SortedCollectionInterface<T>  {
  
  static class Node<T>{};
  public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

  public boolean remove(T data) throws NullPointerException, IllegalArgumentException; 

  public boolean contains(T data);

  public T get(String s);

  public int size();

  public boolean isEmpty();

  public void clear();

  public String toInOrderString();
  
  public T getLeft(SongInterfaceBD song);

  public T getRight(SongInterfaceBD data);
 
  public T getRoot();


 


}
