import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * This class was made to implement methods that test hits and misses.
 * This will test both cannon and laser.
 */
public class TestHitOrMiss {

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

        gameTest.getP1Grid().addShip(p1Input[0]);

        gameTest.getP2Grid().addShip(p2Input[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This tests whether a hit is made appropriately.
     */
    @Test
    public void testHit() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());

        assertEquals(true, gameTest.getP1().getCannon().getShipHit());

    }

    /**
     * This test whether a miss is made appropriately.
     */
    @Test
    public void testMiss() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());

        assertEquals(false, gameTest.getP1().getCannon().getShipHit());
    }

    /**
     * This tests whether a cannon hit updates on the opposing players ship grid.
     */
    @Test
    public void shipHitBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());

        assertEquals("Critical", gameTest.getP2Grid().getMyShips()[0][0].getSurface());
    }

    /**
     * This tests whether a cannon hit updates on the shooting players shot grid.
     */
    @Test
    public void shotHitBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
    }

    /**
     *
     */
    @Test
    public void shipMissBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());

        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[2][0].getSurface());
    }

    /**
     * This tests whether a cannon miss updates on the shooting players shot grid.
     */
    @Test
    public void shotMissBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[2][0].getSurface());
    }

    /**
     * This tests whether a cannon hits a surfaced ship only.
     */
    @Test
    public void subUnderShipHit() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getCannon());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }

    /**
     * This tests whether a cannon misses, when there is a underwater sub at the location.
     */
    @Test
    public void subSubmergedCannon() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);

        gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("D1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getCannon());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }
}
