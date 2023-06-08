import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class PBBackendTest {

  @Test
  //tests backend's loadData
  void test1() {
    RedBlackTreeBD tree = new RedBlackTreeBD();
    SongReaderBD reader = new SongReaderBD();
    PBBackendBD backend = new PBBackendBD(tree, reader);
    
    String filename = "filename";
    
    try {
      backend.loadData(filename);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //System.out.println(tree.size());
    assertEquals(tree.size(), 1);
  }
  @Test
  //tests backend's addToPlaylist
  void test2()
  {
    RedBlackTreeBD tree = new RedBlackTreeBD();
    SongReaderBD reader = new SongReaderBD();
    PBBackendBD backend = new PBBackendBD(tree, reader);
    
    SongBD song1 = new SongBD("Homecoming", "ye", "Graduation", 10000);
    backend.addToPlaylist(song1);
    assertEquals(backend.getSize(), 1);
  }
  @Test
  //tests backend's removeToPlaylist
  void test3() 
  {
    RedBlackTreeBD tree = new RedBlackTreeBD();
    SongReaderBD reader = new SongReaderBD();
    PBBackendBD backend = new PBBackendBD(tree, reader);
    
    SongBD song1 = new SongBD("Homecoming", "ye", "Graduation", 10000);
    backend.addToPlaylist(song1);
    assertEquals(backend.getSize(), 1);
    backend.removeFromPlaylist(song1);
    assertEquals(backend.getSize(), 0);
  }
  
  @Test
  //tests backend's getAllSongs
  void test4()
  {
    RedBlackTreeBD tree = new RedBlackTreeBD();
    SongReaderBD reader = new SongReaderBD();
    PBBackendBD backend = new PBBackendBD(tree, reader);
    SongBD song1 = new SongBD("Homecoming", "ye", "Graduation", 10000);
    SongBD song2 = new SongBD("Stronger", "ye", "Graduation", 20000);
    SongBD song3 = new SongBD("Rich Flex", "Drake", "Her Loss", 5000);
    backend.addToPlaylist(song1);
    backend.addToPlaylist(song2);
    //backend.addToPlaylist(song3);
    
    //System.out.println(backend.getAllSongs(true));
    String output = "Homecoming - ye" + "\n" + "Stronger - ye" + "\n";
    //System.out.println(output);
    assertEquals(backend.getAllSongs(true), output);
    backend.addToPlaylist(song3);
    output += "Rich Flex - Drake" + "\n";
    assertEquals(backend.getAllSongs(true), output);
    
  }
  @Test
  //tests backend's addToDatabase and getStatisticsString
  void test5()
  {
    RedBlackTreeBD tree = new RedBlackTreeBD();
    SongReaderBD reader = new SongReaderBD();
    PBBackendBD backend = new PBBackendBD(tree, reader);
    
    SongBD song1 = new SongBD("Homecoming", "ye", "Graduation", 10000);
    SongBD song2 = new SongBD("Stronger", "ye", "Graduation", 20000);
    SongBD song3 = new SongBD("Rich Flex", "Drake", "Her Loss", 5000);
    
    backend.addToDatabase(song1);
    backend.addToDatabase(song2);
    backend.addToDatabase(song3);
    
    assertEquals(backend.getStatisticsString(), "There are 3 in the database.");  
    
  }
  @Test
  //tests the frontend's loadData command 
  //CodeReviewOfFrontendDeveloper
  void codeReviewOfFrontendDeveloperTest1()
  {
    TextUITester tester = new TextUITester("L\nsongs1.csv\nV");
    Scanner scanner = new Scanner(System.in);
    try
    {
      SongReaderInterface songReader = new SongReader();
      RedBlackTreeInterface<SongInterface> redBlackTree;
      redBlackTree = new RedBlackTreeAE();
      PBBackendInterface backend = new PBBackend(redBlackTree, songReader);
      PBFrontendInterface frontend = new PBFrontendFD(scanner, backend);
      frontend.runCommandLoop();
      String output = tester.checkOutput();
      assertTrue(output.contains("Title: Astronaut In The Ocean, Artist: Masked Wolf, Album: Single, Plays: 4000000, "
          + "Title: Deja Vu, Artist: Olivia Rodrigo, Album: SOUR, Plays: 5000000, "
          + "Title: Good 4 U, Artist: Olivia Rodrigo, Album: SOUR, Plays: 10000000, "
          + "Title: Kiss Me More, Artist: Doja Cat featuring SZA, Album: Planet Her, Plays: 8000000, "
          + "Title: Leave The Door Open, Artist: Silk Sonic, Album: An Evening with Silk Sonic, Plays: 9000000, "
          + "Title: Levitating, Artist: Dua Lipa, Album: Future Nostalgia, Plays: 11000000, "
          + "Title: Montero (Call Me By Your Name), Artist: Lil Nas X, Album: Montero, Plays: 12000000, "
          + "Title: Peaches, Artist: Justin Bieber featuring Daniel Caesar and Giveon, Album: Justice, Plays: 7000000, "
          + "Title: Save Your Tears, Artist: The Weeknd and Ariana Grande, Album: After Hours (Remix), Plays: 6000000 "));
    }
    catch(Exception e)
    {
      
    }
  }
  @Test
  //tests the frontend's displayStatisticsCommand 
  //CodeReviewOfFrontendDeveloper
  void codeReviewOfFrontendDeveloperTest2()
  {
    TextUITester tester = new TextUITester("L\nsongs1.csv\nS");
    Scanner scanner = new Scanner(System.in);
    try
    {
      SongReaderInterface songReader = new SongReader();
      RedBlackTreeInterface<SongInterface> redBlackTree;
      redBlackTree = new RedBlackTreeAE();
      PBBackendInterface backend = new PBBackend(redBlackTree, songReader);
      PBFrontendInterface frontend = new PBFrontendFD(scanner, backend);
      frontend.runCommandLoop();
      String output = tester.checkOutput();
      assertTrue(output.contains("There are 9 songs in the database."));
    }
    catch(Exception e)
    {
      
    }
  }
  @Test
  //Loads the csv file and deletes all Olivia Rodrigo songs from the database
  void IntegrationTest1()
  {
    TextUITester tester = new TextUITester("L\nsongs1.csv\nD\n2\nOlivia Rodrigo\n");
    Scanner scanner = new Scanner(System.in);
    try
    {
      SongReaderInterface songReader = new SongReader();
      RedBlackTreeInterface<SongInterface> redBlackTree;
      redBlackTree = new RedBlackTreeAE();
      PBBackendInterface backend = new PBBackend(redBlackTree, songReader);
      PBFrontendInterface frontend = new PBFrontendFD(scanner, backend);
      frontend.runCommandLoop();
      String output = tester.checkOutput();
      assertTrue(output.contains("Title: Deja Vu, Artist: Olivia Rodrigo, Album: SOUR, Plays: 5000000\n"
          + "Title: Good 4 U, Artist: Olivia Rodrigo, Album: SOUR, Plays: 10000000\n"
          + "Deleted all songs from the database and playlist by Olivia Rodrigo"));
    }
    catch(Exception e)
    {
      
    }
  }
  @Test
  //Loads the csv file, adds three songs to the playlist, and removes one song from the playlist
  void IntegrationTest2()
  {
    TextUITester tester = new TextUITester("L\nsongs1.csv\nA\n"
        + "Astronaut In The Ocean\nn\n"
        + "Deja Vu\nn\n"
        + "Kiss Me More\ny\n"
        + "R\n2\nOlivia Rodrigo\nP");
    Scanner scanner = new Scanner(System.in);
    try
    {
      SongReaderInterface songReader = new SongReader();
      RedBlackTreeInterface<SongInterface> redBlackTree;
      redBlackTree = new RedBlackTreeAE();
      PBBackendInterface backend = new PBBackend(redBlackTree, songReader);
      PBFrontendInterface frontend = new PBFrontendFD(scanner, backend);
      frontend.runCommandLoop();
      String output = tester.checkOutput();
      assertTrue(output.contains("Title: Astronaut In The Ocean, Artist: Masked Wolf, Album: Single, Plays: 4000000\n"
          + "Title: Kiss Me More, Artist: Doja Cat featuring SZA, Album: Planet Her, Plays: 8000000"));
    }
    catch(Exception e)
    {
      
    }
  }
}