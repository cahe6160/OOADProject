package edu.colorado.mtoftheholycross;

public class Game {
    private
        Ship[] p1Fleet;
        Ship[] p2Fleet;
        Grid p1Grid;
        Grid p2Grid;
        Player P1;
        Player P2;

    public Game() {
        p2Fleet = new Ship[3];
        p2Fleet[0] = new Ship("Minesweeper", "A1", "A2", "A1");
        p2Fleet[1] = new Ship("Destroyer", "B1", "B3", "B2");
        p2Fleet[2] = new Ship("Battleship", "C1", "C4", "C3");


        p1Fleet = new Ship[3];
        p1Fleet[0] = new Ship("Minesweeper", "A7", "A8", "A7");
        p1Fleet[1] = new Ship("Destroyer", "B7", "B9", "B8");
        p1Fleet[2] = new Ship("Battleship", "C7", "C10", "C9");

        p1Grid = new Grid(false);
        p2Grid = new Grid(true);

        P1 = new Player();
        P2 = new Player();
    }

    public Grid getP1Grid() {
        return p1Grid;
    }

    public Grid getP2Grid() {
        return p2Grid;
    }

    public Player getP1() {
        return P1;
    }

    public Player getP2() {
        return P2;
    }

    public void setP1Grid(Grid p1Grid) {
        this.p1Grid = p1Grid;
    }

    public void setP2Grid(Grid p2Grid) {
        this.p2Grid = p2Grid;
    }

    public void setP1(Player p1) {
        P1 = p1;
    }

    public void setP2(Player p2) {
        P2 = p2;
    }

    public int switchTurn(){
        if(p2Grid.isWaiting && playerSurrender()) {
            System.out.println("Player 2 surrendered!");
            return 0;
        }else if(p1Grid.isWaiting && playerSurrender()) {
            System.out.println("Player 1 surrendered!");
            return 0;
        }
        p1Grid.isWaiting = !p1Grid.isWaiting;
        p2Grid.isWaiting = !p2Grid.isWaiting;
        return -1;
    }

    public Boolean playerSurrender() {
        if(p1Grid.isWaiting) {
            for (int i = 0; i < p1Fleet.length; i++) {
                if (!p1Grid.isSunk(p1Fleet[i])) {
                    return false;
                }
            }
        }
        if(p2Grid.isWaiting) {
            for (int i = 0; i < p2Fleet.length; i++) {
                if (!p2Grid.isSunk(p2Fleet[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Ship[] getP1Fleet() {
        return p1Fleet;
    }

    public Ship[] getP2Fleet() {
        return p2Fleet;
    }

    public boolean activateSonar(String location){

        int[] sonarCenter = p1Grid.convertPosition(location);

        if(!p1Grid.isWaiting && P1.getSonarPulse() == 0){
            System.out.println("Error, no sonar charges remain.");
            return false;
        } else if(!p1Grid.isWaiting && P2.getShipCount() == 3) {
            System.out.println("Error, at least one ship must be sunk in order to activate sonar.");
            return false;
        } else if(p1Grid.isWaiting && P2.getSonarPulse() == 0) {
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

    public int[][] getBounds(int[] centerLocation){
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
}
