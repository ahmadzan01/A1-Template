
package ca.mcmaster.se2aa4.mazerunner.Final;

 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import java.io.IOException;
 import ca.mcmaster.se2aa4.mazerunner.Maze;
 import ca.mcmaster.se2aa4.mazerunner.MazeReader;

 
 public class Main {
     private static final Logger logger = LogManager.getLogger();
 
     public static void main(String[] args) {
         if (args.length < 1) {
             System.out.println("Usage: java Main <maze-file>");
             return;
         }
 
         System.out.println("Starting Maze Runner");
 
         try {
             char[][] mazeStructure = MazeReader.readMazeFromFile(args[0]);
             Maze maze = new Maze(mazeStructure);
 
             logger.info("Maze Loaded:");
             maze.showMaze();
 
             FinalMaze.main(args); // Call the final maze solver
         } catch (IOException e) {
             logger.error("Error reading the maze file: ", e);
         }
 
         System.out.println("End of Maze Runner");
     }
 }
 