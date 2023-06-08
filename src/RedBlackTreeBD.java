

public class RedBlackTreeBD implements RedBlackTreeInterfaceBD{
  protected int size = 0;
  public boolean insert(Comparable data) throws NullPointerException, IllegalArgumentException {
    // TODO Auto-generated method stub
    size++;
    return true;
  }

  @Override
  public boolean remove(Comparable data) throws NullPointerException, IllegalArgumentException {
    // TODO Auto-generated method stub
    size--;
    return false;
  }

  @Override
  public boolean contains(Comparable data) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Comparable get(String s) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public int size() {
    // TODO Auto-generated method stub
    return size;
  }

  @Override
  public boolean isEmpty() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String toInOrderString() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public SongBD getRoot() {
    // TODO Auto-generated method stub
    return new SongBD("Homecoming", "ye", "Graduation", 10000);
  }

  @Override
  public Comparable getLeft(SongInterfaceBD data) {
    // TODO Auto-generated method stub
    if(data.getTitle().equals("Rich Flex"))
    {
      return null;
    }
    return new SongBD("Rich Flex", "Drake", "Her Loss", 5000);
  }

  @Override
  public Comparable getRight(SongInterfaceBD data) {
    // TODO Auto-generated method stub
    if(data.getTitle().equals("Stronger"))
    {
      return null;
    }
    return new SongBD("Stronger", "ye", "Graduation", 20000);
  }


}
