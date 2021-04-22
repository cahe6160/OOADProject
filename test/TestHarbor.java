//import edu.colorado.mtoftheholycross.Cell;
//import edu.colorado.mtoftheholycross.Game;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileDescriptor;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
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
//        gameTest.getP1Grid().setPlayerFleet(new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)});
//        gameTest.getP2Grid().setPlayerFleet(new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)});
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