package csc143.gol;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;

import javax.swing.Action;
import javax.swing.AbstractAction;
import java.beans.*;

/**
 * Creates a GUI for My Game of Life
 *
 * @author Jessica Walters
 * @version PA8: Game of Life, Animated Game | Standard level
 */
public class GameOfLifeBoard extends JPanel implements PropertyChangeListener
{
    //GameOfLife variable
    private static MyGameOfLife game;
    JPanel theBoard;
    public boolean running = false;
    private Thread animationThread;
    private int sleepTime = 120;
   
    /**
     * Constructor for objects of class GameOfLifeBoard
     */
    public GameOfLifeBoard(MyGameOfLife game)
    {
        theBoard = new JPanel(new java.awt.GridLayout(GameOfLife.ROW_COUNT, GameOfLife.COLUMN_COUNT, 1, 1));
        setPreferredSize(new java.awt.Dimension(GameOfLife.ROW_COUNT*25, GameOfLife.COLUMN_COUNT*25));
        theBoard.setBackground(java.awt.Color.black);
        setLayout(new java.awt.BorderLayout());
        theBoard.setBorder(BorderFactory.createLineBorder(java.awt.Color.black, 1));
        add(theBoard);

        this.game = game;
        game.addChangeListener(this);

        //JPanel panel = this;

        for (int i = 1; i <= GameOfLife.ROW_COUNT; i++) {
            for (int j = 1; j <= GameOfLife.COLUMN_COUNT; j++){
                final int row = i;
                final int col = j;
                Space space = new Space(i, j, game);
                space.addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e) {
                            if(game.getCellState(row, col) == 0){
                                game.setCellState(row, col, 1);
                            } else {
                                game.setCellState(row, col, 0);
                            }
                        }
                    });
                theBoard.add(space);

            }
        }
        
        // setup the window
        JFrame win = new JFrame("Animated Game Of Life");
        win.setSize(600, 600);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JFrame.setDefaultLookAndFeelDecorated( true );
        win.addWindowListener(new WindowAdapter() {

        @Override
        public void windowClosed(WindowEvent e) {
            running = false;
        }
    });
        JPanel p = new JPanel();
        JPanel p2 = new JPanel(new java.awt.BorderLayout());
        p2.add(this);
        
        //Create Start Animation Button
        JButton startA = new JButton("Start Animation");
        startA.setActionCommand("start");

        //Create plus Button
        JButton plus = new JButton("+");

        //Create minus Button
        JButton minus = new JButton("-");

        //add generations per minute label
        JLabel gens = new JLabel("Generations per minute");
        JTextField num = new JTextField(Integer.toString(sleepTime));
        //create toolbar
        JPanel toolbar = new JPanel();

        //add handler that starts animation
        startA.addActionListener (new ActionListener() {
                @Override public void actionPerformed(ActionEvent e) {
                    if(e.getActionCommand().equals("start")){
                        startA.setText("Stop Animation");
                        startA.setActionCommand("stop");
                        //create a new thread
                        animationThread = new Thread(new Runnable() {
                                public void run() {
                                    running = true;
                                    //clear the board
                                    while (running) {

                                        //advance the game
                                        game.nextGeneration();

                                        pause();
                                    }
                                }

                            });
                        animationThread.start();
                    } else if(e.getActionCommand().

                    equals("stop")){ 
                        running = false;
                        startA.setText("Start Animation");
                        startA.setActionCommand("start");
                    }
                } 
            });

        //add action listener to plus button
        plus.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    int n = sleepTime + 20;
                    
                    if(n > 500) {
                        java.awt.Toolkit t = java.awt.Toolkit.getDefaultToolkit();
                        t.beep();
                        sleepTime = 500;
                    } else {
                        sleepTime = n;
                    }
                    num.setText(Integer.toString(sleepTime));
                }                
            });

        //add action listener to minus button
        minus.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent e) {
                    int n = sleepTime - 20;
                    
                    if(n < 60) {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        sleepTime = 60;
                    } else {
                        sleepTime = n;
                    }
                    num.setText(Integer.toString(sleepTime));

                }                
            });

        //add event listener to text field
        num.addActionListener( new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String number = num.getText();
                    int newGpm = Integer.parseInt(number);
                    if(newGpm < 60 || newGpm > 500) {
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(null, "Generations per Minute must be between 60 and 500");
                        num.setText(Integer.toString(sleepTime));
                    } else {
                        sleepTime = newGpm;
                        num.setText(Integer.toString(sleepTime));
                    }

                }
            });

        //add buttons to toolbar
        //New row
        JPanel toolbar2 = new JPanel();
        toolbar.add(startA);
        toolbar2.add(gens);
        toolbar2.add(num);
        toolbar2.add(plus);
        toolbar2.add(minus);
        //add the toolbar to the container
        JPanel tools = new JPanel(new java.awt.GridLayout(2, 1));
        tools.add(toolbar);
        tools.add(toolbar2);
        p2.add(tools, java.awt.BorderLayout.SOUTH);
        //add the panel to the container
        p.add(p2);
        win.add(p);

        //Create the menu bar.
        JMenuBar menuBar = new JMenuBar();

        //create file chooser
        JFileChooser chooser = new JFileChooser();

        //add file filtering
        javax.swing.filechooser.FileNameExtensionFilter filter = new javax.swing.filechooser.FileNameExtensionFilter(".gol", "gol");
        chooser.setFileFilter(filter);

        //add file menu
        JMenu file = new JMenu("File");
        file.setMnemonic(java.awt.event.KeyEvent.VK_F);

        //add file menu to menu bar
        menuBar.add(file);

        //add open functionality to file
        JMenuItem open = new JMenuItem("Open", java.awt.event.KeyEvent.VK_O);
        open.addActionListener (new ActionListener()
            {
                @Override public void actionPerformed(ActionEvent e)  {
                    
                    //specify where to open the dialog
                    int returnVal = chooser.showOpenDialog(win);
                    //get the chosen file
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        java.io.File file = chooser.getSelectedFile();
                        try {
                            //run filOpen to read the file
                            game.fileOpen(file);     
                        }catch (java.io.IOException exception){
                            JOptionPane.showMessageDialog(null, "Error Opening File: " + chooser.getSelectedFile().getName(), "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                }
            });
        //add the open file functionality to the file menu
        file.add(open);

        //add save functionality to file
        JMenuItem save = new JMenuItem("Save",java.awt.event.KeyEvent.VK_S);

        save.addActionListener (new ActionListener()
            {
                @Override public void actionPerformed(ActionEvent e)  {
                    //set the location of where the Save Dialog opens
                    int returnVal = chooser.showSaveDialog(win);
                    //get the file chosen and compare against existing files
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        java.io.File file = chooser.getSelectedFile();
                        java.io.File existingFile = new java.io.File(chooser.getSelectedFile().toString());
                        if (existingFile.exists()) {
                            int response = JOptionPane.showConfirmDialog(null,"Overwrite file: " + file,"Yes", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if (response != JOptionPane.YES_OPTION) {
                                return;
                            } 
                        }
                        //if it doesn't repeat a file name, run save file 
                        try {
                            game.fileSave(file); 
                        }catch (java.io.IOException exception){
                            JOptionPane.showMessageDialog(null, "Error Saving File: " + chooser.getSelectedFile().getName(), "Message", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
        //add the save option to the menu
        file.add(save);

        //add the separator
        file.addSeparator();

        //add starting point to menu
        JMenuItem start = new JMenuItem("Starting Point",java.awt.event.KeyEvent.VK_B);

        start.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {

                    //set board to default state
                    for (int i = 1; i <= GameOfLife.ROW_COUNT; i++) {
                        for (int j = 1; j <= GameOfLife.COLUMN_COUNT; j++){
                            game.setCellState(i,j,0);
                        }
                    }
                    //set the board
                    setBoard(game);

                }
            });
        //add start to the file menu
        file.add(start);

        //add clear board to menu
        JMenuItem clear = new JMenuItem("Clear Board", java.awt.event.KeyEvent.VK_C);

        clear.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                    //set all cells to zero
                    for (int i = 1; i <= GameOfLife.ROW_COUNT; i++) {
                        for (int j = 1; j <= GameOfLife.COLUMN_COUNT; j++){
                            game.setCellState(i,j,0);
                        }
                    }

                }
            });
        //add clear to the file menu
        file.add(clear);

        //add Exit option
        JMenuItem exit = new JMenuItem("Exit Game of Life", java.awt.event.KeyEvent.VK_X);

        exit.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                    win.dispose();
                }
            });
        //add exit functionality to the file menu
        file.add(exit);
        
        //add file menu
        JMenu view = new JMenu("View");
        view.setMnemonic(java.awt.event.KeyEvent.VK_V);
        
        menuBar.add(view);
        
        //add AddView menu
        JMenuItem addView = new JMenuItem("Add View", java.awt.event.KeyEvent.VK_V);
        
        //add StartNew menu
        JMenuItem startNew = new JMenuItem("Start New", java.awt.event.KeyEvent.VK_N);
        
        addView.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                     GameOfLifeBoard newBoard = new GameOfLifeBoard(game);
                     win.setLocation(500, 0);
                     menuBar.setVisible(false);
                     tools.setVisible(false);
                }
            });
            
        startNew.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                     MyGameOfLife newGame = new MyGameOfLife();
                     GameOfLifeBoard board = new GameOfLifeBoard(newGame);
                     
                }
            });
        view.add(addView);
        view.add(startNew);
        //add the menu bar to the window
        win.add(menuBar, java.awt.BorderLayout.NORTH);
        // show the window at the preferred size
        win.setVisible(true);
        win.pack();
    }

    /**
     * Sets values in a GameOfLifeBoard
     * @param board a GameOfLifeBoard, must be 17 by 17 or larger
     * @return 
     */
    public static void setBoard(MyGameOfLife model) {
        // the two alive cells in the upper left
        model.setCellState(3, 4, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(4, 4, csc143.gol.GameOfLife.ALIVE);
        // the block in the upper right
        model.setCellState(3, 13, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(3, 14, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(4, 13, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(4, 14, csc143.gol.GameOfLife.ALIVE);
        // the beehive in the center
        model.setCellState(8, 7, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(8, 8, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(9, 6, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(9, 9, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(10, 7, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(10, 8, csc143.gol.GameOfLife.ALIVE);
        // the glider in the lower left
        model.setCellState(15, 6, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(16, 4, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(16, 6, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(17, 5, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(17, 6, csc143.gol.GameOfLife.ALIVE);
        // the blinker in the lower right
        model.setCellState(13, 13, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(13, 14, csc143.gol.GameOfLife.ALIVE);
        model.setCellState(13, 15, csc143.gol.GameOfLife.ALIVE);

    }

    private void pause() {
        try {
            // pause the thread for SLEEP secs to get out timer
            Thread.sleep(60000/sleepTime);
        } catch(InterruptedException e) {
            //do nothing
        }
    }

    public void propertyChange(PropertyChangeEvent e) {
        repaint();
        revalidate();
        
    }

    /**
     * main method to kick off the application and create the GUI
     * 
     * @param args The command-line arguments,ignored
     */
    public static void main(String[] args) {
        //check command line arguments
        if(args.length > 0) {
            try {
                String fileName = args[0];
                game.fileOpen(fileName);  
            }catch (java.io.IOException exception){
                JOptionPane.showMessageDialog(null, "Error Opening File: " + args[0], "Message", JOptionPane.ERROR_MESSAGE);
            }
        }
        //create MyGameOfLife
        MyGameOfLife model = new MyGameOfLife();

        //create Board
        GameOfLifeBoard board = new GameOfLifeBoard(model);
        
        
    }

}
class Space extends JPanel {
    public final int row;
    public final int col;
    public final MyGameOfLife game;

    public Space(int row, int col, MyGameOfLife game) {
        setPreferredSize(new java.awt.Dimension(25, 25));
        this.row = row;
        this.col = col;
        this.game = game;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint the underlying component
        super.paintComponent(g);
        //set line color
        g.setColor(java.awt.Color.BLACK);
        if(game.getCellState(row, col) == 1) {
            //draw inner oval
            g.fillOval(4, 4, 15, 15);
        }
    }


}

