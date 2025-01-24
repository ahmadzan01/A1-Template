/* Author: Naqeeb Ahmadzan.
 * Handles reading and parsing a maze from a file.
 * In Step 2, this abstraction supports loading the maze structure, 
 * enabling exploration. By isolating file reading and parsing logic here, 
 * the code is modular and easy to adapt for different input formats.
 */

package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MazeReader {
    public static char[][] readMazeFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        StringBuilder mazeBuilder = new StringBuilder();
        int rows = 0;
        while ((line = reader.readLine()) != null) {
            mazeBuilder.append(line).append("\n");
            rows++;
        }
        reader.close();

        String[] lines = mazeBuilder.toString().split("\n");
        char[][] maze = new char[rows][];
        for (int i = 0; i < rows; i++) {
            maze[i] = lines[i].toCharArray();
        }

        return maze;
    }
}
