package edu.colorado.mtoftheholycross;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Game class represents the actual battleship game being played.
 * Holds player and their respective grids.
 * This uses the Singleton design pattern, which allows only fr a single instance of game to be created.
 */
public class Game {

    private static Game single_instance = null;

    private Grid p1Grid;
    private Grid p2Grid;
    private Player P1;
    private Player P2;
    private Invoker invoker = new Invoker();

    private Game() {

        p1Grid = new Grid(false);
        p2Grid = new Grid(true);

        P1 = new Player();
        P2 = new Player();
    }

    public static Game getInstance() {
        if(single_instance == null) {
            single_instance = new Game();
        }
        return single_instance;
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

    /**
     * Write high score to file using turn count and player name.
     * @param player Player object of the winning player.
     */
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

    /**
     * Switch turn is called at the end of every turn.
     * Checks if a player has surrendered, switches turns,
     * and calls for random events.
     * @return integer value that represents whether a player has surrendered.
     */
    public int switchTurn(){
        if(p2Grid.getIsWaiting() && playerSurrender()) {
            System.out.println("Player 2 surrendered!");
            P1.incrementTurnCount();
            System.out.println("Your score this game was " + P1.getTurnCount() + " turns!");
            updateScores(P1);
            P1.setIsWinner(true);
            p1Grid.setIsWaiting(true);
            return 0;
        }else if(p1Grid.getIsWaiting() && playerSurrender()) {
            System.out.println("Player 1 surrendered!");
            P2.incrementTurnCount();
            System.out.println("Your score this game was " + P2.getTurnCount() + " turns!");
            updateScores(P2);
            P2.setIsWinner(true);
            p2Grid.setIsWaiting(true);
            return 0;
        }
        if(p1Grid.getIsWaiting()) {
            P2.incrementTurnCount();
        } else {
            P1.incrementTurnCount();
        }
        int event = ThreadLocalRandom.current().nextInt(0, 20 + 1);
        if(event == 1) {
            int eventType = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            if(eventType == 3) {
                new RandEvent(p1Grid, P1, eventType);
                p1Grid.setIsWaiting(!p1Grid.getIsWaiting());
                p2Grid.setIsWaiting(!p2Grid.getIsWaiting());
            } else {
                if(p1Grid.getIsWaiting()) {
                    new RandEvent(p2Grid, P2, eventType);
                } else {
                    new RandEvent(p1Grid, P1, eventType);
                }
            }
        }
        p1Grid.setIsWaiting(!p1Grid.getIsWaiting());
        p2Grid.setIsWaiting(!p2Grid.getIsWaiting());
        return -1;
    }

    /**
     * Loops through a players fleet, and checks if all their ships have been sunk.
     * @return whether or not the current player has surrendered.
     */
    public boolean playerSurrender() {

        if(p1Grid.getIsWaiting()) {
            for (int i = 0; i < p1Grid.getPlayerFleet().size(); i++) {
                p1Grid.isSunk(i);
            }
            if(p1Grid.getShipCount() == 0) {
                return true;
            }
        }
        if(p2Grid.getIsWaiting()) {
            for (int i = 0; i < p2Grid.getPlayerFleet().size(); i++) {
                p2Grid.isSunk(i);
            }
            if(p2Grid.getShipCount() == 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * This method creates a command, using the command pattern, sets it,
     * and ultimately calls for the sips to move.
     * @param direction string of direction the fleet will move.
     * @param fleet an array of the current players ships
     * @param grid the grid of the current player
     */
    public void makeMove(String direction, Ship[] fleet, Grid grid) {
        Command moveShips = new MoveCommand(grid, direction, fleet);
        invoker.setCommand(moveShips);
        invoker.makeMove();
    }

    /**
     * Using the command pattern, this method will undo a previous command
     * made by the invoker.
     */
    public void undoMove() {
        invoker.undoMove();
    }

    /**
     * Using the command pattern, this method will redo a previous command
     * made by the Invoker.
     */
    public void redoMove() {
        invoker.redoMove();
    }

    /**
     * The checkHint method adds the result of a previous hit.
     * It then checks the last five moves to see if there has been a hit, if none,
     * we add a Hint to the players arsenal.
     * @param hitResult
     */
    public void checkHint(Weapon hitResult) {
        if(p1Grid.getIsWaiting()) {
            if(hitResult.getShipHit() == true || hitResult.getUnderShipHit() == true) {
                P2.getHint().setLastShot(true);
            } else {
                P2.getHint().setLastShot(false);
            }
            for(int i = 0; i < 5; i++) {
                if(P2.getHint().getLastShot()[i]) {
                    return;
                }
            }
            P2.getHint().addHint();
        } else {
            if(hitResult.getShipHit() || hitResult.getUnderShipHit()) {
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

    /**
     * The play method is responsible for a adding ships and the entire game loop.
     * This method is responsible for making tthe game playable in the command line.
     */
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
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 1 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 1) || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p1Grid.addShip(new Minesweeper(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            int headRow = p1Grid.convertPosition(headCoord)[0];
            int headCol = p1Grid.convertPosition(headCoord)[1];
            int tailRow = p1Grid.convertPosition(tailCoord)[0];
            int tailCol = p1Grid.convertPosition(tailCoord)[1];

            if(headRow < 0 || headRow > 9 || tailRow < 0 || tailRow > 9 || headCol < 0 || headCol > 9 || tailCol < 0 || tailCol > 9) {
                System.out.println("Error, invalid ship placement. Coordinate out of bounds");
                headCoord = "K11";
            } else if(headRow == tailRow) {
                if(headCol < tailCol) {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            } else {
                if(headRow < tailRow) {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            }
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 2 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 2) || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p1Grid.addShip(new Destroyer(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;

        do {
            System.out.println("Enter a location for the front of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            int headRow = p1Grid.convertPosition(headCoord)[0];
            int headCol = p1Grid.convertPosition(headCoord)[1];
            int tailRow = p1Grid.convertPosition(tailCoord)[0];
            int tailCol = p1Grid.convertPosition(tailCoord)[1];

            if(headRow < 0 || headRow > 9 || tailRow < 0 || tailRow > 9 || headCol < 0 || headCol > 9 || tailCol < 0 || tailCol > 9) {
                System.out.println("Error, invalid ship placement. Coordinate out of bounds");
                headCoord = "K11";
            } else if(headRow == tailRow) {
                if(headCol < tailCol) {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 3].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 3].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            } else {
                if(headRow < tailRow) {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 3][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 3][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            }

        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3) || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p1Grid.addShip(new Battleship(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;
        boolean isSubmerged = false;
        int[] hangPiece;

        do {
            System.out.println("Enter a location for the front of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            System.out.println("Do you want your submarine submerged or not? Enter true or false.");
            isSubmerged = Boolean.parseBoolean(scan.nextLine());
            int[] headC = p1Grid.convertPosition(headCoord);
            int[] tailC = p1Grid.convertPosition(tailCoord);
            int headRow = headC[0];
            int headCol = headC[1];
            int tailRow = tailC[0];
            int tailCol = tailC[1];
            if (headRow == tailRow) {
                if (headCol < tailCol) {
                    hangPiece = new int[]{tailRow - 1, tailCol - 1};
                } else {
                    hangPiece = new int[]{tailRow + 1, tailCol + 1};
                }
            } else {
                if (headRow < tailRow) {
                    hangPiece = new int[]{tailRow - 1, tailCol + 1};
                } else {
                    hangPiece = new int[]{tailRow + 1, tailCol -1};
                }
            }

            if(!isSubmerged) {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        if (headRow == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol + 3].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 1][headCol + 2].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headRow == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow][headCol - 3].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 1][headCol - 2].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    }
                } else {
                    if (headRow < tailRow) {
                        if (headCol == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 3][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow + 2][headCol + 1].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headCol == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p1Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 3][headCol].getSurface().equals("Sea") || !p1Grid.getMyShips()[headRow - 2][headCol - 1].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    }
                }
            } else {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        if (headRow == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headRow == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    }
                } else {
                    if (headRow < tailRow) {
                        if (headCol == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headCol == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    }
                }
            }
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || hangPiece[0] < 0 || hangPiece[0] > 9 || hangPiece[1] < 0 || hangPiece[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3) || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p1Grid.addShip(new Submarine(headCoord, tailCoord, isSubmerged));
        headCoord = null;
        tailCoord = null;
        do{
            System.out.println("Enter a location for your tower ship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            if(!p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getUnderwater().equals("Sea")) {
                System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                headCoord = "K11";
            }
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p1Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getUnderwater().equals("Sea"));
        p1Grid.addShip(new TowerShip(headCoord, true));
        headCoord = null;
        tailCoord = null;

        System.out.println(P2.getPlayerName() + ", it is your turn to place your fleet.");
        do {
            System.out.println("Enter a location for the front of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your minesweeper. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 1 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 1) || !p2Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p2Grid.addShip(new Minesweeper(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;
        do {
            System.out.println("Enter a location for the front of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your destroyer. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            int headRow = p1Grid.convertPosition(headCoord)[0];
            int headCol = p1Grid.convertPosition(headCoord)[1];
            int tailRow = p1Grid.convertPosition(tailCoord)[0];
            int tailCol = p1Grid.convertPosition(tailCoord)[1];

            if(headRow < 0 || headRow > 9 || tailRow < 0 || tailRow > 9 || headCol < 0 || headCol > 9 || tailCol < 0 || tailCol > 9) {
                System.out.println("Error, invalid ship placement. Coordinate out of bounds");
                headCoord = "K11";
            } else if(headRow == tailRow) {
                if(headCol < tailCol) {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            } else {
                if(headRow < tailRow) {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            }
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 2 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 2) || !p2Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p2Grid.addShip(new Destroyer(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;

        do {
            System.out.println("Enter a location for the front of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your battleship. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            int headRow = p1Grid.convertPosition(headCoord)[0];
            int headCol = p1Grid.convertPosition(headCoord)[1];
            int tailRow = p1Grid.convertPosition(tailCoord)[0];
            int tailCol = p1Grid.convertPosition(tailCoord)[1];

            if(headRow < 0 || headRow > 9 || tailRow < 0 || tailRow > 9 || headCol < 0 || headCol > 9 || tailCol < 0 || tailCol > 9) {
                System.out.println("Error, invalid ship placement. Coordinate out of bounds");
                headCoord = "K11";
            } else if(headRow == tailRow) {
                if(headCol < tailCol) {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 3].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 3].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            } else {
                if(headRow < tailRow) {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 3][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                } else {
                    if(!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 3][headCol].getSurface().equals("Sea")) {
                        System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                        headCoord = "K11";
                    }
                }
            }

        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p1Grid.convertPosition(headCoord)[0] > 9 || p1Grid.convertPosition(headCoord)[1] < 0 || p1Grid.convertPosition(headCoord)[1] > 9 || p1Grid.convertPosition(tailCoord)[0] < 0 || p1Grid.convertPosition(tailCoord)[0] > 9 || p1Grid.convertPosition(tailCoord)[1] < 0 || p1Grid.convertPosition(tailCoord)[1] > 9 || (Math.abs(p1Grid.convertPosition(headCoord)[0] - p1Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p1Grid.convertPosition(headCoord)[1] - p1Grid.convertPosition(tailCoord)[1]) != 3) || !p2Grid.getMyShips()[p1Grid.convertPosition(headCoord)[0]][p1Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p1Grid.convertPosition(tailCoord)[0]][p1Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p2Grid.addShip(new Battleship(headCoord, tailCoord));
        headCoord = null;
        tailCoord = null;
        isSubmerged = false;
        do {
            System.out.println("Enter a location for the front of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            System.out.println("Enter a location for the back of your submarine. Formatted as Letter+Number. (E.g 'A5')");
            tailCoord = scan.nextLine();
            System.out.println("Do you want your submarine submerged or not? Enter true or false.");
            isSubmerged = Boolean.parseBoolean(scan.nextLine());
            int[] headC = p1Grid.convertPosition(headCoord);
            int[] tailC = p1Grid.convertPosition(tailCoord);
            int headRow = headC[0];
            int headCol = headC[1];
            int tailRow = tailC[0];
            int tailCol = tailC[1];
            if (headRow == tailRow) {
                if (headCol < tailCol) {
                    hangPiece = new int[]{tailRow - 1, tailCol - 1};
                } else {
                    hangPiece = new int[]{tailRow + 1, tailCol + 1};
                }
            } else {
                if (headRow < tailRow) {
                    hangPiece = new int[]{tailRow - 1, tailCol + 1};
                } else {
                    hangPiece = new int[]{tailRow + 1, tailCol -1};
                }
            }
            if(!isSubmerged) {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        if (headRow == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 2].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol + 3].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 1][headCol + 2].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headRow == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 1].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 2].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow][headCol - 3].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 1][headCol - 2].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    }
                } else {
                    if (headRow < tailRow) {
                        if (headCol == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 2][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 3][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow + 2][headCol + 1].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headCol == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        } else if (!p2Grid.getMyShips()[headRow][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 1][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 2][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 3][headCol].getSurface().equals("Sea") || !p2Grid.getMyShips()[headRow - 2][headCol - 1].getSurface().equals("Sea")) {
                            System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                            headCoord = "K11";
                        }
                    }
                }
            } else {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        if (headRow == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headRow == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    }
                } else {
                    if (headRow < tailRow) {
                        if (headCol == 9) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    } else {
                        if (headCol == 0) {
                            System.out.println("Error, invalid ship placement. Submarine not fully in bounds.");
                            headCoord = "K11";
                        }
                    }
                }
            }
        } while(p2Grid.convertPosition(headCoord)[0] < 0 || p2Grid.convertPosition(headCoord)[0] > 9 || p2Grid.convertPosition(headCoord)[1] < 0 || p2Grid.convertPosition(headCoord)[1] > 9 || p2Grid.convertPosition(tailCoord)[0] < 0 || p2Grid.convertPosition(tailCoord)[0] > 9 || p2Grid.convertPosition(tailCoord)[1] < 0 || p2Grid.convertPosition(tailCoord)[1] > 9 || hangPiece[0] < 0 || hangPiece[0] > 9 || hangPiece[1] < 0 || hangPiece[1] > 9 || (Math.abs(p2Grid.convertPosition(headCoord)[0] - p2Grid.convertPosition(tailCoord)[0]) != 3 && Math.abs(p2Grid.convertPosition(headCoord)[1] - p2Grid.convertPosition(tailCoord)[1]) != 3) || !p2Grid.getMyShips()[p2Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p2Grid.convertPosition(tailCoord)[0]][p2Grid.convertPosition(tailCoord)[1]].getSurface().equals("Sea"));
        p2Grid.addShip(new Submarine(headCoord, tailCoord, isSubmerged));
        headCoord = null;
        tailCoord = null;
        do{
            System.out.println("Enter a location for your tower ship. Formatted as Letter+Number. (E.g 'A5')");
            headCoord = scan.nextLine();
            if(!p2Grid.getMyShips()[p2Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p2Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getUnderwater().equals("Sea")) {
                System.out.println("Error, invalid ship placement. A ship already occupies part of this space.");
                headCoord = "K11";
            }
        } while(p1Grid.convertPosition(headCoord)[0] < 0 || p2Grid.convertPosition(headCoord)[0] > 9 || p2Grid.convertPosition(headCoord)[1] < 0 || p2Grid.convertPosition(headCoord)[1] > 9 || !p2Grid.getMyShips()[p2Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getSurface().equals("Sea") || !p2Grid.getMyShips()[p2Grid.convertPosition(headCoord)[0]][p2Grid.convertPosition(headCoord)[1]].getUnderwater().equals("Sea"));
        p2Grid.addShip(new TowerShip(headCoord, true));
        headCoord = null;
        tailCoord = null;


        while(!P1.getIsWinner() && !P2.getIsWinner()) {
            while(!getP1Grid().getIsWaiting()) {
                String choice = "";
                while(choice.equals("") || !choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) {
                    System.out.println(P1.getPlayerName() + ", it is your turn.\nWhat would you like to do?\n1. Make a Hit\n2. Use Sonar\n3. Move Fleet\n4. Print Boards\n5. Get a hint.\nEnter a number to choose");
                    choice = scan.nextLine();
                }

                if(choice.equals("1")) {
                    String hitLocation = "";
                    while(hitLocation.equals("") || p1Grid.convertPosition(hitLocation)[0] < 0 || p1Grid.convertPosition(hitLocation)[0] > 9 || p1Grid.convertPosition(hitLocation)[1] < 0 || p1Grid.convertPosition(hitLocation)[1] > 9) {
                        System.out.println("Enter a location to hit. Formatted as LetterNumber. (E.g 'A5')");
                        hitLocation = scan.nextLine();
                    }

                    if(p2Grid.getShipCount() == p2Grid.getPlayerFleet().size()) {
                        P1.getCannon().makeHit(hitLocation, p2Grid);
                        if(P1.getCannon().getShipHit()) {
                            System.out.println("You cannoned their Ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p1Grid.updateBoards(hitLocation, P1.getCannon());
                        p2Grid.updateBoards(hitLocation, P1.getCannon());
                        checkHint(P1.getCannon());
                    } else {
                        P1.getLaser().makeHit(hitLocation, p2Grid);
                        if(P1.getLaser().getShipHit() || P1.getLaser().getUnderShipHit()) {
                            System.out.println("You lasered their Ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p1Grid.updateBoards(hitLocation, P1.getLaser());
                        p2Grid.updateBoards(hitLocation, P1.getLaser());
                        checkHint(P1.getLaser());
                    }
                    switchTurn();

                }else if (choice.equals("2")) {
                    String sonarLocation = "";
                    System.out.println("Enter a location to scan from.");
                    while(sonarLocation.equals("") ||p1Grid.convertPosition(sonarLocation)[0] < 0 || p1Grid.convertPosition(sonarLocation)[0] > 9 || p1Grid.convertPosition(sonarLocation)[1] < 0 || p1Grid.convertPosition(sonarLocation)[1] > 9) {
                        sonarLocation = scan.nextLine();
                    }

                    if(P1.getSonar().makeHit(sonarLocation, p2Grid)) {
                        switchTurn();
                    }
                }else if(choice.equals("3")){
                    String choice3 = "";
                    while(!choice3.equals("1") && !choice3.equals("2") && !choice3.equals("3")) {
                        System.out.println("What would you like to do?\n1. Move all ships in a direction\n2. Undo previous move command\n3. Redo previous move command\n Enter a number.");
                        choice3 = scan.nextLine();
                    }
                    if(choice3.equals("1")) {
                        String direction = "";
                        System.out.println("Indicate a direction for your ship to Move. Spell out the direction in all caps. (E.g. 'NORTH')");
                        while(direction.equals("") || !direction.equals("NORTH") && !direction.equals("SOUTH") && !direction.equals("EAST") && !direction.equals("WEST")) {
                            direction = scan.nextLine();
                        }
                        makeMove(direction, p1Grid.getPlayerFleet().toArray(new Ship[p1Grid.getPlayerFleet().size()]), p1Grid);
                    } else if(choice3.equals("2")) {
                        undoMove();
                    } else if(choice3.equals("3")) {
                        redoMove();
                    }
                    switchTurn();
                }else if(choice.equals("4")) {
                    System.out.println("My Ship Board:");
                    p1Grid.printMyShips();
                    System.out.println("My Shot Board:");
                    p1Grid.printMyShots();
                } else if(choice.equals("5")) {
                    P1.getHint().makeHit("A0", p2Grid);
                    switchTurn();
                }

            }
            while(!getP2Grid().getIsWaiting()) {
                String choice = "";
                while(!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5")) {
                    System.out.println(P2.getPlayerName() + ", it is your turn.\nWhat would you like to do?\n1. Make a Hit\n2. Use Sonar\n3. Move Fleet\n4. Print Boards\n5. Get a hint.\nEnter a number to choose");
                    choice = scan.nextLine();
                }
                if(choice.equals("1")) {
                    String hitLocation = "";
                    while(hitLocation.equals("") || p2Grid.convertPosition(hitLocation)[0] < 0 || p2Grid.convertPosition(hitLocation)[0] > 9 || p2Grid.convertPosition(hitLocation)[1] < 0 || p2Grid.convertPosition(hitLocation)[1] > 9) {
                        System.out.println("Enter a location to hit. Formatted as Letter+Number. (E.g 'A5')");
                        hitLocation = scan.nextLine();
                    }

                    if(p1Grid.getShipCount() == p1Grid.getPlayerFleet().size()) {
                        P2.getCannon().makeHit(hitLocation, p1Grid);
                        if(P2.getCannon().getShipHit()) {
                            System.out.println("You cannoned their ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p2Grid.updateBoards(hitLocation, P2.getCannon());
                        p1Grid.updateBoards(hitLocation, P2.getCannon());
                        checkHint(P2.getCannon());
                    } else {
                        P2.getLaser().makeHit(hitLocation, p1Grid);
                        if(P2.getLaser().getShipHit() || P2.getLaser().getUnderShipHit()) {
                            System.out.println("You lasered their ship!");
                        } else {
                            System.out.println("You missed!");
                        }
                        p2Grid.updateBoards(hitLocation, P2.getLaser());
                        p1Grid.updateBoards(hitLocation, P2.getLaser());
                        checkHint(P2.getLaser());
                    }
                    switchTurn();

                }else if(choice.equals("2")) {
                    String sonarLocation = "";
                    System.out.println("Enter a location to scan from.");
                    while(sonarLocation.equals("") || p2Grid.convertPosition(sonarLocation)[0] < 0 || p2Grid.convertPosition(sonarLocation)[0] > 9 || p2Grid.convertPosition(sonarLocation)[1] < 0 || p2Grid.convertPosition(sonarLocation)[1] > 9) {
                        sonarLocation = scan.nextLine();
                    }
                    if(P2.getSonar().makeHit(sonarLocation, p1Grid)) {
                        switchTurn();
                    }
                }else if(choice.equals("3")) {
                    String choice3 = "";
                    while(!choice3.equals("1") && !choice3.equals("2") && !choice3.equals("3")) {
                        System.out.println("What would you like to do?\n1. Move all ships in a direction\n2. Undo previous move command\n3. Redo previous move command\n Enter a number.");
                        choice3 = scan.nextLine();
                    }
                    if(choice3.equals("1")) {
                        String direction = "";
                        System.out.println("Indicate a direction for your ship to Move. Spell out the direction in all caps. (E.g. 'NORTH')");
                        while(direction.equals("") || !direction.equals("NORTH") && !direction.equals("SOUTH") && !direction.equals("EAST") && !direction.equals("WEST")) {
                            direction = scan.nextLine();
                        }
                        makeMove(direction, p2Grid.getPlayerFleet().toArray(new Ship[p2Grid.getPlayerFleet().size()]), p2Grid);
                    } else if(choice3.equals("2")) {
                        undoMove();
                    } else if(choice3.equals("3")) {
                        redoMove();
                    }
                    switchTurn();
                }else if (choice.equals("4")) {
                        System.out.println("My Ship Board:");
                        p2Grid.printMyShips();
                        System.out.println("My Shot Board");
                        p2Grid.printMyShots();
                } else if(choice.equals("5")) {
                    P2.getHint().makeHit("A0", p1Grid);
                    switchTurn();
                }
            }
        }
        File inputFile = new File("src/resources/HighScores.txt");
        scan = null;

        try {
            scan = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("---CURRENT HIGH SCORES---");
        while(scan.hasNextLine()) {
            System.out.println(scan.nextLine());
        }
        scan.close();
    }
}