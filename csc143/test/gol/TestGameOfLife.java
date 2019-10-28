package csc143.test.gol;

import javax.swing.JOptionPane;
/**
 * Testcsc143.gol.GameOfLife creates an example board for csc143.gol.GameOfLife
 *
 * @author Updated By: Jessica Walters 
 * @version Programming Assignment 1: Life, Next Generation | Challenge Level
 */

public class TestGameOfLife {

    /**
     * Sets values in a csc143.gol.GameOfLifeBoard
     * @param board, a csc143.gol.GameOfLifeBoard, must be 17 by 17 or larger
     * @return 
     */
    public static void setBoard(csc143.gol.GameOfLife board) {
        // the two alive cells in the upper left
        board.setCellState(3, 4, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(4, 4, csc143.gol.GameOfLife.ALIVE);
        // the block in the upper right
        board.setCellState(3, 13, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(3, 14, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(4, 13, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(4, 14, csc143.gol.GameOfLife.ALIVE);
        // the beehive in the center
        board.setCellState(8, 7, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(8, 8, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(9, 6, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(9, 9, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(10, 7, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(10, 8, csc143.gol.GameOfLife.ALIVE);
        // the glider in the lower left
        board.setCellState(15, 6, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(16, 4, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(16, 6, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(17, 5, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(17, 6, csc143.gol.GameOfLife.ALIVE);
        // the blinker in the lower right
        board.setCellState(13, 13, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(13, 14, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(13, 15, csc143.gol.GameOfLife.ALIVE);
    }

    /**
     * Main method which triggers the creation and value population of a csc143.gol.GameOfLife board.and prints the boards to the terminal window
     * @param args an array of strings, though the program doesn't "do" anything with these parameters
     * @return 
     */
    public static void main(String[] args) {
        //create and initialize new csc143.gol.GameOfLife
        csc143.gol.GameOfLife life = null;
        life = new csc143.gol.MyGameOfLife();
        //set the board with initial values
        setBoard(life);
    
        //System.out.println("Starting point:");
        //System.out.println(life.toString());
        //System.out.println();
        //print out 7 generations of the board with a dialog box message in between each generation
        for (int i = 0; i < 7; i++) {
            System.out.println("Generation " + i + ":");
            System.out.println(life);
            System.out.println();
            //create the next generation
            life.nextGeneration();
            //show alert message
            JOptionPane.showMessageDialog(null, "Click OK to continue");
        }
    }

} 