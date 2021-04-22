package edu.colorado.mtoftheholycross;

public class TowerShip extends Ship {
    public TowerShip(String head, boolean submerged) {

        name = "Tower Ship";
        this.head = head;
        this.tail = head;
        this.submerged = submerged;
        captainLocation = head;
    }
}
