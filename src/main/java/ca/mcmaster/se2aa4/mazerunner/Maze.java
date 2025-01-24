/* Author: Naqeeb Ahmadzan.
*  Represents the maze structure as a 2D grid. 
*  This file is part of Step 2, where the Maze object abstraction is 
*  created to support the exploration. It stores the maze layout and 
*  provides methods to access and manipulate the maze. This abstraction 
*  ensures a clear separation of responsibilities, focusing on the maze's 
*  representation.
*/ 


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
