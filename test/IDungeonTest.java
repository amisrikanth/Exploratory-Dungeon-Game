import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import dungeon.model.Collectibles;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
import dungeon.model.IDungeon;
import dungeon.model.Smell;
import randomgenerator.CustomRandomGenerator;
import randomgenerator.IRandomNumberGenerator;
import randomgenerator.PseudoRandomMinGenerator;
import randomgenerator.RandomNumberGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the model.IDungeon Interface and all the class that implement it.
 */
public class IDungeonTest {

  IRandomNumberGenerator generatorMin;
  IRandomNumberGenerator generatorMax;
  IRandomNumberGenerator generator;
  IDungeon dungeon;
  IRandomNumberGenerator customGenerator;

  @Before
  public void setUp() {
    generator = new RandomNumberGenerator();
    generatorMin = new PseudoRandomMinGenerator("min");
    generatorMax = new PseudoRandomMinGenerator("max");
    customGenerator = new CustomRandomGenerator();
    dungeon = new Dungeon("Srikanth", 6, 6, generatorMin, 7, false, 25, 3);
  }

  @Test
  public void getInterconnectivity() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generator, 0, false, 50, 4);
    assertEquals(0, dungeon1.getInterconnectivity());
    assertEquals(7, dungeon.getInterconnectivity());
  }

  @Test
  public void isWrapped() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generator, 0, true, 50, 3);
    assertTrue(dungeon1.isWrapped());
    assertFalse(dungeon.isWrapped());
  }

  @Test
  public void getPlayerDescription() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    String expected = "Name : Srikanth\nItems collected :\n ARROW : 3\n"
            + "Current Location number : 1\nCurrent Location type : CAVE";
    assertEquals(expected, dungeon1.getPlayerDescription());
  }

  @Test
  public void getLocationDescription() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("You are in a cave\nCollectibles at the location : "));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("ARROW : 1"));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("DIAMOND : 1"));
  }

  @Test
  public void getStartLocation() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertEquals(dungeon1.getStartLocation(), dungeon1.getCurrentLocationOfPlayer());
  }

  @Test
  public void getEndLocation() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertEquals(32, dungeon1.getEndLocation());
  }

  @Test
  public void generateDungeon() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 0, true, 0, 4);
    String expectedDungeon = " - --- P --- C --- C --- C --- -    \n |     |     |     |     |     "
            + "|    \n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n C     E     C     C     C     C    \n                                    \n";
    assertEquals(expectedDungeon, dungeon1.toString());
    IDungeon dungeon2 = new Dungeon("Srikanth", 6, 6, generatorMin, 5, true, 0, 3);
    expectedDungeon = " P --- C --- C --- C --- C --- C ---\n |     |     |     |     |     "
            + "|    \n C --- C --- C --- C --- C     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n C     E     C     C     C     C    \n                                    \n";
    assertEquals(expectedDungeon, dungeon2.toString());
    IDungeon dungeon3 = new Dungeon("Srikanth", 6, 6, generatorMin, 0, true, 50, 3);
    expectedDungeon = " - --- P*--- C*--- C*--- C*--- -    \n |     |     |     |     |     "
            + "|    \n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n C*    E     C     C     C     C    \n                                    \n";
    assertEquals(expectedDungeon, dungeon3.toString());
    IDungeon dungeon4 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    expectedDungeon = " P*--- C*--- C*--- C*--- C*--- C*---\n |     |     |     |     |     "
            + "|    \n C*--- C*--- C*--- C --- C --- C    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n -     -     -     -     -     -    \n |     |     |     |     |     |    "
            + "\n C     E     C     C     C     C    \n                                    \n";
    assertEquals(expectedDungeon, dungeon4.toString());
  }

  @Test
  public void getPercentOfCavesWithTreasure() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertEquals(50, dungeon1.getPercentOfCavesWithTreasure());
    assertEquals(9, dungeon1.toString().chars()
            .mapToObj(e -> String.valueOf((char) e)).filter(e -> e.equals("*")).count());
  }

  @Test
  public void movePlayer() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertEquals(1, dungeon1.getCurrentLocationOfPlayer());
    dungeon1.movePlayer(Direction.EAST);
    assertEquals(2, dungeon1.getCurrentLocationOfPlayer());
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(8, dungeon1.getCurrentLocationOfPlayer());
    dungeon1.movePlayer(Direction.WEST);
    assertEquals(7, dungeon1.getCurrentLocationOfPlayer());
    dungeon1.movePlayer(Direction.NORTH);
    assertEquals(1, dungeon1.getCurrentLocationOfPlayer());
  }

  @Test
  public void collectTreasure() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("You are in a cave\nCollectibles at the location : "));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("ARROW : 1"));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("DIAMOND : 1"));
    dungeon1.collectItemAtCurrentPlayerLocation(Collectibles.DIAMOND);
    String expected = "You are in a cave\nCollectibles at the location : \n ARROW : 1";
    assertEquals(expected, dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer()));
  }

  @Test
  public void testConnectivity() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 6, generatorMin, 6, true, 50, 3);
    int numberOfLocations = 6 * 6;
    boolean[] visited = new boolean[numberOfLocations];
    for (int i = 0; i < numberOfLocations; i++) {
      visited[i] = false;
    }
    visited[dungeon1.getCurrentLocationOfPlayer() - 1] = true;
    List<Integer> listOfNodes = new ArrayList<>();
    listOfNodes.add(dungeon1.getCurrentLocationOfPlayer());
    recurse(dungeon1, visited, listOfNodes);
    assertEquals(numberOfLocations, listOfNodes.size());
  }

  private void recurse(IDungeon dungeon, boolean[] visited, List<Integer> listOfNodes) {
    List<Direction> directions = dungeon.getPossibleDirectionsFromLocation(
            dungeon.getCurrentLocationOfPlayer());
    for (Direction direction : directions) {
      dungeon.movePlayer(direction);
      if (!visited[dungeon.getCurrentLocationOfPlayer() - 1]) {
        visited[dungeon.getCurrentLocationOfPlayer() - 1] = true;
        listOfNodes.add(dungeon.getCurrentLocationOfPlayer());
        recurse(dungeon, visited, listOfNodes);
      }
      if (direction == Direction.EAST) {
        dungeon.movePlayer(Direction.WEST);
      } else if (direction == Direction.WEST) {
        dungeon.movePlayer(Direction.EAST);
      } else if (direction == Direction.NORTH) {
        dungeon.movePlayer(Direction.SOUTH);
      } else if (direction == Direction.SOUTH) {
        dungeon.movePlayer(Direction.NORTH);
      }
    }
  }

  @Test
  public void placementOfOtyugh() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 1);
    assertFalse(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("Otyugh"));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getEndLocation())
            .contains("There is a healthy Otyugh in the cave."));
    dungeon1.shootArrow(Direction.SOUTH, 2);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getEndLocation())
            .contains("There is an injured Otyugh in the cave"));
    dungeon1.shootArrow(Direction.SOUTH, 2);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getEndLocation())
            .contains("There is a dead Otyugh in the cave"));
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.SOUTH);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is a dead Otyugh in the cave"));
    assertEquals(dungeon1.getCurrentLocationOfPlayer(), dungeon1.getEndLocation());
  }


  @Test
  public void playerKilledByHealthyMonster() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    dungeon1.movePlayer(Direction.EAST);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is a healthy Otyugh in the cave."));
    assertFalse(dungeon1.isPlayerAlive());
    assertTrue(dungeon1.isGameOver());
  }

  @Test
  public void playerKilledByInjuredMonster() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMax, 4, true, 40, 2);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.WEST);
    dungeon1.movePlayer(Direction.WEST);
    dungeon1.movePlayer(Direction.WEST);
    dungeon1.shootArrow(Direction.SOUTH, 1);
    dungeon1.movePlayer(Direction.SOUTH);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is an injured Otyugh in the cave"));
    assertTrue(dungeon1.isGameOver());
    assertFalse(dungeon1.isPlayerAlive());
  }

  @Test
  public void playerNotKilledByInjuredMonster() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    dungeon1.shootArrow(Direction.EAST, 1);
    dungeon1.movePlayer(Direction.EAST);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is an injured Otyugh in the cave"));
    assertFalse(dungeon1.isGameOver());
    assertTrue(dungeon1.isPlayerAlive());
  }

  @Test
  public void pickArrow() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    assertTrue(dungeon1.getPlayerDescription().contains("ARROW : 3"));
    assertTrue(dungeon1.getLocationDescription(dungeon1
            .getCurrentLocationOfPlayer()).contains("ARROW"));
    dungeon1.collectItemAtCurrentPlayerLocation(Collectibles.ARROW);
    assertTrue(dungeon1.getPlayerDescription().contains("ARROW : 4"));
    assertFalse(dungeon1.getLocationDescription(dungeon1
            .getCurrentLocationOfPlayer()).contains("ARROW"));
  }

  @Test
  public void shootArrow() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    assertTrue(dungeon1.getPlayerDescription().contains("ARROW : 3"));
    dungeon1.shootArrow(Direction.EAST, 2);
    assertTrue(dungeon1.getPlayerDescription().contains("ARROW : 2"));
    dungeon1.shootArrow(Direction.EAST, 1);
    assertTrue(dungeon1.getPlayerDescription().contains("ARROW : 1"));
    dungeon1.shootArrow(Direction.EAST, 2);
    assertFalse(dungeon1.getPlayerDescription().contains("ARROW"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidShoot() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    dungeon1.shootArrow(Direction.EAST, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidShootDirection() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 40, 2);
    dungeon1.shootArrow(null, 2);
  }

  @Test
  public void arrowsAtCavesAndTunnels() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 100, 2);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("ARROW"));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("DIAMOND"));
    dungeon1.movePlayer(Direction.SOUTH);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("ARROW"));
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("DIAMOND"));
    dungeon1.movePlayer(Direction.SOUTH);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("ARROW"));
    assertFalse(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("DIAMOND"));
  }

  @Test
  public void shootCrookedThroughTunnels() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, customGenerator, 2, true, 40, 18);
    dungeon1.shootArrow(Direction.NORTH, 1);
    dungeon1.shootArrow(Direction.NORTH, 1);
    dungeon1.movePlayer(Direction.NORTH);
    dungeon1.movePlayer(Direction.WEST);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is a dead Otyugh in the cave"));
  }

  @Test
  public void shootCrookedThroughCaves() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, customGenerator, 2, true, 40, 18);
    dungeon1.shootArrow(Direction.SOUTH, 1);
    dungeon1.shootArrow(Direction.SOUTH, 1);
    dungeon1.shootArrow(Direction.SOUTH, 2);
    dungeon1.movePlayer(Direction.SOUTH);
    dungeon1.movePlayer(Direction.SOUTH);
    assertTrue(dungeon1.getLocationDescription(dungeon1.getCurrentLocationOfPlayer())
            .contains("There is an injured Otyugh in the cave"));
  }


  @Test
  public void smellTest() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generatorMin, 4, true, 100, 2);
    assertEquals(dungeon1.getSmellAtLocation(dungeon1.getCurrentLocationOfPlayer()),
            Smell.MORE_PUNGENT);
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(dungeon1.getSmellAtLocation(dungeon1.getCurrentLocationOfPlayer()),
            Smell.LESS_PUNGENT);
    dungeon1.movePlayer(Direction.SOUTH);
    assertEquals(dungeon1.getSmellAtLocation(dungeon1.getCurrentLocationOfPlayer()),
            Smell.NO_SMELL);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidRowsAndColumns() {
    new Dungeon("Srikanth", 6, -3, generatorMin, 3, true, 50, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidInterconnectivity() {
    new Dungeon("Srikanth", 6, 6, generatorMin, 78, true, 50, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidTreasureCavePercentage() {
    new Dungeon("Srikanth", 6, 8, generatorMin, 3, true, -50, 3);
  }

  @Test
  public void testReset() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generator, 4, true, 100, 2);
    dungeon1.movePlayer(dungeon1.getPossibleDirectionsFromLocation(dungeon1
            .getCurrentLocationOfPlayer()).get(0));
    int end = dungeon1.getEndLocation();
    dungeon1.reset();
    assertEquals(dungeon1.getStartLocation(), dungeon1.getCurrentLocationOfPlayer());
    assertEquals(end, dungeon1.getEndLocation());
  }

  @Test
  public void testRegenerateDungeon() {
    IDungeon dungeon1 = new Dungeon("Srikanth", 6, 7, generator, 4, true, 100, 2);
    int start = dungeon1.getStartLocation();
    int end = dungeon1.getEndLocation();
    boolean flag = true;
    for (int i = 0; i < 10; i++) {
      dungeon1.regenerateDungeon();
      if (start != dungeon1.getStartLocation() || end != dungeon1.getEndLocation()) {
        flag = false;
      }
    }
    assertFalse(flag);

  }

}