package dungeon.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the dungeon.ILocation interface and all the classes that implement it.
 */
public class ILocationTest {

  ILocation location;

  @Before
  public void setUp() {
    location = new Location(7);
    location.assignItemsToLocation(Collectibles.SAPPHIRE, 30);
    location.assignItemsToLocation(Collectibles.RUBY, 40);
    location.assignItemsToLocation(Collectibles.DIAMOND, 50);
  }

  @Test
  public void getDescription() {
    location.connectSouth();
    assertTrue(location.getDescription()
            .contains("You are in a cave\nCollectibles at the location : "));
    assertTrue(location.getDescription().contains("SAPPHIRE : 30"));
    assertTrue(location.getDescription().contains("RUBY : 40"));
    assertTrue(location.getDescription().contains("DIAMOND : 50"));
  }

  @Test
  public void getPossibleDirection() {
    location.connectEast();
    location.connectSouth();
    List<Direction> expected = new ArrayList<>();
    expected.add(Direction.EAST);
    expected.add(Direction.SOUTH);
    assertEquals(expected, location.getPossibleDirections());
  }

  @Test
  public void getTreasure() {
    Map<Collectibles, Integer> expected = new HashMap<>();
    expected.put(Collectibles.SAPPHIRE, 30);
    expected.put(Collectibles.RUBY, 40);
    expected.put(Collectibles.DIAMOND, 50);
    assertEquals(expected, location.getCollectibles());
  }

  @Test
  public void getLocationType() {
    location.connectSouth();
    location.connectNorth();
    assertEquals(LocationType.TUNNEL, location.getLocationType());
    location.connectWest();
    assertEquals(LocationType.CAVE, location.getLocationType());
  }
}