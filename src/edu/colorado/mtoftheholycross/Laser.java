package edu.colorado.mtoftheholycross;

public class Laser extends Cannon {
    public void makeHit(String location, Grid opponentBoard){

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
    }
}
