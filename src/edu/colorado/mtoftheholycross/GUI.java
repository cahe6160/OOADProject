package edu.colorado.mtoftheholycross;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * GUI class represents the Interface that is in development for Battleship game.
 * Will not be run in command line.
 */
public class GUI extends javax.swing.JFrame {

    private final JButton shotGridGUI[][] = new JButton[10][10];
    private final JButton shipGridGUI[][] = new JButton[10][10];
    private final JPanel shipPanel = new JPanel();
    private final JPanel shotPanel = new JPanel();

    public GUI() {

        shipPanel.setLayout(new GridBagLayout());
        shipPanel.setBackground(new Color(131, 209, 232));
        shipPanel.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));

        shotPanel.setLayout(new GridBagLayout());
        shotPanel.setBackground(new Color(192, 192, 192));
        shotPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        buildShotBoard();
        buildShipBoard();

        JFrame frame1 = new JFrame("frame1");
        frame1.setTitle("My Ships");
        frame1.setPreferredSize(new Dimension(400, 400));
        frame1.setResizable(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.add(shipPanel);
        frame1.pack();
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("frame2");
        frame2.setTitle("My Shots");
        frame2.setPreferredSize(new Dimension(400, 400));
        frame2.setResizable(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.add(shotPanel);
        frame2.pack();
        frame2.setVisible(true);

    }

    /**
     * Builds a 10x10 grid of clickable buttons, specific for a players ship board.
     */
    private void buildShipBoard() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        for (int col = 0; col < 10; col++)
        {
            for (int row = 0; row < 10; row++)
            {
                JButton button = new JButton();
                button.setBackground(new Color(131, 209, 232));
                button.setBorder(BorderFactory.createLineBorder(new Color(32, 156, 185)));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setPreferredSize(new java.awt.Dimension(34, 34));

                setShipGrid(col, row, button);

                c.gridx = col;
                c.gridy = row;

                shipPanel.add(button, c);
            }
        }
    }

    /**
     * Builds a 10x10 grid of clickable buttons, specific for a players shot board.
     */
    private void buildShotBoard() {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        for (int col = 0; col < 10; col++)
        {
            for (int row = 0; row < 10; row++)
            {
                JButton button = new JButton();
                button.setBackground(new Color(192, 192, 192));
                button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                button.setPreferredSize(new java.awt.Dimension(34, 34));
                button.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mousePressed(MouseEvent event)
                    {
                        JButton button = (JButton) event.getSource();
                        Rectangle rectangle = button.getBounds();
                        Point point = button.getLocation();

                        int row = point.y / rectangle.height + 1;
                        int col = (point.x / rectangle.width + 65);
                        char col1 = (char)col;

                        System.out.println("SHOT AT " + col1 + row);
                        button.setBackground(Color.RED);

                    }
                });

                setShotGrid(col, row, button);

                c.gridx = col;
                c.gridy = row;

                shotPanel.add(button, c);
            }
        }
    }

    private void setShotGrid(int x, int y, JButton button) {
        shotGridGUI[y][x] = button;
    }

    private void setShipGrid(int x, int y, JButton button) {
        shipGridGUI[y][x] = button;
    }
}