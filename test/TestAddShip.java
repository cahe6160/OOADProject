import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Minesweeper;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Cell;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestAddShip {

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testAddShip() {
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[0]);

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[0][0].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][0].getSurface());
    }

    @Test
    public void surfacedSub() {
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[3]);

        assertEquals("Armor", gameTest.getP2Grid().getMyShips()[3][3].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[2][3].getSurface());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[2][4].getSurface());
    }

    @Test
    public void subUnderneathShip() {
        gameTest.getP2TestFleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[0][3].getSurface());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][3].getSurface());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[0][3].getUnderwater());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][3].getUnderwater());
    }

    @Test
    public void subUnderNoShip() {
        gameTest.getP2TestFleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[3]);

        assertEquals("Armor", gameTest.getP2Grid().getMyShips()[3][3].getUnderwater());
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[2][3].getUnderwater());
        assertEquals("Ship", gameTest.getP2Grid().getMyShips()[2][4].getUnderwater());
        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[3][3].getSurface());
    }
}
