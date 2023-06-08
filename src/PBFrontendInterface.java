// --== CS400 Spring 2023 File Header Information ==--
// Name: Arjun Gopal
// Email: gopal5@wisc.edu
// Team: BI
// TA: Naman Gupta
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>
import java.util.List;

public interface PBFrontendInterface {
  //public PBFrontendXX(Scanner userInput, PBBackendInterface backend);
  public void runCommandLoop();
  public char mainMenuPrompt();
  public void loadDataCommand();
  public List<String> chooseSongsPrompt();
  public void addSongsCommand(List<String> songs);
  public void removeSongsCommand(List<String> songs);
  public void deleteFromDatabaseCommand(List<String> songs);
  public void displaySongsCommand(boolean all);
  public void displayStatsCommand();
}


