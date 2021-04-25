import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class' methods are used to test the capabilities of the space laser.
 * Tests command line outputs, and that some variables are updated accordingly.
 */
public class TestSpaceLaser {

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

        gameTest = Game.getInstance();;
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This tests that a laser weapon is capable of hitting ships on the surface.
     * Makes sure the values in the my shots grid at that position are updated accordingly.
     */
    @Test
    public void hitSurface() {
        gameTest.getP2Grid().addShip(p2Input[0]);

        gameTest.getP1().getLaser().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][0].getUnderwater());
    }

    /**
     * This tests that a laser weapon is capable of hitting ships underwater.
     * Makes sure the values in the my shots grid at that position are updated accordingly.
     */
    @Test
    public void hitSubmerged() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);

        gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getLaser());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getUnderwater());
    }

    /**
     * This tests that a laser weapon is capable of hitting ships both on the surface and underwater.
     * Makes sure the values in the my shots grid, at that position, are updated accordingly.
     */
    @Test
    public void hitBoth() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getLaser().makeHit("D2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D2", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getUnderwater());
    }
}
