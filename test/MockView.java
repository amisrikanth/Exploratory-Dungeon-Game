import java.io.IOException;

import dungeon.controller.IDungeonGUIController;
import dungeon.model.IReadOnlyDungeon;
import dungeon.view.IView;

/**
 * This class is a mock view to test the interaction with the GUI controller.
 */
public class MockView implements IView {
  private Appendable log;

  /**
   * Initializes the log that is checked for the correctness of the GUI controller.
   * @param log the log.
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void makeVisible() {
    try {
      log.append("Made the frame visible\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addClickListener(IDungeonGUIController listener) {
    try {
      log.append("Controller added for listening to clicks on view\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void createDungeon(IReadOnlyDungeon model) {
    try {
      log.append("Created Dungeon screen\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void bindKeys(IDungeonGUIController listener) {
    try {
      log.append("Controller added for listening to keyboard key presses\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public String showPopUp(String text, String type) {
    if (type.equals("input")) {
      try {
        log.append("Showed pop up for asking input\n");
      } catch (IOException e) {
        // do nothing
      }
      return "1";
    }
    else if (type.equals("msg")) {
      try {
        log.append("Showed pop up for showing info\n");
      } catch (IOException e) {
        // do nothing
      }
    }
    return null;
  }

  @Override
  public void refresh() {
    try {
      log.append("The frame is refreshed\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void close() {
    try {
      log.append("The game is quit\n");
    } catch (IOException e) {
      // do nothing
    }
  }


  @Override
  public void storeDungeonParameters(String name, int rows, int columns, int interconnectivity,
                                     boolean isWrapped, int percentageOfCavesWithTreasures,
                                     int numberOfOtyughs) {
    try {
      log.append("Controller added for listening to key press events\n");
    } catch (IOException e) {
      // do nothing
    }
  }
}
