import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * This class is meant to test several methods in adding a ship to the grid, and the player
 */
public class TestAddShip {

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
    }

    /**
     * Make sure that a Minesweeper is added to the grid correctly.
     */
    @Test
    public void testAddShip() {
        gameTest.getP2Grid().addShip(p2Input[0]);

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[0][0].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][0].getSurface());
    }

    /**
     * Tests the scenario where a sub is added on the surface.
     */
    @Test
    public void surfacedSub() {
        gameTest.getP2Grid().addShip(p2Input[3]);

        assertEquals("Armor", gameTest.getP2Grid().getMyShips()[3][3].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[2][3].getSurface());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[2][4].getSurface());
    }

    /**
     * Tests the scenario where a sub is placed underneath another ship
     */
    @Test
    public void subUnderneathShip() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[0][3].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][3].getSurface());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[0][3].getUnderwater());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][3].getUnderwater());
    }

    /**
     * Tests the scenario where a sub is placed underwater.
     */
    @Test
    public void subUnderNoShip() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);

        assertEquals("Armor", gameTest.getP2Grid().getMyShips()[3][3].getUnderwater());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[2][3].getUnderwater());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[2][4].getUnderwater());
        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[3][3].getSurface());
    }
}
