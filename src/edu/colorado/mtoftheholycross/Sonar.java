package edu.colorado.mtoftheholycross;

public class Sonar extends Weapon {

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

    public boolean activateSonar(String location, Grid p1Grid, Grid p2Grid, Player P1, Player P2){

        int[] sonarCenter = p1Grid.convertPosition(location);

        if(!p1Grid.getIsWaiting() && P1.getSonarCount() == 0){
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(!p1Grid.getIsWaiting() && p2Grid.getShipCount() == p2Grid.getPlayerFleet().size()) {
            System.out.println("Error, at least one ship must be sunk in order to activate sonar.");
            return false;
        } else if(p1Grid.getIsWaiting() && P2.getSonarCount() == 0) {
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(p1Grid.getIsWaiting() && p1Grid.getShipCount() == p1Grid.getPlayerFleet().size()) {
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

        if(p1Grid.getIsWaiting()){
            Cell[][] p1Board = p1Grid.getMyShips();
            String[][] sonarArray = {{"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}};
            int counter = 0;
            for(int i = bounds[1][0]; i < bounds[2][0] + 1; i++){
                int jCounter = 0;
                for(int j = bounds[1][1]; j < bounds[3][1] + 1; j++){
                    if(!p1Board[i][j].getSurface().equals("Sea") && !p1Board[i][j].getUnderwater().equals("Sea")){
                        sonarArray[counter][jCounter] = "Ship/Ship";
                    } else if(p1Board[i][j].getSurface().equals("Sea") && !p1Board[i][j].getUnderwater().equals("Sea")){
                        sonarArray[counter][jCounter] = "Sea/Ship";
                    } else if(!p1Board[i][j].getSurface().equals("Sea") && p1Board[i][j].getUnderwater().equals("Sea")){
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
        } else {
            Cell[][] p2Board = p2Grid.getMyShips();
            String[][] sonarArray = {{"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}, {"Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog", "Fog/Fog"}};
            int counter = 0;
            for(int i = bounds[1][0]; i < bounds[2][0] + 1; i++){
                int jCounter = 0;
                for(int j = bounds[1][1]; j < bounds[3][1] + 1; j++){
                    if(!p2Board[i][j].getSurface().equals("Sea") && !p2Board[i][j].getUnderwater().equals("Sea")){
                        sonarArray[counter][jCounter] = "Ship/Ship";
                    } else if(p2Board[i][j].getSurface().equals("Sea") && !p2Board[i][j].getUnderwater().equals("Sea")){
                        sonarArray[counter][jCounter] = "Sea/Ship";
                    } else if(!p2Board[i][j].getSurface().equals("Sea") && p2Board[i][j].getUnderwater().equals("Sea")){
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
        }
        return true;
    }
}
