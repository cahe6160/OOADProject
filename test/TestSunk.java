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

public class TestSunk {

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1TestFleet()[0]);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testSunk() {
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        //Shot 2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());

        assertEquals(true, gameTest.getP2Grid().isSunk(gameTest.getP2TestFleet()[0]));
    }

    @Test
    public void testSunkMessage() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        //Shot 2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());

        gameTest.getP2Grid().isSunk(gameTest.getP2TestFleet()[0]);

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper", standardOutput);
    }

    @Test
    public void testNotSunk() {
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        assertEquals(false, gameTest.getP2Grid().isSunk(gameTest.getP2TestFleet()[0]));
    }
}
