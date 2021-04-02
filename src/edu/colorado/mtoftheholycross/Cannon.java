package edu.colorado.mtoftheholycross;

public class Cannon extends Weapon {
    public void makeHit(String location, Grid opponentBoard){

        int[] position = opponentBoard.convertPosition(location);
        int hitRow = position[0];
        int hitCol = position[1];

        this.emptyHit();

        if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Ship")) {
            this.setShipHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Captain")){
            this.setShipHit(true);
            this.setCaptainHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Armor")){
            this.setShipHit(true);
            this.setArmorHit(true);
        }
    }
}
