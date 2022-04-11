package dungeon.model;

/**
 * The model Dungeon is a network of tunnels and caves that are interconnected so that player can
 * explore the entire world by traveling from cave to cave through the tunnels that connect them.
 * Each location in the grid represents a location in the dungeon where a player can explore and
 * can be connected to at most four (4) other locations: one to the north, one to the east,
 * one to the south, and one to the west. Each dungeon can be constructed with a degree of
 * interconnectivity and the Dungeons may or may not be wrapped.
 */
public interface IDungeon extends IReadOnlyDungeon {

  /**
   * This method generates a new dungeon with the same configuration that was provided
   * while the Dungeon was created.
   */
  void regenerateDungeon();

  /**
   * This method resets the dungeon state to the state when it was created.
   */
  void reset();

  /**
   * This method moves the player in the direction specified.
   *
   * @param direction The direction to move the player.
   */
  void movePlayer(Direction direction);

  /**
   * This method is used to make the player collect a collectible item at the location the player
   * is in.
   */
  boolean collectItemAtCurrentPlayerLocation(Collectibles item);

  /**
   * This method lets the player shoot arrows in a given direction travelling the given number of
   * caves.
   *
   * @param direction  The direction in which the arrow has to be fired.
   * @param numOfCaves The number of caves the arrow should travel.
   * @return
   */
  boolean shootArrow(Direction direction, int numOfCaves);
}
