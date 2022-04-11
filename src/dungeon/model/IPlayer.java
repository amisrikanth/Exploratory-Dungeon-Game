package dungeon.model;

import java.util.Map;

/**
 * This interface represents a player. The player in a dungeon can explore all the locations of
 * the tunnel and has to start the dungeon exploration from a cave and end at a cave.
 * The player can collect treasure from the caves while passing through them.
 */
interface IPlayer {

  /**
   * This method gives the description of the player, the location of the player and treasure
   * collected if any.
   *
   * @return Description of the player.
   */
  String getDescription();

  /**
   * This method gives the name of the player.
   *
   * @return The name of the player.
   */
  String getName();

  /**
   * This method gives the treasure collected by the player.
   *
   * @return The treasure collected by the player.
   */
  Map<Collectibles, Integer> getCollectedItems();

  /**
   * This method gives the current location of the player.
   *
   * @return The current location of the player.
   */
  ILocation getCurrentLocation();

  /**
   * This method lets the player collect the collectible items from the current location.
   */
  void pickCollectibles(Collectibles collectible);

  /**
   * This method facilitates setting the location of the player.
   *
   * @param location The location to set for a player.
   */
  void setLocation(ILocation location);

  void shootArrow();
}
