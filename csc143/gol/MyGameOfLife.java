package csc143.gol;

import java.beans.*;

/**
 * MyGameOfLife implements GameOfLife
 *
 * @author Jessica Walters
 * @version Programming Assignment 1: Life, Next Generation | Challenge Level
 */
public class MyGameOfLife implements GameOfLife
{
    // the current board
    private int[][] board;
    //the new board for each new generation
    private int[][] newBoard;
    //array to allow for updating of board
    private int [][] temp;
    //an array to help us check all of the possible neighbors of a given cell
    private final int[][] NEIGHBORINGCELLS = {{-1, -1},{-1, 0},{-1, 1},{0, -1},{ 0, 1},{1, -1},{1, 0},{1, 1}};
    PropertyChangeSupport pcs;
    //private int gpm = 120;
    /**
     * Constructor for objects of class MyGameOfLife
     */
    public MyGameOfLife()
    {
        this.board = new int [ROW_COUNT][COLUMN_COUNT];
        this.newBoard = new int [ROW_COUNT][COLUMN_COUNT];
        this.pcs = new PropertyChangeSupport(this);
    }
    // public void setGPM(int n){
        // this.gpm = n;
        // pcs.firePropertyChange("board", 0, 1);
    // }
    // public int getGPM(){
        // return this.gpm;
    // }
    /**
     * Gets the current state of a given sell.
     * @param row The row number of the given cell, 1 - 19
     * @param col The column number of the given cell, 1 - 19
     * @return The current state of the given cell
     */
    public int getCellState(int row, int col)
    {
        if(row < 1 || row > ROW_COUNT){
            throw new IllegalArgumentException("Row is out of bounds");
        }
        if(col < 1 || col > COLUMN_COUNT){
            throw new IllegalArgumentException("Column is out of bounds");
        }
        return this.board[row - 1][col - 1];
    }

    /**
     * Sets the state of the given sell to the given state
     * value.
     * @param row The row number of the given cell, 1 - 19
     * @param col The column number of the given cell, 1 - 19
     * @param state The new state for the given cell.
     */
    public void setCellState(int row, int col, int state){
        if(row < 1 || row > ROW_COUNT){
            throw new IllegalArgumentException("Row is out of bounds");
        }
        if(col < 1 || col > COLUMN_COUNT){
            throw new IllegalArgumentException("Column is out of bounds");
        }
        if(state != GameOfLife.ALIVE && state != GameOfLife.DEAD){
            throw new IllegalArgumentException("State must be 0 or 1");
        }
        this.board[row - 1][col - 1] = state;
        pcs.firePropertyChange("board", 0, 1);
    }

    /**
     * Checks to see if a neighboring cell actually exists
     * 
     * @param row The row number of the given cell, 0 - 18
     * @param col The column number of the given cell, 0 - 18
     * @return true if the neighbor exists and false if it doesn't
     */
    public boolean doesCellExist(int row, int col){
        //a cell exists if it fits in the bounds of the board array
        return row>= 0 && col >= 0 && row < this.board.length && col < this.board[row].length;
    }

    /**
     * Finds the number of alive neighbors to a given cell
     * 
     * @param row The row number of the given cell, 0 - 18
     * @param col The column number of the given cell, 0 - 18
     * @return the number of alive neighbors the current cell has
     */
    public int aliveNeighbors(int row, int col){
        //tracks the number of alive neighbors to our cell
        int numAliveNeighbors = 0;
        //for each loop iterates through the NEIGHBORINGCELLS array and checks each one. If the neighbor exists
        //and is alive, then increment numAliveNeighbors
        for (int[] position : NEIGHBORINGCELLS) {
            if (doesCellExist(row + position[0], col + position[1]) && board[row + position[0]][col + position[1]] == 1){
                numAliveNeighbors++;
            }
        }
        return numAliveNeighbors;
    }

    /**
     * this method creates the next iteration of the board according to the rules
     * of GameOfLife
     *
     */
    public void nextGeneration() {
        //holds the number of alive neighbors for a given cell
        int aliveNeighbors = 0;
        //check each cell, calculate number of alive neighbors, set the cell's value for the next generation
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                //find the number of alive neighbors
                aliveNeighbors = aliveNeighbors(i,j);
                //depending on the state of the current cell, set the state of the cell on the new board
                if (board[i][j] == 0) {
                    if (aliveNeighbors == 3){
                        //set the current cell in the new board to alive
                        newBoard[i][j] = 1;
                    } else {
                        //set the current cell in the new board to dead
                        newBoard[i][j] = 0;
                    }
                } else if (board[i][j] == 1){
                    if (aliveNeighbors > 1 && aliveNeighbors < 4) {
                        //set the current cell in the new board to alive
                        newBoard[i][j] = 1;
                    } else {
                        //set the current cell in the new board to dead
                        newBoard[i][j] = 0;
                    } 
                }
                //reset the value of aliveNeighbors to 0 for a fresh start on the next cell
                aliveNeighbors = 0;
            }
        }
        //swap out the old board and replace it with the new one
        temp = board;
        board = newBoard;
        newBoard = temp;
        pcs.firePropertyChange("board", 0, 1);
    }

    /**
     * Opens and reads a file of data for a GOL board
     * 
     * @param filename the name of the file
     * @return
     */
    public void fileOpen(String filename) throws java.io.IOException { 
        java.io.FileInputStream fis = new java.io.FileInputStream(filename);
        java.io.DataInputStream dis = new java.io.DataInputStream(fis);
        for (int i = 0; i< board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                board[i][j] = dis.readInt();
            }
        }
        dis.close();
        pcs.firePropertyChange("board", 0, 1);
    }
    /**
     * Opens and reads a file of data for a GOL board
     * 
     * @param file a file
     * @return
     */
    public void fileOpen(java.io.File file) throws java.io.IOException { 
        java.io.FileInputStream fis = new java.io.FileInputStream(file);
        java.io.DataInputStream dis = new java.io.DataInputStream(fis);
        for (int i = 0; i< board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                board[i][j] = dis.readInt();
            }
        }
        dis.close();
        pcs.firePropertyChange("board", 0, 1);
    }
    /**
     * Outputs and saves to a file the data for a GOL board
     * 
     * @param filename the name of the file
     * @return
     */
    public void fileSave(String filename) throws java.io.IOException {
        java.io.FileOutputStream fos;
        fos = new java.io.FileOutputStream(filename);
        java.io.DataOutputStream dos;
        dos = new java.io.DataOutputStream(fos);

        for (int i = 0; i< board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                dos.writeInt(board[i][j]);
            }
        }

        fos.close();
    }
    /**
     * Outputs and saves to a file the data for a GOL board
     * 
     * @param file a file
     * @return
     */
    public void fileSave(java.io.File file) throws java.io.IOException {
        java.io.FileOutputStream fos;
        fos = new java.io.FileOutputStream(file);
        java.io.DataOutputStream dos;
        dos = new java.io.DataOutputStream(fos);

        for (int i = 0; i< board.length; i++){
            for(int j = 0; j<board[i].length; j++){
                dos.writeInt(board[i][j]);
            }
        }

        fos.close();
    }
    /**
     * returns the GameOfLife Board represented as a series of "." and "O"
     *
     * @param  
     * @return the GameOfLife Board represented as a series of "."s and "O"s.
     */
    public String toString(){
        String printedBoard= "";
        for(int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0){
                    printedBoard += ". ";
                } else {
                    printedBoard += "O ";
                }
            }
            printedBoard += "\n";
        }
        return printedBoard;
    }
    public void addChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }
}
