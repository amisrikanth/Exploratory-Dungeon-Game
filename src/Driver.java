import java.io.InputStreamReader;

import dungeon.controller.DungeonController;
import dungeon.controller.IDungeonController;
import dungeon.model.Dungeon;
import dungeon.controller.DungeonGuiController;
import dungeon.view.DungeonView;
import dungeon.model.IDungeon;
import dungeon.controller.IDungeonGUIController;
import dungeon.view.IView;
import randomgenerator.IRandomNumberGenerator;
import randomgenerator.RandomNumberGenerator;

/**
 * This is a driver class which also is the entry point to the program. this class simulates
 * the model.Dungeon by making use of the model classes.
 */
public class Driver {
  /**
   * Entry point to the program.
   */
  public static void main(String[] args) {
    if (args != null && args.length > 0) {
      try {
        IDungeonController controller = new DungeonController(
                new InputStreamReader(System.in), System.out);
        controller.play(new Dungeon(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                new RandomNumberGenerator(), Integer.parseInt(args[3]),
                Boolean.parseBoolean(args[4]),
                Integer.parseInt(args[5]), Integer.parseInt(args[6])));
      } catch (IllegalArgumentException ex) {
        System.out.println("Invalid input passed for Integers");
      } catch (ArrayIndexOutOfBoundsException ex) {
        System.out.println("Please check your input parameters");
      }
    } else {
      IRandomNumberGenerator generator = new RandomNumberGenerator();
      IDungeon dungeon = new Dungeon("Player", 8, 8, generator, 10, true, 50, 4);
      IView view = new DungeonView(dungeon);
      IDungeonGUIController controller = new DungeonGuiController(view, dungeon);
      controller.play();
    }
  }
}