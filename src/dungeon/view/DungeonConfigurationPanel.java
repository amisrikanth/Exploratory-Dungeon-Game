package dungeon.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import static java.lang.Integer.parseInt;

//This is a package private class which will be used internally by the view.
class DungeonConfigurationPanel extends JPanel {
  private final JTextField inputName;
  private final JTextField inputNumOfRows;
  private final JTextField inputNumOfColumns;
  private final JTextField inputInterconnectivity;
  private final JRadioButton yes;
  private final JTextField inputTreasurePercentage;
  private final JTextField inputNumberOfOtyughs;
  private final JButton submit;
  private final JButton reset;
  private final JLabel res;

  public DungeonConfigurationPanel() {
    this.setLayout(null);
    JLabel title = new JLabel("Dungeon Configuration");
    title.setFont(new Font("Arial", Font.PLAIN, 30));
    title.setSize(350, 35);
    title.setLocation(300, 30);
    this.add(title);

    JLabel name = new JLabel("Name", SwingConstants.RIGHT);
    name.setFont(new Font("Arial", Font.PLAIN, 20));
    name.setSize(100, 20);
    name.setLocation(300, 100);
    this.add(name);

    inputName = new JTextField();
    inputName.setFont(new Font("Arial", Font.PLAIN, 15));
    inputName.setSize(200, 25);
    inputName.setLocation(450, 100);
    this.add(inputName);

    JLabel numOfRows = new JLabel("Rows (Min 6)", SwingConstants.RIGHT);
    numOfRows.setFont(new Font("Arial", Font.PLAIN, 20));
    numOfRows.setSize(150, 20);
    numOfRows.setLocation(250, 150);
    this.add(numOfRows);

    inputNumOfRows = new JTextField();
    inputNumOfRows.setFont(new Font("Arial", Font.PLAIN, 15));
    inputNumOfRows.setSize(200, 25);
    inputNumOfRows.setLocation(450, 150);
    this.add(inputNumOfRows);

    JLabel numOfColumns = new JLabel("Columns (Min 6)", SwingConstants.RIGHT);
    numOfColumns.setFont(new Font("Arial", Font.PLAIN, 20));
    numOfColumns.setSize(160, 20);
    numOfColumns.setLocation(240, 200);
    this.add(numOfColumns);

    inputNumOfColumns = new JTextField();
    inputNumOfColumns.setFont(new Font("Arial", Font.PLAIN, 15));
    inputNumOfColumns.setSize(200, 25);
    inputNumOfColumns.setLocation(450, 200);
    this.add(inputNumOfColumns);

    JLabel interconnectivity = new JLabel("Interconnectivity", SwingConstants.RIGHT);
    interconnectivity.setFont(new Font("Arial", Font.PLAIN, 20));
    interconnectivity.setSize(160, 20);
    interconnectivity.setLocation(250, 250);
    this.add(interconnectivity);

    inputInterconnectivity = new JTextField();
    inputInterconnectivity.setFont(new Font("Arial", Font.PLAIN, 15));
    inputInterconnectivity.setSize(200, 25);
    inputInterconnectivity.setLocation(450, 250);
    this.add(inputInterconnectivity);

    JLabel wrapping = new JLabel("Wrapping", SwingConstants.RIGHT);
    wrapping.setFont(new Font("Arial", Font.PLAIN, 20));
    wrapping.setSize(100, 25);
    wrapping.setLocation(300, 300);
    this.add(wrapping);

    yes = new JRadioButton("Yes");
    yes.setFont(new Font("Arial", Font.PLAIN, 15));
    yes.setSelected(true);
    yes.setSize(75, 20);
    yes.setLocation(450, 305);
    this.add(yes);

    JRadioButton no = new JRadioButton("No");
    no.setFont(new Font("Arial", Font.PLAIN, 15));
    no.setSelected(false);
    no.setSize(80, 20);
    no.setLocation(525, 305);
    this.add(no);

    ButtonGroup wrappingButtonGroup = new ButtonGroup();
    wrappingButtonGroup.add(yes);
    wrappingButtonGroup.add(no);

    JLabel treasurePercentage = new JLabel("Percentage Of Treasure/Arrows", SwingConstants.RIGHT);
    treasurePercentage.setFont(new Font("Arial", Font.PLAIN, 20));
    treasurePercentage.setSize(300, 25);
    treasurePercentage.setLocation(100, 350);
    this.add(treasurePercentage);

    inputTreasurePercentage = new JTextField();
    inputTreasurePercentage.setFont(new Font("Arial", Font.PLAIN, 15));
    inputTreasurePercentage.setSize(200, 25);
    inputTreasurePercentage.setLocation(450, 350);
    this.add(inputTreasurePercentage);

    JLabel numberOfOtyughs = new JLabel("Number Of Otyughs", SwingConstants.RIGHT);
    numberOfOtyughs.setFont(new Font("Arial", Font.PLAIN, 20));
    numberOfOtyughs.setSize(300, 25);
    numberOfOtyughs.setLocation(100, 400);
    this.add(numberOfOtyughs);

    inputNumberOfOtyughs = new JTextField();
    inputNumberOfOtyughs.setFont(new Font("Arial", Font.PLAIN, 15));
    inputNumberOfOtyughs.setSize(200, 25);
    inputNumberOfOtyughs.setLocation(450, 400);
    this.add(inputNumberOfOtyughs);

    submit = new JButton("Submit");
    submit.setFont(new Font("Arial", Font.PLAIN, 15));
    submit.setSize(100, 20);
    submit.setLocation(300, 475);
    this.add(submit);

    reset = new JButton("Reset");
    reset.setFont(new Font("Arial", Font.PLAIN, 15));
    reset.setSize(100, 20);
    reset.setLocation(450, 475);
    this.add(reset);

    res = new JLabel("");
    res.setFont(new Font("Arial", Font.PLAIN, 20));
    res.setSize(500, 30);
    res.setLocation(340, 525);
    this.add(res);

    setVisible(true);
  }

  public void addMouseClickListener(IView listener) {
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mouseReleased(MouseEvent e) {
        super.mouseClicked(e);
        if (e.getSource() == submit) {
          try {
            listener.storeDungeonParameters(inputName.getText(), parseInt(inputNumOfRows.getText()),
                    parseInt(inputNumOfColumns.getText()),
                    parseInt(inputInterconnectivity.getText()),
                    yes.isSelected(), parseInt(inputTreasurePercentage.getText()),
                    parseInt(inputNumberOfOtyughs.getText()));
          }
          catch (Exception exception) {
            res.setText("Invalid Input !!");
          }
        } else if (e.getSource() == reset) {
          String def = "";
          inputName.setText(def);
          inputNumOfRows.setText(def);
          inputNumOfColumns.setText(def);
          inputInterconnectivity.setText(def);
          inputTreasurePercentage.setText(def);
          inputNumberOfOtyughs.setText(def);
          yes.setSelected(true);
          res.setText(def);
        }
      }
    };
    submit.addMouseListener(clickAdapter);
    reset.addMouseListener(clickAdapter);
  }

}
