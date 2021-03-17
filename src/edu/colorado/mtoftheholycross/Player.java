package edu.colorado.mtoftheholycross;

public class Player {
    private int shipCount;
    private int sonarCount;
    private Sonar sonar;
    private Cannon cannon;


    Player(){
        this.shipCount = 3;
        this.sonarCount = 2;
        sonar = new Sonar();
        cannon = new Cannon();
    }

    public Cannon getCannon() {
        return cannon;
    }

    public Sonar getSonar() {
        return sonar;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int getSonarCount() {
        return sonarCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public void setSonarCount(int sonarCount) {
        this.sonarCount = sonarCount;
    }
}