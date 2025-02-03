

package ca.mcmaster.se2aa4.mazerunner.Final;

import java.io.*;
import java.util.*;

public class FinalMaze {
    private static char[][] maze;
    private static int startX, startY, endX, endY;
    private static char direction;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No arguments provided.");
            return;
        }

        String mazeFile = null;
        String pathSequence = null;
        boolean validatePath = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-i")) {
                mazeFile = args[i + 1];
            } else if (args[i].equals("-p")) {
                pathSequence = args[i + 1];
                validatePath = true;
            }
        }

        if (mazeFile == null) {
            System.out.println("Error: Maze file not specified.");
            return;
        }

        try {
            loadMaze(mazeFile);
            if (validatePath) {
                if (isValidPath(pathSequence)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                System.out.println(findPath());
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to read maze file.");
        }
    }

    private static void loadMaze(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }

        maze = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
        }

        locateEntryExit();
    }

    private static void locateEntryExit() {
        for (int i = 0; i < maze.length; i++) {
            if (maze[i][0] == ' ') {
                startX = i;
                startY = 0;
                direction = 'E';
            } else if (maze[i][maze[0].length - 1] == ' ') {
                startX = i;
                startY = maze[0].length - 1;
                direction = 'W';
            }
        }
        for (int i = 0; i < maze.length; i++) {
            if (maze[i][0] == ' ') {
                endX = i;
                endY = 0;
            } else if (maze[i][maze[0].length - 1] == ' ') {
                endX = i;
                endY = maze[0].length - 1;
            }
        }
    }

    private static boolean isValidPath(String path) {
        int x = startX, y = startY;
        char dir = direction;

        for (char move : path.toCharArray()) {
            if (move == 'F') {
                int[] next = moveForward(x, y, dir);
                if (maze[next[0]][next[1]] == '#') {
                    return false; // Path Validation - hits a wall
                }
                x = next[0];
                y = next[1];
            } else if (move == 'L' || move == 'R') {
                dir = turn(dir, move);
            }
        }
        return x == endX && y == endY; // Exit is found
    }

    private static int[] moveForward(int x, int y, char dir) {
        if (dir == 'N') x--;
        else if (dir == 'S') x++;
        else if (dir == 'E') y++;
        else if (dir == 'W') y--;
        return new int[]{x, y};
    }

    private static char turn(char dir, char turn) {
        if (dir == 'N') return (turn == 'L') ? 'W' : 'E';
        if (dir == 'S') return (turn == 'L') ? 'E' : 'W';
        if (dir == 'E') return (turn == 'L') ? 'N' : 'S';
        return (turn == 'L') ? 'S' : 'N';
    }

    private static String findPath() {
        StringBuilder path = new StringBuilder();
        int x = startX, y = startY;
        char dir = direction;

        while (x != endX || y != endY) {
            int[] next = moveForward(x, y, dir);
            if (maze[next[0]][next[1]] != '#') {
                path.append("F");
                x = next[0];
                y = next[1];
            } else {
                dir = turn(dir, 'R'); // Turn right if blocked
                path.append("R");
            }
        }
        return path.toString(); // Path Finding
    }
}