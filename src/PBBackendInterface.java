import java.io.FileNotFoundException;
import java.util.List;

public interface PBBackendInterface {
//Public PBBackendXX( RedBlackTreeTraversalInterface<String, SongInterface> redBlackTree, SongReaderInterface songReader) 

  public void loadData(String filename) throws FileNotFoundException;
  public List<String> findSongsByTitleWords(String words);
  public List<String> findSongsByArtistName(String words);
  public List<String> findSongsByAlbum(String words);
  public String getAllSongs(boolean inPlaylist);
  public void addToPlaylist(List<String> songsToAdd);
  public List<SongInterface> removeFromPlaylist(List<String> songsToRemove);
  public SongInterface deleteFromDatabase(String toDelete);
  public String getStatisticsString();
  public void addToDatabase(SongInterface song);

}
