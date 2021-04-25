package edu.colorado.mtoftheholycross;

/**
 * Laser class, which is an extension from Canon
 * Laser fires, and now it hits Ships on the waters surface and those that are underwater.
 */
public class Laser extends Cannon {

    /**
     * Determines the results of a laser strike,
     * and updates attributes of the weapon accordingly.
     * @param location location of strike attack
     * @param opponentBoard this is the oppnents board/grid
     * @return whether or not the hit location was valid.
     */
    @Override
    public boolean makeHit(String location, Grid opponentBoard){

        super.makeHit(location, opponentBoard);

        int[] position = opponentBoard.convertPosition(location);
        int hitRow = position[0];
        int hitCol = position[1];

        if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Ship")) {
            this.setUnderShipHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Captain")){
            this.setUnderShipHit(true);
            this.setUnderCaptainHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Armor")) {
            this.setUnderShipHit(true);
            this.setUnderArmorHit(true);
        }
        return true;
    }
}
