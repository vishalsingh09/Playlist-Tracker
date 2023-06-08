import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class SongReaderBD implements SongReaderInterfaceBD{

  @Override
  public List<SongInterfaceBD> readFromFile(String filename) throws FileNotFoundException {
    // TODO Auto-generated method stub
    List<SongInterfaceBD> songs = new ArrayList<SongInterfaceBD>();
    SongBD song1 = new SongBD("Bigger Picture", "Lil Baby", "My Turn", 10000);
    songs.add(song1);
    return songs;
  }

}
