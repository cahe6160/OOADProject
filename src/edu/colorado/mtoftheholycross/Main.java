package edu.colorado.mtoftheholycross;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class Main {

    public static void main(String[] args) {

        // ------------------------
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
/*
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
*/

        //Unicode test
        /*
        MapSymbols test = new MapSymbols();
        System.out.println(test.sub);
        System.out.println(test.damage);
        System.out.println(test.armor);
        System.out.println(test.captain);
        System.out.println(test.sea);
        System.out.println(test.fog);
        System.out.println(test.hit);
        System.out.println(test.miss);
        */

        //Move test
        /*
        Game gameTest;
        Cell[][] p2ShipBoard;
        Cell[][] p1ShipBoard;

        gameTest = new Game();

        p2ShipBoard = gameTest.getP2Grid().getMyShips();
        p1ShipBoard = gameTest.getP1Grid().getMyShips();

        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);
        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[5]);
        gameTest.getP1().getCannon().makeHit("A1", gameTest.getP2Grid());
        gameTest.getP2Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.getP1Grid().updateBoards("A1", gameTest.getP1().getCannon());
        gameTest.checkHint(gameTest.getP1().getCannon());
        gameTest.switchTurn();

        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[4]);
        gameTest.makeMove("NORTH", gameTest.getP2Fleet(), gameTest.getP2Grid());

        gameTest.getP2Grid().printMyShips();
        */
//        int test = 0;
//        System.out.println("test12" + (char)(test + 65));

        //Hint Test
//        Game gameTest;
//        Cell[][] p2ShipBoard;
//        Cell[][] p1ShipBoard;
//
//        gameTest = new Game();
//
//        p2ShipBoard = gameTest.getP2Grid().getMyShips();
//        p1ShipBoard = gameTest.getP1Grid().getMyShips();
//
//        gameTest.getP2Grid().addShip(gameTest.getP2Fleet()[0]);
//
//        gameTest.getP1().getCannon().makeHit("B1", gameTest.getP2Grid());
//        gameTest.checkHint(gameTest.getP1().getCannon());
//        gameTest.getP1Grid().updateBoards("B1", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("B1", gameTest.getP1().getCannon());
//
//        gameTest.getP1().getCannon().makeHit("B2", gameTest.getP2Grid());
//        gameTest.checkHint(gameTest.getP1().getCannon());
//        gameTest.getP1Grid().updateBoards("B2", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("B2", gameTest.getP1().getCannon());
//
//        gameTest.getP1().getCannon().makeHit("B3", gameTest.getP2Grid());
//        gameTest.checkHint(gameTest.getP1().getCannon());
//        gameTest.getP1Grid().updateBoards("B3", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("B3", gameTest.getP1().getCannon());
//
//        gameTest.getP1().getCannon().makeHit("B4", gameTest.getP2Grid());
//        gameTest.checkHint(gameTest.getP1().getCannon());
//        gameTest.getP1Grid().updateBoards("B4", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("B4", gameTest.getP1().getCannon());
//
//        gameTest.getP1().getCannon().makeHit("B5", gameTest.getP2Grid());
//        gameTest.checkHint(gameTest.getP1().getCannon());
//        gameTest.getP1Grid().updateBoards("B5", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("B5", gameTest.getP1().getCannon());
//
//        for(int i =0; i<5;i++) {
//            System.out.println(gameTest.getP1().getLastShot()[i]);
//       }

        // Testing Towership
//        Game gameTest;
//        Cell[][] p2ShipBoard;
//        Cell[][] p1ShipBoard;
//
//        gameTest = new Game();
//
//        gameTest.getP1Grid().addShip(gameTest.getP2Fleet()[6]);
//
//        p2ShipBoard = gameTest.getP2Grid().getMyShips();
//        p1ShipBoard = gameTest.getP1Grid().getMyShips();
//
//
//        gameTest.getP1().getCannon().makeHit("J10", gameTest.getP1Grid());
//        gameTest.getP1Grid().updateBoards("J10", gameTest.getP1().getCannon());
//        gameTest.getP2Grid().updateBoards("J10", gameTest.getP1().getCannon());
//        gameTest.getP1Grid().printMyShots();


//        String IMAGE_PATH = "/src/edu/colorado/mtoftheholycross/test/HighScores.txt";
//        //private final String IMAGE_PATH = "../battleship/resources/images/";
//
//        try {
//            byte[] oldFile = Files.readAllBytes(Paths.get(IMAGE_PATH));
//            byte[] newFile = Files.readAllBytes(Paths.get("/testFile.txt"));
//            System.out.println(Arrays.equals(oldFile, newFile));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }
}