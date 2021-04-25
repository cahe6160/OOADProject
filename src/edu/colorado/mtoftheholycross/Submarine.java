package edu.colorado.mtoftheholycross;

/**
 * Submarine class, which extends Ship
 * Submarine differs in size, can be placed underwater, and has a different captains location
 */
public class Submarine extends Ship {

    public Submarine(String head, String tail, boolean submerged) {

        name = "Submarine";
        this.head = head;
        this.tail = tail;
        this.submerged = submerged;
        captainLocation = tail;
    }
}
