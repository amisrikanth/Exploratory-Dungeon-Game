package dungeon.controller;

import java.io.IOException;

import dungeon.model.Collectibles;
import dungeon.model.IDungeon;

class Pick implements IDungeonCommand {
  Collectibles item;

  public Pick(Collectibles item) {
    if (item == null) {
      throw new IllegalArgumentException("Invalid Collectible");
    }
    this.item = item;
  }

  @Override
  public String execute(IDungeon m) throws IOException {
    if (m == null) {
      throw new IllegalArgumentException("Invalid model");
    }
    if (m.collectItemAtCurrentPlayerLocation(item)) {
      return String.format("\nYou pick up 1 %s",item.toString().toLowerCase());
    }
    else {
      return "\nThe collectible is not present at the location";
    }
  }
}
