/* Author: Naqeeb Ahmadzan.
* The entry point for the Maze Runner program. In Step 2, this file ties 
* together the walking skeleton by initializing the Maze, Runner, and 
* MazeReader abstractions. It demonstrates their basic functionality, 
* such as loading a maze, initializing the runner, and simulating simple 
* movement. This provides a foundational framework for further exploration 
* and extension.
*/


package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        System.out.println("Starting Maze Runner");

        try {
            // Example file path (replace with your actual path)
            String mazeFilePath = "examples/medium.maz.txt";

            // Read the maze
            char[][] mazeStructure = MazeReader.readMazeFromFile(mazeFilePath);
            Maze maze = new Maze(mazeStructure);

            // Display the maze
            logger.info("Maze Loaded:");
            maze.showMaze();

            // Initialize runner at the top-left corner (0, 0)
            Runner runner = new Runner(0, 0);
            logger.info("Runner starting at: " + runner.getCurrentPosition());

            // Example move
            runner.moveRight();
            logger.info("Runner moved to: " + runner.getCurrentPosition());
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }

        System.out.println("End of Maze Runner");
    }
}
