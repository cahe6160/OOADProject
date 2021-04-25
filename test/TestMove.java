import edu.colorado.mtoftheholycross.*;
import org.junit.*;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class test the ability to move ships.
 * Includes checking current ship locations, and undo and redo tests.
 */
public class TestMove {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    /**
     * The set up method is used to set up outputStreamCaptor.
     * Specifically, this will help us read console output later on in the class.
     */
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;
    Ship[] p1Input;
    Ship[] p2Input;

    /**
     * Resets the single instance of Game
     * @throws SecurityException Security Exception
     * @throws NoSuchFieldException If the single_instance does not exist
     * @throws IllegalArgumentException Invalid argument
     * @throws IllegalAccessException Illegal Access
     */
    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    /**
     * Initializes some variable that will be manipulated in the coming tests.
     * Also, has some premade ship objects that will be added/used later on.
     */
    @Before
    public void init() {

        gameTest = Game.getInstance();;
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

    }

    /**
     * This test checks that a ship fleet moves North, while there is no blockage.
     */
    @Test
    public void northNoBlockage() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    /**
     * This test checks that a ship fleet moves East, while there is no blockage.
     */
    @Test
    public void eastNoBlockage() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("EAST", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][1].getSurface());
    }

    /**
     * This test checks that a ship fleet does not move East, because of a blockage.
     * Specifically, this checks the command line output is correct.
     */
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

    /**
     * This test checks that a ship fleet does not move North, because of a blockage.
     * Specifically, this checks the command line output is correct.
     */
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

    /**
     * This test checks that a ship fleet does not move North, because of a blockage.
     * Specifically, this checks that the ships do not move on the grid.
     */
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

    /**
     * This test checks that after a move command, a user can undo.
     * Specifically, checks that the ships on the grid move back.
     */
    @Test
    public void northUndo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    /**
     * This test checks that after several move commands, a user can undo more than once.
     * Specifically, checks that the ships on the grid move back.
     */
    @Test
    public void multiUndo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.undoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[6][0].getSurface());
    }

    /**
     * This test checks that after an undo move command, a redo command is done correctly.
     * Specifically, checks that the ships on the grid move correctly.
     */
    @Test
    public void undoRedo() {
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.makeMove("NORTH", gameTest.getP1Grid().getPlayerFleet().toArray(new Ship[gameTest.getP1Grid().getPlayerFleet().size()]), gameTest.getP1Grid());
        gameTest.undoMove();
        gameTest.redoMove();

        assertEquals("Captain", gameTest.getP1Grid().getMyShips()[5][0].getSurface());
    }

    /**
     * This test checks that after a several undo move commands, several redo commands still work.
     * Specifically, checks that the ships on the grid move correctly.
     */
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
