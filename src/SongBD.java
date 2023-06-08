
public class SongBD implements SongInterfaceBD{
  
  private String title;
  private String artist;
  private String album;
  private int plays;
  public SongBD(String title, String artist, String album, int plays)
  {
    this.title = title;
    this.artist = artist;
    this.album = album;
    this.plays = plays;
  }
  public int compareTo(SongInterface o) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return title;
  }

  @Override
  public String getArtist() {
    // TODO Auto-generated method stub
    return artist;
  }

  @Override
  public String getAlbum() {
    // TODO Auto-generated method stub
    return album;
  }

  @Override
  public int getPlays() {
    // TODO Auto-generated method stub
    return 1000000;
  }
  public String toString()
  {
    return title + " - " + artist;
    
  }
  @Override
  public int compareTo(SongInterfaceBD o) {
    // TODO Auto-generated method stub
    return 0;
  }

}
