/* Author: Naqeeb Ahmadzan.
* Title: Maze Navigation Program, this is the testing file that has 10 unit test cases.
*/

package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;

class AllTests {


    // --- Maze Tests ---
    @Test
    void testLoadMaze() throws IOException {
        Maze maze = new Maze("examples/small.maz.txt");
        assertNotNull(maze.getMazeGrid());
    }



    @Test
    void testMazeDimensions() throws IOException {
        Maze maze = new Maze("examples/small.maz.txt");
        assertTrue(maze.getRows() > 0);
        assertTrue(maze.getColumns() > 0);
    }


    // --- Tests Path Validation ---
    @Test
    void testPathValidationCorrect() throws IOException {
        NavigateMaze navigator = new NavigateMaze("examples/straight.maz.txt", "FFFF");
        assertTrue(navigator.PathValidate("FFFF"));
    }



    @Test
    void testPathValidationIncorrect() throws IOException {
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt", "FLF");
        assertFalse(navigator.PathValidate("FLF"));
    }



    @Test
    void testPathComputation() throws IOException {
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt");
        String path = navigator.PathCompute();
        assertTrue(navigator.PathValidate(path));
    }



    @Test
    void testEntryExitDetection() throws IOException {
        NavigateMaze navigator = new NavigateMaze("examples/straight.maz.txt");
        int entry = navigator.findEntry();
        int exit = navigator.findExit();
        assertEquals(2, entry); 
        assertEquals(2, exit); 
    }


    // --- Format Conversion Tests ---
    @Test
    void testCanonicalToFactored() {
        String canonical = "FFFRFF";
        String factored = FormChanger.canonicalToFactored(canonical);
        assertEquals("3F R 2F", factored);
    }



    @Test
    void testFactoredToCanonical() {
        String factored = "3F R 2F";
        String canonical = FormChanger.factoredToCanonical(factored);
        assertEquals("FFFRFF", canonical);
    }



    @Test
    void testEdgeCaseEmptyPath() {
        String empty = "";
        assertEquals("", FormChanger.canonicalToFactored(empty));
        assertEquals("", FormChanger.factoredToCanonical(empty));
    }



    @Test
    void testInvalidPathCharacters() throws IOException {
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt", "FX");
        assertFalse(navigator.PathValidate("FX"));
    }
}