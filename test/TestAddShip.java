import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

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
    String[][] p2ShipBoard;
    String [][] p1ShipBoard;

    @Before
    public void init() {

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testAddShip() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[0][0]);
        assertEquals( "Ship", gameTest.getP2Grid().getMyShips()[1][0]);
    }
}
