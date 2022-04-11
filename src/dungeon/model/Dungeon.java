package dungeon.model;

import randomgenerator.IRandomNumberGenerator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * The model.Dungeon class implements the IDungeons interface. The model.Dungeon is a network
 * of tunnels and caves that are interconnected so that player can explore the entire world by
 * traveling from cave to cave through the tunnels that connect them. Each location in the grid
 * represents a location in the dungeon where a player can explore and can be connected to at
 * most four other locations: one to the north, one to the east, one to the south, and one to
 * the west. Each dungeon can be constructed with a degree of interconnectivity and the
 * Dungeons may or may not be wrapped.
 */
public class Dungeon implements IDungeon {
  private final int rows;
  private final int columns;
  private final IRandomNumberGenerator randomNumberGenerator;
  private final ILocation[][] locations;
  private final int interconnectivity;
  private final boolean isWrapped;
  private final int percentageOfCavesWithTreasures;
  private final List<Edge> edges;
  List<ILocation> caves;
  private ILocation[][] lastLocationsCopy;
  private int numberOfOtyughs;
  private List<Edge> excludedEdges;
  private Player player;
  private int start;
  private int end;
  private List<Set<Edge>> includedEdges;
  private boolean isGameOver;
  private boolean isPlayerAlive;
  private int pitLocation;

  /**
   * The constructor that initializes all the attributes of the class.
   *
   * @param rows                           The rows of the dungeon.
   * @param columns                        The columns of the dungeon.
   * @param randomNumberGenerator          The random Number Generator.
   * @param interconnectivity              The interconnectivity of the dungeon.
   * @param isWrapped                      The wrapping of the dungeon.
   * @param percentageOfCavesWithTreasures The percentage of caves with treasure.
   */
  public Dungeon(String name, int rows, int columns, IRandomNumberGenerator randomNumberGenerator,
                 int interconnectivity, boolean isWrapped, int percentageOfCavesWithTreasures,
                 int numberOfOtyughs) {
    if (rows < 6 || columns < 6) {
      throw new IllegalArgumentException("Rows and columns have to be more than or equal to 6");
    }
    if (randomNumberGenerator == null) {
      throw new IllegalArgumentException("Input arguments cannot be null");
    }
    if (isWrapped && interconnectivity > 2 * rows * columns - rows * columns + 1
            || !isWrapped && interconnectivity > 2 * rows * columns - (rows + columns)
            - rows * columns + 1) {
      throw new IllegalArgumentException("The passed interconnectivity is not valid");
    }
    if (percentageOfCavesWithTreasures < 0 || percentageOfCavesWithTreasures > 100) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    if (numberOfOtyughs <= 0) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    if (name == null) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    this.rows = rows;
    this.columns = columns;
    this.randomNumberGenerator = randomNumberGenerator;
    this.interconnectivity = interconnectivity;
    this.isWrapped = isWrapped;
    this.numberOfOtyughs = numberOfOtyughs;
    this.percentageOfCavesWithTreasures = percentageOfCavesWithTreasures;
    this.locations = new Location[rows][columns];
    this.includedEdges = new ArrayList<>();
    this.excludedEdges = new ArrayList<>();
    this.edges = new ArrayList<>();
    this.caves = new ArrayList<>();
    regenerateDungeon();
    this.player = new Player(name, locations[(start - 1) / columns][(start - 1) % columns]);
  }

  @Override
  public void regenerateDungeon() {
    generateLocations();
    generateEdges();
    generateDungeon();
    this.lastLocationsCopy = new Location[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        lastLocationsCopy[i][j] = new Location(locations[i][j]);
      }
    }
    resetHelper();
  }

  private void resetHelper() {
    this.isGameOver = false;
    this.isPlayerAlive = true;
    if (player != null) {
      this.player = new Player(player.getName(),
              locations[(start - 1) / columns][(start - 1) % columns]);
    }
  }

  @Override
  public void reset() {
    if (lastLocationsCopy != null) {
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < columns; j++) {
          locations[i][j] = new Location(lastLocationsCopy[i][j]);
        }
      }
    }
    resetHelper();
  }

  private void generateLocations() {
    int count = 1;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        locations[i][j] = new Location(count);
        count++;
      }
    }
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public boolean isWrapped() {
    return isWrapped;
  }

  @Override
  public String getPlayerDescription() {
    return player.getDescription();
  }

  @Override
  public String getLocationDescription(int locationNumber) {
    if (locationNumber < 1 || locationNumber > rows * columns) {
      throw new IllegalArgumentException("Invalid location number passed");
    }
    return locations[(locationNumber - 1) / columns][(locationNumber - 1) % columns]
            .getDescription();
  }

  @Override
  public int getStartLocation() {
    return start;
  }

  @Override
  public List<Direction> getPossibleDirectionsFromLocation(int locationNumber) {
    return locations[(locationNumber - 1) / columns]
            [(locationNumber - 1) % columns].getPossibleDirections();
  }

  @Override
  public int getEndLocation() {
    return end;
  }

  private void generateDungeon() {
    includedEdges = new ArrayList<>();
    excludedEdges = new ArrayList<>();
    caves = new ArrayList<>();
    determineConnections();
    satisfyInterconnectivity();
    generateStart();
    generateEnd();

    addTreasureToCaves();
    addArrowsToLocations();
    if (caves.size() <= numberOfOtyughs) {
      numberOfOtyughs = caves.size() - 1;
    }
    placeOtyughsInCaves();
  }

  private void generateEdges() {
    int count = 1;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (isWrapped) {
          if (j == columns - 1) {
            edges.add(createEdge(count, count - columns + 1));
          } else {
            edges.add(createEdge(count, count + 1));
          }
          if (i == rows - 1) {
            edges.add(createEdge(count, count - (rows - 1) * columns));
          } else {
            edges.add(createEdge(count, count + columns));
          }
        } else {
          if (j != columns - 1) {
            edges.add(createEdge(count, count + 1));
          }
          if (i != rows - 1) {
            edges.add(createEdge(count, count + columns));
          }
        }
        count++;
      }
    }
  }

  private void determineConnections() {
    int[] intArray;
    if (!isWrapped) {
      intArray = randomNumberGenerator.generateRandomNumbers(0,
              2 * rows * columns - (rows + columns + 1),
              2 * rows * columns - (rows + columns));
    } else {
      intArray = randomNumberGenerator.generateRandomNumbers(0,
              2 * rows * columns - 1, 2 * rows * columns);
    }
    for (int num : intArray) {
      segregateEdges(num);
    }
  }

  private void segregateEdges(int num) {
    boolean added;
    Set<Edge> x1 = null;
    Set<Edge> y1 = null;
    added = true;
    for (Set<Edge> s : includedEdges) {
      Edge x = s.stream().filter(edge
          -> (edge.fromNode == edges.get(num).fromNode) || (edge.toNode
              == edges.get(num).fromNode)).findAny().orElse(null);
      if (x != null) {
        x1 = s;
      }
      Edge y = s.stream().filter(edge
          -> (edge.fromNode == edges.get(num).toNode) || (edge.toNode
              == edges.get(num).toNode)).findAny().orElse(null);
      if (y != null) {
        y1 = s;
      }
    }
    if (x1 != null && y1 != null && x1 != y1) {
      x1.addAll(y1);
      includedEdges.remove(y1);
    } else if (x1 != null && y1 != null && x1 == y1) {
      excludedEdges.add(edges.get(num));
      added = false;
    } else if (y1 == null && x1 != null) {
      x1.add(edges.get(num));
    } else if (y1 != null && x1 == null) {
      y1.add(edges.get(num));
    } else {
      Set<Edge> asd = new HashSet<>();
      asd.add(edges.get(num));
      includedEdges.add(asd);
    }
    if (added) {
      connectLocations(edges.get(num));
    }
  }

  private void connectLocations(Edge edge) {
    if ((edge.fromNode - 1) / columns == (edge.toNode - 1) / columns) {
      locations[(edge.fromNode - 1) / columns][(edge.fromNode - 1) % columns].connectEast();
      locations[(edge.toNode - 1) / columns][(edge.toNode - 1) % columns].connectWest();
    } else {
      locations[(edge.fromNode - 1) / columns][(edge.fromNode - 1) % columns].connectSouth();
      locations[(edge.toNode - 1) / columns][(edge.toNode - 1) % columns].connectNorth();
    }
  }

  private void satisfyInterconnectivity() {
    int[] numbers = randomNumberGenerator.generateRandomNumbers(0,
            excludedEdges.size() - 1, interconnectivity);
    for (int num : numbers) {
      connectLocations(excludedEdges.get(num));
    }
  }

  private Edge createEdge(int from, int to) {
    return new Edge(from, to);
  }

  @Override
  public int getPercentOfCavesWithTreasure() {
    return percentageOfCavesWithTreasures;
  }

  private void generateStart() {
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (locations[i][j].getLocationType() == LocationType.CAVE) {
          caves.add(locations[i][j]);
        }
      }
    }
    start = caves.get(randomNumberGenerator.getRandomNumber(0, caves.size() - 1))
            .getLocationNumber();
  }


  private void generateEnd() {
    Map<Integer, Integer> distanceFromStart = bfsToGetSuitableEndNodes(
            locations[(start - 1) / columns][(start - 1) % columns]);
    List<Map.Entry<Integer, Integer>> eligibleEnd;
    eligibleEnd = distanceFromStart.entrySet().stream().filter(
        entry -> entry.getValue() >= 5).toList();
    if (eligibleEnd.size() == 0) {
      generateDungeon();
    } else {
      int number = randomNumberGenerator.getRandomNumber(0,
              eligibleEnd.size() - 1);
      end = eligibleEnd.get(number).getKey();
    }

  }

  private Map<Integer, Integer> bfsToGetSuitableEndNodes(ILocation start) {
    Map<Integer, Integer> distanceFromStart = new HashMap<>();
    Queue<Integer> locationQueue = new ArrayDeque<>();
    locationQueue.add(start.getLocationNumber());
    boolean[] visited = new boolean[rows * columns];
    int[] distance = new int[rows * columns];
    for (int i = 0; i < rows * columns; i++) {
      visited[i] = false;
      distance[i] = -1;
    }
    distance[start.getLocationNumber() - 1] = 0;
    visited[start.getLocationNumber() - 1] = true;
    while (!locationQueue.isEmpty()) {
      int currentLocation = locationQueue.remove();
      List<Integer> adjacentNodes = traverseNode(
              locations[(currentLocation - 1) / columns][(currentLocation - 1) % columns]);

      for (Integer adjacentNode : adjacentNodes) {
        if (!visited[adjacentNode - 1]) {
          visited[adjacentNode - 1] = true;
          distance[adjacentNode - 1] = distance[currentLocation - 1] + 1;
          if (caves.stream().anyMatch(e -> e.getLocationNumber() == adjacentNode)) {
            distanceFromStart.put(adjacentNode,
                    distance[adjacentNode - 1]);
          }
          locationQueue.add(adjacentNode);
        }
      }
    }
    return distanceFromStart;
  }

  private List<Integer> traverseNode(ILocation node) {
    List<Integer> connectedNodes = new ArrayList<>();
    if (node.getPossibleDirections().contains(Direction.EAST)) {
      connectedNodes.add(node.getLocationNumber() % columns == 0 ? node.getLocationNumber()
              - columns + 1 : node.getLocationNumber() + 1);
    }
    if (node.getPossibleDirections().contains(Direction.WEST)) {
      connectedNodes.add(node.getLocationNumber() % columns == 1 ? node.getLocationNumber()
              + columns - 1 : node.getLocationNumber() - 1);
    }
    if (node.getPossibleDirections().contains(Direction.SOUTH)) {
      connectedNodes.add((node.getLocationNumber() > (rows - 1) * columns ? node.getLocationNumber()
              - (rows - 1) * columns : node.getLocationNumber() + columns));
    }
    if (node.getPossibleDirections().contains(Direction.NORTH)) {
      connectedNodes.add(node.getLocationNumber() <= columns ? node.getLocationNumber() + (rows - 1)
              * columns : node.getLocationNumber() - columns);
    }
    return connectedNodes;
  }

  private void addTreasureToCaves() {
    int[] numArray = randomNumberGenerator.generateRandomNumbers(0, caves.size() - 1,
            Math.round(percentageOfCavesWithTreasures * caves.size()) / 100);
    for (int num : numArray) {
      int numberOfTreasuresPerCave = randomNumberGenerator.getRandomNumber(1, 3);
      for (int i = 0; i < numberOfTreasuresPerCave; i++) {
        caves.get(num).assignItemsToLocation(Collectibles.randomTreasure(randomNumberGenerator),
                randomNumberGenerator.getRandomNumber(1, 3));
      }
    }
  }

  private void addArrowsToLocations() {
    int[] numArray = randomNumberGenerator.generateRandomNumbers(0, rows * columns - 1,
            Math.round(percentageOfCavesWithTreasures * rows * columns) / 100);
    for (int num : numArray) {
      locations[(num) / columns][(num) % columns]
              .assignItemsToLocation(Collectibles
                      .ARROW, randomNumberGenerator.getRandomNumber(1, 3));
    }
  }

  private void placeOtyughsInCaves() {
    int count = 0;
    Set<Integer> numArray = new HashSet<>();
    numArray.add(caves.indexOf(locations[(end - 1) / columns][(end - 1) % columns]));
    numArray.add(caves.indexOf(locations[(start - 1) / columns][(start - 1) % columns]));
    int[] randomNumbers = randomNumberGenerator.generateRandomNumbers(
            0, caves.size() - 1, numberOfOtyughs - 1, numArray);
    locations[(end - 1) / columns][(end - 1) % columns].placeOtyugh();
    for (int num : randomNumbers) {
      caves.get(num).placeOtyugh();
    }
  }

  @Override
  public void movePlayer(Direction direction) {
    if (direction == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    if (!player.getCurrentLocation().getPossibleDirections().contains(direction)) {
      throw new IllegalArgumentException("There is no path from current cave in that direction");
    }
    movePlayerHelper(moveHelper(player.getCurrentLocation(), direction));

  }

  private ILocation moveHelper(ILocation location, Direction direction) {
    ILocation newLocation = null;
    switch (direction) {
      case EAST:
        newLocation = locations[(location.getLocationNumber() - 1) / columns]
                [(location.getLocationNumber()) % columns];
        break;
      case WEST:
        newLocation = locations[(location.getLocationNumber() - 1) / columns]
                [location.getLocationNumber() % columns != 1
                ? (location.getLocationNumber() - 2) % columns :
                (location.getLocationNumber() + columns - 2) % columns];
        break;
      case NORTH:
        newLocation = locations[location.getLocationNumber() <= columns
                ? (location.getLocationNumber()
                + (rows - 1) * columns - 1) / columns : (location
                .getLocationNumber() - columns - 1) / columns]
                [(location.getLocationNumber() - 1) % columns];
        break;
      case SOUTH:
        newLocation = locations[location.getLocationNumber()
                > (rows - 1) * columns ? (location.getLocationNumber()
                - (rows - 1) * columns - 1) / columns : (location
                .getLocationNumber() + columns - 1) / columns]
                [(location.getLocationNumber() - 1) % columns];
        break;
      default:
        break;
    }
    return newLocation;
  }

  private void movePlayerHelper(ILocation location) {
    player.setLocation(location);
    if (location.getMonsterHealth() == 100) {
      isGameOver = true;
      isPlayerAlive = false;
    } else if (location.getMonsterHealth() == 50) {
      isGameOver = randomNumberGenerator.getRandomNumber(0, 1) == 1;
      isPlayerAlive = !isGameOver;
    }
    if (!isGameOver && getCurrentLocationOfPlayer() == getEndLocation()) {
      isGameOver = true;
    }
  }

  @Override
  public Smell getSmellAtLocation(int locationNumber) {
    Map<Integer, Integer> distanceFromCurrentLocation =
            bfsToGetSuitableEndNodes(locations[(locationNumber - 1) / columns]
                    [(locationNumber - 1) % columns]);
    List<Map.Entry<Integer, Integer>> adjacentSingleDistanceCave;
    adjacentSingleDistanceCave = distanceFromCurrentLocation.entrySet().stream().filter(
        entry -> locations[(entry.getKey() - 1) / columns][(entry.getKey() - 1) % columns]
                    .getMonsterHealth() > 0 && entry.getValue() == 1).toList();
    List<Map.Entry<Integer, Integer>> adjacentDoubleDistanceCave;
    adjacentDoubleDistanceCave = distanceFromCurrentLocation.entrySet().stream().filter(
        entry -> locations[(entry.getKey() - 1) / columns][(entry.getKey() - 1) % columns]
                    .getMonsterHealth() > 0 && entry.getValue() == 2).toList();
    if (adjacentSingleDistanceCave.size() > 0 || adjacentDoubleDistanceCave.size() > 1) {
      return Smell.MORE_PUNGENT;
    }
    if (adjacentDoubleDistanceCave.size() == 1) {
      return Smell.LESS_PUNGENT;
    }
    return Smell.NO_SMELL;
  }

  @Override
  public boolean shootArrow(Direction direction, int distance) {
    if (!player.getCollectedItems().containsKey(Collectibles.ARROW)) {
      throw new IllegalStateException("You are out of arrows, explore to find more");
    }
    if (distance <= 0) {
      throw new IllegalArgumentException("Invalid distance input.");
    }
    if (direction == null) {
      throw new IllegalArgumentException("Invalid Direction.");
    }
    player.shootArrow();
    return shootArrowHelper(player.getCurrentLocation(), direction,
            player.getCurrentLocation().getLocationType() == LocationType.CAVE ? distance + 1
                    : distance);
  }

  @Override
  public boolean isLocationVisited(int locationNumber) {
    return locations[(locationNumber - 1) / columns]
            [(locationNumber - 1) % columns].isVisited();
  }

  private boolean shootArrowHelper(ILocation currentLocation, Direction direction, int distance) {
    if (direction == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    if (currentLocation.getLocationType() == LocationType.CAVE
            && distance == 1 && currentLocation.getMonsterHealth() > 0) {
      currentLocation.hitMonster();
      return true;
    }
    if (distance > 0) {
      switch (direction) {
        case EAST:
          if (currentLocation.getLocationType() == LocationType.CAVE
                  && currentLocation.getPossibleDirections().contains(Direction.EAST)) {
            return shootArrowHelper(moveHelper(currentLocation, Direction.EAST),
                    Direction.EAST, distance - 1);
          } else if (currentLocation.getLocationType() == LocationType.TUNNEL) {
            return arrowTunnelTraversal(currentLocation, distance,
                    currentLocation.getPossibleDirections().contains(Direction.WEST)
                            ? Direction.WEST : currentLocation.getPossibleDirections().stream()
                            .filter(x -> x != Direction.EAST).findFirst().orElse(null));
          }
          break;
        case WEST:
          if (currentLocation.getLocationType() == LocationType.CAVE
                  && currentLocation.getPossibleDirections().contains(Direction.WEST)) {
            return shootArrowHelper(moveHelper(currentLocation, Direction.WEST),
                    Direction.WEST, distance - 1);
          } else if (currentLocation.getLocationType() == LocationType.TUNNEL) {
            return arrowTunnelTraversal(currentLocation, distance,
                    currentLocation.getPossibleDirections().contains(Direction.EAST)
                            ? Direction.EAST : currentLocation.getPossibleDirections().stream()
                            .filter(x -> x != Direction.WEST).findFirst().orElse(null));
          }
          break;
        case NORTH:
          if (currentLocation.getLocationType() == LocationType.CAVE
                  && currentLocation.getPossibleDirections().contains(Direction.NORTH)) {
            return shootArrowHelper(moveHelper(currentLocation, Direction.NORTH),
                    Direction.NORTH, distance - 1);
          } else if (currentLocation.getLocationType() == LocationType.TUNNEL) {
            return arrowTunnelTraversal(currentLocation, distance,
                    currentLocation.getPossibleDirections().contains(Direction.SOUTH)
                            ? Direction.SOUTH : currentLocation.getPossibleDirections().stream()
                            .filter(x -> x != Direction.NORTH).findFirst().orElse(null));
          }
          break;
        case SOUTH:
          if (currentLocation.getLocationType() == LocationType.CAVE
                  && currentLocation.getPossibleDirections().contains(Direction.SOUTH)) {
            return shootArrowHelper(moveHelper(currentLocation, Direction.SOUTH),
                    Direction.SOUTH, distance - 1);
          } else if (currentLocation.getLocationType() == LocationType.TUNNEL) {
            return arrowTunnelTraversal(currentLocation, distance,
                    currentLocation.getPossibleDirections().contains(Direction.NORTH)
                            ? Direction.NORTH : currentLocation.getPossibleDirections().stream()
                            .filter(x -> x != Direction.SOUTH).findFirst().orElse(null));
          }
          break;
        default:
          break;
      }
    }
    return false;
  }

  private boolean arrowTunnelTraversal(ILocation currentLocation, int distance, Direction dir) {
    List<Direction> possibleDirections = currentLocation.getPossibleDirections();
    possibleDirections.remove(dir);
    return shootArrowHelper(moveHelper(currentLocation, possibleDirections.get(0)),
            possibleDirections.get(0), distance);
  }

  @Override
  public int getCurrentLocationOfPlayer() {
    return player.getCurrentLocation().getLocationNumber();
  }

  @Override
  public boolean collectItemAtCurrentPlayerLocation(Collectibles item) {
    if (item == null) {
      throw new IllegalArgumentException("Invalid collectible");
    }
    if (getLocationDescription(getCurrentLocationOfPlayer()).contains(item.toString())) {
      player.pickCollectibles(item);
      return true;
    }
    return false;
  }

  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  @Override
  public boolean isPlayerAlive() {
    return isPlayerAlive;
  }

  @Override
  public int numberOfRowsInDungeon() {
    return rows;
  }

  @Override
  public int numberOfColumnsInDungeon() {
    return columns;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    StringBuilder sb1 = new StringBuilder();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        if (player != null && ((player.getCurrentLocation().getLocationNumber() - 1) / columns)
                == i && ((player.getCurrentLocation().getLocationNumber() - 1) % columns) == j) {
          sb.append(" P");
        } else if (locations[i][j].getLocationType() == LocationType.CAVE) {
          if ((start - 1) / columns == i
                  && (start - 1) % columns == j) {
            sb.append(" S");
          } else if ((end - 1) / columns == i
                  && (end - 1) % columns == j) {
            sb.append(" E");
          } else {
            sb.append(" C");
          }
        } else {
          sb.append(" -");
        }
        if (locations[i][j].getCollectibles().containsKey(Collectibles.RUBY)
                || locations[i][j].getCollectibles().containsKey(Collectibles.DIAMOND)
                || locations[i][j].getCollectibles().containsKey(Collectibles.SAPPHIRE)) {
          sb.append("*");
        } else {
          sb.append(" ");
        }
        if (locations[i][j].getPossibleDirections().contains(Direction.EAST)) {
          sb.append("---");
        } else {
          sb.append("   ");
        }
        if (locations[i][j].getPossibleDirections().contains(Direction.SOUTH)) {
          sb1.append(" |    ");
        } else {
          sb1.append("      ");
        }

      }
      sb.append("\n");
      sb.append(sb1);
      sb.append("\n");
      sb1.setLength(0);
    }
    return sb.toString();
  }
}
