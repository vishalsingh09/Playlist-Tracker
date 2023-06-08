import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SongReader implements SongReaderInterface {

  // read filename and return a list of songs with the the filename in a csv format "Song Name,Artist,Album,Plays"
  // if file is not found, throw a FileNotFoundException
  public List<SongInterface> readFromFile(String filename) throws FileNotFoundException {
    File file = new File(filename);
    if (!file.exists()) {
      throw new FileNotFoundException();
    }
    Scanner scanner = new Scanner(file);
    List<SongInterface> songs = new ArrayList<SongInterface>();
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      // System.out.println(line);
      
      String[] songData = line.split(",");
      if( songData.length != 4) {
        continue;
      }
      String title = songData[0];
      String artist = songData[1];
      String album = songData[2];
      int plays;
      try{
        plays = Integer.parseInt(songData[3]);
        SongInterface song = new Song(title, artist, album, plays);
        songs.add(song);
      }
      catch (NumberFormatException e) {        
      }
    }
    scanner.close();
    return songs;
  }

}
