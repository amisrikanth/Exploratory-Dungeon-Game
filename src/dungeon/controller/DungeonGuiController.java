package dungeon.controller;

import dungeon.model.IDungeon;
import dungeon.view.IView;
import dungeon.model.Collectibles;
import dungeon.model.Direction;
import dungeon.model.Dungeon;
import randomgenerator.RandomNumberGenerator;

import java.io.IOException;

/**
 * This class acts as the Graphical User interface controller for the Dungeon and handles the input
 * from the view and tells the model to mutate its state based on the actions and events on the
 * view and conveys the state of model to view through a read only model.
 */
public class DungeonGuiController implements IDungeonGUIController {
  private final IView view;
  IDungeonCommand cmd;
  private IDungeon model;
  private boolean shoot;

  /**
   * Initializes the parameters along with the input parameters.
   * @param view the view.
   * @param model the model.
   */
  public DungeonGuiController(IView view, IDungeon model) {
    if (view == null || model == null) {
      throw new IllegalArgumentException("Inputs cannot be null");
    }
    this.model = model;
    this.view = view;
    cmd = null;
  }

  @Override
  public void play() {
    view.addClickListener(this);
    view.bindKeys(this);
    view.makeVisible();
  }

  @Override
  public void handleCellClick(int location) {
    try {
      if (!model.isGameOver()) {
        int columns = model.numberOfRowsInDungeon();
        int rows = model.numberOfColumnsInDungeon();
        if (model.getPossibleDirectionsFromLocation(model.getCurrentLocationOfPlayer())
                .contains(Direction.SOUTH)) {
          if (model.getCurrentLocationOfPlayer() + columns == location
                  || (model.isWrapped() && model.getCurrentLocationOfPlayer()
                  - (rows - 1) * columns == location)) {
            cmd = new Move(Direction.SOUTH);
          }
        }
        if (model.getPossibleDirectionsFromLocation(model.getCurrentLocationOfPlayer())
                .contains(Direction.NORTH)) {
          if (model.getCurrentLocationOfPlayer() - columns == location
                  || (model.isWrapped() && model.getCurrentLocationOfPlayer()
                  + (rows - 1) * columns == location)) {
            cmd = new Move(Direction.NORTH);
          }
        }
        if (model.getPossibleDirectionsFromLocation(model.getCurrentLocationOfPlayer())
                .contains(Direction.EAST)) {
          if (model.getCurrentLocationOfPlayer() + 1 == location
                  || (model.isWrapped() && model.getCurrentLocationOfPlayer()
                  - (columns - 1) == location)) {
            cmd = new Move(Direction.EAST);
          }
        }
        if (model.getPossibleDirectionsFromLocation(model.getCurrentLocationOfPlayer())
                .contains(Direction.WEST)) {
          if (model.getCurrentLocationOfPlayer() - 1 == location
                  || (model.isWrapped() && model.getCurrentLocationOfPlayer()
                  + (columns - 1) == location)) {
            cmd = new Move(Direction.WEST);
          }
        }
        try {
          view.refresh();
          if (cmd != null) {
            String message = cmd.execute(model).replace("\n", "");
            if (!message.equals("")) {
              view.showPopUp(message, "msg");
            }
            cmd = null;
          }
          view.refresh();
        } catch (IOException e) {
          view.showPopUp(e.getMessage(), "msg");
        }
        view.refresh();
      }
    } catch (IllegalArgumentException | IllegalStateException ex) {
      if (!ex.getMessage().equals("null")) {
        view.showPopUp(ex.getMessage(), "msg");
      }
    }
  }

  @Override
  public void handleMenuItemClick(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    switch (item) {
      case "Player Description":
        view.showPopUp(model.getPlayerDescription(), "msg");
        break;
      case "Location Description":
        view.showPopUp(model.getLocationDescription(model.getCurrentLocationOfPlayer()), "msg");
        break;
      case "Same Dungeon":
        this.model.reset();
        view.refresh();
        break;
      case "New Dungeon":
        this.model.regenerateDungeon();
        view.refresh();
        break;
      case "Quit":
        view.close();
        break;
      default:
        break;
    }
  }

  @Override
  public void handleKeyPress(String item) {
    if (item == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    try {
      if (!model.isGameOver()) {
        switch (item) {
          case "west":
            if (shoot) {
              cmd = new Shoot(Integer.parseInt(view.showPopUp("Number of caves?",
                      "input")), Direction.WEST);
              shoot = false;
            } else {
              cmd = new Move(Direction.WEST);
            }
            break;
          case "north":
            if (shoot) {
              cmd = new Shoot(Integer.parseInt(view.showPopUp("Number of caves?",
                      "input")), Direction.NORTH);
              shoot = false;
            } else {
              cmd = new Move(Direction.NORTH);
            }
            break;
          case "east":
            if (shoot) {
              cmd = new Shoot(Integer.parseInt(view.showPopUp("Number of caves?",
                      "input")), Direction.EAST);
              shoot = false;
            } else {
              cmd = new Move(Direction.EAST);
            }
            break;
          case "south":
            if (shoot) {
              cmd = new Shoot(Integer.parseInt(view.showPopUp("Number of caves?",
                      "input")), Direction.SOUTH);
              shoot = false;
            } else {
              cmd = new Move(Direction.SOUTH);
            }
            break;
          case "shoot":
            shoot = !shoot;
            break;
          case "arrow" : case "ruby" : case "diamond" : case "sapphire":
            Collectibles collectibles = getCollectibles(item);
            cmd = new Pick(collectibles);
            break;
          default:
            break;

        }
        try {
          view.refresh();
          if (cmd != null) {
            String message = cmd.execute(model).replace("\n", "");
            if (!message.equals("")) {
              view.showPopUp(message, "msg");
            }
            cmd = null;
          }
          view.refresh();
        } catch (IOException e) {
          view.showPopUp(e.getMessage(), "msg");
        }
      }
    } catch (IllegalArgumentException | IllegalStateException ex) {
      if (!ex.getMessage().equals("null")) {
        view.showPopUp(ex.getMessage(), "msg");
      }
    }
  }

  @Override
  public void createModel(String name, int rows, int columns, int interconnectivity,
                          boolean isWrapped, int percentageOfCavesWithTreasures,
                          int numberOfOtyughs) {
    this.model = new Dungeon(name, rows, columns, new RandomNumberGenerator(), interconnectivity,
            isWrapped, percentageOfCavesWithTreasures, numberOfOtyughs);
  }

  @Override
  public void generateDungeon() {
    this.view.createDungeon(model);
    play();
  }

  private Collectibles getCollectibles(String item) {
    Collectibles collectibles = null;
    if ("arrow".equals(item)) {
      collectibles = Collectibles.ARROW;
    } else if ("ruby".equals(item)) {
      collectibles = Collectibles.RUBY;
    } else if ("diamond".equals(item)) {
      collectibles = Collectibles.DIAMOND;
    } else if ("sapphire".equals(item)) {
      collectibles = Collectibles.SAPPHIRE;
    }
    return collectibles;
  }

}
