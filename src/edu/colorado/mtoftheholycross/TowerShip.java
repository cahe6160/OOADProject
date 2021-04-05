package edu.colorado.mtoftheholycross;

public class TowerShip extends Ship {
    public TowerShip(String head, String tail, boolean submerged) {

        name = "Tower Ship";
        this.head = head;
        this.tail = tail;
        this.submerged = submerged;
        captainLocation = tail;
    }
}
