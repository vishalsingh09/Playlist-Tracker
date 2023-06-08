import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
public class PBBackend implements PBBackendInterface{
  
  private RedBlackTreeInterface<SongInterface> redBlackTree;
  private SongReaderInterface songReader;
  private ArrayList<SongInterface> playlist;
  private int size;
  /**
   * constructor for the backend class
   * @param redBlackTree, red black tree that uses songs objects
   * @param songReader, reads a file of songs to add the red black tree
   */
  public PBBackend(RedBlackTreeInterface<SongInterface> redBlackTree, SongReaderInterface songReader)
  {
    this.redBlackTree = redBlackTree;
    this.songReader = songReader;
    playlist = new ArrayList<SongInterface>();
    size = 0;
  }
  public PBBackend(RedBlackTreeInterfaceBD<SongInterface> redBlackTree, SongReaderInterfaceBD songReader)
  {
    playlist = new ArrayList<SongInterface>();
    size = 0;
  }
  /**
   * @param filename, name of the file to retrieve songs
   * @throws FileNotFoundException, if the file cannot be find, then an exception is thrown 
   */
  @Override
  public void loadData(String filename) throws FileNotFoundException {
    // TODO Auto-generated method stub
    ArrayList<SongInterface> songs = (ArrayList<SongInterface>) songReader.readFromFile(filename);
    for(SongInterface song: songs)
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
  private void inOrderTraversal(SongInterface song, String words, List<String> matchingSongs, String type)
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
      for(SongInterface song: playlist) 
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
  @Override
  public void addToPlaylist(List<String> songsToAdd) {
      //creates a sublist that contains each song
    ArrayList<String> relatedSongs = new ArrayList<String>();
    for(String songTitle: songsToAdd)
    {
      relatedSongs.addAll(findSongsByTitleWords(songTitle));
    }
    String title = "";
    String artist = "";
    String album = "";
    int plays = 0;
    for(String str: relatedSongs)
    {
      String[] songFields = str.split(", ");

      for (String field : songFields) {
          String[] parts = field.split(": ");
          if (parts.length == 2) {
              String fieldTitle = parts[0].trim();
              String fieldValue = parts[1].trim();

              switch (fieldTitle) {
                  case "Title":
                      title = fieldValue;
                      break;
                  case "Artist":
                      artist = fieldValue;
                      break;
                  case "Album":
                      album = fieldValue;
                      break;
                  case "Plays":
                      plays = Integer.parseInt(fieldValue);
                      break;
              }
          }
      }
      Song song = new Song(title, artist, album, plays);
      playlist.add(song);
    }
  }
  @Override
  /**
   * This method removes a song from the playlist if it is present and returns the removed song. 
   * @param songsToRemove The song to be removed from the playlist.
   * @return The removed song.
   * @throws IllegalArgumentException If the song is not found in the playlist.
   */
  public List<SongInterface> removeFromPlaylist(List<String> songsToRemove){
    // TODO Auto-generated method stub
    //converts the song string into song objects
    //splits the song string into by the commas
    List<SongInterface> songsRemoved = new ArrayList<SongInterface>();
    for(String str: songsToRemove)
    {
      String[] songFields = str.split(", ");
      String title = "";
      String artist = "";
      String album = "";
      int plays = 0;

      for (String field : songFields) {
          String[] parts = field.split(": ");
          if (parts.length == 2) {
              String fieldTitle = parts[0].trim();
              String fieldValue = parts[1].trim();

              switch (fieldTitle) {
                  case "Title":
                      title = fieldValue;
                      break;
                  case "Artist":
                      artist = fieldValue;
                      break;
                  case "Album":
                      album = fieldValue;
                      break;
                  case "Plays":
                      plays = Integer.parseInt(fieldValue);
                      break;
              }
          }
      }
      //if the tree contains the song, then the song is removed
      if(redBlackTree.contains(new Song(title, artist, album, plays)))
      {
        playlist.remove(new Song(title, artist, album, plays));
        songsRemoved.add(new Song(title, artist, album, plays));
      }
    }
    return songsRemoved;
  }
  /**
   * Removes the specified Song from the database.
   * @param toDelete the Song to be removed from the database
   * @return the Song that was removed, or null if it was not found in the database
  */
  @Override
  public SongInterface deleteFromDatabase(String toDelete) {
    // TODO Auto-generated method stub
    //converts the song string into song objects
    //splits the song string into by the commas
    String[] songFields = toDelete.split(", ");
    String title = "";
    String artist = "";
    String album = "";
    int plays = 0;

    for (String field : songFields) {
        String[] parts = field.split(": ");
        if (parts.length == 2) {
            String fieldTitle = parts[0].trim();
            String fieldValue = parts[1].trim();

            switch (fieldTitle) {
                case "Title":
                    title = fieldValue;
                    break;
                case "Artist":
                    artist = fieldValue;
                    break;
                case "Album":
                    album = fieldValue;
                    break;
                case "Plays":
                    plays = Integer.parseInt(fieldValue);
                    break;
            }
        }
    }
    
    Song songToDelete = new Song(title, artist, album, plays);
    if(playlist.contains(songToDelete))
    {
      playlist.remove(songToDelete);
    }
    if(redBlackTree.contains(songToDelete))
    {
      redBlackTree.remove(songToDelete);
      return songToDelete;
    }
    return null;
  }
  /**
   * Adds the specified SongInterface to the database if it does not already exist.
   * @param song the SongInterface to be added to the database
  */
  public void addToDatabase(SongInterface song) {
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
    return "There are " + redBlackTree.size() + " songs in the database.";
  }
  
}
