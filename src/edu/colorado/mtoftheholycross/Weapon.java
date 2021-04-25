package edu.colorado.mtoftheholycross;

/**
 * Template Method:
 * Abstract class for weapon
 * This provides a template for all weapons to be made.
 * Cannon, Laser, Sonar, and Hint all use this class.
 */
abstract class Weapon {

    private boolean shipHit = false;
    private boolean captainHit = false;
    private boolean armorHit = false;

    private boolean underShipHit = false;
    private boolean underCaptainHit = false;
    private boolean underArmorHit = false;

    public void emptyHit() {
        shipHit = false;
        captainHit = false;
        armorHit = false;

        underShipHit = false;
        underCaptainHit= false;
        underArmorHit = false;
    }

    /**
     * Initializes a makeHit function that will be overriden based on the type of Weapon used.
     * @param location location of the players attack
     * @param opponentBoard the opponents board/grid
     * @return returns whether or not the hit was valid
     */
    public abstract boolean makeHit(String location, Grid opponentBoard);

    public void setShipHit(boolean isHit) {
        this.shipHit = isHit;
    }

    public void setCaptainHit(boolean captainHit) {
        this.captainHit = captainHit;
    }

    public void setArmorHit(boolean armorHit) {
        this.armorHit = armorHit;
    }

    public void setUnderShipHit(boolean underShipHit) {
        this.underShipHit = underShipHit;
    }

    public void setUnderCaptainHit(boolean underCaptainHit) {
        this.underCaptainHit = underCaptainHit;
    }

    public void setUnderArmorHit(boolean underArmorHit) {
        this.underArmorHit = underArmorHit;
    }

    public boolean getShipHit() {
        return shipHit;
    }

    public boolean getCaptainHit() {
        return captainHit;
    }

    public boolean getArmorHit() {
        return armorHit;
    }

    public boolean getUnderShipHit() {
        return underShipHit;
    }

    public boolean getUnderCaptainHit() {
        return underCaptainHit;
    }

    public boolean getUnderArmorHit() {
        return underArmorHit;
    }
}


