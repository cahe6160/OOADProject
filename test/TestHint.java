import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
    Ship[] p1Input;
    Ship[] p2Input;

    @Before
    public void init() {

        gameTest = new Game();

        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};
        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

        gameTest.getP2Grid().addShip(p2Input[0]);

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

        gameTest.getP1().getHint().activateHint(gameTest.getP2Grid(), gameTest.getP2Grid().getPlayerFleet().toArray(new Ship[gameTest.getP2Grid().getPlayerFleet().size()]));

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Hint: There is a ship located in the upper left quadrant.", standardOutput);
    }
}
