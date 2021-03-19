import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Minesweeper;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

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
    String[][] p2ShipBoard;
    String [][] p1ShipBoard;

    @Before
    public void init() {

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        int[] hitMiss = gameTest.getP2().getCannon().makeHit("A1", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
    }

    @Test
    public void hitSurface() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);

        int[] hitMiss = gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", hitMiss);

        assertEquals("HIT/Sea", gameTest.getP1Grid().getMyShots()[3][0]);
    }

    @Test
    public void hitSubmerged() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);

        int[] hitMiss = gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", hitMiss);

        assertEquals("Sea/HIT", gameTest.getP1Grid().getMyShots()[3][0]);
    }

    @Test
    public void hitBoth() {
        gameTest.getP2Fleet()[3].setSubmerged(true);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[3]);
        gameTest.getP2Grid().addShip(new Minesweeper("D1", "D2"));

        int[] hitMiss = gameTest.getP1().getLaser().makeHit("D1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("D1", hitMiss);

        assertEquals("HIT/HIT", gameTest.getP1Grid().getMyShots()[3][0]);
    }
}
