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

public class TestMove {

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
    public void northNoBlockage() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

    }

    @Test
    public void eastNoBlockage() {
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

    }

    @Test
    public void northEdge() {

    }

    @Test
    public void eastEdge() {

    }

    @Test
    public void northShip() {

    }

    @Test
    public void eastShip() {

    }
}
