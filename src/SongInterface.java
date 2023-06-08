// --== CS400 Spring 2023 File Header Information ==--
// Name: Arjun Gopal
// Email: gopal5@wisc.edu
// Team: BI
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
public interface SongInterface extends Comparable<SongInterface>{
   // public SongInterface(String title, String artist, String album, int plays);
   public String getTitle();
   public String getArtist();
   public String getAlbum();
   public int getPlays(); 
   public int compareTo(SongInterface otherSong);
}
