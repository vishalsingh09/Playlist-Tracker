import java.io.FileNotFoundException;
import java.util.List;

public interface SongReaderInterface {
  // public SongReaderInterface();
  public List<SongInterface> readFromFile(String filename) throws FileNotFoundException;
}
