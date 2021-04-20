package edu.colorado.mtoftheholycross;

import javax.security.auth.Destroyable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class Game {

    private Ship[] testp1Fleet;
    private Ship[] testp2Fleet;

    private Ship[] p1Fleet;
    private Ship[] p2Fleet;
    private Grid p1Grid;
    private Grid p2Grid;
    private Player P1;
    private Player P2;
    private Invoker invoker = new Invoker();

    public Game() {
        testp2Fleet = new Ship[7];
        testp2Fleet[0] = new Minesweeper("A1", "A2");
        testp2Fleet[1] = new Destroyer("B1", "B3");
        testp2Fleet[2] = new Battleship("C1", "C4");
        testp2Fleet[3] = new Submarine("D1", "D4", false);
        testp2Fleet[4] = new Minesweeper("A3", "A4");
        testp2Fleet[5] = new Minesweeper("B4", "B5");
        testp2Fleet[6] = new TowerShip("J10", "J10", true);


        testp1Fleet = new Ship[4];
        testp1Fleet[0] = new Minesweeper("A7", "A8");
        testp1Fleet[1] = new Destroyer("B7", "B9");
        testp1Fleet[2] = new Battleship("J1", "J4");
        testp1Fleet[3] = new Submarine("D7", "D10", false);

        p1Fleet = new Ship[2];
        p1Fleet[0] = new Minesweeper("-1", "-1");
        p1Fleet[1] = new Destroyer("-1", "-1");
//        p1Fleet[2] = new Battleship("-1", "-1");
//        p1Fleet[3] = new Submarine("-1", "-1", false);
//        p1Fleet[4] = new TowerShip("-1", "-1", false);

        p2Fleet = new Ship[2];
        p2Fleet[0] = new Minesweeper("-1", "-1");
        p2Fleet[1] = new Destroyer("-1", "-1");
//        p2Fleet[2] = new Battleship("-1", "-1");
//        p2Fleet[3] = new Submarine("-1", "-1", false);
//        p2Fleet[4] = new TowerShip("-1", "-1", false);

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

    public void updateScores(Player player) {
        File inputFile = new File("src/resources/HighScores.txt");
        Scanner scan = null;

        try {
            scan = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        List<String> nameArray = new ArrayList<String>();
        List<Integer> scoreArray = new ArrayList<Integer>();

        while(scan.hasNextLine()) {
            String[] delimitedScore = scan.nextLine().split(",");
            nameArray.add(delimitedScore[0]);
            scoreArray.add(Integer.parseInt(delimitedScore[1]));
        }

        for(int i = 0; i < scoreArray.size(); i++) {
            if(scoreArray.get(i) > player.getTurnCount()) {
                scoreArray.add(i, player.getTurnCount());
                nameArray.add(i, player.getPlayerName());
                scoreArray.remove(5);
                nameArray.remove(5);
                break;
            }
        }

        scan.close();
        try {
            FileWriter writer = new FileWriter("src/resources/HighScores.txt");
            FileWriter writer2 = new FileWriter("src/resources/testFile.txt");
            for(int i =0; i<5; i++) {
                writer.write(nameArray.get(i) + "," + scoreArray.get(i) + "\n");
                writer2.write(nameArray.get(i) + "," + scoreArray.get(i) + "\n");
            }
            writer.close();
            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int switchTurn(){
        if(p2Grid.getIsWaiting() && playerSurrender()) {
            System.out.println("Player 2 surrendered!");
            P1.incrementTurnCount();
            System.out.println("Your score this game was " + P1.getTurnCount() + " turns!");
            updateScores(P1);
            P1.setIsWinner(true);
            return 0;
        }else if(p1Grid.getIsWaiting() && playerSurrender()) {
            System.out.println("Player 1 surrendered!");
            P2.incrementTurnCount();
            System.out.println("Your score this game was " + P2.getTurnCount() + " turns!");
            updateScores(P2);
            P2.setIsWinner(true);
            return 0;
        }
        if(p1Grid.getIsWaiting()) {
            P2.incrementTurnCount();
        } else {
            P1.incrementTurnCount();
        }
        p1Grid.setIsWaiting(!p1Grid.getIsWaiting());
        p2Grid.setIsWaiting(!p2Grid.getIsWaiting());
        return -1;
    }

    public boolean playerSurrender() {
        if(p1Grid.getIsWaiting()) {
            for (int i = 0; i < p1Fleet.length; i++) {
                if (!p1Grid.isSunk(p1Fleet[i])) {
                    return false;
                }
            }
        }
        if(p2Grid.getIsWaiting()) {
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
    public Ship[] getP1TestFleet() {return testp1Fleet;}

    public Ship[] getP2Fleet() {
        return p2Fleet;
    }
    public Ship[] getP2TestFleet() {
        return testp2Fleet;
    }

    public void makeMove(String direction, Ship[] fleet, Grid grid) {
        Command moveShips = new MoveCommand(grid, direction, fleet);
        invoker.setCommand(moveShips);
        invoker.makeMove();
    }

    public void undoMove() {
        invoker.undoMove();
    }

    public void redoMove() {
        invoker.redoMove();
    }

    public void checkHint(Weapon hitResult) {
        if(p1Grid.getIsWaiting()) {
            if(hitResult.getShipHit() == true || hitResult.getUnderShipHit() == true) {
                P2.getHint().setLastShot(true);
            } else {
                P2.getHint().setLastShot(false);
            }
            for(int i = 0; i < 5; i++) {
                if(P2.getHint().getLastShot()[i] == true) {
                    return;
                }
            }
            P2.getHint().addHint();
        } else {
            if(hitResult.getShipHit() == true || hitResult.getUnderShipHit() == true) {
                P1.getHint().setLastShot(true);
            } else {
                P1.getHint().setLastShot(false);
            }

            for(int i = 0; i < 5; i++) {
                if(P1.getHint().getLastShot()[i] == true) {
                    return;
                }
            }
            P1.getHint().addHint();
        }
    }

    public void play() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Player one, what is your name?");
        P1.setPlayerName(scan.nextLine());

        System.out.println("Player two, what is your name?");
        P2.setPlayerName(scan.nextLine());

        System.out.println(P1.getPlayerName() + ", it is your turn to place your fleet.");
        String headCoord = null;
        String tailCoord = null;

        do {
            System.out.println("Enter a location for the front of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 1 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 1));
        p1Fleet[0] = new Minesweeper(headCoord, tailCoord);
        p1Grid.addShip(p1Fleet[0]);
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 2 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 2));
        p1Fleet[1] = new Destroyer(headCoord, tailCoord);
        p1Grid.addShip(p1Fleet[1]);
        headCoord = null;
        tailCoord = null;
        /*
        do {
            System.out.println("Enter a location for the front of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3));
        p1Fleet[2] = new Battleship(headCoord, tailCoord);
        p1Grid.addShip(p1Fleet[2]);
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine(); //Add a check to check for the dangling piece being out of bounds.
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3));
        p1Fleet[3] = new Submarine(headCoord, tailCoord, false); //Make sure to ask if they want it submerged or not.
        p1Grid.addShip(p1Fleet[3]);
        headCoord = null;
        tailCoord = null;
        do{
            System.out.println("Enter a location for your tower ship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9);
        p1Fleet[4] = new TowerShip(headCoord, tailCoord, true);
        p1Grid.addShip(p1Fleet[4]);
        headCoord = null;
        tailCoord = null;
        */
        System.out.println(P2.getPlayerName() + ", it is your turn to place your fleet.");
        do {
            System.out.println("Enter a location for the front of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p2Grid.convertPosition(headCoord)[0] < 0 || p2Grid.convertPosition(headCoord)[0] > 9 || p2Grid.convertPosition(headCoord)[1] < 0 || p2Grid.convertPosition(headCoord)[1] > 9 || p2Grid.convertPosition(tailCoord)[0] < 0 || p2Grid.convertPosition(tailCoord)[0] > 9 || p2Grid.convertPosition(tailCoord)[1] < 0 || p2Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p2Grid.convertPosition(headCoord)[0] - p2Grid.convertPosition(tailCoord)[0]) != 1 && Math.abs(p2Grid.convertPosition(headCoord)[1] - p2Grid.convertPosition(tailCoord)[1]) != 1));
        p2Fleet[0] = new Minesweeper(headCoord, tailCoord);
        p2Grid.addShip(p2Fleet[0]);
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p2Grid.convertPosition(headCoord)[0] < 0 || p2Grid.convertPosition(headCoord)[0] > 9 || p2Grid.convertPosition(headCoord)[1] < 0 || p2Grid.convertPosition(headCoord)[1] > 9 || p2Grid.convertPosition(tailCoord)[0] < 0 || p2Grid.convertPosition(tailCoord)[0] > 9 || p2Grid.convertPosition(tailCoord)[1] < 0 || p2Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p2Grid.convertPosition(headCoord)[0] - p2Grid.convertPosition(tailCoord)[0]) != 2 && Math.abs(p2Grid.convertPosition(headCoord)[1] - p2Grid.convertPosition(tailCoord)[1]) != 2));
        p2Fleet[1] = new Destroyer(headCoord, tailCoord);
        p2Grid.addShip(p2Fleet[1]);
        headCoord = null;
        tailCoord = null;
        /*
        do {
            System.out.println("Enter a location for the front of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3));
        p2Fleet[2] = new Battleship(headCoord, tailCoord);
        p2Grid.addShip(p2Fleet[2]);
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine(); //Add a check to check for the dangling piece being out of bounds.
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3));
        p2Fleet[3] = new Submarine(headCoord, tailCoord, false); //Make sure to ask want submerged not
        headCoord = null;
        tailCoord = null;
        p2Grid.addShip(p2Fleet[3]);
        do{
            System.out.println("Enter a location for your tower ship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9);
        p2Fleet[4] = new TowerShip(headCoord, tailCoord, true);
        headCoord = null;
        tailCoord = null;
        p2Grid.addShip(p2Fleet[4]);
        */
        while(!P1.getIsWinner() && !P2.getIsWinner()) {
            while(!getP1Grid().getIsWaiting()) {
                String choice = "";
                while(choice.equals("") || !choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")) {
                    System.out.println(P1.getPlayerName() + ", it is your turn.\nWhat would you like to do?\n1. Make a Hit\n2. Use Sonar\n3. Move Fleet\n4. Print Boards\nEnter a number to choose");
                    choice = scan.nextLine();
                    //System.out.println("LINE 387: choice = " + choice);
                }

                if(choice.equals("1")) {
                    String hitLocation = "";
                    while(hitLocation.equals("") || p1Grid.convertPosition(hitLocation)[0] < 0 || p1Grid.convertPosition(hitLocation)[0] > 9 || p1Grid.convertPosition(hitLocation)[1] < 0 || p1Grid.convertPosition(hitLocation)[1] > 9) {
                        System.out.println("Enter a location to hit. Formatted as LetterNumber. (E.g 'A5')");
                        hitLocation = scan.nextLine();
                    }

                    if(p2Grid.getShipCount() == 5) {
                        P1.getCannon().makeHit(hitLocation, p2Grid);
                        if(P1.getCannon().getShipHit()) {
                            System.out.println("You cannoned their Ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p1Grid.updateBoards(hitLocation, P1.getCannon());
                        p2Grid.updateBoards(hitLocation, P1.getCannon());
                    } else {
                        P1.getLaser().makeHit(hitLocation, p2Grid);
                        if(P1.getLaser().getShipHit()) {
                            System.out.println("You lasered their Ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p1Grid.updateBoards(hitLocation, P1.getLaser());
                        p2Grid.updateBoards(hitLocation, P1.getLaser());
                    }

                    switchTurn();

                }else if (choice.equals("2")) {
                    String sonarLocation = "";
                    System.out.println("Enter a location to scan from.");
                    while(sonarLocation.equals("") ||p1Grid.convertPosition(sonarLocation)[0] < 0 || p1Grid.convertPosition(sonarLocation)[0] > 9 || p1Grid.convertPosition(sonarLocation)[1] < 0 || p1Grid.convertPosition(sonarLocation)[1] > 9) {
                        sonarLocation = scan.nextLine();
                    }

                    if(P1.getSonar().activateSonar(sonarLocation, p1Grid, p2Grid, P1, P2)) {
                        switchTurn();
                    }
                }else if(choice.equals("3")){
                    String direction = "";
                    while(direction.equals("") || !direction.equals("NORTH") && !direction.equals("SOUTH") && !direction.equals("EAST") && !direction.equals("WEST")) {
                        System.out.println("Indicate a direction for your ship to Move. Spell out the direction in all caps. (E.g. 'NORTH')");
                        direction = scan.nextLine();
                    }
                    makeMove(direction, p1Fleet, p1Grid);
                    switchTurn();
                }else if(choice.equals("4")) {
                    System.out.println("My Ship Board:");
                    p1Grid.printMyShips();
                    System.out.println("My Shot Board:");
                    p1Grid.printMyShots();
                }

            }
            while(!getP2Grid().getIsWaiting()) {
                String choice = "";
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4")) {
                    System.out.println(P2.getPlayerName() + ", it is your turn.\nWhat would you like to do?\n1. Make a Hit\n2. Use Sonar\n3. Move Fleet\n4. Print Boards\nEnter a number to choose");
                    choice = scan.nextLine();
                }
                if(choice.equals("1")) {
                    String hitLocation = "";
                    while(hitLocation.equals("") || p2Grid.convertPosition(hitLocation)[0] < 0 || p2Grid.convertPosition(hitLocation)[0] > 9 || p2Grid.convertPosition(hitLocation)[1] < 0 || p2Grid.convertPosition(hitLocation)[1] > 9) {
                        System.out.println("Enter a location to hit. Formatted as LetterNumber. (E.g 'A5')");
                        hitLocation = scan.nextLine();
                    }

                    if(p1Grid.getShipCount() == 5) {
                        P2.getCannon().makeHit(hitLocation, p1Grid);
                        if(P2.getCannon().getShipHit()) {
                            System.out.println("You cannoned their ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p2Grid.updateBoards(hitLocation, P2.getCannon());
                        p1Grid.updateBoards(hitLocation, P2.getCannon());
                    } else {
                        P2.getLaser().makeHit(hitLocation, p1Grid);
                        if(P2.getLaser().getShipHit()) {
                            System.out.println("You lasered their ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p2Grid.updateBoards(hitLocation, P2.getLaser());
                        p1Grid.updateBoards(hitLocation, P2.getLaser());
                    }
                    switchTurn();

                }else if(choice.equals("2")) {
                    String sonarLocation = "";
                    System.out.println("Enter a location to scan from.");
                    while(sonarLocation.equals("") || p2Grid.convertPosition(sonarLocation)[0] < 0 || p2Grid.convertPosition(sonarLocation)[0] > 9 || p2Grid.convertPosition(sonarLocation)[1] < 0 || p2Grid.convertPosition(sonarLocation)[1] > 9) {
                        sonarLocation = scan.nextLine();
                    }
                    if(P1.getSonar().activateSonar(sonarLocation, p1Grid, p2Grid, P1, P2)) {
                        switchTurn();
                    }
                }else if(choice.equals("3")) {
                    String direction = "";
                    System.out.println("Indicate a direction for your ship to Move. Spell out the direction in all caps. (E.g. 'NORTH')");
                    while(direction.equals("") || !direction.equals("NORTH") && !direction.equals("SOUTH") && !direction.equals("EAST") && !direction.equals("WEST")) {
                        direction = scan.nextLine();
                    }
                    makeMove(direction, p2Fleet, p2Grid);
                    switchTurn();
                }else if (choice.equals("4")) {
                        System.out.println("My Ship Board:");
                        p2Grid.printMyShips();
                        System.out.println("My Shot Board");
                        p2Grid.printMyShots();
                }
            }
        }

    }
}
