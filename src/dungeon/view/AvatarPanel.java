package dungeon.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

//This is a package private class which will be used internally by the view.
class AvatarPanel extends JPanel {
  private final String nameOfAvatar;

  public AvatarPanel(String name) {
    this.nameOfAvatar = name;
    this.setBackground(new Color(161, 209, 226));
    this.setBorder(new LineBorder(Color.black));
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Image scaledImage = null;
    String path = "/images/" + nameOfAvatar + ".png";
    try {
      scaledImage = ImageIO.read(getClass().getResourceAsStream(path)).getScaledInstance(
              this.getWidth() / 3, (int) (this.getHeight() * 0.75), Image.SCALE_SMOOTH);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (scaledImage != null) {
      g.drawImage(scaledImage, 145, 25, this);
    }
  }

  public String getNameOfAvatar() {
    return nameOfAvatar;
  }
}
