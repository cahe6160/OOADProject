import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Cell;

import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestMove {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

    }

    @Test
    public void northNoBlockage() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    @Test
    public void eastNoBlockage() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(2, gameTest.getP1Fleet(), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][1].getSurface());
    }

    @Test
    public void eastEdge() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[2]);
        gameTest.makeMove(2, gameTest.getP1Fleet(), gameTest.getP1Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, at least one ship is too far east, unable to move fleet.", standardOutput);
    }

    @Test
    public void northEdge() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[2]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, at least one ship is too far north, unable to move fleet.", standardOutput);
    }

    @Test
    public void northBlockage() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[5]);
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        gameTest.switchTurn();

        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[4]);
        gameTest.makeMove(1, gameTest.getP2Fleet(), gameTest.getP2Grid());

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[1][0].getSurface());
    }

    @Test
    public void northUndo() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    @Test
    public void multiUndo() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    @Test
    public void undoRedo() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.redoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    @Test
    public void multiUndoRedo() {
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.makeMove(1, gameTest.getP1Fleet(), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.undoMove();
        gameTest.redoMove();
        gameTest.redoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[4][0].getSurface());
    }
}
