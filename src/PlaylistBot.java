import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistBot {

  public static void main(String[] args) throws FileNotFoundException {
    // TODO Auto-generated method stub
    SongReaderInterface songReader = new SongReader();
    RedBlackTreeInterface<SongInterface> redBlackTree;
    redBlackTree = new RedBlackTreeAE();
    PBBackendInterface backend = new PBBackend(redBlackTree, songReader);
    Scanner scanner = new Scanner(System.in);
    PBFrontendInterface frontend = new PBFrontendFD(scanner, backend);
    frontend.runCommandLoop();
  }

}
