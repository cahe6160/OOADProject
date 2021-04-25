package edu.colorado.mtoftheholycross;

/**
 * Minesweeper class, which extends Ship
 * Minesweeper differs in size, and has a different captains location
 */
public class Minesweeper extends Ship {

    public Minesweeper(String head, String tail) {

        name = "Minesweeper";
        this.head = head;
        this.tail = tail;
        captainLocation = head;
    }
}
