package dungeon.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import dungeon.controller.IDungeonGUIController;
import dungeon.model.IReadOnlyDungeon;


/**
 * This class is the view for the Dungeon game, the class is responsible for taking in various
 * inputs from the user in the form of keyboard inputs and mouse events and conveys these events to
 * the controller. It has a read only model from which the view can directly get the state of the
 * model. The view uses various panels to collect information and shows the game. The view gives
 * additional information in the form of popups (JOptionPane).
 */
public class DungeonView extends JFrame implements IView {

  private final DungeonConfigurationPanel configPanel;
  private JPanel startPanel;
  private JPanel playerConfigPanel;
  private LocationPanel[] locationPanels;
  private AvatarPanel[] avatarPanels;
  private JPanel mainGamePanel;
  private JScrollPane scrollPane;
  private JMenuBar menuBar;
  private JMenu restart;
  private JMenuItem menuItem1;
  private JMenuItem menuItem2;
  private JMenuItem menuItem3;
  private JMenuItem menuItem4;
  private JMenuItem additionalMenuItem1;
  private JMenuItem additionalMenuItem;
  private int rows;
  private int columns;
  private IDungeonGUIController listener;
  private String avatar;

  /**
   * The constructor takes in the read only model and initializes the parameters of the class.
   * @param model The Read only model.
   */
  public DungeonView(IReadOnlyDungeon model) {
    super("Dungeon");
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    createDungeonHelper(model);
    this.setBounds(300, 90, 900, 700);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);
    configPanel = new DungeonConfigurationPanel();
    configPanel.addMouseClickListener(this);
    playerAvatarScreenHelper();
    scrollPane.setVisible(false);
    menuBar.setVisible(false);
    startScreenConstruction();
  }

  @Override
  public void createDungeon(IReadOnlyDungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("model cannot be null");
    }
    playerConfigPanel.setVisible(false);
    configPanel.setVisible(false);
    createDungeonHelper(model);
    add(scrollPane);
  }


  private void createDungeonHelper(IReadOnlyDungeon model) {
    this.rows = model.numberOfRowsInDungeon();
    this.columns = model.numberOfColumnsInDungeon();
    this.menuBar = new CustomMenuBar();

    this.menuItem1 = new JMenuItem("Home");
    this.menuItem2 = new JMenuItem("Player Description");
    this.menuItem3 = new JMenuItem("Location Description");
    this.menuItem4 = new JMenuItem("Quit");
    restart = new JMenu("Restart");
    additionalMenuItem = new JMenuItem("Same Dungeon");
    restart.add(additionalMenuItem);
    additionalMenuItem1 = new JMenuItem("New Dungeon");
    restart.add(additionalMenuItem1);
    JMenu home = new JMenu();
    home.add(menuItem1);
    home.addSeparator();
    home.add(restart);
    home.addSeparator();
    home.add(menuItem2);
    home.addSeparator();
    home.add(menuItem3);
    home.addSeparator();
    home.add(menuItem4);
    menuBar.setBackground(Color.black);
    try {
      home.setIcon(new ImageIcon(ImageIO.read(Objects.requireNonNull(
              getClass().getResourceAsStream("/images/HamburgerMenuIcon.jpeg")))));
    } catch (IOException e) {
      e.printStackTrace();
    }
    menuBar.add(home);
    setJMenuBar(menuBar);
    int numberOfLocations = rows * columns;
    locationPanels = new LocationPanel[numberOfLocations];
    mainGamePanel = new JPanel();
    mainGamePanel.setLayout(new GridLayout(rows, columns));
    for (int i = 0; i < numberOfLocations; i++) {
      locationPanels[i] = new LocationPanel(model, i + 1, avatar);
      mainGamePanel.add(locationPanels[i]);
    }
    mainGamePanel.setBackground(Color.BLACK);
    mainGamePanel.setPreferredSize(new Dimension(
            Math.max(rows * 115, columns * 115), Math.max(columns * 115, rows * 115)));
    scrollPane = new JScrollPane(mainGamePanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(13);
    scrollPane.getHorizontalScrollBar().setUnitIncrement(13);
  }

  @Override
  public void bindKeys(IDungeonGUIController listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    Action upButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("north");
      }
    };
    Action downButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("south");
      }
    };
    Action leftButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("west");
      }
    };
    Action rightButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("east");
      }
    };
    Action aButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("arrow");
      }
    };
    Action dButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("diamond");
      }
    };
    Action sButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("sapphire");
      }
    };
    Action rButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("ruby");
      }
    };
    Action fButtonAction = new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.handleKeyPress("shoot");
      }
    };
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_UP, 0), "upButtonAction");
    mainGamePanel.getActionMap().put("upButtonAction", upButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_DOWN, 0), "downButtonAction");
    mainGamePanel.getActionMap().put("downButtonAction", downButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_LEFT, 0), "leftButtonAction");
    mainGamePanel.getActionMap().put("leftButtonAction", leftButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_RIGHT, 0), "rightButtonAction");
    mainGamePanel.getActionMap().put("rightButtonAction", rightButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_A, 0), "aButtonAction");
    mainGamePanel.getActionMap().put("aButtonAction", aButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_S, 0), "sButtonAction");
    mainGamePanel.getActionMap().put("sButtonAction", sButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_D, 0), "dButtonAction");
    mainGamePanel.getActionMap().put("dButtonAction", dButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_R, 0), "rButtonAction");
    mainGamePanel.getActionMap().put("rButtonAction", rButtonAction);
    mainGamePanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke
            .getKeyStroke(KeyEvent.VK_F, 0), "fButtonAction");
    mainGamePanel.getActionMap().put("fButtonAction", fButtonAction);
  }

  private void dungeonConfigScreenConstruction() {
    if (startPanel != null) {
      startPanel.setVisible(false);
    }
    if (mainGamePanel != null) {
      mainGamePanel.setVisible(false);
    }
    this.add(configPanel);
  }

  private void playerConfigScreenConstruction() {
    if (startPanel != null) {
      startPanel.setVisible(false);
    }
    if (mainGamePanel != null) {
      mainGamePanel.setVisible(false);
    }
    if (configPanel != null) {
      configPanel.setVisible(false);
    }
    playerAvatarScreenHelper();
    this.add(playerConfigPanel);
    MouseAdapter adapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        avatar = ((AvatarPanel) e.getSource()).getNameOfAvatar();
        listener.generateDungeon();
      }
    };
    for (int i = 0; i < 4; i++) {
      avatarPanels[i].addMouseListener(adapter);
    }
  }

  private void playerAvatarScreenHelper() {
    playerConfigPanel = new JPanel(new GridLayout(2, 2));
    avatarPanels = new AvatarPanel[4];
    for (int i = 0; i < 4; i++) {
      avatarPanels[i] = new AvatarPanel("avatar" + (i + 1));
      playerConfigPanel.add(avatarPanels[i]);
    }
  }

  private void startScreenConstruction() {
    startPanel = new JPanel();
    startPanel.setBackground(Color.BLUE);
    startPanel.setLayout(new GridLayout(3, 3));
    this.add(startPanel);
    StartPanelCells[] startPanelCells = new StartPanelCells[9];
    String dungeonImagePath = "/images/roommaze.png";
    String startImagePath = "/images/dungeons.png";
    for (int i = 0; i < 9; i++) {
      if (i != 4) {
        startPanelCells[i] = new StartPanelCells(dungeonImagePath);
      } else {
        startPanelCells[i] = new StartPanelCells(startImagePath);
      }
      startPanel.add(startPanelCells[i]);
    }
    MouseAdapter adapter = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        dungeonConfigScreenConstruction();
      }
    };
    startPanelCells[4].addMouseListener(adapter);
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }

  @Override
  public void addClickListener(IDungeonGUIController listener) {
    if (listener == null) {
      throw new IllegalArgumentException("listener cannot be null");
    }
    this.listener = listener;
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getSource() instanceof LocationPanel) {
          LocationPanel panel = (LocationPanel) e.getSource();
          int panelNumber = panel.getPanelNumber();
          listener.handleCellClick(panelNumber);
        } else if (e.getSource() instanceof JMenuItem) {
          JMenuItem source = (JMenuItem) e.getSource();
          listener.handleMenuItemClick(source.getText());
          if (source.getText().equals("Home")) {
            scrollPane.setVisible(false);
            menuBar.setVisible(false);
            configPanel.setVisible(true);
          }
        }
      }
    };
    for (int i = 0; i < rows * columns; i++) {
      locationPanels[i].addMouseListener(clickAdapter);
    }
    menuItem1.addMouseListener(clickAdapter);
    restart.addMouseListener(clickAdapter);
    menuItem2.addMouseListener(clickAdapter);
    menuItem3.addMouseListener(clickAdapter);
    menuItem4.addMouseListener(clickAdapter);
    additionalMenuItem.addMouseListener(clickAdapter);
    additionalMenuItem1.addMouseListener(clickAdapter);
  }

  @Override
  public String showPopUp(String text, String type) {
    if (text == null || type == null) {
      throw new IllegalArgumentException("Input cannot be null");
    }
    if (type.equalsIgnoreCase("input")) {
      return JOptionPane.showInputDialog(this, text, null);
    } else if (type.equalsIgnoreCase("msg")) {
      JOptionPane.showMessageDialog(this, text);
    }
    return null;
  }

  @Override
  public void refresh() {
    repaint();
  }

  @Override
  public void close() {
    System.exit(0);
  }

  @Override
  public void storeDungeonParameters(String name, int rows, int columns, int interconnectivity,
                                     boolean isWrapped, int percentageOfCavesWithTreasures,
                                     int numberOfOtyughs) {

    listener.createModel(name, rows, columns, interconnectivity, isWrapped,
            percentageOfCavesWithTreasures, numberOfOtyughs);
    playerConfigScreenConstruction();

  }


}
