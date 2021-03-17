package edu.colorado.mtoftheholycross;

public class Minesweeper extends Ship {

    public Minesweeper(String head, String tail) {
        casualtyReported = false;
        name = "Minesweeper";
        this.head = head;
        this.tail = tail;
        captainLocation = head;
    }
}
