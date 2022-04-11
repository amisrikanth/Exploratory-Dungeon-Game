package dungeon.model;

import randomgenerator.IRandomNumberGenerator;

import java.util.List;

/**
 * This enum represents the types of collectibles that the locations in a dungeon can have.
 */
public enum Collectibles {
  DIAMOND, SAPPHIRE, RUBY, ARROW;

  private static final List<Collectibles> treasureList = List.of(values());
  private static final int size = treasureList.size();

  /**
   * This method randomly generates a treasure from the list of treasure within the enum.
   *
   * @param generator The random number generator
   * @return The weapon to equip a player.
   */
  public static Collectibles randomTreasure(IRandomNumberGenerator generator) {
    if (generator == null) {
      throw new IllegalArgumentException("Method parameters cannot be null.");
    }
    return treasureList.get(generator.getRandomNumber(0, size - 2));
  }

}
