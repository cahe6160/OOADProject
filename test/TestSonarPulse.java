import edu.colorado.mtoftheholycross.*;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;

/**
 * This class' methods are used to test the new weapon, the Sonar Pulse.
 * Again, this checks command line output and other variables.
 */
public class TestSonarPulse {

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

        gameTest.getP1Grid().addShip(p1Input[2]);
        gameTest.getP2Grid().addShip(p2Input[2]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    /**
     * This tests the output of the command line, when a user
     * tries to use the Sonar when they have none remaining.
     */
    @Test
    public void noCharges(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1().getSonar().setSonarCount(0);

        gameTest.getP1().getSonar().makeHit("A3", gameTest.getP2Grid());//activateSonar

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Error, no sonar charges remain.", standardOutput);
    }

    /**
     * This tests the output of the command line, when a user
     * tries to use the Sonar without sinking an opposing ship first.
     */
    @Test
    public void noShipSunk(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1().getSonar().makeHit("A3", gameTest.getP2Grid());//activateSonar

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Error, at least one ship must be sunk in order to activate sonar.", standardOutput);
    }

    /**
     * This tests the output of a Sonar Pulse, when the pulse does not detect a ship in the vicinity.
     */
    @Test
    public void noShipDetected(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String[][] referenceMap = {{"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea"}, {"Fog/Fog", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}};
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(referenceMap[i][j] + " ");
            }
            System.out.println();
        }

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        final ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().makeHit("E6", gameTest.getP2Grid());//activateSonar

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

    /**
     * This tests the output of a Sonar Pulse, when the pulse partially detects a ship, but is still in the fog.
     */
    @Test
    public void shipInFog(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String[][] referenceMap = {{"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea"}, {"Fog/Fog", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}};
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(referenceMap[i][j] + " ");
            }
            System.out.println();
        }

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        final ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().makeHit("E6", gameTest.getP2Grid());//activateSonar

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

    /**
     * This tests the output of a Sonar Pulse, when the pulse partially detects a ship in the vicinity.
     */
    @Test
    public void shipPartiallyInFog(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String[][] referenceMap = {{"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Ship/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Sea/Sea"}, {"Fog/Fog", "Sea/Sea", "Sea/Sea", "Sea/Sea", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}};
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(referenceMap[i][j] + " ");
            }
            System.out.println();
        }

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        final ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().makeHit("D5", gameTest.getP2Grid());//activateSonar

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

    /**
     * This tests the output of a Sonar Pulse, when the pulse fully detects a ship in the vicinity.
     */
    @Test
    public void fullyDetected(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        String[][] referenceMap = {{"Fog/Fog", "Fog/Fog", "Ship/Sea", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Sea/Sea", "Ship/Sea", "Sea/Sea", "Fog/Fog"}, {"Sea/Sea", "Sea/Sea", "Ship/Sea", "Sea/Sea", "Sea/Sea"}, {"Fog/Fog", "Sea/Sea", "Ship/Sea", "Sea/Sea", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Sea/Sea", "Fog/Fog", "Fog/Fog"}};
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(referenceMap[i][j] + " ");
            }
            System.out.println();
        }

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        final ByteArrayOutputStream myTestOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myTestOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().makeHit("C3", gameTest.getP2Grid());//activateSonar

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

    /**
     * This tests the command line output when a sonar location is too close ot the edge of the grid.
     */
    @Test
    public void outOfBounds(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().makeHit("J10", gameTest.getP2Grid());//activateSonar

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, sonar center out of bounds. Please choose another coordinate.", standardOutput);
    }
}