package csc143.gol;

import java.awt.Graphics;
import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Creates a GUI for My Game of Life
 *
 * @author Jessica Walters
 * @version PA2: Game of Life, Graphical Interface | Standard level
 */
public class GameOfLifeBoard extends javax.swing.JPanel
{
    //GameOfLife variable
    private static GameOfLife game;

    /**
     * Constructor for objects of class GameOfLifeBoard
     */
    public GameOfLifeBoard(GameOfLife game)
    {
        setPreferredSize(new java.awt.Dimension(GameOfLife.ROW_COUNT*25, GameOfLife.COLUMN_COUNT*25));
        this.game = game;
    }

    /**
     * Draws the game of life board according to cell state
     * 
     * @param
     * @return
     */
    public void drawBoard() {
        //for each cell, check if it's alive or dead and render add 
        //the corresponding component to the board
        for (int i = 1; i <= GameOfLife.ROW_COUNT; i++) {
            for (int j = 1; j <= GameOfLife.COLUMN_COUNT; j++){
                if(game.getCellState(i,j) == 0) {
                    this.add(new DeadCell());
                } else if (game.getCellState(i,j) == 1) {
                    this.add(new AliveCell());
                }

            }
        }
    }

    /**
     * Sets values in a GameOfLifeBoard
     * @param board a GameOfLifeBoard, must be 17 by 17 or larger
     * @return 
     */
    public static void setBoard(GameOfLife board) {
        // the blinker in the upper left
        board.setCellState(1, 1, GameOfLife.ALIVE);
        board.setCellState(2, 1, GameOfLife.ALIVE);
        board.setCellState(3, 1, GameOfLife.ALIVE);
        // the block in the upper right
        board.setCellState(1, 5, GameOfLife.ALIVE);
        board.setCellState(1, 6, GameOfLife.ALIVE);
        board.setCellState(2, 5, GameOfLife.ALIVE);
        board.setCellState(2, 6, GameOfLife.ALIVE);
        // the glider in the lower left
        board.setCellState(5, 4, GameOfLife.ALIVE);
        board.setCellState(6, 2, GameOfLife.ALIVE);
        board.setCellState(6, 4, GameOfLife.ALIVE);
        board.setCellState(7, 3, GameOfLife.ALIVE);
        board.setCellState(7, 4, GameOfLife.ALIVE);
    
    }

    /**
     * main method to kick off the application and create the GUI
     * 
     * @param args The command-line arguments,ignored
     */
    public static void main(String[] args) {

        // setup the window
        JFrame win = new JFrame("Game Of Life");
        win.setSize(500, 500);
        win.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        javax.swing.JFrame.setDefaultLookAndFeelDecorated( true );

        //instatiate GOL object
        GameOfLife testgame = new MyGameOfLife();

        //create Board
        GameOfLifeBoard board = new GameOfLifeBoard(testgame);

        //create  JPanel
        javax.swing.JPanel panel = new javax.swing.JPanel();

        //create grid layout
        java.awt.Container c = win.getContentPane();
        board.setLayout(new java.awt.GridLayout(GameOfLife.ROW_COUNT, GameOfLife.COLUMN_COUNT, 0, 0));

        //logic to construct the board
        board.drawBoard();

        //add the board to the JPanel
        panel.add(board);

        //add the panel to the container
        c.add(panel);

        //Create buttons
        javax.swing.JButton b1 = new javax.swing.JButton("Next Generation");
        javax.swing.JButton b2 = new javax.swing.JButton("Starting Point");

        //create toolbar
        javax.swing.JPanel toolbar = new javax.swing.JPanel();

        //add buttons to toolbar
        toolbar.add(b2);
        toolbar.add(b1);

        //add the toolbar to the container
        win.add(toolbar, java.awt.BorderLayout.SOUTH);
        //add handler to Next Generation Button
        b1.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                    //clear the board
                    board.removeAll();
                    //advance the game
                    game.nextGeneration();
                    //redraw the board
                    board.drawBoard();
                    //add visuals back to the panel
                    panel.repaint();
                    panel.revalidate();
                }
            });
        b2.addActionListener (new ActionListener() 
            {
                @Override public void actionPerformed(ActionEvent e) {
                    board.removeAll();
                    //set board to default state
                    for (int i = 1; i <= GameOfLife.ROW_COUNT; i++) {
                        for (int j = 1; j <= GameOfLife.COLUMN_COUNT; j++){
                            game.setCellState(i,j,0);
                        }
                    }
                    //set the board
                    setBoard(testgame);
                    //redraw the board
                    board.drawBoard();
                    //add visuals back to the panel
                    panel.repaint();
                    panel.revalidate();
                }
            });

        // show the window at the preferred size
        win.setVisible(true);
        win.pack();
    }

    class DeadCell extends javax.swing.JComponent {
        /**
         * Constructor, sets the preferred size.
         */
        public DeadCell() {
            setPreferredSize(new java.awt.Dimension(25, 25));
        }

        /**
         * The necessary method. This method renders the component.
         * 
         * @param g The Graphics object use to render
         */
        @Override
        public void paintComponent(java.awt.Graphics g) {
            // paint the underlying component
            super.paintComponent(g);
            //set line color
            g.setColor(java.awt.Color.BLACK);
            // draw rectangle
            g.drawRect(0, 0, 25, 25);
        }
    }
    class AliveCell extends javax.swing.JComponent {
        /**
         * Constructor, sets the preferred size.
         */
        public AliveCell() {
            setPreferredSize(new java.awt.Dimension(25, 25));
        }

        /**
         * The necessary method. This method renders the component.
         * 
         * @param g The Graphics object use to render
         */
        @Override
        public void paintComponent(java.awt.Graphics g) {
            // paint the underlying component
            super.paintComponent(g);
            //set line color
            g.setColor(java.awt.Color.BLACK);
            // draw rectangle
            g.drawRect(0, 0, 25, 25);
            //draw inner oval
            g.fillOval(5, 5, 15, 15);
        }
    }
}
