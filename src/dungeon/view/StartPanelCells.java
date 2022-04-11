package dungeon.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//This is a package private class which will be used internally by the view.
class StartPanelCells extends JPanel {
  private final String path;

  public StartPanelCells(String path) {
    if (path == null) {
      throw new IllegalArgumentException("path cannot be null");
    }
    this.path = path;
    this.setBackground(Color.RED);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    InputStream imageStream = getClass().getResourceAsStream(path);
    Image scaledImage = null;
    try {
      if (imageStream != null) {
        scaledImage = ImageIO.read(imageStream).getScaledInstance(this.getWidth(), this.getHeight(),
                Image.SCALE_SMOOTH);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (scaledImage != null) {
      g.drawImage(scaledImage, 0, 0, this);
    }
  }
}
