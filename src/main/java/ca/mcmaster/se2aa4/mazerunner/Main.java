/* Author: Naqeeb Ahmadzan.
 * Title: Maze Navigation Program
 * 
 * This program allows a user to navigate a maze using given instructions or find a path through the maze.
 * It supports two main functionalities:
 *      1. Validating a given path to determine if it correctly traverses the maze.
 *      2. Computing a valid path from the maze entrance to the exit.
 * 
 * Features Implemented:
 * - Navigation with Instructions: Users can input a path and check if it correctly follows the maze structure.
 * - Finding a Path: The program can automatically compute a valid path through the maze.
 * - Exit Detection: The program determines when an exit has been reached.
 * - Path Validation: Ensures the provided path does not go through walls or out of bounds.
 * 
 * Usage:
 * - Run the program with the '-i' option to specify a maze file.
 * - Optionally, provide '-p' with a path string to check if it's valid.
 * - If no path is provided, the program will compute one.
*/


package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

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
                PathStrategy strategy = new RightHandStrategy(); // Strategy pattern
                NavigateMaze navigate = new NavigateMaze(inputFile, strategy);

                if (cmd.hasOption("p")) {
                    String path = cmd.getOptionValue("p");
                    boolean isValid = navigate.PathValidate(path);
                    System.out.println(isValid ? "Correct Path" : "Incorrect Path");
                } else {
                    System.out.println(navigate.PathCompute());
                }
            } else {
                System.err.println("Wrong format, please use '-i'");
            }
        } catch (Exception e) {
            System.err.println("An error has occurred: " + e.getMessage());
        }
    }
}

// ================== STRATEGY PATTERN ==================
interface PathStrategy {
    String computePath(Maze maze);
}

class RightHandStrategy implements PathStrategy {
    @Override
    public String computePath(Maze maze) {
        char[][] grid = maze.getMazeGrid();
        int row = findEntry(maze);
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
                path.append("RF");
                row = rightRow;
                col = rightCol;
            } else {
                int nextRow = row + moves[direction][0];
                int nextCol = col + moves[direction][1];
                if (grid[nextRow][nextCol] != '#') {
                    path.append("F");
                    row = nextRow;
                    col = nextCol;
                } else {
                    direction = (direction + 3) % 4;
                    path.append("L");
                }
            }
        }
        return FormChanger.getInstance().canonicalToFactored(path.toString());
    }

    public int findEntry(Maze maze) {
        for (int x = 0; x < maze.getRows(); x++) {
            if (maze.getMazeGrid()[x][0] != '#') return x;
        }
        return -1;
    }
}
// ======================================================

// ================== SINGLETON PATTERN ==================
class FormChanger {
    private static FormChanger instance;
    
    private FormChanger() {} // Private constructor
    
    public static FormChanger getInstance() {
        if (instance == null) {
            instance = new FormChanger();
        }
        return instance;
    }

    public String canonicalToFactored(String canonicalPath) {
        if (canonicalPath.isEmpty()) return "";
        StringBuilder factored = new StringBuilder();
        int count = 1;
        for (int i = 1; i < canonicalPath.length(); i++) {
            if (canonicalPath.charAt(i) == canonicalPath.charAt(i - 1)) count++;
            else {
                if (count > 1) factored.append(count);
                factored.append(canonicalPath.charAt(i - 1)).append(" ");
                count = 1;
            }
        }
        if (count > 1) factored.append(count);
        factored.append(canonicalPath.charAt(canonicalPath.length() - 1));
        return factored.toString();
    }

    public String factoredToCanonical(String factoredPath) {
        if (factoredPath.isEmpty()) return "";
        StringBuilder canonical = new StringBuilder();
        int count = 1;
        for (int i = 0; i < factoredPath.length(); i++) {
            char ch = factoredPath.charAt(i);
            if (Character.isDigit(ch)) count = Character.getNumericValue(ch);
            else if (ch != ' ') {
                canonical.append(String.valueOf(ch).repeat(count));
                count = 1;
            }
        }
        return canonical.toString();
    }
}
// =======================================================

class Maze {
    private char[][] mazeGrid;
    private int rows = 0;
    private int columns = 0;

    public Maze(String inputFile) throws IOException {
        initializeMaze(inputFile);
    }

    private void initializeMaze(String inputFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (rows == 0) columns = line.length();
                rows++;
            }
            mazeGrid = new char[rows][columns];
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int x = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                for (int y = 0; y < line.length(); y++) {
                    mazeGrid[x][y] = line.charAt(y);
                }
                x++;
            }
        }
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public char[][] getMazeGrid() { return mazeGrid; }
}

class NavigateMaze {
    private final Maze maze;
    private final PathStrategy strategy;

    public NavigateMaze(String inputFile, PathStrategy strategy) throws IOException {
        this.maze = new Maze(inputFile);
        this.strategy = strategy;
    }

    public boolean PathValidate(String path) {
        String canonicalPath = FormChanger.getInstance().factoredToCanonical(path);
        int row = findEntry();
        int col = 0;
        int direction = 1;
        char[][] grid = maze.getMazeGrid();

        for (char step : canonicalPath.toCharArray()) {
            if (step == 'L') direction = (direction + 3) % 4;
            else if (step == 'R') direction = (direction + 1) % 4;
            else if (step == 'F') {
                int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                row += moves[direction][0];
                col += moves[direction][1];
                if (invalidPosition(row, col, grid)) return false;
            } else return false;
        }
        return col == maze.getColumns() - 1;
    }

    public String PathCompute() {
        return strategy.computePath(maze);
    }

    public int findEntry() {
        for (int x = 0; x < maze.getRows(); x++) {
            if (maze.getMazeGrid()[x][0] != '#') return x;
        }
        return -1;
    }

    private boolean invalidPosition(int row, int col, char[][] grid) {
        return row < 0 || col < 0 || 
               row >= maze.getRows() || 
               col >= maze.getColumns() || 
               grid[row][col] == '#';
    }
}