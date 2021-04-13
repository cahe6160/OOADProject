package edu.colorado.mtoftheholycross;

public class Hint extends Weapon {

    private boolean[] lastShot = {true, true, true, true, true};
    private int index;
    private int hintCount;

    public boolean[] getLastShot() {
        return lastShot;
    }

    public void activateHint(Grid opponentGrid, Ship[] opponentFleet) {
        if(hintCount == 0) {
            System.out.println("Error, not enough hints available.");
            return;
        }
        hintCount--;
        while(true) {
            int ranShip = (int) ((Math.random() * (opponentFleet.length - 1)));
            if (opponentFleet[ranShip].getCasualtyReported() == false) {
                if (opponentFleet[ranShip].getCaptainLocation().charAt(0) <= 'E') {
                    if (opponentFleet[ranShip].getCaptainLocation().charAt(1) <= '5') {
                        System.out.println("Hint: There is a ship located in the upper left quadrant.");
                    } else {
                        System.out.println("Hint: There is a ship located in the lower left quadrant.");
                    }
                } else if (opponentFleet[ranShip].getCaptainLocation().charAt(0) > 'E') {
                    if (opponentFleet[ranShip].getCaptainLocation().charAt(1) > '5') {
                        System.out.println("Hint: There is a ship located in the lower right quadrant.");
                    } else {
                        System.out.println("Hint: There is a ship located in the upper right quadrant.");
                    }
                }
                return;
            }
        }
    }

    public int getHintCount() { return hintCount; }

    public void setHintCount(int hintCount) { this.hintCount = hintCount; }

    public void addHint() {
        hintCount++;
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
