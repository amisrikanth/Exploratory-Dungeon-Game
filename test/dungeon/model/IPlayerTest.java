package dungeon.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the dungeon.IPlayer interface and all the classes that implement it.
 */
public class IPlayerTest {

  IPlayer player;
  ILocation location;

  @Before
  public void setUp() {
    location = new Location(2);
    location.assignItemsToLocation(Collectibles.DIAMOND, 1);
    location.assignItemsToLocation(Collectibles.RUBY, 2);
    player = new Player("Srikanth", location);
  }

  @Test
  public void getDescription() {
    String expected = "Name : Srikanth\nItems collected :\n ARROW : 3"
            + "\nCurrent Location number : 2\nCurrent Location type : CAVE";
    assertEquals(expected, player.getDescription());
    player.pickCollectibles(Collectibles.DIAMOND);
    assertTrue(player.getDescription()
            .contains("Name : Srikanth"));
    assertTrue(player.getDescription().contains("ARROW : 3"));
    assertTrue(player.getDescription().contains("DIAMOND : 1"));
    assertTrue(player.getDescription()
            .contains("Current Location type : CAVE"));
    assertTrue(player.getDescription().contains("Current Location number : 2"));

  }

  @Test
  public void getName() {
    assertEquals("Srikanth", player.getName());
  }

  @Test
  public void getItemsCollected() {
    Map<Collectibles, Integer> collectibles = new HashMap<>();
    collectibles.put(Collectibles.DIAMOND, 1);
    collectibles.put(Collectibles.RUBY, 2);
    collectibles.put(Collectibles.ARROW,3);
    player.pickCollectibles(Collectibles.DIAMOND);
    player.pickCollectibles(Collectibles.RUBY);
    player.pickCollectibles(Collectibles.RUBY);
    assertEquals(collectibles, player.getCollectedItems());
  }

  @Test
  public void getCurrentLocation() {
    assertEquals(location, player.getCurrentLocation());
  }

  @Test
  public void setLocation() {
    ILocation anotherLocation = new Location(3);
    anotherLocation.assignItemsToLocation(Collectibles.SAPPHIRE, 3);
    anotherLocation.assignItemsToLocation(Collectibles.RUBY, 2);
    anotherLocation.assignItemsToLocation(Collectibles.DIAMOND, 3);
    player.setLocation(anotherLocation);
    assertTrue(player.getCurrentLocation().getDescription()
            .contains("You are in a cave\nCollectibles at the location : "));
    assertTrue(player.getCurrentLocation().getDescription().contains("SAPPHIRE : 3"));
    assertTrue(player.getCurrentLocation().getDescription().contains("RUBY : 2"));
    assertTrue(player.getCurrentLocation().getDescription().contains("DIAMOND : 3"));
  }
}