import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.assertEquals;

public class TestHighScores {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Ship[] p1Input;
    Ship[] p2Input;

    @Before
    public void resetSingleton() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field instance = Game.class.getDeclaredField("single_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Before
    public void init() {

        gameTest = Game.getInstance();
        p1Input = new Ship[]{new Minesweeper("A7", "A8"), new Destroyer("B7", "B9"), new Battleship("J1", "J4"), new Submarine("D7", "D10", false)};
        p2Input = new Ship[]{new Minesweeper("A1", "A2"), new Destroyer("B1", "B3"), new Battleship("C1", "C4"), new Submarine("D1", "D4", false), new Minesweeper("A3", "A4"), new Minesweeper("B4", "B5"), new TowerShip("J10", true)};
    }

    @Test
    public void scoreOutput() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP2Grid().addShip(p2Input[0]);
        gameTest.getP1Grid().addShip(p1Input[0]);
        //P1 Shoots at P2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.switchTurn();
        //P2 Shoots at P1
        gameTest.getP2().getCannon().makeHit("D5", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("D5", gameTest.getP2().getCannon());
        gameTest.getP2Grid().updateBoards("D5", gameTest.getP2().getCannon());
        gameTest.switchTurn();
        //P1 Shoots at P2
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.switchTurn();

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("You sunk my Minesweeper\nPlayer 2 surrendered!\nYour score this game was 2 turns!", standardOutput);

    }

    @Test
    public void correctTest() {

        gameTest.getP2Grid().addShip(p2Input[0]);
        gameTest.getP1Grid().addShip(p1Input[0]);
        gameTest.getP1().setPlayerName("Will");
        //P1 Shoots at P2
        gameTest.getP1().getCannon().makeHit("A2", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A2", gameTest.getP1().getCannon());
        gameTest.switchTurn();
        //P2 Shoots at P1
        gameTest.getP2().getCannon().makeHit("D5", gameTest.getP1Grid());
        gameTest.getP1Grid().updateBoards("D5", gameTest.getP2().getCannon());
        gameTest.getP2Grid().updateBoards("D5", gameTest.getP2().getCannon());
        gameTest.switchTurn();
        //P1 Shoots at P2
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.switchTurn();

        File highScores = new File("src/resources/HighScores.txt");
        File testFile = new File("src/resources/testFile.txt");
        Scanner oldScan = null;
        Scanner newScan = null;

        try {
            oldScan = new Scanner(testFile);
            newScan = new Scanner(highScores);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> oldFile = new ArrayList<String>();
        List<String> newFile = new ArrayList<String>();

        while(oldScan.hasNextLine()) {
            oldFile.add(oldScan.nextLine());
        }
        while(newScan.hasNextLine()) {
            newFile.add(newScan.nextLine());
        }
        assertEquals(oldFile, newFile);
    }
}