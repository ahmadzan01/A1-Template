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
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java -jar mazerunner.jar -i <maze_file> [-p <path>] [-method <method>]");
            return;
        }

        String mazeFilePath = null;
        String pathToValidate = null;
        String method = "righthand"; // Default method

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i") && i + 1 < args.length) {
                mazeFilePath = args[i + 1];
            } else if (args[i].equals("-p") && i + 1 < args.length) {
                pathToValidate = args[i + 1];
            } else if (args[i].equals("-method") && i + 1 < args.length) {
                method = args[i + 1].toLowerCase();
            }
        }

        if (mazeFilePath == null) {
            System.out.println("Error: Maze file path must be specified with -i flag.");
            return;
        }

        try {
            char[][] mazeStructure = MazeReader.readMazeFromFile(mazeFilePath);
            Maze maze = new Maze(mazeStructure);
            Runner runner = new Runner(maze);

            if (pathToValidate != null) {
                // This part fulfills the feature of path validation
                if (runner.validatePath(pathToValidate)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                // This part fulfills the feature of finding a path
                String computedPath = runner.findPath(method);
                System.out.println(computedPath);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read the maze file.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}