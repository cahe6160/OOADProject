import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestSunk {

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setStreams() {
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

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
    public void testSunk() {
        //Shot 1
        Boolean hitMiss = gameTest.getP1Grid().checkHit("A1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
        //Shot 2
        hitMiss = gameTest.getP1Grid().checkHit("A2", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A2", hitMiss);
        gameTest.getP2Grid().updateBoards("A2", hitMiss);

        assertEquals(true, gameTest.getP2Grid().isSunk(gameTest.getP2Fleet()[0]));
    }

//    @Test
//    public void testSunkMessage() {
//        //Shot 1
//        Boolean hitMiss = gameTest.getP1Grid().checkHit("A1", p2ShipBoard);
//        gameTest.getP1Grid().updateBoards("A1", hitMiss);
//        gameTest.getP2Grid().updateBoards("A1", hitMiss);
//        //Shot 2
//        hitMiss = gameTest.getP1Grid().checkHit("A2", p2ShipBoard);
//        gameTest.getP1Grid().updateBoards("A2", hitMiss);
//        gameTest.getP2Grid().updateBoards("A2", hitMiss);
//
////        System.out.println("Anything");
//        assertEquals("You sunk my Minesweeper", out.toString());
//    }

    @Test
    public void testNotSunk() {
        //Shot 1
        Boolean hitMiss = gameTest.getP1Grid().checkHit("A1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
        assertEquals(false, gameTest.getP2Grid().isSunk(gameTest.getP2Fleet()[0]));
    }
}
