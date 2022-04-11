import java.util.ArrayList;
import java.util.List;

import dungeon.model.Collectibles;
import dungeon.model.Direction;
import dungeon.model.IDungeon;
import dungeon.model.Smell;

/**
 * This class is a mock dungeon to test the input from the controller.
 */
public class MockDungeon implements IDungeon {

  private final String uniqueCode;
  private final StringBuilder log;

  /**
   * Initializes the attributes.
   *
   * @param log        The log that keeps a log of input.
   * @param uniqueCode Code to output.
   */
  MockDungeon(StringBuilder log, String uniqueCode) {
    this.log = log;
    this.uniqueCode = uniqueCode;
  }

  @Override
  public int getInterconnectivity() {
    return 4;
  }

  @Override
  public boolean isWrapped() {
    return true;
  }

  @Override
  public String getPlayerDescription() {
    return "Name : Srikanth\nItems collected :\n ARROW : 3"
            + "\nCurrent Location number : 2\nCurrent Location type : CAVE";
  }

  @Override
  public String getLocationDescription(int locationNumber) {
    log.append(String.format("\nInput Location Number : %d", locationNumber));
    return "You are in a cave\nCollectibles at the location : \n RUBY : 2\n" +
            " DIAMOND : 1\n ARROW : 3";
  }

  @Override
  public int getStartLocation() {
    return 1;
  }

  @Override
  public int getEndLocation() {
    return 43;
  }

  @Override
  public List<Direction> getPossibleDirectionsFromLocation(int locationNumber) {
    List<Direction> directions = new ArrayList<>();
    directions.add(Direction.EAST);
    directions.add(Direction.WEST);
    directions.add(Direction.SOUTH);
    return directions;
  }

  @Override
  public int getPercentOfCavesWithTreasure() {
    return 0;
  }

  @Override
  public void regenerateDungeon() {
  //do nothing
  }

  @Override
  public void reset() {
  //do nothing
  }

  @Override
  public void movePlayer(Direction direction) {
    log.append(String.format("\nInput direction : %s", direction.toString().toLowerCase()));
  }

  @Override
  public int getCurrentLocationOfPlayer() {
    return 1;
  }

  @Override
  public boolean collectItemAtCurrentPlayerLocation(Collectibles item) {
    log.append(String.format("\nInput collectible item : %s", item.toString().toLowerCase()));
    return false;
  }

  @Override
  public Smell getSmellAtLocation(int locationNum) {
    return Smell.MORE_PUNGENT;
  }

  @Override
  public boolean shootArrow(Direction direction, int numOfCaves) {
    log.append(String.format("\nInput direction and number of caves : %s,%d",
            direction.toString().toLowerCase(), numOfCaves));
    return true;
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public boolean isPlayerAlive() {
    return true;
  }

  @Override
  public int numberOfColumnsInDungeon() {
    return 7;
  }

  @Override
  public int numberOfRowsInDungeon() {
    return 7;
  }

  @Override
  public boolean isLocationVisited(int locationNumber) {
    return true;
  }

  @Override
  public String toString() {
    return uniqueCode;
  }
}
