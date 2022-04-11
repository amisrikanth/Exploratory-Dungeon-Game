import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import dungeon.model.Dungeon;
import dungeon.controller.DungeonController;
import dungeon.controller.DungeonGuiController;
import dungeon.model.IDungeon;
import dungeon.controller.IDungeonController;
import dungeon.controller.IDungeonGUIController;
import dungeon.view.DungeonView;
import dungeon.view.IView;
import randomgenerator.PseudoRandomMinGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the controller for the dungeon.
 */
public class DungeonControllerTest {

  IDungeon wrappingDungeon;
  IDungeon nonWrappingDungeon;

  @Before
  public void setUp() {
    wrappingDungeon = new Dungeon("Srikanth",7, 7,
            new PseudoRandomMinGenerator("min"), 4, true, 40, 11);
    nonWrappingDungeon = new Dungeon("Srikanth",7, 7,
            new PseudoRandomMinGenerator("min"), 4, false, 40, 11);
  }

  @Test
  public void reachingEnd() {
    Appendable gameLog = new StringBuilder();
    StringReader input =
            new StringReader("s 1 s s 1 s p arrow m s s 1 s s 1 s m s m s m s m s m s");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertEquals(102, gameLog.toString().split("\r\n|\r|\n").length);
    assertTrue(gameLog.toString().contains("Congrats! You have reached the end of the Dungeon."));
    assertEquals(wrappingDungeon.getCurrentLocationOfPlayer(), wrappingDungeon.getEndLocation());
  }

  @Test
  public void killingOtyugh() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("s 1 s s 1 s p arrow m s q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertEquals(54, gameLog.toString().split("\r\n|\r|\n").length);
    assertTrue(wrappingDungeon.getLocationDescription(wrappingDungeon.getCurrentLocationOfPlayer())
            .contains("There is a dead Otyugh in the cave"));
    assertTrue(gameLog.toString().contains("You hear a great howl in the distance"));
  }

  @Test
  public void arrowShotMiss() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("s 3 s q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertTrue(gameLog.toString().contains("You shoot an arrow into the darkness"));
  }

  @Test
  public void playerPickingTreasure() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("p diamond q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertEquals(20, gameLog.toString().split("\r\n|\r|\n").length);
    assertTrue(gameLog.toString().contains("You pick up 1 diamond"));
    assertTrue(wrappingDungeon.getPlayerDescription().toLowerCase().contains("diamond"));
  }

  @Test
  public void playerPickingArrow() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("p arrow q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertEquals(20, gameLog.toString().split("\r\n|\r|\n").length);
    assertTrue(gameLog.toString().contains("You pick up 1 arrow"));
    assertTrue(wrappingDungeon.getPlayerDescription().toLowerCase().contains("arrow : 4"));
  }

  @Test
  public void playerGettingKilled() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("m e");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertEquals(14, gameLog.toString().split("\r\n|\r|\n").length);
    assertFalse(wrappingDungeon.isPlayerAlive());
  }

  @Test
  public void validateInputsToModel() {
    StringBuilder modelLog = new StringBuilder();
    IDungeon mockDungeon = new MockDungeon(modelLog, "ValidDungeonModel");
    StringReader input = new StringReader("s 1 s s 1 s p arrow m s q");
    Appendable gameLog = new StringBuilder();
    IDungeonController controller = new DungeonController(input, gameLog);
    controller.play(mockDungeon);
    String expectedModelLog = "\nInput Location Number : 1\n"
            + "Input Location Number : 1\nInput Location Number : 1\nInput direction and number"
            + " of caves : south,1\nInput Location Number : 1\nInput Location Number : 1\n"
            + "Input Location Number : 1\nInput direction and number of caves : south,1\n"
            + "Input Location Number : 1\nInput Location Number : 1\n"
            + "Input Location Number : 1\nInput collectible item : arrow\n"
            + "Input Location Number : 1\nInput Location Number : 1\nInput Location Number : 1\n"
            + "Input direction : south\nInput Location Number : 1\nInput Location Number : 1\n"
            + "Input Location Number : 1";
    String expectedGameLog = "\n\nYou smell something terrible nearby\nYou are in a cave\n"
            + "Collectibles at the location : \n RUBY : 2\n DIAMOND : 1\n ARROW : 3\n"
            + "Doors lead to the E, S, W\n\nMove, Pickup, Shoot, (M-P-S)?\nNo. of caves?\n"
            + "Where to?\nYou hear a great howl in the distance\n\nYou smell something terrible"
            + " nearby\nYou are in a cave\nCollectibles at the location : \n RUBY : 2\n "
            + "DIAMOND : 1\n ARROW : 3\nDoors lead to the E, S, W\n\nMove, Pickup, Shoot, (M-P-S)?"
            + "\nNo. of caves?\nWhere to?\nYou hear a great howl in the distance\n\nYou smell "
            + "something terrible nearby\nYou are in a cave\nCollectibles at the location : \n "
            + "RUBY : 2\n DIAMOND : 1\n ARROW : 3\nDoors lead to the E, S, W\n\nMove, Pickup, "
            + "Shoot, (M-P-S)?\nWhat?\nThe collectible is not present at the location\n\nYou smell "
            + "something terrible nearby\nYou are in a cave\nCollectibles at the location : \n "
            + "RUBY : 2\n DIAMOND : 1\n ARROW : 3\nDoors lead to the E, S, W\n\nMove, Pickup, "
            + "Shoot, (M-P-S)?\nWhere to?\n\nYou smell something terrible nearby\nYou are in a"
            + " cave\nCollectibles at the location : \n RUBY : 2\n DIAMOND : 1\n ARROW : 3\nDoors "
            + "lead to the E, S, W\n\nMove, Pickup, Shoot, (M-P-S)?";
    assertEquals(expectedModelLog, modelLog.toString());
    assertEquals(expectedGameLog, gameLog.toString());
    assertEquals(mockDungeon.toString(), "ValidDungeonModel");
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    Appendable gameLog = new FailingAppendable();
    StringReader input = new StringReader("s 1 s s 1 s p arrow m s s 1 s s 1 s m s m s m s m s");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidModel() {
    IDungeon dungeon = null;
    Appendable gameLog = new StringBuilder();
    StringReader input =
            new StringReader("s 1 s s 1 s p arrow m s s 1 s s 1 s m s m s m s m s m s");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(dungeon);
  }

  @Test
  public void invalidInputForCollectingCollectibles() {
    Appendable gameLog = new StringBuilder();
    StringReader input =
            new StringReader("p abcd q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertTrue(gameLog.toString().contains("Invalid Collectible"));
  }

  @Test
  public void invalidInputForPlayerMove() {
    Appendable gameLog = new StringBuilder();
    StringReader input =
            new StringReader("m wrong q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertTrue(gameLog.toString().contains("Invalid direction"));
  }

  @Test
  public void invalidInputForShootingArrow() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("s 2 wrong q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertTrue(gameLog.toString().contains("Invalid direction"));
    input = new StringReader("s wrong s q");
    gameLog = new StringBuilder();
    c = new DungeonController(input, gameLog);
    c.play(wrappingDungeon);
    assertTrue(gameLog.toString().contains("The input is not valid"));
  }

  @Test
  public void quitTheGame() {
    Appendable gameLog = new StringBuilder();
    StringReader input = new StringReader("q");
    IDungeonController c = new DungeonController(input, gameLog);
    c.play(nonWrappingDungeon);
    assertEquals("\n\nYou smell something terrible nearby\nYou are in a cave\n"
            + "Collectibles at the location : \n ARROW : 1\n DIAMOND : 1\n"
            + "Doors lead to the E, S, W\n\nMove, Pickup, Shoot, (M-P-S)?", gameLog.toString());
    assertEquals(10, gameLog.toString().split("\r\n|\r|\n").length);
  }

  @Test
  public void testGraphicalController() {
    StringBuilder log = new StringBuilder();
    StringBuilder modelLog = new StringBuilder();
    IView view = new MockView(log);
    IDungeon mockModel = new MockDungeon(modelLog,"170496");
    IDungeonGUIController controller = new DungeonGuiController(view,mockModel);
    controller.play();
    controller.handleKeyPress("arrow");
    controller.handleKeyPress("ruby");
    controller.handleKeyPress("sapphire");
    controller.handleKeyPress("diamond");
    controller.handleCellClick(4);
    controller.handleKeyPress("north");
    controller.handleKeyPress("east");
    controller.handleKeyPress("south");
    controller.handleKeyPress("west");
    controller.handleKeyPress("shoot");
    controller.handleKeyPress("north");
    controller.handleKeyPress("shoot");
    controller.handleKeyPress("east");
    controller.handleKeyPress("shoot");
    controller.handleKeyPress("south");
    controller.handleKeyPress("shoot");
    controller.handleKeyPress("west");
    controller.handleMenuItemClick("Player");
    controller.handleMenuItemClick("Quit");
    controller.handleMenuItemClick("Same Dungeon");
    controller.handleMenuItemClick("New Dungeon");
    controller.handleMenuItemClick("Home");
    controller.createModel("Sri",6,6,5,true,100,2);
    controller.generateDungeon();
    String expected = "Controller added for listening to clicks on view\n"
            + "Controller added for listening to keyboard key presses\n"
            + "Made the frame visible\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for asking input\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for asking input\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for asking input\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Showed pop up for asking input\n"
            + "The frame is refreshed\n"
            + "Showed pop up for showing info\n"
            + "The frame is refreshed\n"
            + "The game is quit\n"
            + "The frame is refreshed\n"
            + "The frame is refreshed\n"
            + "Created Dungeon screen\n"
            + "Controller added for listening to clicks on view\n"
            + "Controller added for listening to keyboard key presses\n"
            + "Made the frame visible\n";
    assertEquals(57,log.toString().split("\r\n|\r|\n").length);
    assertEquals(expected,log.toString());
    String expectedModelLog = "Input collectible item : arrow\n"
            + "Input collectible item : ruby\n"
            + "Input collectible item : sapphire\n"
            + "Input collectible item : diamond\n"
            + "Input direction : north\n"
            + "Input direction : east\n"
            + "Input direction : south\n"
            + "Input direction : west\n"
            + "Input direction and number of caves : north,1\n"
            + "Input direction and number of caves : east,1\n"
            + "Input direction and number of caves : south,1\n"
            + "Input direction and number of caves : west,1";
    assertEquals(expectedModelLog,modelLog);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModelInputGUIController() {

    new DungeonGuiController(null,wrappingDungeon);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidViewInput() {
    StringBuilder log = new StringBuilder();
    IView view = new MockView(log);
    new DungeonGuiController(view,null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModelToview() {
    IView view = new DungeonView(null);
  }

}