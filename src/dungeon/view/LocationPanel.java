package dungeon.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dungeon.model.IReadOnlyDungeon;
import dungeon.model.Direction;
import dungeon.model.Smell;

//This is a package private class which will be used internally by the view.
class LocationPanel extends JPanel {
  private final IReadOnlyDungeon model;
  private final int panelNumber;
  private final String nameOfAvatar;

  public LocationPanel(IReadOnlyDungeon model, int panelNumber, String nameOfAvatar) {
    this.model = model;
    this.panelNumber = panelNumber;
    this.nameOfAvatar = nameOfAvatar;
    this.setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    StringBuilder fileName = new StringBuilder();
    String locationDescription = model.getLocationDescription(panelNumber);
    BufferedImage locationImage = null;
    List<Direction> possibleDirections = model.getPossibleDirectionsFromLocation(panelNumber);
    String smellImgPath = "";
    String otyughImagePath = "/images/otyugh.png";
    String playerImagePath = "/images/" + nameOfAvatar + "GameImage.png";
    String blackArrowImagePath = "/images/arrow-black.png";
    String whiteArrowImagePath = "/images/arrow-white.png";
    String diamondImagePath = "/images/diamond.png";
    String rubyImagePath = "/images/ruby.png";
    String sapphireImagePath = "/images/emerald.png";

    if (possibleDirections.contains(Direction.NORTH)) {
      fileName.append("N");
    }
    if (possibleDirections.contains(Direction.EAST)) {
      fileName.append("E");
    }
    if (possibleDirections.contains(Direction.SOUTH)) {
      fileName.append("S");
    }
    if (possibleDirections.contains(Direction.WEST)) {
      fileName.append("W");
    }
    Smell locationSmell = model.getSmellAtLocation(panelNumber);
    if (locationSmell == Smell.MORE_PUNGENT) {
      smellImgPath = "/images/stench02.png";
    } else if (locationSmell == Smell.LESS_PUNGENT) {
      smellImgPath = "/images/stench01.png";
    }
    String path;
    path = "/images/" + fileName + ".png";
    try {
      locationImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (model.isLocationVisited(panelNumber)) {
      if (!smellImgPath.equals("")) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, smellImgPath, 8, 1);
        }
      }
      if (locationDescription.contains("healthy Otyugh")
              || locationDescription.contains("injured Otyugh")) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, otyughImagePath, 15, 21);
        }
      }
      if (panelNumber == model.getCurrentLocationOfPlayer()) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, playerImagePath, 34, 10);
        }
      }
      if (locationDescription.contains("ARROW")) {
        if (locationDescription.contains("cave")) {
          if (locationImage != null) {
            locationImage = overlay(locationImage, blackArrowImagePath, 19, 50);
          }
        } else if (locationDescription.contains("tunnel")) {
          if (locationImage != null) {
            locationImage = overlay(locationImage, whiteArrowImagePath, 19, 41);
          }
        }
      }
      if (locationDescription.contains("DIAMOND")) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, diamondImagePath, 14, 22);
        }
      }
      if (locationDescription.contains("RUBY")) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, rubyImagePath, 14, 28);
        }
      }
      if (locationDescription.contains("SAPPHIRE")) {
        if (locationImage != null) {
          locationImage = overlay(locationImage, sapphireImagePath, 14, 34);
        }
      }

      Image scaledImage = null;
      if (locationImage != null) {
        scaledImage = locationImage.getScaledInstance(this.getWidth(),
                this.getHeight(), Image.SCALE_SMOOTH);
      }
      g.drawImage(scaledImage, 0, 0, this);
    }

  }

  private BufferedImage overlay(BufferedImage starting, String filePath, int offsetX, int offsetY) {
    try {
      BufferedImage overlay = ImageIO.read(Objects
              .requireNonNull(getClass().getResourceAsStream(filePath)));
      int w = Math.max(starting.getWidth(), overlay.getWidth());
      int h = Math.max(starting.getHeight(), overlay.getHeight());
      BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
      Graphics g = combined.getGraphics();
      g.drawImage(starting, 0, 0, null);
      g.drawImage(overlay, offsetX, offsetY, null);
      return combined;
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public int getPanelNumber() {
    return panelNumber;
  }

}
