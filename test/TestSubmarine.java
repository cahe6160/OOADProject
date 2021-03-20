import apple.laf.JRSUIConstants;
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

public class TestSubmarine {

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP2Fleet()[3]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void surfaced() {
        assertEquals(false, gameTest.getP1Fleet()[3].getSubmerged());
    }

    @Test
    public void submerged() {
        gameTest.getP1Fleet()[3].setSubmerged(true);
        assertEquals(true, gameTest.getP1Fleet()[3].getSubmerged());
    }
}