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
