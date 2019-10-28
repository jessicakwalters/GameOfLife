package csc143.test.gol;

/**
 * This is the sample test program for the initial Game of Life
 * implementation. It is set up to test on a 8x8 checker board or
 * a Go board with the origin at the center.
 */
public class TestAdaptiveImplementation {

    /**
     * Set the initial pattern of ALIVE cells
     * @param board The GameOfLife board to update
     */
    public static void setBoard(csc143.gol.GameOfLife board) {
        // the blinker in the upper left
        board.setCellState(1, 1, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(2, 1, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(3, 1, csc143.gol.GameOfLife.ALIVE);
        // the block in the upper right
        board.setCellState(1, 5, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(1, 6, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(2, 5, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(2, 6, csc143.gol.GameOfLife.ALIVE);
        // the glider in the lower left
        board.setCellState(5, 4, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(6, 2, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(6, 4, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(7, 3, csc143.gol.GameOfLife.ALIVE);
        board.setCellState(7, 4, csc143.gol.GameOfLife.ALIVE);
    }
    
    /**
     * The driver for this test case
     * @param args The command-line arguments
     */
    public static void main(String[] args) {
        csc143.gol.GameOfLife life = null;
        life = new csc143.gol.MyGameOfLife();
        setBoard(life);
        System.out.println("Starting point:");
        System.out.println(life.toString());
        System.out.println();
        life.nextGeneration();
        System.out.println("First Generation:");
        System.out.println(life);
        System.out.println();
        life.nextGeneration();
        System.out.println("Second Generation:");
        System.out.println(life);
        System.out.println();
    }

}