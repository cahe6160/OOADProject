package edu.colorado.mtoftheholycross;
  
public class Main {

    public static void main(String[] args) {
        Game gameTest = new Game();

        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[0]);
        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[1]);
        gameTest.p1Grid.addShip(gameTest.getP1Fleet()[2]);

        gameTest.p2Grid.addShip(gameTest.getP2Fleet()[0]);
        gameTest.p2Grid.addShip(gameTest.getP2Fleet()[1]);
        gameTest.p2Grid.addShip(gameTest.getP2Fleet()[2]);

        String[][] p2ShipBoard = gameTest.p2Grid.getMyShips();
        String[][] p1ShipBoard = gameTest.p1Grid.getMyShips();

        gameTest.p1Grid.updateBoards("A1", gameTest.p1Grid.checkHit("A1", p2ShipBoard));
        gameTest.p2Grid.updateBoards("A1", gameTest.p1Grid.checkHit("A1", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }
        
        gameTest.p2Grid.updateBoards("A1", gameTest.p2Grid.checkHit("A1", p1ShipBoard));
        gameTest.p1Grid.updateBoards("A1", gameTest.p2Grid.checkHit("A1", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("A2", gameTest.p1Grid.checkHit("A2", p2ShipBoard));
        gameTest.p2Grid.updateBoards("A2", gameTest.p1Grid.checkHit("A2", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("A2", gameTest.p2Grid.checkHit("A2", p1ShipBoard));
        gameTest.p1Grid.updateBoards("A2", gameTest.p2Grid.checkHit("A2", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("B1", gameTest.p1Grid.checkHit("B1", p2ShipBoard));
        gameTest.p2Grid.updateBoards("B1", gameTest.p1Grid.checkHit("B1", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("B1", gameTest.p2Grid.checkHit("B1", p1ShipBoard));
        gameTest.p1Grid.updateBoards("B1", gameTest.p2Grid.checkHit("B1", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("B2", gameTest.p1Grid.checkHit("B2", p2ShipBoard));
        gameTest.p2Grid.updateBoards("B2", gameTest.p1Grid.checkHit("B2", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("B2", gameTest.p2Grid.checkHit("B2", p1ShipBoard));
        gameTest.p1Grid.updateBoards("B2", gameTest.p2Grid.checkHit("B2", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("B3", gameTest.p1Grid.checkHit("B3", p2ShipBoard));
        gameTest.p2Grid.updateBoards("B3", gameTest.p1Grid.checkHit("B3", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("B3", gameTest.p2Grid.checkHit("B3", p1ShipBoard));
        gameTest.p1Grid.updateBoards("B3", gameTest.p2Grid.checkHit("B3", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("C1", gameTest.p1Grid.checkHit("C1", p2ShipBoard));
        gameTest.p2Grid.updateBoards("C1", gameTest.p1Grid.checkHit("C1", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("C1", gameTest.p2Grid.checkHit("C1", p1ShipBoard));
        gameTest.p1Grid.updateBoards("C1", gameTest.p2Grid.checkHit("C1", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("C2", gameTest.p1Grid.checkHit("C2", p2ShipBoard));
        gameTest.p2Grid.updateBoards("C2", gameTest.p1Grid.checkHit("C2", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("C2", gameTest.p2Grid.checkHit("C2", p1ShipBoard));
        gameTest.p1Grid.updateBoards("C2", gameTest.p2Grid.checkHit("C2", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("C3", gameTest.p1Grid.checkHit("C3", p2ShipBoard));
        gameTest.p2Grid.updateBoards("C3", gameTest.p1Grid.checkHit("C3", p2ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p2Grid.updateBoards("C3", gameTest.p2Grid.checkHit("C3", p1ShipBoard));
        gameTest.p1Grid.updateBoards("C3", gameTest.p2Grid.checkHit("C3", p1ShipBoard));

        if(gameTest.switchTurn() == 0){
            System.out.println("Game Over");
            return;
        }

        gameTest.p1Grid.updateBoards("C4", gameTest.p1Grid.checkHit("C4", p2ShipBoard));
        gameTest.p2Grid.updateBoards("C4", gameTest.p1Grid.checkHit("C4", p2ShipBoard));

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
        }

    }
}