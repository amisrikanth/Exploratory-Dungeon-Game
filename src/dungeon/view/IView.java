package dungeon.view;

import dungeon.controller.IDungeonGUIController;
import dungeon.model.IReadOnlyDungeon;

/**
 * The view interface. To motivate the methods here
 * think about all the operations that the controller
 * would need to invoke on the view
 *
 */
public interface IView {
  /**
   * Make the view visible. This is usually called
   * after the view is constructed
   */
  void makeVisible();

  /**
   * Set up the controller to handle click events in this view.
   *
   * @param listener the controller
   */
  void addClickListener(IDungeonGUIController listener);

  /**
   * This method takes in a model and recreates the dungeon on the view.
   * @param model The read only model.
   */
  void createDungeon(IReadOnlyDungeon model);

  /**
   * Set up the controller to handle key press events in this view.
   *
   * @param listener the controller
   */
  void bindKeys(IDungeonGUIController listener);


  /**
   * Signal the view to show a pop showing information or ask for an input.
   * @param text The text to be shown on the popup.
   * @param type message or input.
   * @return the input given by the user for input box or empty string.
   */
  String showPopUp(String text, String type);

  /**
   * Signal the view to draw itself after a move or an action is made.
   */
  void refresh();

  /**
   * Signal the view to close the quit the game and close the instance.
   */
  void close();

  /**
   * This method takes in the input from the configuration screen and notifies the controller and
   * the controller creates a dungeon based on the given parameters.
   * @param name Then name of the player.
   * @param rows The rows of the dungeon.
   * @param columns The columns of the dungeon.
   * @param interconnectivity The interconnectivity of the dungeon.
   * @param isWrapped If the dungeon is wrapped.
   * @param percentageOfCavesWithTreasures the percentage of caves with treasures / percentage of
   *                                       locations with arrows.
   * @param numberOfOtyughs the number of otyughs in the dungeon.
   */
  void storeDungeonParameters(String name, int rows, int columns, int interconnectivity,
                              boolean isWrapped, int percentageOfCavesWithTreasures,
                              int numberOfOtyughs);

}
