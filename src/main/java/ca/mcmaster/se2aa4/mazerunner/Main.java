/* Author: Naqeeb Ahmadzan.
* The entry point for the Maze Runner program. In Step 2, this file ties 
* together the walking skeleton by initializing the Maze, Runner, and 
* MazeReader abstractions. It demonstrates their basic functionality, 
* such as loading a maze, initializing the runner, and simulating simple 
* movement. This provides a foundational framework for further exploration 
* and extension.
*/


package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
// import org.apache.commons.cli.ParseException;

public class Main {

        public static void main(String[] args) {
            Options options = new Options();
            options.addOption("i", true, "Path to file containing maze");
            options.addOption("p", true, "Input path to check for legitimacy");
    
            CommandLineParser parser = new DefaultParser();
            try {
                CommandLine cmd = parser.parse(options, args);
                if (cmd.hasOption("i")) {
                    String inputFile = cmd.getOptionValue("i");
                    if (cmd.hasOption("p")) {
                        String path = cmd.getOptionValue("p");
                        NavigateMaze navigate = new NavigateMaze(inputFile, path);
                        boolean isValid = navigate.PathValidate(path);
                        System.out.println(isValid ? "Correct Path" : "Incorrect Path");
                    } else {
                        NavigateMaze navigate = new NavigateMaze(inputFile);
                        System.out.println(navigate.PathCompute());
                    }
                } else {
                    System.err.println("Wrong format, please use '-i'");
                }
            } catch (Exception e) {
                System.err.println("An error has occurred");
            }
        }
    }
    
    class Maze {
        private char[][] mazeGrid;
        private int rows = 0;
        private int columns = 0;
    
        public Maze(String inputFile) throws IOException {
            initializeMaze(inputFile);
        }
    
        private void initializeMaze(String inputFile) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (rows == 0) columns = line.length();
                rows++;
            }
            reader.close();
    
            mazeGrid = new char[rows][columns];
            reader = new BufferedReader(new FileReader(inputFile));
            int x = 0;
            while ((line = reader.readLine()) != null) {
                for (int y = 0; y < line.length(); y++) {
                    mazeGrid[x][y] = line.charAt(y);
                }
                x++;
            }
            reader.close();
        }
    
        public int getRows() {
            return rows;
        }
    
        public int getColumns() {
            return columns;
        }
    
        public char[][] getMazeGrid() {
            return mazeGrid;
        }
    }
    
    class NavigateMaze {
        private Maze maze;
        private String path;
    
        public NavigateMaze(String inputFile) throws IOException {
            this.maze = new Maze(inputFile);
        }
    
        public NavigateMaze(String inputFile, String path) throws IOException {
            this.maze = new Maze(inputFile);
            this.path = path;
        }
    
        private int findEntry() {
            for (int x = 0; x < maze.getRows(); x++) {
                if (maze.getMazeGrid()[x][0] != '#') return x;
            }
            return -1;
        }
    
        private int findExit() {
            for (int x = 0; x < maze.getRows(); x++) {
                if (maze.getMazeGrid()[x][maze.getColumns() - 1] != '#') return x;
            }
            return -1;
        }
    
        public boolean PathValidate(String path) {
            String canonicalPath = FormChanger.factoredToCanonical(path);
            int row = findEntry();
            int col = 0;
            int direction = 1;
            char[][] grid = maze.getMazeGrid();
    
            for (char step : canonicalPath.toCharArray()) {
                if (step == 'L') {
                    direction = (direction + 3) % 4;
                } else if (step == 'R') {
                    direction = (direction + 1) % 4;
                } else if (step == 'F') {
                    int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                    row += moves[direction][0];
                    col += moves[direction][1];
    
                    if (row < 0 || col < 0 || row >= maze.getRows() || col >= maze.getColumns() || grid[row][col] == '#') {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return col == maze.getColumns() - 1;
        }
    
        public String PathCompute() {
            char[][] grid = maze.getMazeGrid();
            int row = findEntry();
            int col = 0;
            int direction = 1;
            StringBuilder path = new StringBuilder();
            int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    
            while (col != maze.getColumns() - 1) {
                int rightDir = (direction + 1) % 4;
                int rightRow = row + moves[rightDir][0];
                int rightCol = col + moves[rightDir][1];
    
                if (grid[rightRow][rightCol] != '#') {
                    direction = rightDir;
                    path.append("R");
                    row = rightRow;
                    col = rightCol;
                    path.append("F");
                } else {
                    int nextRow = row + moves[direction][0];
                    int nextCol = col + moves[direction][1];
    
                    if (grid[nextRow][nextCol] != '#') {
                        row = nextRow;
                        col = nextCol;
                        path.append("F");
                    } else {
                        direction = (direction + 3) % 4;
                        path.append("L");
                    }
                }
            }
            return FormChanger.canonicalToFactored(path.toString());
        }
    }
    
    class FormChanger {
        public static String canonicalToFactored(String canonicalPath) {
            StringBuilder factored = new StringBuilder();
            int count = 1;
            for (int i = 1; i < canonicalPath.length(); i++) {
                if (canonicalPath.charAt(i) == canonicalPath.charAt(i - 1)) {
                    count++;
                } else {
                    if (count > 1) factored.append(count);
                    factored.append(canonicalPath.charAt(i - 1)).append(" ");
                    count = 1;
                }
            }
            if (count > 1) factored.append(count);
            factored.append(canonicalPath.charAt(canonicalPath.length() - 1));
            return factored.toString();
        }
    
        public static String factoredToCanonical(String factoredPath) {
            StringBuilder canonical = new StringBuilder();
            int count = 1;
            for (int i = 0; i < factoredPath.length(); i++) {
                char ch = factoredPath.charAt(i);
                if (Character.isDigit(ch)) {
                    count = Character.getNumericValue(ch);
                } else if (ch != ' ') {
                    canonical.append(String.valueOf(ch).repeat(count));
                    count = 1;
                }
            }
            return canonical.toString();
        }
    }
