/* Author: Naqeeb Ahmadzan.
 * Represents the player navigating the maze.
 * As part of Step 2, this abstraction was created to simulate a runner 
 * exploring the maze. It tracks the current position of the runner and 
 * provides methods to move through the maze. This ensures that navigation 
 * logic is isolated and can be expanded later.
 */ 


package ca.mcmaster.se2aa4.mazerunner;

public class Runner {
    private int currentRow;
    private int currentCol;

    public Runner(int startRow, int startCol) {
        this.currentRow = startRow;
        this.currentCol = startCol;
    }

    public void moveUp() {
        currentRow--;
    }

    public void moveDown() {
        currentRow++;
    }

    public void moveLeft() {
        currentCol--;
    }

    public void moveRight() {
        currentCol++;
    }

    public String getCurrentPosition() {
        return "(" + currentRow + ", " + currentCol + ")";
    }
}
