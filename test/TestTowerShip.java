import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Minesweeper;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Submarine;
import edu.colorado.mtoftheholycross.Cell;

import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestTowerShip {
    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP2Fleet()[6]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surfaceHit() {
        gameTest.getP1().getCannon().makeHit("J10", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("J10", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("J10", gameTest.getP1().getCannon());

        assertEquals("Damage", gameTest.getP1Grid().getMyShips()[9][9].getSurface());
        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[9][9].getUnderwater());

    }

    @Test
    public void laserHit() {
        gameTest.getP1().getCannon().makeHit("J10", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("J10", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("J10", gameTest.getP1().getCannon());

        assertEquals("Damage", gameTest.getP1Grid().getMyShips()[9][9].getSurface());
        assertEquals("Damage", gameTest.getP1Grid().getMyShips()[9][9].getUnderwater());
    }
}
