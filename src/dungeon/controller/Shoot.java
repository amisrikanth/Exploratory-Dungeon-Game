package dungeon.controller;

import java.io.IOException;

import dungeon.model.IDungeon;
import dungeon.model.Direction;

class Shoot implements IDungeonCommand {
  Direction direction;
  int distance;

  public Shoot(int distance, Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Invalid direction");
    }
    if (distance <= 0) {
      throw new IllegalArgumentException("Invalid distance");
    }
    this.direction = direction;
    this.distance = distance;
  }

  @Override
  public String execute(IDungeon m) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("Invalid model");
    }
    if (m.shootArrow(direction,distance)) {
      return "\nYou hear a great howl in the distance";
    }
    else {
      return "\nYou shoot an arrow into the darkness";
    }
  }

}
