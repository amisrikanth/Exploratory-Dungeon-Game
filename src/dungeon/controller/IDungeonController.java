package dungeon.controller;

import dungeon.model.IDungeon;

/**
 * This is the interface for the controller for the Dungeon. the controller handles the input and
 * tells the model to mutate its state based on the input and conveys the state of model to output.
 */
public interface IDungeonController {

  /**
   * This method lets the user play the game by passing the model.
   * @param dungeon The Dungeon Model.
   */
  void play(IDungeon dungeon);
}
