import edu.colorado.mtoftheholycross.Game;
import edu.colorado.mtoftheholycross.Minesweeper;
import edu.colorado.mtoftheholycross.Ship;
import edu.colorado.mtoftheholycross.Grid;
import edu.colorado.mtoftheholycross.Submarine;
import edu.colorado.mtoftheholycross.Cell;
import edu.colorado.mtoftheholycross.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.junit.*;
import org.junit.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGUI {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private GUI graphics;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testCellClick() {

        graphics = new GUI();
        Robot testBot = null;
        try {
            testBot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        testBot.mouseMove(50, 50);
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));
        testBot.mousePress(InputEvent.BUTTON1_MASK);
        testBot.mouseRelease(InputEvent.BUTTON1_MASK);
        final String standardOutput = myOut.toString().trim();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        assertEquals("SHOT AT A1", standardOutput);
    }

    @Test
    public void testHit() {

        Robot testBot = null;
        try {
            testBot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        testBot.mouseMove(50, 50);
        testBot.mousePress(InputEvent.BUTTON1_MASK);
        testBot.mouseRelease(InputEvent.BUTTON1_MASK);

        assertEquals(Color.GREEN, testBot.getPixelColor(50, 50));
    }

    @Test
    public void testMiss() {

        Robot testBot = null;
        try {
            testBot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        testBot.mouseMove(50, 50);
        testBot.mousePress(InputEvent.BUTTON1_MASK);
        testBot.mouseRelease(InputEvent.BUTTON1_MASK);

        assertEquals(Color.RED, testBot.getPixelColor(50, 50));
    }
}