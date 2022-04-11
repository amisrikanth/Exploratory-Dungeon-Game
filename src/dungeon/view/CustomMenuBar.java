package dungeon.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JMenuBar;

//This is a package private class which will be used internally by the view.
class CustomMenuBar extends JMenuBar {

  @Override
  public void paintComponent(Graphics g) {
    g.setColor(Color.black);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
  }

}
