package dungeon.controller;

/**
 * This is the GUI interface for the controller for the Dungeon. the controller handles the input
 * from the view and tells the model to mutate its state based on the actions and events on the
 * view and conveys the state of model to view through a read only model.
 */
public interface IDungeonGUIController {

  /**
   * This method lets the user play the game and also adds the controller to listen to the
   * events in the view.
   */
  void play();

  /**
   * Handle a mouse click action on a single location of the dungeon, such as to make a move.
   * @param location the location on which the mouse was clicked.
   */
  void handleCellClick(int location);

  /**
   * Handle a mouse click action on the menu items such as restart, go to home screen and quit.
   * @param item the item that was clicked.
   */
  void handleMenuItemClick(String item);

  /**
   * This method takes in the input from the configuration screen and creates a dungeon based on the
   * given parameters.
   * @param name Then name of the player.
   * @param rows The rows of the dungeon.
   * @param columns The columns of the dungeon.
   * @param interconnectivity The interconnectivity of the dungeon.
   * @param isWrapped If the dungeon is wrapped.
   * @param percentageOfCavesWithTreasures the percentage of caves with treasures / percentage of
   *                                       locations with arrows.
   * @param numberOfOtyughs the number of otyughs in the dungeon.
   */
  void createModel(String name, int rows, int columns, int interconnectivity,
                   boolean isWrapped, int percentageOfCavesWithTreasures,
                   int numberOfOtyughs);

  /**
   * this generates the dungeon with the inputs for the dungeon given by the user.
   */
  void generateDungeon();

  /**
   * Handle a Key press event on the keyboard such as "s", "d", "a", "r" and arrow keys .
   * @param item what event was triggered.
   */
  void handleKeyPress(String item);
}

