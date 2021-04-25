import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class and its methods test the capabilities of a submarine work correctly.
 * This one will not test command line output.
 */
public class TestSubmarine {

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

        gameTest.getP1Grid().addShip(p2Input[3]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This tests that a submarine ship can be on the surface.
     */
    @Test
    public void surfaced() {
        assertEquals(false, gameTest.getP1Grid().getPlayerFleet().get(0).getSubmerged());
    }

    /**
     * This tests that a submarine ship can be underwater.
     */
    @Test
    public void submerged() {
        p2Input[3].setSubmerged(true);
        gameTest.getP1Grid().addShip(p2Input[3]);
        assertEquals(true, gameTest.getP1Grid().getPlayerFleet().get(1).getSubmerged());
    }
}