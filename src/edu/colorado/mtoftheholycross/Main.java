package edu.colorado.mtoftheholycross;

public class Main {

    public static void main(String[] args) {

        /*Game gameTest = new Game();

        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[0]);

        gameTest.p2Grid.addShip(gameTest.getP2Fleet()[0]);

        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[1]);
        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[2]);

        System.out.println("CAPTAIN LOCATIONS");
        System.out.println(gameTest.getP1Fleet()[0].getCaptainLocation());
        System.out.println(gameTest.getP1Fleet()[1].getCaptainLocation());
        System.out.println(gameTest.getP1Fleet()[2].getCaptainLocation());

        String[][] p2ShipBoard = gameTest.p2Grid.getMyShips();
        String[][] p1ShipBoard = gameTest.p1Grid.getMyShips();

        gameTest.p1Grid.updateBoards("A1", p2ShipBoard);
        gameTest.p2Grid.updateBoards("A1", p2ShipBoard);

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }
        
        gameTest.p2Grid.updateBoards("A1", p1ShipBoard);
        gameTest.p1Grid.updateBoards("A1",  p1ShipBoard);

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("A2", p2ShipBoard);
        gameTest.p2Grid.updateBoards("A2", p2ShipBoard);

        System.out.println("P1 Ships\n");
        gameTest.p1Grid.printMyShips();
        System.out.println("P1 Shots\n");
        gameTest.p1Grid.printMyShots();

        System.out.println("\n");

        System.out.println("P2 Ships\n");
        gameTest.p2Grid.printMyShips();
        System.out.println("P2 Shots\n");
        gameTest.p2Grid.printMyShots();

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }*/


        //Sonar test

        Game gameTest;
        String[][] p2ShipBoard;
        String [][] p1ShipBoard;

        gameTest = new Game();

        gameTest.getP1Grid().addShip(gameTest.getP1Fleet()[2]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[2]);

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

        gameTest.getP2().setShipCount(2);

        String[][] referenceMap = {{"Fog", "Fog", "Sea", "Fog", "Fog"}, {"Fog", "Ship", "Sea", "Sea", "Fog"}, {"Sea", "Sea", "Sea", "Sea", "Sea"}, {"Fog", "Sea", "Sea", "Sea", "Fog"}, {"Fog", "Fog", "Sea", "Fog", "Fog"}};

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                System.out.print(referenceMap[i][j]);
            }
            System.out.println();
        }

        gameTest.getP1().getSonar().activateSonar("D5", gameTest.getP1Grid(), gameTest.getP2Grid(), gameTest.getP1(), gameTest.getP2());

    }
}