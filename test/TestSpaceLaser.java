import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;

public class TestSpaceLaser {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

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

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void hitSurface() {
        gameTest.getP2Grid().addShip(p2Input[0]);

        gameTest.getP1().getLaser().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][0].getUnderwater());
    }

    @Test
    public void hitSubmerged() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);

        gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getLaser());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getUnderwater());
    }

    @Test
    public void hitBoth() {
        p2Input[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(p2Input[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getLaser().makeHit("D2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D2", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getUnderwater());
    }
}
