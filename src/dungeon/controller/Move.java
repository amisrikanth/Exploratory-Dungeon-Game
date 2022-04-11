package dungeon.controller;

import java.io.IOException;

import dungeon.model.Direction;
import dungeon.model.IDungeon;

class Move implements IDungeonCommand {
  Direction direction;

  public Move(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction");
    }
    this.direction = direction;
  }

  @Override
  public String execute(IDungeon m) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("Invalid model");
    }
    m.movePlayer(this.direction);
    if (m.isGameOver()) {
      if (!m.isPlayerAlive()) {
        return "\nChomp, chomp, chomp, you are eaten by an Otyugh!\n"
                + "Better luck next time";
      }
      else {
        return "\nCongrats! You have reached the end of the Dungeon.";
      }
    }
    return "";
  }
}
