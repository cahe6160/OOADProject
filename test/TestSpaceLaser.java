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

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Before
    public void init() {

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void hitSurface() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

        gameTest.getP1().getLaser().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][0].getSurface());
        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][0].getUnderwater());
    }

    @Test
    public void hitSubmerged() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);

        gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", gameTest.getP1().getLaser());

        assertEquals("MISS", gameTest.getP1Grid().getMyShots()[0][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[0][3].getUnderwater());
    }

    @Test
    public void hitBoth() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        gameTest.getP1().getLaser().makeHit("D2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D2", gameTest.getP1().getLaser());

        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getSurface());
        assertEquals("HIT", gameTest.getP1Grid().getMyShots()[1][3].getUnderwater());
    }
}
