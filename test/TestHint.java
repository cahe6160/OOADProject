import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class is used to implement methods that test the Hint feature.
 * Specifically, performs output checks and Hint availability.
 */
public class TestHint {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * The set up method is used to set up outputStreamCaptor.
     * Specifically, this will help us read console output later on in the class.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;
    Ship[] p1Input;
    Ship[] p2Input;

    /**
     * Resets the single instance of Game
     * @throws SecurityException Security Exception
     * @throws NoSuchFieldException If the single_instance does not exist
     * @throws IllegalArgumentException Invalid argument
     * @throws IllegalAccessException Illegal Access
     */
    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    /**
     * Initializes some variable that will be manipulated in the coming tests.
     * Also, has some premade ship objects that will be added/used later on.
     */
    @Before
    public void init() {

        gameTest = Game.getInstance();

        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};
        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

        gameTest.getP2Grid().addShip(p2Input[0]);

    }

    /**
     * This test is made to check that a hit updates the recent shots array correctly.
     * This array determines if a player gets a hint.
     */
    @Test
    public void testAtLeastOneHit() {
        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());

        boolean[] referenceGrid = {false, true, true, true, true};
        assertArrayEquals(referenceGrid, gameTest.getP1().getHint().getLastShot());
    }

    /**
     * This test is made to check that a 5 consecutive misses are recorded correctly.
     * This array determines if a player gets a hint.
     */
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

    /**
     * This test was made to test the command line output of calling Hint.
     * Should output a quadrant where a ship is located.
     */
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

        gameTest.getP1().getHint().makeHit("K1", gameTest.getP2Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Hint: There is a ship located in the upper left quadrant.", standardOutput);
    }
}