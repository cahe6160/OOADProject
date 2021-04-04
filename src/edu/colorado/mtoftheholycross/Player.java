package edu.colorado.mtoftheholycross;

public class Player {

    private int shipCount;
    private int sonarCount;
    private Sonar sonar;
    private Cannon cannon;
    private Laser laser;
    private boolean[] lastShot = {true, true, true, true, true};
    private int index;


    Player(){
        this.shipCount = 3;
        this.sonarCount = 2;
        sonar = new Sonar();
        cannon = new Cannon();
        laser = new Laser();
    }

    public Cannon getCannon() {
        return cannon;
    }

    public Sonar getSonar() {
        return sonar;
    }

    public Laser getLaser() {
        return laser;
    }

    public int getShipCount() {
        return shipCount;
    }

    public int getSonarCount() {
        return sonarCount;
    }

    public boolean[] getLastShot() { return lastShot;}

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public void setSonarCount(int sonarCount) {
        this.sonarCount = sonarCount;
    }

    public void setLastShot(boolean hitMiss) {
        lastShot[index % 5] = hitMiss;
        index++;
    }
    public void resetLastShot() {
        for(int i=0;i<5;i++) {
            this.lastShot[i] = true;
        }
    }

}