package edu.colorado.mtoftheholycross;

public class Submarine extends Ship {

    public Submarine(String head, String tail, boolean submerged) {

        name = "Submarine";
        this.head = head;
        this.tail = tail;
        this.submerged = submerged;
        captainLocation = tail;
    }
}
