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

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);

        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void testHit() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());

        assertEquals(1, hitMiss[0]);

    }

    @Test
    public void testMiss() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());

        assertEquals(0, hitMiss[0]);
    }

    @Test
    public void shipHitBoardUpdateTest() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", hitMiss);

        assertEquals("Critical", gameTest.getP2Grid().getMyShips()[0][0].getSurface());
    }

    @Test
    public void shotHitBoardUpdateTest() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", hitMiss);

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
    }

    @Test
    public void shipMissBoardUpdateTest() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", hitMiss);

        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[2][0].getSurface());
    }

    @Test
    public void shotMissBoardUpdateTest() {
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A3", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A3", hitMiss);

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[2][0].getSurface());
    }

    @Test
    public void subUnderShipHit() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        int[] hitMiss = gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", hitMiss);

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }

    @Test
    public void subSubmergedCannon() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);

        int[] hitMiss = gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("D1", hitMiss);
        gameTest.getP1Grid().updateBoards("D1", hitMiss);

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }
}
