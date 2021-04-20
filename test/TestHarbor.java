//import edu.colorado.mtoftheholycross.Game;
//import edu.colorado.mtoftheholycross.Minesweeper;
//import edu.colorado.mtoftheholycross.Ship;
//import edu.colorado.mtoftheholycross.Grid;
//import edu.colorado.mtoftheholycross.Cell;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.Suite;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.lang.*;
//
//public class TestHarbor {
//
//    private final PrintStream standardOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//
//    @Before
//    public void setUp() {
//        System.setOut(new PrintStream(outputStreamCaptor));
//    }
//
//    Game gameTest;
//    Cell[][] p2ShipBoard;
//    Cell[][] p1ShipBoard;
//
//    @Before
//    public void init() {
//
//        gameTest = new Game();
//
//        p2ShipBoard = gameTest.getP2Grid().getMyShips();
//        p1ShipBoard = gameTest.getP1Grid().getMyShips();
//
//        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);
//
//    }
//
//    @Test
//    public void testInvalidPlacement() {
//        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(myOut));
//        gameTest
//
//        final String standardOutput = myOut.toString().trim();
//        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//
//    }
//
//    @Test
//    public void testPlaceHarbor() {
//        gameTest.getP1Harbor().getLocation();
//    }
//
//    @Test
//    public void testHarborToShip() {
//
//    }
//}
