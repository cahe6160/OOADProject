import apple.laf.JRSUIConstants;
import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;

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

    Game gameTest;
    String[][] p2ShipBoard;
    String [][] p1ShipBoard;

    @Before
    public void init() {
        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[0]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();
    }

    @Test
    public void noCharges(){
        assertEquals(null, gameTest.getP1().activateSonar("A3"));
    }

    @Test
    public void noShipSunk(){
        assertEquals(null, gameTest.getP1().activateSonar("A3"));
    }

    @Test
    public void sonarActivation(){
        String[][] referenceMap = {{"SHIP", "SHIP", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}, {"FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG", "FOG"}}
        assertEquals(referenceMap, gameTest.getP1().activateSonar("A3"));
    }
}