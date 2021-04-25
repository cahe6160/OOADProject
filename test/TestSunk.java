import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class and its methods check that the isSunk method works as expected.
 * This will check command line output and other variables.
 */
public class TestSunk {

    Game gameTest;
    Ship[] p1Input;
    Ship[] p2Input;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

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

        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.getP2Grid().addShip(p2Input[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This checks that after a minesweeper is sunk, it reports that correctly.
     */
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

        assertEquals(true, gameTest.getP2Grid().isSunk(0));
    }

    /**
     * This tests after a ship is sunk, the command line reports that to the player.
     */
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

        gameTest.getP2Grid().isSunk(0);

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper", standardOutput);
    }

    /**
     * This tests that if a ship is hit but not sunk, it reports that correctly.
     */
    @Test
    public void testNotSunk() {
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        assertEquals(false, gameTest.getP2Grid().isSunk(0));
    }
}
