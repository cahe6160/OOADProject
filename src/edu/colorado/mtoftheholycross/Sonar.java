package edu.colorado.mtoftheholycross;

/**
 * Sonar class, which is an extension from Weapon
 * Sonar fires, and reveals a portion of the opponents ships,
 * from a given location.
 */
public class Sonar extends Weapon {

    private int sonarCount = 3;

    private int[][] getBounds(int[] centerLocation){
        int[][] bounds = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};

        //Bottom right
        bounds[0][0] = centerLocation[0] + 2;
        bounds[0][1] = centerLocation[1] + 2;

        //Top left
        bounds[1][0] = centerLocation[0] - 2;
        bounds[1][1] = centerLocation[1] - 2;

        //Bottom left
        bounds[2][0] = centerLocation[0] + 2;
        bounds[2][1] = centerLocation[1] - 2;

        //Top right
        bounds[3][0] = centerLocation[0] - 2;
        bounds[3][1] = centerLocation[1] + 2;

        return bounds;
    }

    /**
     * If sonar has been made available to the player,
     * it reveals a 5x5 section of the opponents grid at the location given to it.
     * @param location location where user decided to use Sonar
     * @param opponentGrid the opponents grid
     * @return whether or not the sonar was used appropriately
     */
    @Override
    public boolean makeHit(String location, Grid opponentGrid){

        int[] sonarCenter = opponentGrid.convertPosition(location);

        if(sonarCount == 0){
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(opponentGrid.getShipCount() == opponentGrid.getPlayerFleet().size()) {
            System.out.println("Error, at least one ship must be sunk in order to activate sonar.");
            return false;
        }
        int[][] bounds = getBounds(sonarCenter);
        for(int i = 0; i < 4; i++){
            for( int j = 0; j < 2; j ++){
                if(bounds[i][j] < 0 || bounds[i][j] > 9){
                    System.out.println("Error, sonar center out of bounds. Please choose another coordinate.");
                    return false;
                }
            }
        }


        Cell[][] opponentBoard = opponentGrid.getMyShips();
        String[][] sonarArray = {{"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}};
        int counter = 0;
        for(int i = bounds[1][0]; i < bounds[2][0] + 1; i++){
            int jCounter = 0;
            for(int j = bounds[1][1]; j < bounds[3][1] + 1; j++){
                if(!opponentBoard[i][j].getSurface().equals("Sea") && !opponentBoard[i][j].getUnderwater().equals("Sea")){
                    sonarArray[counter][jCounter] = "Ship/Ship";
                } else if(opponentBoard[i][j].getSurface().equals("Sea") && !opponentBoard[i][j].getUnderwater().equals("Sea")){
                    sonarArray[counter][jCounter] = "Sea/Ship";
                } else if(!opponentBoard[i][j].getSurface().equals("Sea") && opponentBoard[i][j].getUnderwater().equals("Sea")){
                    sonarArray[counter][jCounter] = "Ship/Sea";
                } else {
                    sonarArray[counter][jCounter] = "Sea/Sea";
                }
                jCounter++;
            }
            counter++;
        }

        sonarArray[0][0] = "Fog/Fog";
        sonarArray[0][1] = "Fog/Fog";
        sonarArray[1][0] = "Fog/Fog";

        sonarArray[3][0] = "Fog/Fog";
        sonarArray[4][0] = "Fog/Fog";
        sonarArray[4][1] = "Fog/Fog";

        sonarArray[0][4] = "Fog/Fog";
        sonarArray[0][3] = "Fog/Fog";
        sonarArray[1][4] = "Fog/Fog";

        sonarArray[4][4] = "Fog/Fog";
        sonarArray[4][3] = "Fog/Fog";
        sonarArray[3][4] = "Fog/Fog";

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(sonarArray[i][j] + " ");
            }
            System.out.println();
        }
        sonarCount--;
        return true;
    }

    public int getSonarCount() {
        return sonarCount;
    }

    public void setSonarCount(int sonarCount1) {
        this.sonarCount = sonarCount1;
    }
}
