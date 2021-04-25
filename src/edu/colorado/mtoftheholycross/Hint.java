package edu.colorado.mtoftheholycross;

/**
 * Hint class, extends from Weapon.
 * If available this one fires, and gives a hint of where their ships might be
 */
public class Hint extends Weapon {

    private boolean[] lastShot = {true, true, true, true, true};
    private int index;
    private int hintCount;

    public boolean[] getLastShot() {
        return lastShot;
    }

    /**
     * If there are hints available, earned after 5 straight misses,
     * reports a quadrant where an opponents ship is located.
     * @param location location of strike attack
     * @param opponentGrid this is the opponents board/grid
     * @return whether or not the Hint was valid
     */
    public boolean makeHit(String location, Grid opponentGrid) {
        if(hintCount == 0) {
            System.out.println("Error, not enough hints available.");
            return false;
        }
        hintCount--;
        while(true) {
            int ranShip = (int) ((Math.random() * (opponentGrid.getPlayerFleet().size() - 1)));
            if (opponentGrid.getPlayerFleet().get(ranShip).getCasualtyReported() == false) {
                if (opponentGrid.getPlayerFleet().get(ranShip).getCaptainLocation().charAt(0) <= 'E') {
                    if (opponentGrid.getPlayerFleet().get(ranShip).getCaptainLocation().charAt(1) <= '5') {
                        System.out.println("Hint: There is a ship located in the upper left quadrant.");
                    } else {
                        System.out.println("Hint: There is a ship located in the lower left quadrant.");
                    }
                } else if (opponentGrid.getPlayerFleet().get(ranShip).getCaptainLocation().charAt(0) > 'E') {
                    if (opponentGrid.getPlayerFleet().get(ranShip).getCaptainLocation().charAt(1) > '5') {
                        System.out.println("Hint: There is a ship located in the lower right quadrant.");
                    } else {
                        System.out.println("Hint: There is a ship located in the upper right quadrant.");
                    }
                }
                return true;
            }
        }
    }

    public int getHintCount() { return hintCount; }

    public void setHintCount(int hintCount) { this.hintCount = hintCount; }

    public void addHint() {
        hintCount++;
    }

    /**
     * Adds the shot results into the corresponding spot, and increments the index.
     * @param hitMiss Either true or false, represents recent shot results.
     */
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
