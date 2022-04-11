package dungeon.controller;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import dungeon.model.Direction;
import dungeon.model.IDungeon;
import dungeon.model.Collectibles;
import dungeon.model.Smell;

/**
 * This class acts as the controller for the Dungeon and handles the input and appends the output to
 * the Appendable.
 */
public class DungeonController implements IDungeonController {
  private final Readable in;
  private final Appendable out;

  /**
   * The constructor initializes the parameters.
   * @param in The readable.
   * @param out The appendable.
   */
  public DungeonController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Invalid  parameters passed to the constructor");
    }
    this.in = in;
    this.out = out;
  }

  @Override
  public void play(IDungeon dungeon) {
    if (dungeon == null) {
      throw new IllegalArgumentException("Invalid model");
    }
    try {
      Scanner scanInput = new Scanner(this.in);
      String entry;
      StringBuilder builder = new StringBuilder();
      StringBuilder anotherBuilder = new StringBuilder();
      IDungeonCommand cmd = null;
      while (!dungeon.isGameOver()) {
        try {
          if (dungeon.getSmellAtLocation(dungeon.getCurrentLocationOfPlayer())
                  == Smell.LESS_PUNGENT) {
            builder.append("\n\nYou smell something nearby\n");
          } else if (dungeon.getSmellAtLocation(dungeon.getCurrentLocationOfPlayer())
                  == Smell.MORE_PUNGENT) {
            builder.append("\n\nYou smell something terrible nearby\n");
          }
          builder.append(dungeon.getLocationDescription(dungeon.getCurrentLocationOfPlayer()));
          builder.append(dungeon.getLocationDescription(dungeon.getCurrentLocationOfPlayer())
                  .contains("cave") ? "\nDoors lead to the" : "\nthat continues to the");
          if (dungeon.getPossibleDirectionsFromLocation(dungeon.getCurrentLocationOfPlayer())
                  .contains(Direction.NORTH)) {
            builder.append(" N,");
          }
          if (dungeon.getPossibleDirectionsFromLocation(dungeon.getCurrentLocationOfPlayer())
                  .contains(Direction.EAST)) {
            builder.append(" E,");
          }
          if (dungeon.getPossibleDirectionsFromLocation(dungeon.getCurrentLocationOfPlayer())
                  .contains(Direction.SOUTH)) {
            builder.append(" S,");
          }
          if (dungeon.getPossibleDirectionsFromLocation(dungeon.getCurrentLocationOfPlayer())
                  .contains(Direction.WEST)) {
            builder.append(" W,");
          }
          out.append(builder.substring(0, builder.length() - 1));
          builder.setLength(0);
          anotherBuilder.append("(");
          builder.append("\n\nMove, ");

          anotherBuilder.append("M");
          if (dungeon.getLocationDescription(dungeon.getCurrentLocationOfPlayer())
                  .contains("Collectibles")) {
            builder.append("Pickup, ");
            anotherBuilder.append("-P");
          }
          if (dungeon.getPlayerDescription().contains("ARROW")) {
            builder.append("Shoot, ");
            anotherBuilder.append("-S");
          }
          anotherBuilder.append(")?");
          out.append(builder);
          out.append(anotherBuilder);
          builder.setLength(0);
          anotherBuilder.setLength(0);
          entry = scanInput.next();
          switch (entry.toLowerCase()) {
            case "q":
            case "quit":
              return;
            case "p":
              out.append("\nWhat?");
              Collectibles collectibles = null;
              String item = scanInput.next().toLowerCase();
              if ("arrow".equals(item)) {
                collectibles = Collectibles.ARROW;
              } else if ("ruby".equals(item)) {
                collectibles = Collectibles.RUBY;
              } else if ("diamond".equals(item)) {
                collectibles = Collectibles.DIAMOND;
              } else if ("sapphire".equals(item)) {
                collectibles = Collectibles.SAPPHIRE;
              }
              cmd = new Pick(collectibles);
              break;
            case "m":
              out.append("\nWhere to?");
              Direction direction = getDirection(scanInput);
              cmd = new Move(direction);
              break;
            case "s":
              out.append("\nNo. of caves?");
              int distance = scanInput.nextInt();
              out.append("\nWhere to?");
              Direction direction1 = getDirection(scanInput);
              cmd = new Shoot(distance, direction1);
              break;
            default:
              break;
          }
          if (cmd != null) {
            out.append(cmd.execute(dungeon));
            cmd = null;
          }
        } catch (IllegalArgumentException | IllegalStateException ex) {
          out.append("\n").append(ex.getMessage());
        } catch (InputMismatchException ex) {
          out.append("\nThe input is not valid");
        } catch (NoSuchElementException ex) {
          out.append("Incomplete input");
        }
      }
      out.append("\n-----------------Thank you for exploring the dungeon---------------------");
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  private Direction getDirection(Scanner scanInput) {
    Direction direction = null;
    String s = scanInput.next().toLowerCase();
    if (s.equals("w")) {
      direction = Direction.WEST;
    } else if (s.equals("n")) {
      direction = Direction.NORTH;
    } else if (s.equals("e")) {
      direction = Direction.EAST;
    } else if (s.equals("s")) {
      direction = Direction.SOUTH;
    }
    return direction;
  }

}
