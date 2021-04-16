package edu.colorado.mtoftheholycross;

import javax.security.auth.Destroyable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.stream.Stream;

public class Game {

    private Ship[] p1Fleet;
    private Ship[] p2Fleet;
    private Grid p1Grid;
    private Grid p2Grid;
    private Player P1;
    private Player P2;
    private Invoker invoker = new Invoker();

    public Game() {
        p2Fleet = new Ship[7];
        p2Fleet[0] = new Minesweeper("A1", "A2");
        p2Fleet[1] = new Destroyer("B1", "B3");
        p2Fleet[2] = new Battleship("C1", "C4");
        p2Fleet[3] = new Submarine("D1", "D4", false);
        p2Fleet[4] = new Minesweeper("A3", "A4");
        p2Fleet[5] = new Minesweeper("B4", "B5");
        p2Fleet[6] = new TowerShip("J10", "J10", true);


        p1Fleet = new Ship[4];
        p1Fleet[0] = new Minesweeper("A7", "A8");
        p1Fleet[1] = new Destroyer("B7", "B9");
        p1Fleet[2] = new Battleship("J1", "J4");
        p1Fleet[3] = new Submarine("D7", "D10", false);

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
            return 0;
        }else if(p1Grid.getIsWaiting() && playerSurrender()) {
            System.out.println("Player 1 surrendered!");
            P2.incrementTurnCount();
            System.out.println("Your score this game was " + P2.getTurnCount() + " turns!");
            updateScores(P2);
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

    public Ship[] getP2Fleet() {
        return p2Fleet;
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
}
