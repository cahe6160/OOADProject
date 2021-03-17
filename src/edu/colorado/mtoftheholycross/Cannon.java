package edu.colorado.mtoftheholycross;

public class Cannon extends Weapon {
    public int[] makeHit(String location, Grid opponentBoard){

        int[] hitResults = new int[2];
        int[] position = opponentBoard.convertPosition(location);

        if(opponentBoard.getMyShips()[position[1]][position[0]].equals("Ship")) {
            hitResults[0] = 1;
        } else if(opponentBoard.getMyShips()[position[1]][position[0]].equals("Captain")){
            hitResults[1] = 1;
            hitResults[0] = 1;
        } else if(opponentBoard.getMyShips()[position[1]][position[0]].equals("Armor")){
            hitResults[1] = 2;
            hitResults[0] = 1;
        }

        return hitResults;
    }
}
