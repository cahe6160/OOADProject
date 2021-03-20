package edu.colorado.mtoftheholycross;

public class Minesweeper extends Ship {

    public Minesweeper(String head, String tail) {

        name = "Minesweeper";
        this.head = head;
        this.tail = tail;
        captainLocation = head;
    }
}
