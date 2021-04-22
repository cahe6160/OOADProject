import edu.colorado.mtoftheholycross.*;
import org.junit.*;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;


public class TestTowerShip {
    Game gameTest;
    Ship[] p1Input;
    Ship[] p2Input;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Before
    public void init() {
        gameTest = Game.getInstance();;
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        gameTest.getP2Grid().addShip(p2Input[6]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surfaceHit() {
        gameTest.getP1().getCannon().makeHit("J10", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("J10", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("J10", gameTest.getP1().getCannon());

        assertEquals("Damage", gameTest.getP2Grid().getMyShips()[9][9].getSurface());
        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[9][9].getUnderwater());

    }

    @Test
    public void laserHit() {
        gameTest.getP1().getLaser().makeHit("J10", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("J10", gameTest.getP1().getLaser());
        gameTest.getP2Grid().updateBoards("J10", gameTest.getP1().getLaser());

        assertEquals("Damage", gameTest.getP2Grid().getMyShips()[9][9].getSurface());
        assertEquals("Critical", gameTest.getP2Grid().getMyShips()[9][9].getUnderwater());
    }
}
