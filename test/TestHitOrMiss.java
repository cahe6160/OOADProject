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

public class TestHitOrMiss {

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1TestFleet()[0]);

        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testHit() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());

        assertEquals(true, gameTest.getP1().getCannon().getShipHit());

    }

    @Test
    public void testMiss() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());

        assertEquals(false, gameTest.getP1().getCannon().getShipHit());
    }

    @Test
    public void shipHitBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());

        assertEquals("Critical", gameTest.getP2Grid().getMyShips()[0][0].getSurface());
    }

    @Test
    public void shotHitBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
    }

    @Test
    public void shipMissBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());

        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[2][0].getSurface());
    }

    @Test
    public void shotMissBoardUpdateTest() {
        gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", gameTest.getP1().getCannon());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[2][0].getSurface());
    }

    @Test
    public void subUnderShipHit() {
        gameTest.getP2TestFleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getCannon());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }

    @Test
    public void subSubmergedCannon() {
        gameTest.getP2TestFleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[3]);

        gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("D1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getCannon());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }
}
