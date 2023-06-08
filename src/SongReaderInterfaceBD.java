 import java.io.FileNotFoundException;
import java.util.List;

public interface SongReaderInterfaceBD {
  //public SongReaderInterface();
  public List<SongInterfaceBD> readFromFile(String filename) throws FileNotFoundException;
}
