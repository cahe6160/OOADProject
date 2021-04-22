import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestHitOrMiss {

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;
    Ship[] p1Input;
    Ship[] p2Input;

    @Before
    public void init() {
        gameTest = new Game();
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        gameTest.getP1Grid().addShip(p1Input[0]);

        gameTest.getP2Grid().addShip(p2Input[0]);

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
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getCannon().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getCannon());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
    }

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
