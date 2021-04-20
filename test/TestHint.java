import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Minesweeper;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Cell;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;

import static org.junit.Assert.*;

public class TestHint {

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

        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[0]);

    }

    @Test
    public void testAtLeastOneHit() {
        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());

        boolean[] referenceGrid = {false, true, true, true, true};
        assertArrayEquals(referenceGrid, gameTest.getP1().getHint().getLastShot());
    }

    @Test
    public void testAllMisses() {
        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B4", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B4", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B4", gameTest.getP1().getCannon());

        boolean[] referenceGrid = {false, false, false, false, true};
        assertArrayEquals(referenceGrid, gameTest.getP1().getHint().getLastShot());

        gameTest.getP1().getCannon().makeHit("B5", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B5", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B5", gameTest.getP1().getCannon());

        boolean[] secondReferenceGrid = {false, false, false, false, false};
        assertArrayEquals(secondReferenceGrid, gameTest.getP1().getHint().getLastShot());
    }

    @Test
    public void testHintOutput() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B4", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B4", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B4", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B5", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B5", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B5", gameTest.getP1().getCannon());

        gameTest.getP1().getHint().activateHint(gameTest.getP2Grid(), gameTest.getP2TestFleet());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Hint: There is a ship located in the upper left quadrant.", standardOutput);
    }
}
