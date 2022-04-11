package dungeon.model;

import java.util.List;
import java.util.Map;

/**
 * This Interface represents the locations within the dungeon. The location can be a cave or a
 * tunnel, a location can further be classified as tunnel (which has exactly 2 entrances) or a
 * cave(which has 1, 3 or 4 entrances). In the image above, we are representing caves as circular
 * spaces. The start and end point of a dungeon can only be caves and only the caves can have
 * treasure.
 */
interface ILocation {
  /**
   * This method gives the description of the location and the treasure if there is any.
   *
   * @return The description of the location
   */
  String getDescription();

  /**
   * This method gives the collectibles that are present at the location.
   *
   * @return The collectibles that are present at the location.
   */
  Map<Collectibles, Integer> getCollectibles();

  /**
   * This method gives the type of the location i.e. either a cave or a tunnel.
   *
   * @return The type of the location.
   */
  LocationType getLocationType();

  /**
   * This method establishes a  connection between the location and the location to its north.
   */
  void connectNorth();

  /**
   * This method establishes a  connection between the location and the location to its south.
   */
  void connectSouth();

  /**
   * This method establishes a  connection between the location and the location to its east.
   */
  void connectEast();

  /**
   * This method establishes a  connection between the location and the location to its west.
   */
  void connectWest();

  /**
   * This method gives all the possible directions that can be taken from the location.
   *
   * @return The possible directions that can be taken from the location.
   */
  List<Direction> getPossibleDirections();

  /**
   * This method gives the number of the location that uniquely identifies the location.
   *
   * @return The number of the location.
   */
  int getLocationNumber();

  /**
   * This method assigns treasure to the location.
   *
   * @param treasure The treasure to be assigned.
   * @param qty      The quantity of the treasure.
   */
  void assignItemsToLocation(Collectibles treasure, int qty);

  /**
   * This method lets the player collect an item from the location.
   *
   * @return If the picking up was successful or not.
   */
  boolean pickItem(Collectibles collectible);

  /**
   * This method places an Otyugh in the cave with full strength.
   */
  void placeOtyugh();

  /**
   * This method gives the health of the Otyugh at the location if there is an Otyugh and returns 0
   * if there is no Otyugh or if it is dead.
   *
   * @return The health of the monster in the cave.
   */
  int getMonsterHealth();

  /**
   * This method facilitates the arrow hitting the monster and injures the monster.
   */
  void hitMonster();


  void visitLocation();

  boolean isVisited();
}
