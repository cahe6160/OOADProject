package edu.colorado.mtoftheholycross;

/**
 * TowerShip class, which extends Ship
 * TowerShip differs in size, must be placed both on the surface and underwater,
 * and has a different captains location
 */
public class TowerShip extends Ship {
    public TowerShip(String head, boolean submerged) {

        name = "Tower Ship";
        this.head = head;
        this.tail = head;
        this.submerged = submerged;
        captainLocation = head;
    }
}
