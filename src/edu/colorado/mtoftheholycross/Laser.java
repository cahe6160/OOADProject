package edu.colorado.mtoftheholycross;

public class Laser extends Weapon {
    public int[] makeHit(String location, Grid opponentBoard){

        int[] hitResults = new int[4];
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

        if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Ship")) {
            hitResults[3] = 0;
            hitResults[2] = 1;
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Captain")){
            hitResults[3] = 1;
            hitResults[2] = 1;
        } else if(opponentBoard.getMyShips()[hitRow][hitCol].getUnderwater().equals("Armor")){
            hitResults[3] = 2;
            hitResults[2] = 1;
        }

        return hitResults;
    }
}
