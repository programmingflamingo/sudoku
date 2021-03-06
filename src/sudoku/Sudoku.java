package sudoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Sudoku {
  private final JFrame window;
  private final JPanel pnlContainer;
  private final JPanel pnlButtons;
  private final JPanel[][] pnlGrid;
  private final JPanel pnlCandidate;
  private final int [][][] VACandidate;
  private final JTextField[][] tf;
  private final JTextField[] tfCandidate;
  private final JMenuBar menuBar;
  private final JMenu mnuFile;
  private final JMenu mnuHelp;
  private final JMenu mnuHints;
  private final JMenuItem mitFileLoad;
  private final JMenuItem mitFileSave;
  private final JMenuItem mitFileExit;
  private final JMenuItem mitHelpHowtoPlay;
  private final JMenuItem mitHelpHowtoUse;
  private final JMenuItem mitHelpAbout;
  private final JCheckBoxMenuItem mcbHintsCheckonFill;
  private final JMenuItem mitHintsSingle;
  private final JMenuItem mitHintsHidden;
  private final JMenuItem mitHintsLocked;
  private final JMenuItem mitHintsNaked;
  private final JMenuItem mitHintsAll;
  protected final JButton btnClear;
  protected final JButton btnHelp;
  protected final JButton[] btnNumsInGrid;
  protected boolean chkOnFill;
  
  /**
   * Hello World.
   * 
   */
  public Sudoku() {
    window = new JFrame("CS 342 Michael Slomczynski Project 3");
    
    pnlContainer = new JPanel();
    pnlButtons = new JPanel();
    pnlGrid = new JPanel[3][3];
    
    pnlCandidate = new JPanel(new GridLayout(1, 9, 3, 3));
    chkOnFill = false;
    
    VACandidate = new int[9][9][9];
    int a;
    int b;
    int c;
    for (a = 0; a < 9; a++) {
      for (b = 0; b < 9; b++) {
        for (c = 0; c < 9; c++) {
          VACandidate[a][b][c] = c;
        }
      }
    }
    
    int m;
    int n;
    for (m = 0; m < 3; m++) {
      for (n = 0; n < 3; n++) {
        pnlGrid[m][n] = new JPanel();
      }
    }
    
    tfCandidate = new JTextField[9];
    
    for (n = 0; n < 9; n++) {
      tfCandidate[n] = new JTextField();
    }

    tf = new JTextField[9][9];

    for (m = 0; m < 9; m++) {
      for (n = 0; n < 9; n++) {
        tf[m][n] = new JTextField();//String.valueOf(n)
        tf[m][n].addMouseListener(new TextField());
      }
    }
    
    menuBar = new JMenuBar();
    mnuFile = new JMenu("File");
    mnuHelp = new JMenu("Help");
    mnuHints = new JMenu("Hints");
    mnuFile.addMenuListener(new MenuFile());
    mnuHelp.addMenuListener(new MenuHelp());
    mnuHints.addMenuListener(new MenuHints());
    mitFileLoad = new JMenuItem("Load Puzzle");
    mitFileSave = new JMenuItem("Save Puzzle");
    mitFileExit = new JMenuItem("Exit Puzzle");
    mitHelpHowtoPlay = new JMenuItem("How to Play");
    mitHelpHowtoUse = new JMenuItem("How to use the Program");
    mitHelpAbout = new JMenuItem("About the Developers");
    mcbHintsCheckonFill = new JCheckBoxMenuItem("Check Moves on Input");
    mitHintsSingle = new JMenuItem("Display Singles");
    mitHintsHidden = new JMenuItem("Display Hidden Singles");
    mitHintsLocked = new JMenuItem("Display Locked Candidates");
    mitHintsNaked = new JMenuItem("Display Naked Pairs");
    mitHintsAll = new JMenuItem("Display All");
    mitFileLoad.addActionListener(new MenuItemFileLoad());
    mitFileSave.addActionListener(new MenuItemFileSave());
    mitFileExit.addActionListener(new MenuItemFileExit());
    mitHelpHowtoPlay.addActionListener(new MenuItemHelpHowtoPlay());
    mitHelpHowtoUse.addActionListener(new MenuItemHelpHowtoUse());
    mitHelpAbout.addActionListener(new MenuItemHelpAbout());
    mcbHintsCheckonFill.addActionListener(new MenuItemHintsCheckonFill());
    mitHintsSingle.addActionListener(new MenuItemHintsSingle());
    mitHintsHidden.addActionListener(new MenuItemHintsHidden());
    mitHintsLocked.addActionListener(new MenuItemHintsLocked());
    mitHintsNaked.addActionListener(new MenuItemHintsNaked());
    mitHintsAll.addActionListener(new MenuItemHintsAll());
    btnClear = new JButton("X");
    btnHelp = new JButton("Help");
    btnClear.addActionListener(new ButtonClear());
    btnHelp.addActionListener(new ButtonHelp());
    
    btnNumsInGrid = new JButton[9];
    int i;
    for (i = 0; i < 9; i++) {
      String gridNum;
      gridNum = Integer.toString(i + 1);
      btnNumsInGrid[i] = new JButton(gridNum);
      btnNumsInGrid[i].addActionListener(new ButtonNumsInGrid());
    }
  }
  
  /**
   *  Hello World.
   */
  public void createWindow() {
    pnlContainer.setLayout(new BorderLayout());
    
    int m;
    int n;
    for (m = 0; m < 9; m++) {
      for (n = 0; n < 9; n++) {
        tf[m][n].setEnabled(false);
        tf[m][n].setDisabledTextColor(Color.MAGENTA);
        tf[m][n].setHorizontalAlignment(JTextField.CENTER);
        pnlGrid[m / 3][m % 3].add(tf[m][n]);
        pnlGrid[m / 3][m % 3].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
      }
    }
        
    int i;
    for (i = 0; i < 9; i++) {
      pnlButtons.add(btnNumsInGrid[i]);
      btnNumsInGrid[i].setEnabled(false);
    }
    
    pnlButtons.add(btnClear);
    pnlButtons.add(btnHelp);
    btnClear.setEnabled(false);
    btnHelp.setEnabled(false);
    pnlButtons.setLayout(new GridLayout(11, 1, 3, 3));
    
    for (m = 0; m < 3; m++) {
      for (n = 0;n < 3; n++) {
        pnlGrid[m][n].setLayout(new GridLayout(3, 3, 3, 3));
      }
    }
  
    Dimension dimButtons = new Dimension();
    dimButtons.setSize(64, 64);
    pnlButtons.setPreferredSize(dimButtons);
    
    JPanel pnlTop = new JPanel(new GridLayout(1, 3, 3, 3));
    JPanel pnlMid = new JPanel(new GridLayout(1, 3, 3, 3));
    JPanel pnlBot = new JPanel(new GridLayout(1, 3, 3, 3));
    JPanel pnlAll = new JPanel();
    pnlAll.setLayout(new BoxLayout(pnlAll, BoxLayout.Y_AXIS));

    for (m = 0; m < 3; m++) {
      for (n = 0; n < 3; n++) {
        if (m == 0) {
          pnlTop.add(pnlGrid[m][n]);
        }
        if (m == 1) {
          pnlMid.add(pnlGrid[m][n]);
        }
        if (m == 2) {
          pnlBot.add(pnlGrid[m][n]);
        }
      }
    }
    
    pnlAll.add(pnlTop);
    pnlAll.add(pnlMid);
    pnlAll.add(pnlBot);

    Dimension dimCandidate = new Dimension();
    dimCandidate.setSize(40, 40);

    for (n = 0; n < 9; n++) {
      tfCandidate[n].setEnabled(false);
      tfCandidate[n].setDisabledTextColor(Color.BLACK);
      tfCandidate[n].setHorizontalAlignment(JTextField.CENTER);
      tfCandidate[n].setPreferredSize(dimCandidate);
      pnlCandidate.add(tfCandidate[n]);
    }
    
    pnlCandidate.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
    
    pnlContainer.add(pnlAll, BorderLayout.CENTER);
    pnlContainer.add(pnlButtons, BorderLayout.LINE_END);
    pnlContainer.add(pnlCandidate, BorderLayout.SOUTH);
    mnuFile.add(mitFileLoad);
    mnuFile.add(mitFileSave);
    mnuFile.add(mitFileExit);
    menuBar.add(mnuFile);
    mnuHelp.add(mitHelpHowtoPlay);
    mnuHelp.add(mitHelpHowtoUse);
    mnuHelp.add(mitHelpAbout);
    menuBar.add(mnuHelp);
    mnuHints.add(mcbHintsCheckonFill);
    mnuHints.add(mitHintsSingle);
    mnuHints.add(mitHintsHidden);
    mnuHints.add(mitHintsLocked);
    mnuHints.add(mitHintsNaked);
    mnuHints.add(mitHintsAll);
    menuBar.add(mnuHints);
    window.add(pnlContainer);
    window.setJMenuBar(menuBar);
    Dimension winDim = new Dimension();
    
    int width;
    int height;
    width = 640;
    height = 480;

    winDim.setSize(width + 16, height + 29);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(winDim);
    window.setResizable(false);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
  
  protected boolean getDidWin() {
    
    int a; 
    int b;
    int c; 
    for (a = 0; a < 9; a++) {
      for (b = 0; b < 9; b++) {
        for (c = 0; c < 9; c++) {
          if (VACandidate[a][b][c] > -1) {
            return false;
          }
        }
      }
    }
    
    int[] count = new int[9];

    for (a = 0; a < 9; a++) {
      int sum = 0;
      for (c = 0; c < 9; c++) {
        count[c] = c + 1;
      }
      for (b = 0; b < 9; b++) {
        for (c = 0; c < 9; c++) {
          if (Integer.parseInt(tf[a][b].getText()) == count[c]) {
            sum = sum + count[c];
            break;
          }
        }
      }
 
      if (sum != 45) {
        return false;
      }
    }

    for (a = 0; a < 9; a++) {
      {
        int sum = 0;
        for (c = 0; c < 9; c++) {
          count[c] = c + 1;
        }
        for (b = 0; b < 9; b++) {
          int row = getGTtoR(a, b);
          int col = getGTtoC(a, b);
          
          for (c = 0; c < 9; c++) {
            if (Integer.parseInt(tf[row][col].getText()) == count[c]) {
              sum = sum + count[c];
              break;
            }
          }
        }
        
        if (sum != 45) {
          return false;
        }
      } //what is this
    }

    for (a = 0; a < 9; a++) {
      {
        int sum = 0;
        for (c = 0; c < 9; c++) {
          count[c] = c + 1;
        }
        
        for (b = 0; b < 9; b++) {
          int row = getGTtoR(b, a);
          int col = getGTtoC(b, a);
          
          for (c = 0; c < 9; c++) {
            if (Integer.parseInt(tf[row][col].getText()) == count[c]) {
              sum = sum + count[c];
              break;
            }
          }
        }
        
        if (sum != 45) {
          return false;
        }
      
      }  
    }
    return true;
  }
    
  protected void winner() {
    if (getDidWin()) {
      JLabel label = new JLabel("<html><pre>You Won!!!</pre></html>");
      label.setFont(new Font("Courier New", Font.PLAIN, 12));
      JOptionPane.showMessageDialog(null, label, "WINNER", JOptionPane.PLAIN_MESSAGE);
    }
    return;
  }
    
  //g = 0-8
  //t = 0-8
  protected int getGTtoR(int g, int t) {
    int r = (g / 3) * 3 + (t / 3);
    return r;
  }

  //g = 0-8
  //t = 0-8
  protected int getGTtoC(int g, int t) {
    int c = (g * 3 % 9) + (t % 3);
    return c;
  }
    
  //r = 0-8
  //c = 0-8
  protected int getRCtoG(int r, int c) {
    int g = ((r / 3) * 3) + (c / 3);
    return g;
  }

  //r = 0-8
  //c = 0-8
  protected int getRCtoT(int r, int c) {
    int t = ((r % 3) * 3) + (c % 3);
    return t;
  }
    
  protected void setVACandidate(int g, int t, int num, int i) {
    int m;
    int n;
    for (m = 0; m < 9; m++) {
      for (n = 0; n < 9; n++) {
        if (VACandidate[g][m][n] == num - 1) {
          VACandidate[g][m][n] = i;
        }
      }
    }
        
    int x;
    int y;
    int z;
        
    if (g % 3 == 0) {
      m = g + 1;
      n = g + 2;
    } else if (g % 3 == 1) {
      m = g + 1;
      n = g - 1;
    } else {
      m = g - 1;
      n = g - 2;
    }
        
    for (x = 0; x < 9; x++) {
      if ((x == m || x == n)) {
        for (y = 0; y < 9; y++) {
          for (z = 0; z < 9; z++) {
            if (VACandidate[x][t / 3 * 3 + y % 3][z] == num - 1) {
              VACandidate[x][t / 3 * 3 + y % 3][z] = i;
            }
          }
        }
      }
    }
    
    if (g / 3 == 0) {
      m = g + 3;
      n = g + 6;
    } else if (g / 3 == 1) {
      m = g - 3;
      n = g + 3;
    } else {
      m = g - 6;
      n = g - 3;
    }
        
    for (x = 0; x < 9; x++) {
      if ((x == m || x == n)) {
        for (y = 0; y < 9; y++) {
          for (z = 0; z < 9; z++) {
            if (VACandidate[x][t % 3 + y / 3 * 3][z] == num - 1) {
              VACandidate[x][t % 3 + y / 3 * 3][z] = i;
            }
          }
        }
      }
    }
  }
  
  protected void setUndoVACandidate(int g, int t, int num) {
    int m;
    int n;
    for (m = 0; m < 9; m++) {
      if (VACandidate[g][m][num - 1] == -1) {
        VACandidate[g][m][num - 1] = num - 1;
      }
    }
        
    int x;
    int y;
        
    if (g % 3 == 0) {
      m = g + 1;
      n = g + 2;
    } else if (g % 3 == 1) {
      m = g + 1;
      n = g - 1;
    } else {
      m = g - 1;
      n = g - 2;
    }

    for (x = 0; x < 9; x++) {
      if ((x == m || x == n)) {
        for (y = 0; y < 9; y++) {
          if (VACandidate[x][t / 3 * 3 + y % 3][num - 1] == -1) {
            VACandidate[x][t / 3 * 3 + y % 3][num - 1] = num - 1;
          }
        }
      }
    }
        
    if (g / 3 == 0) {
      m = g + 3;
      n = g + 6;
    } else if (g / 3 == 1) {
      m = g - 3;
      n = g + 3;
    } else {
      m = g - 6;
      n = g - 3;
    }
        
    for (x = 0; x < 9; x++) {
      if ((x == m || x == n)) {
        for (y = 0; y < 9; y++) {
          if (VACandidate[x][t % 3 + y / 3 * 3][num - 1] == -1) {
            VACandidate[x][t % 3 + y / 3 * 3][num - 1] = num - 1;
          }
        }
      }
    }
  }
    
  protected class TextField implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent arg0) {
      if (!btnClear.isEnabled() && ((JTextField)arg0.getSource()).getDisabledTextColor() != Color.BLACK) {
        int a = 0;
        int b = 0;
        for (a = 0; a < 9; a++) {
          for (b = 0; b < 9; b++) {
            if (tf[a][b] == arg0.getSource()) {
              if (((JTextField)arg0.getSource()).getText().equals("")) {
                return;
              }
              
              int c = Integer.parseInt(((JTextField)arg0.getSource()).getText());
              ((JTextField)arg0.getSource()).setText("");
              setUndoVACandidate(a, b, c);
            }
          }
        }
      } else if (!btnHelp.isEnabled() && ((JTextField)arg0.getSource()).getDisabledTextColor() != Color.BLACK) {
        int i = 0;
        int j = 0;
        int k = 0;
        for (i = 0; i < 9; i++) {
          for (j = 0; j < 9; j++) {
            if (tf[i][j] == arg0.getSource()) {
              for (k = 0; k < 9; k++) {
                tfCandidate[k].setText("");
              }
              
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  tfCandidate[k].setText(String.valueOf(VACandidate[i][j][k] + 1));
                }
              }
              return;
            }
          }
        }
      } else {
        int i;
        for (i = 0; i < 9; i++) {
          if (!btnNumsInGrid[i].isEnabled() && ((JTextField)arg0.getSource()).getDisabledTextColor() != Color.BLACK) {
            if (chkOnFill == false) {
              int a = 0;
              int b = 0;
              for (a = 0; a < 9; a++) {
                for (b = 0; b < 9; b++) {
                  if (tf[a][b] == arg0.getSource()) {
                    ((JTextField)arg0.getSource()).setText(String.valueOf(i + 1));
                    setVACandidate(a, b, i + 1, -1);
                    winner();
                  }
                }
              }
            } else {
              int a = 0;
              int b = 0;
              int c = 0;
              for (a = 0; a < 9; a++) {
                for (b = 0; b < 9; b++) {
                  if (tf[a][b] == arg0.getSource()) {
                    for (c = 0; c < 9; c++) {
                      if (VACandidate[a][b][c] == i) {
                        ((JTextField)arg0.getSource()).setText(String.valueOf(i + 1));
                        setVACandidate(a, b, VACandidate[a][b][c] + 1, -1);
                        winner();
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub
      
    }
  }

  protected class ButtonClear implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int i;
      for (i = 0; i < 9; i++) {
        btnNumsInGrid[i].setEnabled(true);
        tfCandidate[i].setText("");
      }
      btnClear.setEnabled(false);
      btnHelp.setEnabled(true);
    }
  }
    
  protected class ButtonHelp implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      int i;
      for (i = 0; i < 9; i++) {
        btnNumsInGrid[i].setEnabled(true);
      }
      btnClear.setEnabled(true);
      btnHelp.setEnabled(false);
    }
  }
  
  protected class ButtonNumsInGrid implements ActionListener {
    public int getButtonNumber(ActionEvent e) {
      JButton btnGridNum = (JButton)e.getSource();
      String gridNumText = "";

      if (btnGridNum != null) {
        gridNumText = btnGridNum.getText();
      }

      int gridNum = Integer.parseInt(gridNumText) - 1;
      return gridNum;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
      int disable = getButtonNumber(e);
      int i;
      for (i = 0; i < 9; i++) {
        if (i == disable) {
          btnNumsInGrid[i].setEnabled(false);
        } else {
          btnNumsInGrid[i].setEnabled(true);
        }
        
        tfCandidate[i].setText("");
      }
      btnClear.setEnabled(true);
      btnHelp.setEnabled(true);
    }
  }
    
  protected class MenuFile implements MenuListener {

    @Override
    public void menuCanceled(MenuEvent arg0) {
      
    }

    @Override
    public void menuDeselected(MenuEvent arg0) {
      
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
      
    }
  }

  protected class MenuHelp implements MenuListener {

    @Override
    public void menuCanceled(MenuEvent arg0) {
      
    }

    @Override
    public void menuDeselected(MenuEvent arg0) {
      
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
      
    }
  }
  
  protected class MenuHints implements MenuListener {

    @Override
    public void menuCanceled(MenuEvent arg0) {
      
    }

    @Override
    public void menuDeselected(MenuEvent arg0) {
      
    }

    @Override
    public void menuSelected(MenuEvent arg0) {
      
    }
  }

  protected class MenuItemFileLoad implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
      chooser.setFileFilter(filter);
      chooser.setCurrentDirectory(new java.io.File("."));
      int returnVal = chooser.showOpenDialog(mitFileLoad);
      
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        File f = chooser.getSelectedFile();
        
        try {
          @SuppressWarnings("resource")
          Scanner s = new Scanner(f);
          if (!s.hasNextInt()) {
            JLabel label = new JLabel("<html>Cannot load blank grid.</html>");
            label.setFont(new Font("Courier New", Font.PLAIN, 12));
            JOptionPane.showMessageDialog(null, label, "", JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          
          int x;
          int y;
          int z;
          for (x = 0; x < 9; x++) {
            for (y = 0; y < 9; y++) {
              for (z = 0; z < 9; z++) {
                VACandidate[x][y][z] = z;
                tfCandidate[z].setText("");
              }
            }
          }
          
          int u;
          int w;
          for (w = 0; w < 9; w++) {
            for (u = 0; u < 9; u++) {
              tf[w][u].setDisabledTextColor(Color.MAGENTA);
            }
          }
          
          int i;
          for (i = 0; i < 9; i++) {
            btnNumsInGrid[i].setEnabled(true);
          }
          
          btnClear.setEnabled(true);
          btnHelp.setEnabled(true);

          int m;
          int n;
          for (m = 0; m < 9; m++) {
            for (n = 0; n < 9; n++) {
              tf[m][n].setText("");
            }
          }
          
          while (s.hasNextInt()) {
            int r;
            int c;
            int num;
            r = s.nextInt() - 1;
            c = s.nextInt() - 1;
            num = s.nextInt();

            int g = getRCtoG(r, c);
            int t = getRCtoT(r, c);
            
            //System.out.println("{"+r+"} {"+c+"} {"+num+"}");
            //System.out.println("{"+g+"} {"+t+"}");
            
            tf[g][t].setText(String.valueOf(num));
            tf[g][t].setDisabledTextColor(Color.BLACK);
            
            setVACandidate(g, t, num, -2);
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
    
  protected class MenuItemFileSave implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      int flag = 0;
      int i;
      for (i = 0; i < 9; i++) {
        if (!btnNumsInGrid[i].isEnabled()) {
          flag = 0;
        } else {
          flag = 1;
        }
      }
      
      if (flag == 0) {
        JLabel label = new JLabel("<html>Cannot save blank grid.</html>");
        label.setFont(new Font("Courier New", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(null, label, "", JOptionPane.INFORMATION_MESSAGE);
        return;
      }
      
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
      chooser.setFileFilter(filter);
      chooser.setCurrentDirectory(new java.io.File("."));
      int returnVal = chooser.showSaveDialog(mitFileLoad);
      
      if (returnVal == JFileChooser.APPROVE_OPTION) {
        String file = chooser.getSelectedFile().toString();
        if (!file.endsWith(".txt")) { 
          file += ".txt";
        }
        
        try (FileWriter fw = new FileWriter(file)) {
          int m;
          int n;
          for (m = 0; m < 9; m++) {
            for (n = 0; n < 9; n++) {
              if (!tf[m][n].getText().equals("")) { 
                int r;
                int c;
                int num;
                //int g = (((r)/3)*3)+((c)/3);
                //int t = (((r)%3)*3)+((c)%3);
                r = getGTtoR(m, n) + 1;//((m/3*3)+(n/3))+1; getGTtoR(m, n);
                c = getGTtoC(m, n) + 1;//((m*3)%9+(n%3))+1;
                num = Integer.parseInt(tf[m][n].getText());
                String a = r + " " + c + " " + num + "\n";
                //System.out.println(a);
                fw.write(a.toString());
              }
            }
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
    
  protected class MenuItemFileExit implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      int a;
      int b;
      int c;
      for (a = 0; a < 9; a++) {
        btnNumsInGrid[a].setEnabled(false);
        tfCandidate[a].setText("");
        for (b = 0; b < 9; b++) {
          tf[a][b].setDisabledTextColor(Color.MAGENTA);
          tf[a][b].setText("");
          for (c = 0; c < 9; c++) {
            VACandidate[a][b][c] = c;
          }
        }
      }
      btnClear.setEnabled(false);
      btnHelp.setEnabled(false);
    }
  }
    
  protected class MenuItemHelpHowtoPlay implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      JLabel label = new JLabel("<html><pre>Sudoku is played on a 9x9 grid.<br>This can be further divided into smaller 3x3 subgrids, with each subgrid having the set of numbers 1 to 9.<br>The game will start out with the grid partially filled out.<br>The point of the game is to complete the partially filled out board with the set of numbers.<br><br>The Rules<br>Each subgrid is comprised of unique numbers.<br>|1|2|3|          |1|2|3|<br>|4|5|6|  Valid   |1|2|3|  Invalid<br>|7|8|9|          |1|2|3|<br><br>Each column in the 9x9 grid is comprised of unique numbers.<br>|1|2|3|4|5|6|7|8|9|  Valid<br><br>|1|2|3|1|2|3|1|2|3|  Invalid<br><br>Each row in the 9x9 grid is comprised of unique numbers.<br><br>|1|           |1|<br>|2|           |2|<br>|3|           |3|<br>|4|           |1|<br>|5|  Valid    |2|  Invalid<br>|6|           |3|<br>|7|           |1|<br>|8|           |2|<br>|9|           |3|<br></pre></html>");
      label.setFont(new Font("Courier New", Font.PLAIN, 12));
      JOptionPane.showMessageDialog(null, label, "How to Play Sudoku", JOptionPane.PLAIN_MESSAGE);
    }
  }
    
  protected class MenuItemHelpHowtoUse implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      JLabel label = new JLabel("<html><pre>File<br>  Load Puzzle<br>    The best place to begin. This will take a .txt file and create a new puzzle.<br><br>  Save Puzzle<br>    If you want to save the progress you've made on a certain puzzle you can do that here.<br>    When saving your file the .txt extension will be automatically added.<br><br>  Exit Puzzle<br>    This will exit the current puzzle you have loaded on the grid.<br><br><br>Help<br>  How to Play<br>    Here you can see the rules of the game and examples of when the rules are broken.<br><br>  How to use the Program<br>    This is where you are now. This is general information about what everything does.<br><br>  About the Developers<br>    You can find out who developed the game here.<br><br><br>Hints<br>  Check Moves on Input<br>    An option you can check that makes sure that the moves that you make are possible.<br>    Your input number is compared to the list of numbers in the candidate list of the tile that you are currently trying to play.<br>    If your number is in the list then your move is allowed.<br>    Otherwise your move is ignored. <br><br>  Display Singles<br>    Checks the candidate list of all tiles in the grid.<br>    If there is only one number in the list than that number is automatically put onto the tile.<br>    Once one number is placed on the tile the iteration stops.<br><br>  Display Hidden Singles<br>    Also places a number on a tile.<br>    This compares all the candidate lists of a sub grid and if there is a unique number among them then that number and that tile are chosen.<br><br>  Display Locked Candidates<br>    This does not put a number onto a tile but instead reduces the number of candidates in a list.<br>    There are two scenarios box to row/column or row/column to box.<br>    With box to r/c numbers would be eliminated from the r/c if those same numbers are only available in that same r/c for the box.<br>    With r/c to box the same thought process is applied but the numbers are removed from the box. <br><br>  Display Naked Pairs<br>    This does not put a number onto a tile but instead reduces the number of candidates in a list.<br>    This looks at a group (row, column, subgrid) and checks if in that group there are two candidate lists with only two values that are the same.<br>    If so these values are removed from any other candidate list in the same group.<br><br>  Display All<br>    This will iterate through all of the algorithms solving the sudoku board until it cannot solve it anymore.<br><br><br>The Buttons<br>  Numbers<br>    Pushing one of the buttons with a number will set that number.<br>    Then when you click on the board you are able to place that set number.<br>    You are not able to place numbers on numbers that were loaded from the text file.<br><br>  X / Clear<br>    Pushing this button will allow you to clear numbers placed by you.<br>    You are not allowed to clear numbers that were loaded from the text file.<br><br>  Help<br>    Displays the candidate list for a given tile.<br><br></pre></html>");
      label.setFont(new Font("Courier New", Font.PLAIN, 12));
      //JScrollPane.//showMessageDialog(null, label, "How to use the Program", JOptionPane.PLAIN_MESSAGE);
      JScrollPane js = new JScrollPane(label);
      js.getVerticalScrollBar().setUnitIncrement(16);
      js.setPreferredSize(new Dimension(1200,500));
      JOptionPane.showMessageDialog(null, js, "How to use the Program", JOptionPane.PLAIN_MESSAGE);
    }
  }

  protected class MenuItemHelpAbout implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      JLabel label = new JLabel("<html>CS 342 Program 3<br>Michael Slomczynski<br>ms11<br></html>");
      label.setFont(new Font("Courier New", Font.PLAIN, 12));
      JOptionPane.showMessageDialog(null, label, "How to use the Program", JOptionPane.PLAIN_MESSAGE);
    }
  }

  protected class MenuItemHintsCheckonFill implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (chkOnFill == true) {
        chkOnFill = false;
      } else {
        chkOnFill = true;
      }
    }
  }
    
  protected class MenuItemHintsSingle implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int count = 0;
      //System.out.println("Hello");
      for (i = 0; i < 9; i++) {
        for (j = 0; j < 9; j++) {
          {
            count = 0;
            if (tf[i][j].getDisabledTextColor() != Color.BLACK) {
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  count++;
                }
              }
            }
            
            if (count == 1) {
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  tf[i][j].setText(String.valueOf(VACandidate[i][j][k] + 1));
                  setVACandidate(i, j, VACandidate[i][j][k] + 1, -1);
                  break;
                }
              }
              return;
            }
          }
        }
      }
    }
  }
    
  protected class MenuItemHintsHidden implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int[] count = new int[9];
      //System.out.println("Hello");
      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          count[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          if (tf[i][j].getDisabledTextColor() != Color.BLACK) {
            for (k = 0; k < 9; k++) {
              if (VACandidate[i][j][k] > -1) {
                count[k]++;
              }
            }
          }
        }
        
        for (k = 0; k < 9; k++) {
          if (count[k] == 1) {
            for (j = 0; j < 9; j++) {
              if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK) {
                tf[i][j].setText(String.valueOf(VACandidate[i][j][k] + 1));
                setVACandidate(i, j, VACandidate[i][j][k] + 1, -1);
                
                int a;
                for (a = 0; a < 9; a++) {
                  VACandidate[i][j][a] = -1;
                }
                break;
              }
            }
            return;
          }
        }
      }
    }
  }
    
  protected class MenuItemHintsLocked implements ActionListener {
    public boolean RresB(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int[] countA = new int[9];
      int[] countB = new int[9];
      int[] countC = new int[9];

      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          countA[k] = 0;
          countB[k] = 0;
          countC[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          int r = getGTtoR(i, j);
          int c = getGTtoC(i, j);
          //System.out.println(r+" "+c);
          if (tf[r][c].getDisabledTextColor() != Color.BLACK) { //[r][c] 
            if (r % 3 == 0) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countA[k]++;
                }
              }
            } else if (r % 3 == 1) { //[c]/3
              for (k = 0;k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countB[k]++;
                }
              }
            } else {
              for (k = 0; k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countC[k]++;
                }
              }
            }
          }
        }
        
        for (k = 0; k < 9; k++) {
          /*
           * int a;
           * System.out.print("  A: ");
           * for (a=0;a<9;a++)
           * System.out.print(countA[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  B: ");
           * for (a=0;a<9;a++)
           * System.out.print(countB[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  C: ");
           * for (a=0;a<9;a++)
           * System.out.print(countC[a]+ " ");
           * System.out.println('\n');
           * */
          int flag = 0;
          if (countA[k] != 0 && countB[k] == 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(i, j);
              int c = getGTtoC(i, j);
              if (r % 3 == 0) {
                if (c / 3 == 0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 6][k] == k && tf[r][c + 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello2");
                  }
                }
                
                if (c / 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println(r+ " "+c);
                    //System.out.println("  hello3");
                  }

                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println(r+ " "+c);
                    //System.out.println("  hello4");
                  }
                }
                
                if (c / 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 6][k] == k && tf[r][c - 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello5");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello6");
                  }
                }
              }
            }
            
            if (flag == 1) {
              return true;
            }
          } else if (countA[k] == 0 && countB[k] != 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(i, j);
              int c = getGTtoC(i, j);
              
              if (r % 3 == 1) {
                if (c / 3 == 0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello7");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 6][k] == k && tf[r][c + 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello8");
                  }
                }
                
                if (c / 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello9");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello10");
                  }
                }
                
                if (c / 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 6][k] == k && tf[r][c - 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello11");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello12");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] == 0 && countC[k] != 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(i, j);
              int c = getGTtoC(i, j);
              if (r % 3 == 2) {
                if (c / 3 == 0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello13");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 6][k] == k && tf[r][c + 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello14");
                  }
                }
                
                if (c / 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 3][k] == k && tf[r][c + 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello15");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello16");
                  }
                }
                
                if (c / 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 6][k] == k && tf[r][c - 6].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 6][k] = -1;
                    flag = 1;
                    //System.out.println("  hello17");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 3][k] == k && tf[r][c - 3].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 3][k] = -1;
                    flag = 1;
                    //System.out.println("  hello18");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
        }
      }
      return false;
    }
    
    public boolean CresB(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int[] countA = new int[9];
      int[] countB = new int[9];
      int[] countC = new int[9];

      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          countA[k] = 0;
          countB[k] = 0;
          countC[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          int r = getGTtoR(j, i);
          int c = getGTtoC(j, i);
          if (tf[r][c].getDisabledTextColor() != Color.BLACK) { //[r][c] 
            if (r / 3 == 0) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countA[k]++;
                }
              }
            } else if (r / 3 == 1) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countB[k]++;
                }
              }
            } else {
              for (k = 0; k < 9; k++) {
                if (VACandidate[r][c][k] > -1) {
                  countC[k]++;
                }
              }
            }
          }
        }
        for (k = 0; k < 9; k++) {
          int flag = 0;
          if (countA[k] != 0 && countB[k] == 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(j, i);
              int c = getGTtoC(j, i);
              if (r / 3 == 0) {
                if (c % 3 == 0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 2][k] == k && tf[r][c + 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello2");
                  }
                }
                
                if (c % 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello3");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello4");
                  }
                }
              
                if (c % 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 2][k] == k && tf[r][c - 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello5");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println(r+ " "+c);
                    //System.out.println("  hello6");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          } 
          
          if (countA[k] == 0 && countB[k] != 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(j, i);
              int c = getGTtoC(j, i);
              if (r / 3 == 1) {
                if (c % 3 ==0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello7");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 2][k] == k && tf[r][c + 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello8");
                  }
                }
                
                if (c % 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello9");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello10");
                  }
                }
                
                if (c % 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 2][k] == k && tf[r][c - 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello11");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello12");
                  }
                }
              }
            }
            
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] == 0 && countC[k] != 0) {
            for (j = 0; j < 9; j++) {
              int r = getGTtoR(j, i);
              int c = getGTtoC(j, i);
              if (r / 3 == 2) {
                if (c % 3 == 0) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello13");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 2][k] == k && tf[r][c + 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello14");
                  }
                }
                
                if (c % 3 == 1) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c + 1][k] == k && tf[r][c + 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c + 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello15");
                  }
                  
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello16");
                  }
                }
              
                if (c % 3 == 2) {
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 2][k] == k && tf[r][c - 2].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 2][k] = -1;
                    flag = 1;
                    //System.out.println("  hello17");
                  }
                
                  if (VACandidate[r][c][k] == k && tf[r][c].getDisabledTextColor() != Color.BLACK && VACandidate[r][c - 1][k] == k && tf[r][c - 1].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[r][c - 1][k] = -1;
                    flag = 1;
                    //System.out.println("  hello18");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
        }
      }
      return false;
    }

    public boolean BresR(ActionEvent arg0) {
      int i = 0; 
      int j = 0;
      int k = 0;
      int[] countA = new int[9];
      int[] countB = new int[9];
      int[] countC = new int[9];

      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          countA[k] = 0;
          countB[k] = 0;
          countC[k] = 0;
        }
        
        for (j = 0;j < 9; j++) {
          if (tf[i][j].getDisabledTextColor() != Color.BLACK) { //[r][c]
            if (j / 3 == 0) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countA[k]++;
                }
              }
            } else if (j / 3 == 1) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countB[k]++;
                }
              }
            } else {
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countC[k]++;
                }
              }
            }
          }
        }
        
        for (k = 0; k < 9; k++) {
          /*
           * int r = getGTtoR(i, j);
           * //int c = getGTtoC(i, j);
           * System.out.println("r:"+r+" c:"+c);
           * System.out.println("i:"+i+" j:"+j);
           * System.out.println(i);
           * System.out.println(i);
           * int a;
           * System.out.print("  A: ");
           * for (a=0;a<9;a++)
           * System.out.print(countA[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  B: ");
           * for (a=0;a<9;a++)
           * System.out.print(countB[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  C: ");
           * for (a=0;a<9;a++)
           * System.out.print(countC[a]+ " ");
           * System.out.println('\n');
           * */

          int flag = 0;
          if (countA[k] != 0 && countB[k] == 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              //System.out.println(i+ "" +j);
              //if (j/3==0)
              {
                if (i % 3 == 0) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 2][j][k] == k && tf[i + 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 2][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello2");
                  }
                }
                
                if (i % 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello3");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello4");
                  }
                }
                
                if (i % 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 2][j][k] == k && tf[i - 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 2][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello5");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello6");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] == 0 && countC[k] != 0) {
            for (j = 0; j < 9; j++) {
              //if (j/3==1)
              {
                if (i % 3 == 0) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello7");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 2][j][k] == k && tf[i + 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 2][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello8");
                  }
                }
                
                if (i % 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello9");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello10");
                  }
                }
                
                if (i % 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 2][j][k] == k && tf[i - 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 2][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello11");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello12");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] != 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              //if (j/3==2)
              {
                if (i % 3 == 0) {
                  //System.out.println("hello13");
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println(i+ " "+j);
                    //System.out.println("  hello13");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 2][j][k] == k && tf[i + 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 2][j][k] = -1;
                    flag = 1;
                    //System.out.println(i+ " "+j);
                    //System.out.println("  hello14");
                  }
                }
                
                if (i % 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 1][j][k] == k && tf[i + 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello15");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello16");
                  }
                }

                if (i % 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 2][j][k] == k && tf[i - 2][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 2][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello17");
                  }
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 1][j][k] == k && tf[i - 1][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 1][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello18");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
        }
      }
      return false;
    }

    public boolean BresC(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int[] countA = new int[9];
      int[] countB = new int[9];
      int[] countC = new int[9];

      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          countA[k] = 0;
          countB[k] = 0;
          countC[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          if (tf[i][j].getDisabledTextColor() != Color.BLACK) { //[r][c]
            if (j % 3 == 0) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countA[k]++;
                }
              }
            } else if (j % 3 == 1) { //[c]/3
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countB[k]++;
                }
              }
            } else {
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) {
                  countC[k]++;
                }
              }
            }
          }
        }
        
        for (k = 0; k < 9; k++) {
          /*
           * int r = getGTtoR(i, j);
           * int c = getGTtoC(i, j);
           * System.out.println("r:"+r+" c:"+c);
           * System.out.println("i:"+i+" j:"+j);
           * System.out.println(i);
           * System.out.println(i);
           * int a;
           * System.out.print("  A: ");
           * for (a=0;a<9;a++)
           * System.out.print(countA[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  B: ");
           * for (a=0;a<9;a++)
           * System.out.print(countB[a]+ " ");
           * System.out.println('\n');
           * System.out.print("  C: ");
           * for (a=0;a<9;a++)
           * System.out.print(countC[a]+ " ");
           * System.out.println('\n');
           * */

          int flag = 0;
          if (countA[k] != 0 && countB[k] == 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              //System.out.println(i+ "" +j);
              //if (j/3==0)
              {
                if (i / 3 == 0) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 6][j][k] == k && tf[i + 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 6][j][k] == k && tf[i - 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] == 0 && countC[k] != 0) {
            for (j = 0; j < 9; j++) {
              //if (j/3==1)
              {
                if (i / 3 == 0) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 6][j][k] == k && tf[i + 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 6][j][k] == k && tf[i - 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
          
          if (countA[k] == 0 && countB[k] != 0 && countC[k] == 0) {
            for (j = 0; j < 9; j++) {
              //if (j/3==2)
              {
                if (i / 3 == 0) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 6][j][k] == k && tf[i + 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 1) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i + 3][j][k] == k && tf[i + 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i + 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
                
                if (i / 3 == 2) {
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 6][j][k] == k && tf[i - 6][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 6][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                  
                  if (VACandidate[i][j][k] == k && tf[i][j].getDisabledTextColor() != Color.BLACK && VACandidate[i - 3][j][k] == k && tf[i - 3][j].getDisabledTextColor() != Color.BLACK) {
                    VACandidate[i - 3][j][k] = -1;
                    flag = 1;
                    //System.out.println("  hello1");
                  }
                }
              }
            }
            if (flag == 1) {
              return true;
            }
          }
        }
      }
      return false;
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (RresB(arg0)) {
        //System.out.println("hello1");
        return;
      } else if (CresB(arg0)) {
        //System.out.println("hello2");
        return;
      } else if (BresR(arg0)) {
        //System.out.println("hello3");
        return;
      } else if (BresC(arg0)) {
        //System.out.println("hello4");
        return;
      } else {
        //System.out.println("hello5");
        return;
      }
    }
  }
    
  protected class MenuItemHintsNaked implements ActionListener {
    public boolean GroupCheck(ActionEvent arg0) {
      int i = 0;
      int j = 0; 
      int k = 0;
      int[] count = new int[9];
      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          count[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          if (tf[i][j].getDisabledTextColor() != Color.BLACK) { //[r][c] 
            for (k = 0; k < 9; k++) {
              if (VACandidate[i][j][k] > -1) {
                count[j]++;// = (int) Math.pow(10, k) * k;
              }
            }
          }
        }
        
        int[] compare = new int[9];
        
        for (k = 0; k < 9; k++) {
          compare[k] = 0;
        }
        
        for (j = 0; j < 9; j++) {
          {
            int count2 = 0;
            if (count[j] == 2) {
              for (k = 0; k < 9; k++) {
                if (VACandidate[i][j][k] > -1) { //[r][c] 
                  compare[j] = compare[j] + k * ((int) Math.pow(10, count2));
                  count2++;
                }
              }
            }
          }
        }
        
        int flag = 0;
        for (j = 0; j < 9; j++) {
          for (k = 0; k < 9; k++) {
            if (compare[j] == compare[k] && compare[j] > 0 && j != k) {
              //System.out.println("-->I:"+i+" j:"+j +" k:"+k);
              //System.out.println("c: "+compare[j] +" "+compare[k]);
              int a = compare[j] / 10;
              int b = compare[j] - a * 10;
              int c = 0;
              int d = 0;
              for (c = 0; c < 9; c++) {
                for (d = 0; d < 9; d++) {
                  if ((VACandidate[i][c][d] == a || VACandidate[i][c][d] == b) && (c != j && c != k)) {
                    VACandidate[i][c][d] = -1;
                  }
                }
              }
              
              flag = 1;
              break;
            }
          }
        }
        if (flag == 1) {
          return true;
        }
      }
      return false;
    }

    public boolean RowCheck(ActionEvent arg0) {
      int i = 0; 
      int j = 0;
      int k = 0;
      int[] count = new int[9];
      
      for (i = 0; i < 9; i++) {
        {
          for (k = 0; k < 9; k++) {
            count[k] = 0;
          }
          for (j = 0; j < 9; j++) {
            int row = getGTtoR(i, j);
            int col = getGTtoC(i, j);
            if (tf[row][col].getDisabledTextColor() != Color.BLACK) { //[r][c]
              for (k = 0; k < 9; k++) {
                if (VACandidate[row][col][k] > -1) { //[r][c]
                  int colj = getRCtoT(row, col);
                  count[colj]++;// = (int) Math.pow(10, k) * k;
                }
              }
            }
          }
          
          int[] compare = new int[9];
          //System.out.print("-->");
          for (k = 0; k < 9; k++) {
            compare[k] = 0;
          }
          
          System.out.println();
          for (j = 0; j < 9; j++) {
            int row = getGTtoR(i, j);
            int col = getGTtoC(i, j);
            int count2 = 0;
            if (count[j] == 2) {
              for (k = 0; k < 9; k++) {
                if (VACandidate[row][col][k] > -1) { //[r][c]
                  compare[col] = compare[col] + k * ((int) Math.pow(10, count2));
                  count2++;
                }
              }
            }
          }
          
          int flag = 0;
          for (j = 0; j < 9; j++) {
            for (k = 0; k < 9; k++) {
              if (compare[j] == compare[k] && compare[j] > 0 && j != k) {
                //System.out.println("-->r:"+row+" c:"+col +" k:"+k);
                //System.out.println("-->I:"+i+" j:"+j +" k:"+k);
                //System.out.println("c: "+compare[j] +" "+compare[k]);
                //System.out.println("c: "+compare[col] +" "+compare[k]);
                int a = compare[j] / 10;
                int b = compare[j] - a * 10;
                //System.out.println("aj: "+a +" bj:"+b);
                //System.out.println("ac: "+a +" bc:"+b);
                int c = 0;
                int d = 0;
                
                for (c = 0; c < 9; c++) {
                  int row2 = getGTtoR(i, c);
                  int col2 = getGTtoC(i, c);
                  for (d = 0; d < 9; d++) {
                    if ((VACandidate[i][c][d] == a || VACandidate[i][c][d] == b) && (c != j && c != k)) {
                      VACandidate[row2][col2][d] = -1;
                    }
                  }
                }
                flag = 1;
                break;
              }
            }
            if (flag == 1) {
              return true;
            }
          }
        }
      }
      return false;
    }
    
    public boolean ColumnCheck(ActionEvent arg0) {
      int i = 0;
      int j = 0;
      int k = 0;
      int[] count = new int[9];
      
      for (i = 0; i < 9; i++) {
        for (k = 0; k < 9; k++) {
          count[k] = 0;
        }
        for (j = 0; j < 9; j++) {
          int row = getGTtoR(j, i);
          int col = getGTtoC(j, i);
          
          if (tf[row][col].getDisabledTextColor() != Color.BLACK) { //[r][c]
            for (k = 0; k < 9; k++) {
              if (VACandidate[row][col][k] > -1) {//[r][c]
                int colj = getRCtoG(row, col);
                //System.out.print(colj+ " ");
                count[colj]++;// = (int) Math.pow(10, k) * k;
              }
            }
          }
        }
        
        //System.out.println();
        //for (k=0; k<9; k++)
        //System.out.print(k+ " ");
        //System.out.println();
        //for (k=0; k<9; k++)
        //System.out.print(count[k]+ " ");
        //System.out.println();
        int[] compare = new int[9];
        //System.out.print("-->");
        for (k = 0; k < 9; k++) {
          compare[k] = 0;
        }
        //System.out.println();
        for (j = 0; j < 9; j++) {
          int row = getGTtoR(j, i);
          int col = getGTtoC(j, i);
          int count2 = 0;
          if (count[j] == 2) {
            for (k = 0; k < 9; k++) {
              if (VACandidate[row][col][k] > -1) { //[r][c]
                compare[col] = compare[col] + k * ((int) Math.pow(10, count2));
                count2++;
              }
            }
          }
        }
        
        //for (k=0; k<9; k++)
        //System.out.print(compare[k]);
        //System.out.println();
        
        int flag = 0;
        for (j = 0; j < 9; j++) {
          for (k = 0; k < 9; k++) {
            if (compare[j] == compare[k] && compare[j] > 0 && j != k) {
              //System.out.println("-->r:"+row+" c:"+col +" k:"+k);
              //System.out.println("c: "+compare[col] +" "+compare[k]);
              int a = compare[j] / 10;
              int b = compare[j] - a * 10;
              //System.out.println("aj: "+a +" bj:"+b);
              //System.out.println("ac: "+a +" bc:"+b);
              int c = 0;
              int d = 0;
              for (c = 0; c < 9; c++) {
                int row2 = getGTtoR(c, i);
                int col2 = getGTtoC(c, i);
                for (d = 0; d < 9; d++) {
                  if ((VACandidate[row2][col2][d] == a || VACandidate[row2][col2][d] == b) && (col2 != getGTtoR(j,i) && col2 != getGTtoR(k,i))) {
                    VACandidate[row2][col2][d] = -1;
                  }
                }
              }
              flag = 1;
              break;
            }
          }
          if (flag == 1) {
            return true;
          }
        }
      }
      return false;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
      if (GroupCheck(arg0)) {
        return;
      } else if (RowCheck(arg0)) {
        return;
      } else if (ColumnCheck(arg0)) {
        return;
      } else {
        return;
      }
    }
  }
  
  protected class MenuItemHintsAll implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent arg0) {
      int i = 0;
      
      MenuItemHintsSingle one = new MenuItemHintsSingle();
      MenuItemHintsHidden two = new MenuItemHintsHidden();
      MenuItemHintsLocked three = new MenuItemHintsLocked();
      MenuItemHintsNaked four = new MenuItemHintsNaked();

      while (i < 250) {
        four.actionPerformed(arg0);
        three.actionPerformed(arg0);
        two.actionPerformed(arg0);
        one.actionPerformed(arg0);

        //if (boardDidNotChange())
        //i = 1;
        i++;
      }
      
      return;
    }
  }
    
  public static void main(String[] args) {
    Sudoku s = new Sudoku();
    s.createWindow();
  }
}
