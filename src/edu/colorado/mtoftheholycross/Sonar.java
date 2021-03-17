package edu.colorado.mtoftheholycross;

public class Sonar extends Weapon {

    private int[][] getBounds(int[] centerLocation){
        int[][] bounds = {{-1, -1}, {-1, -1}, {-1, -1}, {-1, -1}};

        bounds[0][0] = centerLocation[0] + 2;
        bounds[0][1] = centerLocation[1] + 2;

        bounds[1][0] = centerLocation[0] - 2;
        bounds[1][1] = centerLocation[1] - 2;

        bounds[2][0] = centerLocation[0] + 2;
        bounds[2][1] = centerLocation[1] - 2;

        bounds[3][0] = centerLocation[0] - 2;
        bounds[3][1] = centerLocation[1] + 2;

        return bounds;
    }

    public boolean activateSonar(String location, Grid p1Grid, Grid p2Grid, Player P1, Player P2){

        int[] sonarCenter = p1Grid.convertPosition(location);

        if(!p1Grid.isWaiting && P1.getSonarCount() == 0){
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(!p1Grid.isWaiting && P2.getShipCount() == 3) {
            System.out.println("Error, at least one ship must be sunk in order to activate sonar.");
            return false;
        } else if(p1Grid.isWaiting && P2.getSonarCount() == 0) {
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(p1Grid.isWaiting && P1.getShipCount() == 3) {
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

        if(p1Grid.isWaiting){
            String[][] p1Board = p1Grid.getMyShips();
            String[][] sonarArray = {{"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}};
            int counter = 0;
            for(int i = bounds[1][1]; i < bounds[3][1] + 1; i++){
                int jCounter = 0;
                for(int j = bounds[1][0]; j < bounds[0][0] + 1; j++){
                    if(!p1Board[i][j].equals("Sea")){
                        System.out.println(sonarArray[counter][jCounter]);
                        sonarArray[counter][jCounter] = "Ship";
                    } else {
                        sonarArray[counter][jCounter] = "Sea";
                    }
                    jCounter++;
                }
                counter++;
            }

            sonarArray[0][0] = "Fog";
            sonarArray[0][1] = "Fog";
            sonarArray[1][0] = "Fog";

            sonarArray[3][0] = "Fog";
            sonarArray[4][0] = "Fog";
            sonarArray[4][1] = "Fog";

            sonarArray[0][4] = "Fog";
            sonarArray[0][3] = "Fog";
            sonarArray[1][4] = "Fog";

            sonarArray[4][4] = "Fog";
            sonarArray[4][3] = "Fog";
            sonarArray[3][4] = "Fog";

            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    System.out.print(sonarArray[i][j] + " ");
                }
                System.out.println();
            }
        } else {
            String[][] p2Board = p2Grid.getMyShips();
            String[][] sonarArray = {{"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}, {"Fog", "Fog", "Fog", "Fog", "Fog"}};
            int counter = 0;
            for(int i = bounds[1][1]; i < bounds[3][1] + 1; i++){
                int jCounter = 0;
                for(int j = bounds[1][0]; j < bounds[0][0] + 1; j++){
                    if(!p2Board[i][j].equals("Sea")){
                        sonarArray[counter][jCounter] = "Ship";
                    } else {
                        sonarArray[counter][jCounter] = "Sea";
                    }
                    jCounter++;
                }
                counter++;
            }

            sonarArray[0][0] = "Fog";
            sonarArray[0][1] = "Fog";
            sonarArray[1][0] = "Fog";

            sonarArray[3][0] = "Fog";
            sonarArray[4][0] = "Fog";
            sonarArray[4][1] = "Fog";

            sonarArray[0][4] = "Fog";
            sonarArray[0][3] = "Fog";
            sonarArray[1][4] = "Fog";

            sonarArray[4][4] = "Fog";
            sonarArray[4][3] = "Fog";
            sonarArray[3][4] = "Fog";

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
