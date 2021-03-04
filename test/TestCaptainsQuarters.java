import apple.laf.JRSUIConstants;
import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

import org.junit.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestCaptainsQuarters {

    Game gameTest;
    String[][] p2ShipBoard;
    String [][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[1]);
        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[2]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[1]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[2]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void hitCaptainMineseeper(){

        gameTest.getP1Grid().updateBoards("A1", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("A1", p2ShipBoard);

        assertEquals(true, gameTest.getP1Grid().isSunk(gameTest.getP2Fleet()[0]));

    }

    @Test
    public void hitCaptainDestroyerOnce(){

        gameTest.getP1Grid().updateBoards("B2", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("B2", p2ShipBoard);
        assertEquals(false, gameTest.getP1Grid().isSunk(gameTest.getP1Fleet()[1]));
    }

    @Test
    public void hitCaptainBattleshipOnce(){

        gameTest.getP1Grid().updateBoards("C3", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("C3", p2ShipBoard);
        assertEquals(false, gameTest.getP1Grid().isSunk(gameTest.getP1Fleet()[1]));
    }

    @Test
    public void hitCaptainDestroyerTwice(){

        gameTest.getP1Grid().updateBoards("B2", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("B2", p2ShipBoard);

        gameTest.getP1Grid().updateBoards("B2", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("B2", p2ShipBoard);

        assertEquals(true, gameTest.getP1Grid().isSunk(gameTest.getP2Fleet()[1]));
    }

    @Test
    public void hitCaptainBattleshipTwice(){

        gameTest.getP1Grid().updateBoards("C3", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("C3", p2ShipBoard);

        gameTest.getP1Grid().updateBoards("C2", p2ShipBoard);
        gameTest.getP2Grid().updateBoards("C2", p2ShipBoard);

        assertEquals(true, gameTest.getP1Grid().isSunk(gameTest.getP2Fleet()[1]));
    }
}