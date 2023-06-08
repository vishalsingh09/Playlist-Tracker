// --== CS400 Spring 2023 File Header Information ==--
// Name: Arjun Gopal
// Email: gopal5@wisc.edu
// Team: BI
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the methods that display the User Interface for the Playlist Bot project
 */
public class PBFrontendFD implements PBFrontendInterface {

  private Scanner userInput;
  private PBBackendInterface backend;

  /**
   * Constructor for the Frontend that initializes the userInput and backend
   * @param userInput the input the user gives through the command-line
   * @param backend the backend that implements the methods called in this class
   */
  public PBFrontendFD(Scanner userInput, PBBackendInterface backend) {
    this.userInput = userInput;
    this.backend = backend;
  }

  /**
   * Helper method to display a a horizontal line of dashes to serve as a gap in the UI
   */
  private void gap() {
    System.out.println(" \n-----------------------------------\n");
  }


  /**
   * This loop repeated prompts the user for commands and displays appropriate
   * feedback for each. This continues until the user enters 'q' to quit.
   */
  @Override
  public void runCommandLoop() {
    gap(); // display welcome message
    System.out.println("Welcome to the Playlist Bot");

    char command = '\0';
    while (command != 'Q') { // main loop continues until user chooses to quit
      gap();
      command = this.mainMenuPrompt();
      switch (command) {
        case 'L': // System.out.println(" [L]oad song database from file");
          loadDataCommand();
          break;
        case 'A': // System.out.println(" [A]dd songs to playlist");
          List<String> songs = chooseSongsPrompt();
          addSongsCommand(songs);
          break;
        case 'R': // System.out.println(" [R]emove songs from playlist");
          removeFromPlaylistPrompt();
          break;
        case 'D': // System.out.println(" [D]elete songs from the database");
          deleteFromDatabasePrompt();
          break;
        case 'P': // System.out.println(" [P]rint songs in Playlist");
          displaySongsCommand(false);
          break;
        case 'V': // System.out.println(" [V]iew all available songs");
          displaySongsCommand(true);
          break;
        case 'S': // System.out.println(" [S]tatistics");
          displayStatsCommand();
          break;
        case 'Q': // System.out.println(" [Q]uit");
          // do nothing, containing loop condition will fail
          break;
        default:
          System.out.println(
              "Didn't recognize that command.  Please type one of the letters presented within []s to identify the command you would like to choose.");
          break;
      }
    }

    gap(); // thank user before ending this application
    System.out.println("Goodbye!");
    gap();
  }

  /**
   * Prints the command options to System.out and reads user's choice through
   * userInput scanner.
   */
  @Override
  public char mainMenuPrompt() {
    // display menu of choices
    System.out.println("Commands:");
    System.out.println("    [A]dd songs to playlist");
    System.out.println("    [R]emove songs from playlist");
    System.out.println("    [L]oad song database from file");
    System.out.println("    [D]elete songs from the database");
    System.out.println("    [P]rint songs in Playlist");
    System.out.println("    [V]iew all available songs");
    System.out.println("    [S]tatistics");
    System.out.println("    [Q]uit");

    // read in user's choice, and trim away any leading or trailing whitespace
    System.out.print("Choose command: ");
    String input = userInput.nextLine().trim();
    if (input.length() == 0) // if user's choice is blank, return null character
      return '\0';
    // otherwise, return an uppercase version of the first character in input
    return Character.toUpperCase(input.charAt(0));
  }

  /**
   * Prompt user to enter filename, and display an error message when loading fails.
   */
  @Override
  public void loadDataCommand() {
    System.out.print("Enter the name of the file to load: ");
    String filename = userInput.nextLine().trim();
    try {
      backend.loadData(filename);
    } catch (FileNotFoundException e) {
      System.out.println("Error: Could not find or load file: " + filename);
    }
  }

  /**
   * This method gives user the ability to interactively add as many songs they want to the playlist
   */
  @Override
  public List<String> chooseSongsPrompt() {
    List<String> songs = new ArrayList<>();
    String loop = "n";
    while (loop.equals("n")) { // this loop ends when the user pressed enter (without typing any words)
      System.out.println("List of songs to add: " + songs.toString());
      System.out.println("Which songs would you like to add?");
      String input = userInput.nextLine().trim();
      if (input.length() == 0) // an empty string ends this loop and method call
        return songs;
      else
        // otherwise the specified word's presence in the list is toggled:
        for (String word : input.split(","))
          if (songs.contains(word))
            songs.remove(word); // remove any words that were already in the list
          else
            songs.add(word); // add any words that were previously missing
        System.out.println("Is that all? (y/n)");
        loop = userInput.nextLine().trim().toLowerCase();
    }
    return songs;
  }


  /**
   * Takes the songs that the user wants to add and confirms their addition to the playlist
   * @param songs the list of songs inputted by the user
   */
  @Override
  public void addSongsCommand(List<String> songs) {
    backend.addToPlaylist(songs);
    System.out.println("Okay, added those songs to your playlist.");
  }

  /**
   * This method gives user the ability to interactively remove as many songs they want from the
   * playlist based on the title, artist, and album of the song
   */
  public void removeFromPlaylistPrompt() {
    System.out.println("Remove songs from the playlist by:\n[1] Name\n[2] Artist\n[3] Album");
    String input = userInput.nextLine().trim();
    switch (input) {
      case "1" -> { // System.out.println("[1] Name");
        System.out.println("Remove songs from the playlist by: ");
        String title = userInput.nextLine().trim();
        removeSongsCommand(backend.findSongsByTitleWords(title));
        System.out.println(
            "Removed all songs from the playlist " +
                "with the title including the words: " + title);
      }
      case "2" -> { // System.out.println("[2] Artist");
        System.out.println("Remove all songs from the playlist by: ");
        String artist = userInput.nextLine().trim();
        removeSongsCommand(backend.findSongsByArtistName(artist));
        System.out.println("Removed all songs from the playlist by " + artist);
      }
      case "3" -> { // System.out.println("[3] Album");
        System.out.println("Remove all songs from the playlist in this Album: ");
        String album = userInput.nextLine().trim();
        removeSongsCommand(backend.findSongsByAlbum(album));
        System.out.println("Removed all songs from the playlist from " + album);
      }
      default -> System.out.println("Invalid Input");
    }
  }

  /**
   * This method performs the operation of removing the songs from the playlist in the backend
   * @param songs the list of songs to remove
   */
  @Override
  public void removeSongsCommand(List<String> songs) {
    backend.removeFromPlaylist(songs);
  }

  /**
   * This method gives user the ability to interactively delete as many songs they want from the
   * entire database based on the title, artist, and album of the song
   */
  public void deleteFromDatabasePrompt() {
    System.out.println("Delete songs from the database by:\n[1] Name\n[2] Artist\n[3] Album");
    String input = userInput.nextLine().trim();
    switch (input) {
      case "1" -> { // System.out.println("[1] Name");
        System.out.println("Delete the songs from the database called: ");
        String title = userInput.nextLine().trim();
        deleteFromDatabaseCommand(backend.findSongsByTitleWords(title));
        System.out.println(
            "Deleted all songs from the database and playlist " +
                "with the title including the words: " + title);
      }
      case "2" -> { // System.out.println("[2] Artist");
        System.out.println("Delete all songs from the database by: ");
        String artist = userInput.nextLine().trim();
        deleteFromDatabaseCommand(backend.findSongsByArtistName(artist));
        System.out.println("Deleted all songs from the database and playlist by " + artist);
      }
      case "3" -> { // System.out.println("[3] Album");
        System.out.println("Delete all songs from the database in this Album: ");
        String album = userInput.nextLine().trim();
        deleteFromDatabaseCommand(backend.findSongsByAlbum(album));
        System.out.println("Deleted all songs from the database and playlist from " + album);
      }
      default -> System.out.println("Invalid Input");
    }
  }

  /**
   * This method performs the operation of deleting the songs from the database in the backend
   * @param songs the list of songs to delete
   */
  @Override
  public void deleteFromDatabaseCommand(List<String> songs) {
    for (String s : songs) {
      backend.deleteFromDatabase(s);
    }
  }

  /**
   * This method retrieves either a list of all songs in the database or a list of all songs
   * in the current playlist
   * @param all the boolean that determines if its a list of all songs or just the songs in the
   *            playlist
   */
  @Override
  public void displaySongsCommand(boolean all) {
    System.out.println(backend.getAllSongs(!all));
  }

  /**
   * This method retrieves important statistics about the playlist bot from the backend and
   * prints it out to console
   */
  @Override
  public void displayStatsCommand() {
    System.out.println(backend.getStatisticsString());
  }
}

