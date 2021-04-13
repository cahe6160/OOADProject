package edu.colorado.mtoftheholycross;

public class Player {

    private int shipCount;
    private int sonarCount;
    private Sonar sonar;
    private Cannon cannon;
    private Laser laser;
    private Hint hint;



    Player(){
        this.shipCount = 3;
        this.sonarCount = 2;
        sonar = new Sonar();
        cannon = new Cannon();
        laser = new Laser();
        hint = new Hint();
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

    public Hint getHint() { return hint; }

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