import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Cell;

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

    Cell[][] p1ShipBoard;
    Cell[][] p2ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[0]);//A1, A2
        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[1]);//B1, B2, B3
        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[2]);//C1, C2, C3, C4

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surrender() {
        //Shot 1
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        //Shot 2
        hitMiss = gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A2", hitMiss);
        gameTest.getP1Grid().updateBoards("A2", hitMiss);
        //Shot 3
        hitMiss = gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B1", hitMiss);
        gameTest.getP1Grid().updateBoards("B1", hitMiss);
        //Shot 4
        hitMiss = gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B2", hitMiss);
        gameTest.getP1Grid().updateBoards("B2", hitMiss);
        //Shot 5
        hitMiss = gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B3", hitMiss);
        gameTest.getP1Grid().updateBoards("B3", hitMiss);
        //Shot 6
        hitMiss = gameTest.getP1().getCannon().makeHit("C1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C1", hitMiss);
        gameTest.getP1Grid().updateBoards("C1", hitMiss);
        //Shot 7
        hitMiss = gameTest.getP1().getCannon().makeHit("C2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C2", hitMiss);
        gameTest.getP1Grid().updateBoards("C2", hitMiss);
        //Shot 8
        hitMiss = gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", hitMiss);
        gameTest.getP1Grid().updateBoards("C3", hitMiss);
        //Shot 9
        hitMiss = gameTest.getP1().getCannon().makeHit("C4", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C4", hitMiss);
        gameTest.getP1Grid().updateBoards("C4", hitMiss);


        assertEquals(true, gameTest.playerSurrender());
    }

    @Test
    public void testPlayerSurrender() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Shot 1
        int[] hitMiss = gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", hitMiss);
        gameTest.getP1Grid().updateBoards("A1", hitMiss);
        //Shot 2
        hitMiss = gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A2", hitMiss);
        gameTest.getP1Grid().updateBoards("A2", hitMiss);

        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nYou sunk my Destroyer\nYou sunk my Battleship\nYou sunk my Submarine\nPlayer 2 surrendered!", standardOutput);

    }
}
