package edu.colorado.mtoftheholycross;

public class Player {
    private
        int shipCount;
        int sonarCount;

    Player(){
        this.shipCount = 3;
        this.sonarCount = 2;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int getSonarPulse() {
        return sonarCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public void setSonarPulse(int sonarCount) {
        this.sonarCount = sonarCount;
    }
}