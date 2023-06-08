

/*
 * Brief Project Description: The project is a playlist bot where users can input songs and the
 * program will add them to a playlist. Users can add songs to their playlists and also remove them
 * when needed. By using a Red-Black Tree data structure, the application can efficiently store and
 * manage a large collection of songs with quick insertion, deletion, and retrieval operations of
 * songs. The DataWrangler reads the CSV file of songs into a Red-Black Tree. The
 * BackendDeveloper's code makes use of the Red-Black Tree data structure to manage all the songs
 * in the “database” and the AlgorithmEngineer extends this functionality in a general way to
 * improve the performance and functionality of the data structure. The FrontendDeveloper is
 * responsible for creating an intuitive user interface through which users can interact with the
 * application and manage their playlist.
 */

// Data Description:
// The data being loaded by this project is a collection of songs, each with a title, artist, album and total plays. The data is stored in a CSV format file, with each row representing a single song and each column representing a different song

// Ex:
// Songname1, artist, album, plays
// Songname2, artist, album, plays
// Songname3, artist, album, plays
// Songname4, artist, album, plays
// Development Responsibilities:
// The Data Wrangler is responsible for developing the Song.java class, which will define the structure of each Song object and the methods needed to read and process the Song data from the CSV file. They will also be responsible for implementing any additional data processing or filtering functions needed to make effective use of the Red-Black Tree data structure.

public class Song implements SongInterface {
  
  
  private String title;

  private String artist;

  private String album;

  private int plays;
  
  /**
   * Constructor for Song
   * @param title   title of the song
   * @param artist  artist of the song
   * @param album   album of the song
   * @param plays   number of times the song has been played
   */
  public Song(String title, String artist, String album, int plays) {
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.plays = plays;
  }
  
  public String getTitle() {
    return title;
  }
  
  public String getArtist() {
    return artist;
  }
  
  public String getAlbum() {
    return album;
  }
  
  public int getPlays() {
    return plays;
  }
  
  public int compareTo(Song otherSong) {
    return this.title.compareTo(otherSong.getTitle());
  }
  
  public String toString() {
    return "Title: " + title + ", Artist: " + artist + ", Album: " + album + ", Plays: " + plays;
  }
  
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (other == this) {
      return true;
    }
    if (!(other instanceof Song)) {
      return false;
    }
    Song otherSong = (Song) other;
    return this.title.equals(otherSong.getTitle()) && this.artist.equals(otherSong.getArtist()) && this.album.equals(otherSong.getAlbum()) && this.plays == otherSong.getPlays();
  }
  
  public int hashCode() {
    return title.hashCode() + artist.hashCode() + album.hashCode() + plays;
  }


  //the compareTo method compares the title of the song to the title of the other song and the artist of the song to the artist of the other song
  @Override
  public int compareTo(SongInterface otherSong) {
    if (this.title.compareTo(otherSong.getTitle()) == 0) {
      return this.artist.compareTo(otherSong.getArtist());
    }
    return this.title.compareTo(otherSong.getTitle());
  }

}
