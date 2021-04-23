import org.junit.Before;
import edu.colorado.mtoftheholycross.*;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestRandEvent {
    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;
    Ship[] p1Input;
    Ship[] p2Input;

    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Before
    public void init() {
        gameTest = Game.getInstance();
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testMalfunction() {
        gameTest.getP1Grid().addShip(p2Input[0]);
        gameTest.getP2Grid().addShip(p2Input[0]);

        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());//MISS
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A3", gameTest.getP1().getCannon());

        new RandEvent(gameTest.getP1Grid(), gameTest.getP1(), 0);
        assertEquals(gameTest.getP1().getSonar().getSonarCount(), 1);
    }

    @Test
    public void testMine() {
        gameTest.getP1Grid().addShip(p2Input[0]);
        gameTest.getP1Grid().addShip(p2Input[1]);
        gameTest.getP2().getCannon().makeHit("A1", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP2().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP2().getCannon());
        new RandEvent(gameTest.getP2Grid(), gameTest.getP2(), 1);
        assert(gameTest.getP1Grid().getMyShips()[0][1].getSurface().equals("Damage") || gameTest.getP1Grid().getMyShips()[2][1].getSurface().equals("Damage"));
    }

    @Test
    public void testWave() {
        gameTest.getP1Grid().addShip(p2Input[0]);
        new RandEvent(gameTest.getP2Grid(), gameTest.getP2(), 2);
        assert(gameTest.getP1Grid().getMyShips()[0][1].getSurface().equals("Ship") || gameTest.getP1Grid().getMyShips()[2][0].getSurface().equals("Ship"));
    }

    @Test
    public void testWellRested() {
        gameTest.getP1Grid().addShip(p2Input[0]);
        gameTest.getP2Grid().addShip(p2Input[0]);

        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());//MISS
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A3", gameTest.getP1().getCannon());

        new RandEvent(gameTest.getP1Grid(), gameTest.getP1(), 3);
        assertFalse(gameTest.getP1Grid().getIsWaiting());
    }
}
