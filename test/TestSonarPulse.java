import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
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

public class TestSonarPulse {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Game gameTest;
    Cell[][] p2ShipBoard;
    Cell[][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1TestFleet()[2]);
        gameTest.getP2Grid().addShip(gameTest.getP2TestFleet()[2]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void noCharges(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1().setSonarCount(0);

        gameTest.getP1().getSonar().activateSonar("A3", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Error, no sonar charges remain.", standardOutput);
    }

    @Test
    public void noShipSunk(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP1().getSonar().activateSonar("A3", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("Error, at least one ship must be sunk in order to activate sonar.", standardOutput);
    }

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
        gameTest.getP1().getSonar().activateSonar("E6", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

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
        gameTest.getP1().getSonar().activateSonar("E6", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

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
        gameTest.getP1().getSonar().activateSonar("D5", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

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
        gameTest.getP1().getSonar().activateSonar("C3", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String testStandardOutput = myTestOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals(standardOutput, testStandardOutput);
    }

    @Test
    public void outOfBounds(){
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        gameTest.getP2Grid().setShipCount(2);
        gameTest.getP1().getSonar().activateSonar("J10", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));

        assertEquals("Error, sonar center out of bounds. Please choose another coordinate.", standardOutput);
    }
}