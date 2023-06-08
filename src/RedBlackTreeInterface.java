public interface RedBlackTreeInterface<T extends Comparable<T>>{

    public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

    public boolean remove(T data) throws NullPointerException, IllegalArgumentException;

    public boolean contains(T data);

    public int size();

    public boolean isEmpty();
    
    public String toInOrderString();
    
    public T getLeft(T data);

    public T getRight(T data);

    public T getRoot();


}
