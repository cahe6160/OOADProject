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

public class TestHitOrMiss {

    Game gameTest;
    String[][] p2ShipBoard;
    String [][] p1ShipBoard;

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
        //System.out.println("Test Hit");
        boolean hitMiss = gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);

        assertEquals(true, hitMiss);

    }

    @Test
    public void testMiss() {
        //System.out.println("Test Miss");

        boolean hitMiss = gameTest.getP1Grid().updateBoards("A3", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A3", p2ShipBoard);

        assertEquals(false, hitMiss);
    }

    @Test
    public void shipHitBoardUpdateTest() {
        //System.out.println("Test Ship Board Update");

        gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);

        assertEquals("Critical", gameTest.getP2Grid().getMyShips()[0][0]);
    }

    @Test
    public void shotHitBoardUpdateTest() {
        //System.out.println("Test Shots Board Update");

        gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0]);
    }

    @Test
    public void shipMissBoardUpdateTest() {
        //System.out.println("Test Ship Board Update");

        gameTest.getP1Grid().updateBoards("A3", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A3", p2ShipBoard);

        assertEquals("Sea", gameTest.getP2Grid().getMyShips()[2][0]);
    }

    @Test
    public void shotMissBoardUpdateTest() {
        //System.out.println("Test Shots Board Update");

        gameTest.getP1Grid().updateBoards("A3", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A3", p2ShipBoard);

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[2][0]);
    }
}
