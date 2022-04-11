package dungeon.model;

class Otyugh implements IMonster {
  private int health;

  public Otyugh() {
    this.health = 100;
  }

  @Override
  public int getHealth() {
    return health;
  }

  @Override
  public void setHealth(int health) {
    this.health = health;
  }

}
