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
        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[1]);//B1, B2, B3
        gameTest.getP2Grid().addShip(gameTest.getP1Fleet()[2]);//C1, C2, C3, C4

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surrender() {
        //Shot 1
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        //Shot 2
        gameTest.getP2Grid().updateBoards("A2", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A2", p2ShipBoard);
        //Shot 3
        gameTest.getP2Grid().updateBoards("B1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("B1", p2ShipBoard);
        //Shot 4
        gameTest.getP2Grid().updateBoards("B2", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("B2", p2ShipBoard);
        //Shot 5
        gameTest.getP2Grid().updateBoards("B3", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("B3", p2ShipBoard);
        //Shot 6
        gameTest.getP2Grid().updateBoards("C1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("C1", p2ShipBoard);
        //Shot 7
        gameTest.getP2Grid().updateBoards("C2", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("C2", p2ShipBoard);
        //Shot 8
        gameTest.getP2Grid().updateBoards("C3", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("C3", p2ShipBoard);
        //Shot 9
        gameTest.getP2Grid().updateBoards("C4", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("C4", p2ShipBoard);


        assertEquals(true, gameTest.playerSurrender());
    }

    @Test
    public void testPlayerSurrender() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Shot 1
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        //Shot 2
        gameTest.getP2Grid().updateBoards("A2", p2ShipBoard);
        gameTest.getP1Grid().updateBoards("A2", p2ShipBoard);

        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nYou sunk my Destroyer\nYou sunk my Battleship\nPlayer 2 surrendered!", standardOutput);

    }
}
