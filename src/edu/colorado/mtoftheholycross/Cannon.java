package edu.colorado.mtoftheholycross;

public class Cannon extends Weapon {
    public int[] makeHit(String location, Grid opponentBoard){

        int[] hitResults = new int[2];
        int[] position = opponentBoard.convertPosition(location);
        int hitRow = position[0];
        int hitCol = position[1];

        if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Ship")) {
            hitResults[1] = 0;
            hitResults[0] = 1;
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Captain")){
            hitResults[1] = 1;
            hitResults[0] = 1;
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getSurface().equals("Armor")){
            hitResults[1] = 2;
            hitResults[0] = 1;
        }

        return hitResults;
    }
}
