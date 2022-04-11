package dungeon.model;

import java.util.List;

/**
 * This interface is a read only version of the model Dungeon. This lets the user get the state of
 * the game at any given point of time abut it does not let any change or mutation of the model.
 * It consists of all the methods that allows the user to get the state of the game.
 */
public interface IReadOnlyDungeon {
  /**
   * This method gives the interconnectivity of the caves in the dungeon.
   *
   * @return interconnectivity of dungeon.
   */
  int getInterconnectivity();

  /**
   * This method gives whether a dungeon is a wrapping dungeon or a non - wrapping dungeon.
   *
   * @return True if wrapping else false.
   */
  boolean isWrapped();

  /**
   * This method gives the description of the player, the location of the player and treasure
   * collected if any.
   *
   * @return Description of the player.
   */
  String getPlayerDescription();

  /**
   * This method gives the description the location when a location number is passed to it.
   *
   * @param locationNumber The location number for description.
   * @return Description of location.
   */
  String getLocationDescription(int locationNumber);

  /**
   * This method gives the starting location/cave of the dungeon.
   *
   * @return The starting location/cave of the dungeon.
   */
  int getStartLocation();

  /**
   * This method gives the end location/cave of the dungeon.
   *
   * @return The end location/cave of the dungeon.
   */
  int getEndLocation();

  /**
   * This method gives a list of possible directions a player can take from his current location.
   *
   * @return List of possible directions a player can take.
   */
  List<Direction> getPossibleDirectionsFromLocation(int locationNumber);

  /**
   * This method gives the percentage of caves which had treasure when the dungeon was created.
   *
   * @return the percentage of caves with treasure.
   */
  int getPercentOfCavesWithTreasure();

  /**
   * This method gives the current location of the player.
   *
   * @return The current location of the player.
   */
  int getCurrentLocationOfPlayer();

  /**
   * This method gives the smell sensed by the player at the current location of the player.
   *
   * @return The smell at the location.
   */
  Smell getSmellAtLocation(int locationId);

  /**
   * This method gives the state of the game, i.e. if the game is over. The game gets over when the
   * player reaches the end cave safely or when the player is killed by an Otyugh.
   *
   * @return True if game is over else false.
   */
  boolean isGameOver();


  /**
   * This method gives the state of the player in the dungeon, i.e. if the player is dead or alive.
   *
   * @return True if player is alive else false.
   */
  boolean isPlayerAlive();

  /**
   * This method gives the number of columns of the dungeon.
   * @return the number of columns of the dungeon.
   */
  int numberOfColumnsInDungeon();

  /**
   * This method gives the number of rows of the dungeon.
   * @return the number of rows of the dungeon.
   */
  int numberOfRowsInDungeon();

  /**
   * This method lets  us check if a given location is visited by the player.
   * @param locationNumber The location number to check if it is visited.
   * @return True if location is visited else false.
   */
  boolean isLocationVisited(int locationNumber);

}
