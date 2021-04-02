package edu.colorado.mtoftheholycross;

public class Weapon {

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


