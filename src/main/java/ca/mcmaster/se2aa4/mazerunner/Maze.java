package ca.mcmaster.se2aa4.mazerunner;

public class Maze {
    private char[][] mazeDesign;

    public Maze(char[][] mazeStructure) {
        this.mazeDesign = mazeStructure;
    }

    public char[][] getMazeStructure() {
        return mazeDesign;
    }

    public void showMaze() {
        for (char[] row : mazeDesign) {
            for (char cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}
