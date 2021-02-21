import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;

import static org.junit.Assert.assertEquals;


public class TestSurrender {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;

    String [][] p1ShipBoard;
    String [][] p2ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[0]);//A1, A2

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surrender() {
        //Shot 1
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1Grid().checkHit("A1", p2ShipBoard));
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1Grid().checkHit("A1", p2ShipBoard));
        //Shot 2
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1Grid().checkHit("A2", p2ShipBoard));
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1Grid().checkHit("A2", p2ShipBoard));

        assertEquals(true, gameTest.playerSurrender());
    }

    @Test
    public void testPlayerSurrender() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Shot 1
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1Grid().checkHit("A1", p2ShipBoard));
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1Grid().checkHit("A1", p2ShipBoard));
        //Shot 2
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1Grid().checkHit("A2", p2ShipBoard));
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1Grid().checkHit("A2", p2ShipBoard));

        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nPlayer 2 surrendered!", standardOutput);

    }
}
