

package ca.mcmaster.se2aa4.mazerunner.Final;

 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.List;
 import ca.mcmaster.se2aa4.mazerunner.MazeReader;
 
 public class FinalMaze {
     private static char[][] maze;
     private static int row, col;
     private static String direction; // "N", "E", "S", "W"
     private static StringBuilder path = new StringBuilder();
 
     public static void main(String[] args) {
         if (args.length < 1) {
             System.out.println("Usage: java Final <maze-file>");
             return;
         }
 
         try {
             maze = MazeReader.readMazeFromFile(args[0]);
             findEntry();
             exploreMaze();
             System.out.println(factorizePath(path.toString()));
         } catch (IOException e) {
             System.err.println("Error reading maze file: " + e.getMessage());
         }
     }
 
     private static void findEntry() {
         for (int i = 0; i < maze.length; i++) {
             if (maze[i][0] == ' ') {
                 row = i;
                 col = 0;
                 direction = "E"; // Facing East
                 return;
             }
             if (maze[i][maze[0].length - 1] == ' ') {
                 row = i;
                 col = maze[0].length - 1;
                 direction = "W"; // Facing West
                 return;
             }
         }
     }
 
     private static void exploreMaze() {
         int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // N, E, S, W
         String[] dirNames = {"N", "E", "S", "W"};
 
         while (col > 0 && col < maze[0].length - 1) { // Stop at exit
             int dirIndex = getDirectionIndex(direction, dirNames);
             for (int i = 0; i < 4; i++) {
                 int newDirIndex = (dirIndex + i) % 4;
                 int newRow = row + directions[newDirIndex][0];
                 int newCol = col + directions[newDirIndex][1];
 
                 if (maze[newRow][newCol] == ' ') {
                     if (i == 1) path.append("R");
                     else if (i == 2) path.append("RR");
                     else if (i == 3) path.append("L");
 
                     row = newRow;
                     col = newCol;
                     path.append("F");
                     direction = dirNames[newDirIndex];
                     break;
                 }
             }
         }
     }
 
     private static int getDirectionIndex(String dir, String[] dirNames) {
         for (int i = 0; i < dirNames.length; i++) {
             if (dirNames[i].equals(dir)) return i;
         }
         return -1;
     }
 
     private static String factorizePath(String rawPath) {
         StringBuilder factorized = new StringBuilder();
         int count = 1;
 
         for (int i = 1; i < rawPath.length(); i++) {
             if (rawPath.charAt(i) == rawPath.charAt(i - 1)) {
                 count++;
             } else {
                 if (count > 1) factorized.append(count);
                 factorized.append(rawPath.charAt(i - 1));
                 count = 1;
             }
         }
 
         if (count > 1) factorized.append(count);
         factorized.append(rawPath.charAt(rawPath.length() - 1));
 
         return factorized.toString();
     }
 }
 