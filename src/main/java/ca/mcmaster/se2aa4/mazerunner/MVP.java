/* Author: Naqeeb Ahmadzan.
 * This is the MVP that reflects on the feature of path navigation.
 */

package ca.mcmaster.se2aa4.mazerunner;

import java.util.Scanner;

public class MVP {

    
    public static void main(String[] args)  {

        Scanner scanner = new Scanner(System.in);

        // This part helps the user naviagte the map and how to use it.
        System.out.println("Welcome to Maze Solver MVP!");
        System.out.println("Instructions:");
        System.out.println("1. Enter the maze dimensions and layout.");
        System.out.println("2. The program will attempt to find a path.");
        System.out.println("Type 'exit' to quit at any time.");


        // This part reads the maze dimensions.
        System.out.print("Enter maze dimensions (rows and columns): ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        scanner.nextLine(); 

        // This part read maze.
        System.out.println("Enter the maze layout (row by row, using '#' for walls and ' ' for paths):");
        char[][] maze = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            maze[i] = scanner.nextLine().toCharArray();
        }

        System.out.println("Maze entered successfully!");
    }
}