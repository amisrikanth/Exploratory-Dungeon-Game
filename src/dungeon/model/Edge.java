package dungeon.model;

//Intentionally made package private
class Edge {
  int fromNode;
  int toNode;

  public Edge(int fromNode, int toNode) {
    if (fromNode < 0 || toNode < 0) {
      throw new IllegalArgumentException("Invalid arguments passed");
    }
    this.fromNode = fromNode;
    this.toNode = toNode;
  }

}
