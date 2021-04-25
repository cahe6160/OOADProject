import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

/**
 * This class was made to test the Captains Quarters feature.
 * This checks that Captains quarters are placed appropriately,
 * and the fact that they are more vulnerable.
 */
public class TestCaptainsQuarters {

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
     * Also, has some premade ship objects that will be used later on.
     */
    @Before
    public void init() {
        gameTest = Game.getInstance();

        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.getP1Grid().addShip(p1Input[1]);
        gameTest.getP1Grid().addShip(p1Input[2]);

        gameTest.getP2Grid().addShip(p2Input[0]);
        gameTest.getP2Grid().addShip(p2Input[1]);
        gameTest.getP2Grid().addShip(p2Input[2]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * Tests that hitting a Minesweeper's captain sinks the ship entirely
     */
    @Test
    public void hitCaptainMinesweeper(){

        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());

        assertEquals(true, gameTest.getP2Grid().isSunk(0));

    }

    /**
     * Tests that hitting a Destroyer's captain once does not sink the ship entirely
     */
    @Test
    public void hitCaptainDestroyerOnce(){

        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());
        assertEquals(false, gameTest.getP1Grid().isSunk(1));
    }

    /**
     * Tests that hitting a Battleship's captain once does not sink the ship entirely
     */
    @Test
    public void hitCaptainBattleshipOnce(){

        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        assertEquals(false, gameTest.getP1Grid().isSunk(1));
    }

    /**
     * Tests that hitting a Destroyer's captain twice sinks the ship entirely
     */
    @Test
    public void hitCaptainDestroyerTwice(){

        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());

        assertEquals(true, gameTest.getP2Grid().isSunk(1));
    }

    /**
     * Tests that hitting a Battleship's captain twice sinks the ship entirely
     */
    @Test
    public void hitCaptainBattleshipTwice(){

        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());

        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());

        assertEquals(true, gameTest.getP2Grid().isSunk(2));
    }
}
