import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
public class PBBackendBD implements PBBackendInterface{
  
  private RedBlackTreeInterfaceBD<SongInterfaceBD> redBlackTree;
  private SongReaderInterfaceBD songReader;
  private ArrayList<SongInterfaceBD> playlist;
  private int size;
  /**
   * constructor for the backend class
   * @param redBlackTree, red black tree that uses songs objects
   * @param songReader, reads a file of songs to add the red black tree
   */
  public PBBackendBD(RedBlackTreeInterfaceBD<SongInterfaceBD> redBlackTree, SongReaderInterfaceBD songReader)
  {
    this.redBlackTree = redBlackTree;
    this.songReader = songReader;
    playlist = new ArrayList<SongInterfaceBD>();
    size = 0;
  }
  /**
   * @param filename, name of the file to retrieve songs
   * @throws FileNotFoundException, if the file cannot be find, then an exception is thrown 
   */
  @Override
  public void loadData(String filename) throws FileNotFoundException {
    // TODO Auto-generated method stub
    ArrayList<SongInterfaceBD> songs = (ArrayList<SongInterfaceBD>) songReader.readFromFile(filename);
    for(SongInterfaceBD song: songs)
    {
      addToDatabase(song);
    }
  }
  
  /**
   * @param words, titles to search by
   * @return a list of song strings
   */
  @Override
  public List<String> findSongsByTitleWords(String words) {
    // TODO Auto-generated method stub
    List<String> songsByTitle = new ArrayList<String>();
    inOrderTraversal(redBlackTree.getRoot(), words, songsByTitle, "title");
    return songsByTitle;
  }
  
  /**
   * @param words, artist names to search by
   * @return a list of song strings
   */
  @Override
  public List<String> findSongsByArtistName(String words) {
    // TODO Auto-generated method stub
    List<String> songsByArtist = new ArrayList<String>();
    inOrderTraversal(redBlackTree.getRoot(), words, songsByArtist, "artist");
    return songsByArtist;
  }
  
  /**
   * @param words, albums to search by
   * @return a list of song strings
   */
  @Override
  public List<String> findSongsByAlbum(String words) {
    // TODO Auto-generated method stub
    List<String> songsByAlbum = new ArrayList<String>();
    inOrderTraversal(redBlackTree.getRoot(), words, songsByAlbum, "album");
    return songsByAlbum;
  }
  
  /**
   * helper method that traverses the red black tree
   * @param song, the root node of the tree to traverse.
   * @param words, the keyword to search for in the song.
   * @param matchingSongs, a list of songs that match the search criteria.
   * @param type, the criterion to use for matching (title, artist, or album).
   */
  private void inOrderTraversal(SongInterfaceBD song, String words, List<String> matchingSongs, String type)
  {
    if(type.equals("title"))
    {
      if(song != null)
      {
        inOrderTraversal(redBlackTree.getLeft(song), words, matchingSongs, type);
        if(song.getTitle().contains(words))
        {
          matchingSongs.add(song.toString());
        }
        inOrderTraversal(redBlackTree.getRight(song), words, matchingSongs, type);
      }
    }
    else if(type.equals("artist"))
    {
      if(song != null)
      {
        inOrderTraversal(redBlackTree.getLeft(song), words, matchingSongs, type);
        if(song.getArtist().contains(words))
        {
          matchingSongs.add(song.toString());
        }
        inOrderTraversal(redBlackTree.getRight(song), words, matchingSongs, type);
      }
    }
    else if(type.equals("album"))
    {
      if(song != null)
      {
        inOrderTraversal(redBlackTree.getLeft(song), words, matchingSongs, type);
        if(song.getAlbum().contains(words))
        {
          matchingSongs.add(song.toString());
        }
        inOrderTraversal(redBlackTree.getRight(song), words, matchingSongs, type);
      }
    }
  }
  
  @Override
  /**
   * This method returns a string representation of all the songs in the database or playlist, 
   * depending on the inPlaylist parameter.
   * @param inPlaylist, A boolean  indicating whether to get all the songs in the playlist or the database.
   * @return A string representation of all the songs in the specified collection.
  */
  public String getAllSongs(boolean inPlaylist) {
    // TODO Auto-generated method stub
    if(inPlaylist)
    {
      String output = "";
      for(SongInterfaceBD song: playlist) 
      {
        output += song.toString() + "\n";
      }
      return output;
    }
    else
    {
      return redBlackTree.toInOrderString();
    }
  }
  /**
   * This method adds a song to the playlist if it is not already present and also adds it to the database.
   * @param songsToAdd The song to be added to the playlist.
   */

  public void addToPlaylist(SongBD songsToAdd) {
    // TODO Auto-generated method stub
    if(!playlist.contains(songsToAdd))
    {
      addToDatabase(songsToAdd);
      playlist.add(songsToAdd);
      size++;
    }
  }

  
  /**
   * This method removes a song from the playlist if it is present and returns the removed song. 
   * @param songsToRemove The song to be removed from the playlist.
   * @return The removed song.
   * @throws IllegalArgumentException If the song is not found in the playlist.
   */
  public SongBD removeFromPlaylist(SongBD songsToRemove) throws IllegalArgumentException{
    // TODO Auto-generated method stub
    for(SongInterfaceBD song: playlist)
    {
      if(song.equals(songsToRemove))
      {
        playlist.remove(song);
        size--;
        return songsToRemove;
      }
    }
    throw new IllegalArgumentException();
  }
  /**
   * Removes the specified Song from the database.
   * @param toDelete the Song to be removed from the database
   * @return the Song that was removed, or null if it was not found in the database
  */
  public SongBD deleteFromDatabase(SongBD toDelete) {
    // TODO Auto-generated method stub
    if(playlist.contains(toDelete))
    {
      playlist.remove(toDelete);
    }
    if(redBlackTree.contains(toDelete))
    {
      redBlackTree.remove(toDelete);
      return toDelete;
    }
    
    return null;
  }
  /**
   * Adds the specified SongInterface to the database if it does not already exist.
   * @param song the SongInterface to be added to the database
  */
  public void addToDatabase(SongInterfaceBD song) {
    // TODO Auto-generated method stub
    if(!redBlackTree.contains(song))
    {
      redBlackTree.insert(song);
      
    }
  }
  /**
   * Returns the number of Songs currently in the database.
   * @return the number of Songs in the database
  */
  public int getSize()
  {
    return playlist.size();
  }
  
  /**
   * Returns a String containing statistics about the current state of the database.
   * @return a String containing the number of Songs in the database
  */
  @Override
  public String getStatisticsString() {
    // TODO Auto-generated method stub
    return "There are " + redBlackTree.size() + " in the database.";
  }
  @Override
  public void addToPlaylist(List<String> songsToAdd) {
    // TODO Auto-generated method stub
    
  }
  @Override
  public List<SongInterface> removeFromPlaylist(List<String> songsToRemove) {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public SongInterface deleteFromDatabase(String toDelete) {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public void addToDatabase(SongInterface song) {
    // TODO Auto-generated method stub
    
  }
  
}