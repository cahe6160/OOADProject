package edu.colorado.mtoftheholycross;

/**
 * Battleship class, which extends Ship
 * Battleship differs in size, and has a different captains location
 */
public class Battleship extends Ship {

    public Battleship(String head, String tail) {

        name = "Battleship";
        this.head = head;
        this.tail = tail;
        if(this.head.substring(0, 1).equals(this.tail.substring(0, 1))) {
            this.captainLocation = this.head.substring(0, 1) + (Integer.parseInt(this.head.substring(1)) + 2);
        } else{
            this.captainLocation = (this.head.charAt(0) + 2) + this.head.substring(1);
        }
    }
}

