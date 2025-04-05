/* Author: Naqeeb Ahmadzan.
 * Title: Maze Navigation Program Test Suite
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

    // --- Path Validation Tests ---
    @Test
    void testPathValidationCorrect() throws IOException {
        PathStrategy strategy = new RightHandStrategy();
        NavigateMaze navigator = new NavigateMaze("examples/straight.maz.txt", strategy);
        assertTrue(navigator.PathValidate("FFFF"));
    }

    @Test
    void testPathValidationIncorrect() throws IOException {
        PathStrategy strategy = new RightHandStrategy();
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt", strategy);
        assertFalse(navigator.PathValidate("FLF"));
    }

    // --- Path Computation Tests ---
    @Test
    void testPathComputation() throws IOException {
        PathStrategy strategy = new RightHandStrategy();
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt", strategy);
        String path = navigator.PathCompute();
        assertTrue(navigator.PathValidate(path));
    }

    // --- Entry/Exit Detection Tests ---
    @Test
    void testEntryExitDetection() throws IOException {
        PathStrategy strategy = new RightHandStrategy();
        NavigateMaze navigator = new NavigateMaze("examples/straight.maz.txt", strategy);
        int entry = navigator.findEntry();
        // int exit = navigator.findExit();
        assertEquals(2, entry); 
        // assertEquals(2, exit); 
    }

    // --- Format Conversion Tests ---
    @Test
    void testCanonicalToFactored() {
        String canonical = "FFFRFF";
        String factored = FormChanger.getInstance().canonicalToFactored(canonical);
        assertEquals("3F R 2F", factored);
    }

    @Test
    void testFactoredToCanonical() {
        String factored = "3F R 2F";
        String canonical = FormChanger.getInstance().factoredToCanonical(factored);
        assertEquals("FFFRFF", canonical);
    }

    @Test
    void testEdgeCaseEmptyPath() {
        String empty = "";
        assertEquals("", FormChanger.getInstance().canonicalToFactored(empty));
        assertEquals("", FormChanger.getInstance().factoredToCanonical(empty));
    }

    // --- Invalid Input Tests ---
    @Test
    void testInvalidPathCharacters() throws IOException {
        PathStrategy strategy = new RightHandStrategy();
        NavigateMaze navigator = new NavigateMaze("examples/small.maz.txt", strategy);
        assertFalse(navigator.PathValidate("FX"));
    }
}