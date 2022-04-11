package dungeon.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The player in a dungeon can explore all the locations of the tunnel and has to start the dungeon
 * exploration from a cave and end at a cave. The player can collect treasure from the caves
 * while passing through them.
 */
class Player implements IPlayer {
  private final String name;
  private final Map<Collectibles, Integer> collectedItems;
  private ILocation currentLocation;

  /**
   * The constructor initializes the attributes of the class.
   *
   * @param name     The name of the player.
   * @param location The start location of the player.
   */
  public Player(String name, ILocation location) {
    if (location == null || name == null) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    this.name = name;
    currentLocation = location;
    currentLocation.visitLocation();
    this.collectedItems = new HashMap<>();
    this.collectedItems.put(Collectibles.ARROW, 3);
  }

  Player(IPlayer player) {
    this.name = player.getName();
    this.collectedItems = player.getCollectedItems();
    this.currentLocation = player.getCurrentLocation();
  }

  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("Name : %1$s", name));
    if (!collectedItems.isEmpty()) {
      sb.append(String.format("\nItems collected :"));
      collectedItems.forEach((k, v) -> sb.append(String.format("\n %1$s : %2$s", k, v)));
    }
    sb.append(String.format("\nCurrent Location number : %1$s",
            currentLocation.getLocationNumber()));
    sb.append(String.format("\nCurrent Location type : %1$s",
            currentLocation.getLocationType()));
    return sb.toString();
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Map<Collectibles, Integer> getCollectedItems() {
    return new HashMap<>(collectedItems);
  }

  @Override
  public ILocation getCurrentLocation() {
    return new Location(currentLocation);
  }

  @Override
  public void pickCollectibles(Collectibles collectible) {
    if (collectible == null) {
      throw new IllegalArgumentException("Invalid argument passed");
    }
    if (currentLocation.pickItem(collectible)) {
      this.collectedItems.put(collectible,
              collectedItems.containsKey(collectible) ? collectedItems.get(collectible) + 1 : 1);
    }
  }

  @Override
  public void setLocation(ILocation location) {
    if (location == null) {
      throw new IllegalArgumentException("Invalid location passed");
    }
    currentLocation = location;
    currentLocation.visitLocation();
  }

  @Override
  public void shootArrow() {
    if (collectedItems.containsKey(Collectibles.ARROW)) {
      if (collectedItems.get(Collectibles.ARROW) > 1) {
        collectedItems.put(Collectibles.ARROW, collectedItems.get(Collectibles.ARROW) - 1);
      } else {
        collectedItems.remove(Collectibles.ARROW);
      }
    }
  }

}
