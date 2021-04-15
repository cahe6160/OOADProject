import edu.colorado.mtoftheholycross.Game;
import org.junit.*;
import org.junit.Test;
import java.io.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.Assert.*;

public class TestHighScores {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;

    @Before
    public void init() {

        gameTest = new Game();
    }

    @Test
    public void scoreOutput() {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1Grid().addShip(gameTest.getP2Fleet()[0]);
        gameTest.getP2().getCannon().makeHit("A1", gameTest.getP1Grid());
        gameTest.getP2().getCannon().makeHit("A2", gameTest.getP1Grid());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Your score this game was 2 turns!", standardOutput);

    }

    @Test
    public void correctTest() {

        gameTest.getP1Grid().addShip(gameTest.getP2Fleet()[0]);
        gameTest.getP2().getCannon().makeHit("A1", gameTest.getP1Grid());
        gameTest.getP2().getCannon().makeHit("A2", gameTest.getP1Grid());

        try {
            byte[] oldFile = Files.readAllBytes(Paths.get("src/resources/HighScores.txt"));
            byte[] newFile = Files.readAllBytes(Paths.get("src/resources/testFile.txt"));
            assertArrayEquals(oldFile, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}