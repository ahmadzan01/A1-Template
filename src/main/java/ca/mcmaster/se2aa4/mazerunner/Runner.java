/* Author: Naqeeb Ahmadzan.
 * Represents the player navigating the maze.
 * As part of Step 2, this abstraction was created to simulate a runner 
 * exploring the maze. It tracks the current position of the runner and 
 * provides methods to move through the maze. This ensures that navigation 
 * logic is isolated and can be expanded later.
 */ 


package ca.mcmaster.se2aa4.mazerunner;

public class Runner {
    private int x, y;
    private Maze maze;

    // Constructor that takes a Maze object
    public Runner(Maze maze) {
        this.maze = maze;
        this.x = maze.getStartX();
        this.y = maze.getStartY();
    }

    // Path validation method
    public boolean validatePath(String path) {
        int tempX = x, tempY = y;
        char direction = 'E'; // Assuming initial direction is East

        for (char move : path.toCharArray()) {
            switch (move) {
                case 'F': // Move forward
                    if (!maze.canMove(tempX, tempY, direction)) {
                        return false;
                    }
                    int[] newPos = maze.move(tempX, tempY, direction);
                    tempX = newPos[0];
                    tempY = newPos[1];
                    break;
                case 'L': // Turn left
                    direction = turnLeft(direction);
                    break;
                case 'R': // Turn right
                    direction = turnRight(direction);
                    break;
                default:
                    return false; // Invalid move
            }
        }
        return maze.isExit(tempX, tempY);
    }

    // Find path using the given method (default: Right-Hand Rule)
    public String findPath(String method) {
        if ("tremaux".equals(method)) {
            return solveUsingTremaux();
        } else {
            return solveUsingRightHand();
        }
    }

    // Right-Hand Rule algorithm
    private String solveUsingRightHand() {
        // Implement the Right-Hand Rule logic
        return "3F R 2F"; // Example output
    }

    // Tremaux Algorithm (Bonus)
    private String solveUsingTremaux() {
        // Implement the Tremaux algorithm logic
        return "2F L F R 2F"; // Example output
    }

    private char turnLeft(char direction) {
        return switch (direction) {
            case 'N' -> 'W';
            case 'W' -> 'S';
            case 'S' -> 'E';
            default -> 'N';
        };
    }

    private char turnRight(char direction) {
        return switch (direction) {
            case 'N' -> 'E';
            case 'E' -> 'S';
            case 'S' -> 'W';
            default -> 'N';
        };
    }
}