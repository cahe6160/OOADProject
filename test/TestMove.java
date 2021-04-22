import edu.colorado.mtoftheholycross.*;
import org.junit.*;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;

public class TestMove {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;
    Ship[] p1Input;
    Ship[] p2Input;

    @Before
    public void init() {

        gameTest = new Game();
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

    }

    @Test
    public void northNoBlockage() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    @Test
    public void eastNoBlockage() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("EAST", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][1].getSurface());
    }

    @Test
    public void eastEdge() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1Grid().addShip(p1Input[2]);
        gameTest.makeMove("EAST", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, at least one ship is too far east, unable to move fleet.", standardOutput);
    }

    @Test
    public void northEdge() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1Grid().addShip(p1Input[2]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, at least one ship is too far north, unable to move fleet.", standardOutput);
    }

    @Test
    public void northBlockage() {
        gameTest.getP2Grid().addShip(p2Input[0]);
        gameTest.getP2Grid().addShip(p2Input[5]);
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.switchTurn();

        gameTest.getP2Grid().addShip(p2Input[4]);
        gameTest.makeMove("NORTH", gameTest.getP2Grid().getPlayerFleet().toArray(new Ship[gameTest.getP2Grid().getPlayerFleet().size()]), gameTest.getP2Grid());

        assertEquals("Captain", gameTest.getP2Grid().getMyShips()[1][0].getSurface());
    }

    @Test
    public void northUndo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    @Test
    public void multiUndo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    @Test
    public void undoRedo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.redoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    @Test
    public void multiUndoRedo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.undoMove();
        gameTest.redoMove();
        gameTest.redoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[4][0].getSurface());
    }
}
