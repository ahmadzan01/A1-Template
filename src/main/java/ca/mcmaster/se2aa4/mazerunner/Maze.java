/* Author: Naqeeb Ahmadzan.
*  Represents the maze structure as a 2D grid. 
*  This file is part of Step 2, where the Maze object abstraction is 
*  created to support the exploration. It stores the maze layout and 
*  provides methods to access and manipulate the maze. This abstraction 
*  ensures a clear separation of responsibilities, focusing on the maze's 
*  representation.
*/ 


package ca.mcmaster.se2aa4.mazerunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class Maze {
    private char[][] maze;
    private int startX, startY, exitX, exitY;

    // Constructor to initialize the maze
    public Maze(char[][] structure) {
        this.maze = structure;
        findEntryAndExit();
    }

    // Finds the entry and exit points
    private void findEntryAndExit() {
        for (int i = 0; i < maze.length; i++) {
            if (maze[i][0] == ' ') { // Entry at the left border
                startX = i;
                startY = 0;
            } else if (maze[i][maze[i].length - 1] == ' ') { // Exit at the right border
                exitX = i;
                exitY = maze[i].length - 1;
            }
        }
    }

    // Returns the start X position
    public int getStartX() {
        return startX;
    }

    // Returns the start Y position
    public int getStartY() {
        return startY;
    }

    // Checks if the given position is the exit
    public boolean isExit(int x, int y) {
        return x == exitX && y == exitY;
    }

    // Checks if the runner can move in a given direction
    public boolean canMove(int x, int y, char direction) {
        switch (direction) {
            case 'N': return x > 0 && maze[x - 1][y] == ' ';
            case 'S': return x < maze.length - 1 && maze[x + 1][y] == ' ';
            case 'E': return y < maze[0].length - 1 && maze[x][y + 1] == ' ';
            case 'W': return y > 0 && maze[x][y - 1] == ' ';
            default: return false;
        }
    }

    // Moves the runner and returns new coordinates
    public int[] move(int x, int y, char direction) {
        if (canMove(x, y, direction)) {
            switch (direction) {
                case 'N': return new int[]{x - 1, y};
                case 'S': return new int[]{x + 1, y};
                case 'E': return new int[]{x, y + 1};
                case 'W': return new int[]{x, y - 1};
            }
        }
        return new int[]{x, y}; // If movement is not possible, stay in place
    }

    // Reads a maze from a file
    public static char[][] readMazeFromFile(String filePath) throws IOException {
        return Files.lines(Paths.get(filePath))
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    // Displays the maze (for debugging)
    public void showMaze() {
        for (char[] row : maze) {
            System.out.println(new String(row));
        }
    }
}
