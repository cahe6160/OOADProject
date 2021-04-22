package edu.colorado.mtoftheholycross;

public class Cannon extends Weapon {
    @Override
    public boolean makeHit(String location, Grid opponentBoard){

        int[] position = opponentBoard.convertPosition(location);
        int hitRow = position[0];
        int hitCol = position[1];

        this.emptyHit();
        if(hitRow < 0 || hitRow > 9 || hitCol < 0 || hitCol > 9) {
            System.out.print("Error, hit location out of bounds.");
            return false;
        }


        if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Ship")) {
            this.setShipHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Captain")){
            this.setShipHit(true);
            this.setCaptainHit(true);
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Armor")){
            this.setShipHit(true);
            this.setArmorHit(true);
        }
        return true;
    }
}
