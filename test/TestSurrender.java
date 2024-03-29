import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class tests that playerSurrender methods are working.
 * This will test command line output and that variables are updated.
 */
public class TestSurrender {

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
    Ship[] p1Input;
    Ship[] p2Input;

    Cell[][] p1ShipBoard;
    Cell[][] p2ShipBoard;

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

        gameTest.getP2Grid().addShip(p2Input[0]);//A1, A2
        gameTest.getP2Grid().addShip(p2Input[1]);//B1, B2, B3
        gameTest.getP2Grid().addShip(p2Input[2]);//C1, C2, C3, C4

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This tests that after all ships and their respective location have taken damage, playerSurrender returns that a player has no ships left.
     */
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
        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        //Shot 6
        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
        //Shot 7
        gameTest.getP1().getCannon().makeHit("C1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C1", gameTest.getP1().getCannon());
        //Shot 8
        gameTest.getP1().getCannon().makeHit("C2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C2", gameTest.getP1().getCannon());
        //Shot 9
        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        //Shot 10
        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        //Shot 11
        gameTest.getP1().getCannon().makeHit("C4", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C4", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C4", gameTest.getP1().getCannon());


        assertEquals(true, gameTest.playerSurrender());
    }

    /**
     * This tests that after all ships and their respective location have taken damage, playerSurrender returns that a player has no ships left.
     * Specifically, this test command line output after a player has surrendered.
     */
    @Test
    public void testPlayerSurrender() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
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
        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
        //Shot 6
        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
        //Shot 7
        gameTest.getP1().getCannon().makeHit("C1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C1", gameTest.getP1().getCannon());
        //Shot 8
        gameTest.getP1().getCannon().makeHit("C2", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C2", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C2", gameTest.getP1().getCannon());
        //Shot 9
        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        //Shot 10
        gameTest.getP1().getCannon().makeHit("C3", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C3", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C3", gameTest.getP1().getCannon());
        //Shot 11
        gameTest.getP1().getCannon().makeHit("C4", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("C4", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("C4", gameTest.getP1().getCannon());

        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nYou sunk my Destroyer\nYou sunk my Battleship\nPlayer 2 surrendered!\nYour score this game was 1 turns!", standardOutput);

    }
}
