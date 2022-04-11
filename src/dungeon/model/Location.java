package dungeon.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * This class implements the ILocation Interface and  represents the locations within the dungeon.
 * The location can be a cave or a tunnel, a location can further be classified as tunnel
 * (which has exactly 2 entrances) or a cave(which has 1, 3 or 4 entrances). In the image above,
 * we are representing caves as circular spaces. The start and end point of a dungeon can only be
 * caves and only the caves can have treasure.
 */
class Location implements ILocation {
  private final int locationNumber;
  private final Map<Collectibles, Integer> collectibles;
  private boolean isNorthConnected;
  private boolean isSouthConnected;
  private boolean isEastConnected;
  private boolean isWestConnected;
  private Otyugh monster;
  private boolean isVisited;

  /**
   * The constructor that initializes the attributes.
   *
   * @param number The location number.
   */
  Location(int number) {
    if (number < 0) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    this.locationNumber = number;
    this.collectibles = new HashMap<>();
    this.isVisited = false;
  }

  /**
   * Copy constructor.
   * @param location location.
   */
  Location(ILocation location) {
    this.locationNumber = location.getLocationNumber();
    this.isVisited = location.isVisited();
    this.collectibles = location.getCollectibles();
    if (location.getDescription().contains("Otyugh")) {
      this.monster = new Otyugh();
      this.monster.setHealth(location.getMonsterHealth());
    }
    this.isNorthConnected = location.getPossibleDirections().contains(Direction.NORTH);
    this.isSouthConnected = location.getPossibleDirections().contains(Direction.SOUTH);
    this.isEastConnected = location.getPossibleDirections().contains(Direction.EAST);
    this.isWestConnected = location.getPossibleDirections().contains(Direction.WEST);
  }

  @Override
  public String getDescription() {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("You are in a %1$s", getLocationType().toString().toLowerCase()));
    if (monster != null) {
      if (monster.getHealth() == 0) {
        sb.append("\nThere is a dead Otyugh in the cave");
      } else if (monster.getHealth() == 50) {
        sb.append("\nThere is an injured Otyugh in the cave");
      } else {
        sb.append("\nThere is a healthy Otyugh in the cave.");
      }
    }
    if (!this.collectibles.isEmpty()) {
      sb.append(("\nCollectibles at the location : "));
      collectibles.forEach((k, v) -> sb.append(String.format("\n %1$s : %2$s", k, v)));
    }
    return sb.toString();
  }

  @Override
  public Map<Collectibles, Integer> getCollectibles() {
    return new HashMap<>(this.collectibles);
  }

  @Override
  public LocationType getLocationType() {
    int result = (isEastConnected ? 1 : 0) + (isWestConnected ? 1 : 0)
            + (isNorthConnected ? 1 : 0) + (isSouthConnected ? 1 : 0);
    return result == 2 ? LocationType.TUNNEL : LocationType.CAVE;
  }

  @Override
  public int getLocationNumber() {
    return locationNumber;
  }

  @Override
  public void connectNorth() {
    isNorthConnected = true;
  }

  @Override
  public void connectSouth() {
    isSouthConnected = true;
  }

  @Override
  public void connectEast() {
    isEastConnected = true;
  }

  @Override
  public void connectWest() {
    isWestConnected = true;
  }

  @Override
  public List<Direction> getPossibleDirections() {
    List<Direction> directions = new ArrayList<>();
    if (isEastConnected) {
      directions.add(Direction.EAST);
    }
    if (isWestConnected) {
      directions.add(Direction.WEST);
    }
    if (isSouthConnected) {
      directions.add(Direction.SOUTH);
    }
    if (isNorthConnected) {
      directions.add(Direction.NORTH);
    }
    return directions;
  }

  @Override
  public void assignItemsToLocation(Collectibles treasure, int qty) {
    if (treasure == null || qty < 0) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    int value;
    if (this.collectibles.containsKey(treasure)) {
      value = this.collectibles.get(treasure);
      value = value + qty;
    } else {
      value = qty;
    }
    this.collectibles.put(treasure, value);
  }

  @Override
  public boolean pickItem(Collectibles collectible) {
    if (collectible == null) {
      throw new IllegalArgumentException("Invalid input passed");
    }
    if (collectibles.containsKey(collectible)) {
      if (collectibles.get(collectible) == 1) {
        collectibles.remove(collectible);
      } else {
        collectibles.put(collectible, collectibles.get(collectible) - 1);
      }
      return true;
    }
    return false;
  }

  @Override
  public void placeOtyugh() {
    this.monster = new Otyugh();
  }

  @Override
  public int getMonsterHealth() {
    return this.monster != null ? this.monster.getHealth() : 0;
  }

  @Override
  public void hitMonster() {
    monster.setHealth(getMonsterHealth() - 50);
  }

  @Override
  public void visitLocation() {
    isVisited = true;
  }

  @Override
  public boolean isVisited() {
    return isVisited;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof ILocation)) {
      return false;
    }
    ILocation other = (ILocation) obj;
    return (this.locationNumber == other.getLocationNumber());
  }

  @Override
  public int hashCode() {
    return Objects.hash(locationNumber,collectibles);
  }

}
