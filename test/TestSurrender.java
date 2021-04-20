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

        gameTest.getP2Grid().addShip(gameTest.getP1TestFleet()[0]);//A1, A2
        gameTest.getP2Grid().addShip(gameTest.getP1TestFleet()[1]);//B1, B2, B3
        gameTest.getP2Grid().addShip(gameTest.getP1TestFleet()[2]);//C1, C2, C3, C4

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surrender() {
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        //Shot 2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        //Shot 3
        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
        //Shot 4
        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        //Shot 5
        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
        //Shot 6
        gameTest.getP1().getCannon().makeHit("C1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C1", gameTest.getP1().getCannon());
        //Shot 7
        gameTest.getP1().getCannon().makeHit("C2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C2", gameTest.getP1().getCannon());
        //Shot 8
        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        //Shot 9
        gameTest.getP1().getCannon().makeHit("C4", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C4", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C4", gameTest.getP1().getCannon());


        assertEquals(true, gameTest.playerSurrender());
    }

    @Test
    public void testPlayerSurrender() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        //Shot 1
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        //Shot 2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());

        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nYou sunk my Destroyer\nYou sunk my Battleship\nYou sunk my Submarine\nYou sunk my Minesweeper\nYou sunk my Minesweeper\nYou sunk my Tower Ship\nPlayer 2 surrendered!\nYour score this game was 1 turns!", standardOutput);

    }
}
