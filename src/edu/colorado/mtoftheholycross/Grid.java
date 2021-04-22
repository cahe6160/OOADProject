package edu.colorado.mtoftheholycross;
import java.util.ArrayList;
import java.util.Arrays;

public class Grid {
    private Cell[][] myShips;
    private Cell[][] myShots;
    private boolean isWaiting;
    private int shipCount;
    ArrayList<Ship> playerFleet = new ArrayList<Ship>();


    public Grid() {
        myShips = new Cell[10][10];
        myShots = new Cell[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                myShips[i][j].setSurface("Sea");
                myShips[i][j].setUnderwater("Sea");
                myShots[i][j].setSurface("Fog");
                myShots[i][j].setUnderwater("Fog");
            }
        }
        isWaiting = true;
        this.shipCount = 0;
    }

    public Grid(Boolean isWaiting) {
        myShips = new Cell[10][10];
        myShots = new Cell[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j ++) {
                myShips[i][j] = new Cell();
                myShots[i][j] = new Cell();

                myShips[i][j].setSurface("Sea");
                myShips[i][j].setUnderwater("Sea");
                myShots[i][j].setSurface("Fog");
                myShots[i][j].setUnderwater("Fog");
            }
        }
        this.isWaiting = isWaiting;
        this.shipCount = 0;
    }

    public Grid(Cell[][] myShips, Cell[][] myShots, Boolean isWaiting) {
        this.myShips = myShips;
        this.myShots = myShots;
        this.isWaiting = isWaiting;
        this.shipCount = 0;
    }

    public Cell[][] getMyShips() {
        return myShips;
    }

    public Cell[][] getMyShots() {
        return myShots;
    }

    public boolean getIsWaiting() {
        return isWaiting;
    }

    public int getShipCount() {
        return shipCount;
    }

    public ArrayList<Ship> getPlayerFleet() { return playerFleet;}

    public void setPlayerFleet(ArrayList<Ship> fleet) {
        this.playerFleet = fleet;
    }

    public void setShipCount(int count) {
        this.shipCount = count;
    }

    public void setIsWaiting(boolean waiting) {
        this.isWaiting = waiting;
    }

    public void decrementShipCount() {
        this.shipCount--;
    }


    public void addShip(Ship shipToAdd) {

        playerFleet.add(shipToAdd);
        shipCount++;

        int[] headPosition = convertPosition(shipToAdd.getHead());
        int[] tailPosition = convertPosition(shipToAdd.getTail());
        int[] captainPosition = convertPosition(shipToAdd.getCaptainLocation());

        int headRow = headPosition[0];
        int headCol = headPosition[1];
        int tailRow = tailPosition[0];
        int tailCol = tailPosition[1];
        int captainRow = captainPosition[0];
        int captainCol = captainPosition[1];

        if(shipToAdd.getName().equals("Minesweeper")) {
            myShips[headRow][headCol].setSurface("Captain");
            myShips[tailRow][tailCol].setSurface("Ship");
        }

        if(shipToAdd.getName().equals("Destroyer")) {
            if(headRow == tailRow){
                if(headCol < tailCol) {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow][headCol + 1].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                } else {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow][tailCol + 1].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                }
            } else {
                if(headRow < tailRow) {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow + 1][headCol].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                } else {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[tailRow + 1][tailCol].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                }
            }
        }

        if(shipToAdd.getName().equals("Battleship")) {
            if(headRow == tailRow){
                if(headCol < tailCol) {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow][headCol + 1].setSurface("Ship");
                    myShips[headRow][headCol + 2].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                } else {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow][tailCol + 1].setSurface("Ship");
                    myShips[headRow][tailCol + 2].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                }
            } else {
                if(headRow < tailRow) {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[headRow + 1][headCol].setSurface("Ship");
                    myShips[headRow + 2][headCol].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                } else {
                    myShips[headRow][headCol].setSurface("Ship");
                    myShips[tailRow + 1][tailCol].setSurface("Ship");
                    myShips[tailRow + 2][tailCol].setSurface("Armor");
                    myShips[tailRow][tailCol].setSurface("Ship");
                }
            }
        }

        if(shipToAdd.getName().equals("Submarine")) {
            if(!shipToAdd.getSubmerged()) {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        myShips[headRow][headCol].setSurface("Ship");
                        myShips[headRow][headCol + 1].setSurface("Ship");
                        myShips[headRow][headCol + 2].setSurface("Ship");
                        myShips[tailRow][tailCol].setSurface("Armor");
                        myShips[tailRow - 1][tailCol - 1].setSurface("Ship");
                    } else {
                        myShips[headRow][headCol].setSurface("Ship");
                        myShips[headRow][tailCol + 1].setSurface("Ship");
                        myShips[headRow][tailCol + 2].setSurface("Ship");
                        myShips[tailRow][tailCol].setSurface("Armor");
                        myShips[tailRow + 1][tailCol + 1].setSurface("Ship");
                    }
                } else {
                    if (headRow < tailRow) {
                        myShips[headRow][headCol].setSurface("Ship");
                        myShips[headRow + 1][headCol].setSurface("Ship");
                        myShips[headRow + 2][headCol].setSurface("Ship");
                        myShips[tailRow][tailCol].setSurface("Armor");
                        myShips[tailRow - 1][tailCol + 1].setSurface("Ship");
                    } else {
                        myShips[headRow][headCol].setSurface("Ship");
                        myShips[tailRow + 1][tailCol].setSurface("Ship");
                        myShips[tailRow + 2][tailCol].setSurface("Ship");
                        myShips[tailRow][tailCol].setSurface("Armor");
                        myShips[tailRow + 1][tailCol - 1].setSurface("Ship");
                    }
                }
            } else {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        myShips[headRow][headCol].setUnderwater("Ship");
                        myShips[headRow][headCol + 1].setUnderwater("Ship");
                        myShips[headRow][headCol + 2].setUnderwater("Ship");
                        myShips[tailRow][tailCol].setUnderwater("Armor");
                        myShips[tailRow - 1][tailCol - 1].setUnderwater("Ship");
                    } else {
                        myShips[headRow][headCol].setUnderwater("Ship");
                        myShips[headRow][tailCol + 1].setUnderwater("Ship");
                        myShips[headRow][tailCol + 2].setUnderwater("Ship");
                        myShips[tailRow][tailCol].setUnderwater("Armor");
                        myShips[tailRow - 1][tailCol + 1].setUnderwater("Ship");
                    }
                } else {
                    if (headRow < tailRow) {
                        myShips[headRow][headCol].setUnderwater("Ship");
                        myShips[headRow + 1][headCol].setUnderwater("Ship");
                        myShips[headRow + 2][headCol].setUnderwater("Ship");
                        myShips[tailRow][tailCol].setUnderwater("Armor");
                        myShips[tailRow - 1][tailCol + 1].setUnderwater("Ship");
                    } else {
                        myShips[headRow][headCol].setUnderwater("Ship");
                        myShips[tailRow + 1][tailCol].setUnderwater("Ship");
                        myShips[tailRow + 2][tailCol].setUnderwater("Ship");
                        myShips[tailRow][tailCol].setUnderwater("Armor");
                        myShips[tailRow + 1][tailCol - 1].setUnderwater("Ship");
                    }
                }
            }
        }
        if(shipToAdd.getName().equals("Tower Ship")) {
            myShips[headRow][headCol].setSurface("Ship");
            myShips[headRow][headCol].setUnderwater("Captain");
        }
    }

    public void printMyShips() {
        for (int i = 0; i < 10; i++) {
            for(int j=0; j < 10 ; j++) {
                System.out.print(myShips[i][j].getSurface() + "/" + myShips[i][j].getUnderwater() + "  ");
            }
            System.out.println();
        }
    }

    public void printMyShots() {
        for (int i = 0; i < 10; i++) {
            for(int j=0; j < 10 ; j++) {
                System.out.print(myShots[i][j].getSurface() + "/" + myShots[i][j].getUnderwater() + "  ");
            }
            System.out.println();
        }
    }

    public void updateBoards(String location, Weapon hitResults) {

        int[] position = convertPosition(location);
        int row = position[0];
        int col = position[1];

        if(hitResults.getShipHit() && isWaiting && !hitResults.getCaptainHit()) {
            myShips[row][col].setSurface("Damage");
        } else if(hitResults.getShipHit() && isWaiting && hitResults.getCaptainHit()) {
            myShips[row][col].setSurface("Critical");
        } else if(hitResults.getShipHit() && isWaiting) {
            myShips[row][col].setSurface("Captain");
        } else if(hitResults.getShipHit() && !hitResults.getArmorHit()) {
            myShots[row][col].setSurface("HIT");
        } else if(hitResults.getShipHit()) {
            myShots[row][col].setSurface("MISS"); //This is miss b/c writeup says first hit to armor counts as miss.
        } else if(!isWaiting){
            myShots[row][col].setSurface("MISS");
        }

        if(hitResults.getArmorHit() && isWaiting) {
            myShips[row][col].setSurface("Captain");
        }

        if(hitResults.getUnderShipHit() && isWaiting && !hitResults.getUnderCaptainHit()) {
            myShips[row][col].setUnderwater("Damage");
        } else if(hitResults.getUnderShipHit() && isWaiting && hitResults.getUnderCaptainHit()) {
            myShips[row][col].setUnderwater("Critical");
        } else if(hitResults.getUnderShipHit() && isWaiting) {
            myShips[row][col].setUnderwater("Captain");
        } else if(hitResults.getUnderShipHit() && !hitResults.getUnderArmorHit()) {
            myShots[row][col].setUnderwater("HIT");
        } else if(hitResults.getUnderShipHit()) {
            myShots[row][col].setUnderwater("MISS");
        } else if(!isWaiting){
            myShots[row][col].setUnderwater("MISS");
        }

        if(hitResults.getUnderArmorHit() && isWaiting) {
            myShips[row][col].setUnderwater("Captain");
        }
    }

    //Helper Functions

    //Converts string coordinate to indices
    public int[] convertPosition(String Location) {
        if(Location == null) {
            return new int[]{-1, -1};
        }
        return new int[]{Integer.parseInt(Location.substring(1)) -1, Location.charAt(0) -65};
    }

    public Boolean isSunk(int shipIndex){
        int[] headPosition = convertPosition(playerFleet.get(shipIndex).getHead());
        int[] tailPosition = convertPosition(playerFleet.get(shipIndex).getTail());
        int[] captainPosition = convertPosition(playerFleet.get(shipIndex).getCaptainLocation());

        int headRow = headPosition[0];
        int headCol = headPosition[1];
        int tailRow = tailPosition[0];
        int tailCol = tailPosition[1];
        int captainRow = captainPosition[0];
        int captainCol = captainPosition[1];

        if((myShips[captainRow][captainCol].getSurface().equals("Critical") || myShips[captainRow][captainCol].getUnderwater().equals("Critical")) && !playerFleet.get(shipIndex).getCasualtyReported()) {
            System.out.println("You sunk my " + playerFleet.get(shipIndex).getName());
            playerFleet.get(shipIndex).setCasualtyReported(true);
            shipCount--;


            if(!playerFleet.get(shipIndex).getName().equals("Submarine")) {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        for (int i = headCol; i < tailCol + 1; i++) {
                            myShips[headRow][i].setSurface("Sea");
                        }
                    } else {
                        for (int i = tailCol; i < headCol + 1; i++) {
                            myShips[headRow][i].setSurface("Sea");
                        }
                    }
                } else {
                    if (headRow < tailRow) {
                        for (int i = headRow; i < tailRow + 1; i++) {
                            myShips[i][headCol].setSurface("Sea");
                        }
                    } else {
                        for (int i = tailRow; i < headRow + 1; i++) {
                            myShips[i][headCol].setSurface("Sea");
                        }
                    }
                }
            } else if(!playerFleet.get(shipIndex).getSubmerged()) {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        for (int i = headCol; i < tailCol + 1; i++) {
                            myShips[headRow][i].setSurface("Sea");
                        }
                        myShips[tailRow - 1][tailCol - 1].setSurface("Sea");
                    } else {
                        for (int i = tailCol; i < headCol + 1; i++) {
                            myShips[headRow][i].setSurface("Sea");
                        }
                        myShips[tailRow + 1][tailCol + 1].setSurface("Sea");
                    }
                } else {
                    if (headRow < tailRow) {
                        for (int i = headRow; i < tailRow + 1; i++) {
                            myShips[i][headCol].setSurface("Sea");
                        }
                        myShips[tailRow - 1][tailCol + 1].setSurface("Sea");
                    } else {
                        for (int i = tailRow; i < headRow + 1; i++) {
                            myShips[i][headCol].setSurface("Sea");
                        }
                        myShips[tailRow + 1][tailCol - 1].setSurface("Sea");
                    }
                }
            } else {
                if (headRow == tailRow) {
                    if (headCol < tailCol) {
                        for (int i = headCol; i < tailCol + 1; i++) {
                            myShips[headRow][i].setUnderwater("Sea");
                        }
                        myShips[tailRow - 1][tailCol - 1].setUnderwater("Sea");
                    } else {
                        for (int i = tailCol; i < headCol + 1; i++) {
                            myShips[headRow][i].setUnderwater("Sea");
                        }
                        myShips[tailRow + 1][tailCol + 1].setUnderwater("Sea");
                    }
                } else {
                    if (headRow < tailRow) {
                        for (int i = headRow; i < tailRow + 1; i++) {
                            myShips[i][headCol].setUnderwater("Sea");
                        }
                        myShips[tailRow - 1][tailCol + 1].setUnderwater("Sea");
                    } else {
                        for (int i = tailRow; i < headRow + 1; i++) {
                            myShips[i][headCol].setUnderwater("Sea");
                        }
                        myShips[tailRow + 1][tailCol - 1].setUnderwater("Sea");
                    }
                }
            }
            return true;
        }

        if(playerFleet.get(shipIndex).getName().equals("Minesweeper") && myShips[headRow][headCol].getSurface().equals("Captain")) {
            return false;
        }

        if(playerFleet.get(shipIndex).getName().equals("Destroyer")) {
            if(headRow == tailRow){
                if(headCol < tailCol && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][headCol + 1].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow][headCol + 1].getSurface().equals("Captain"))) {
                    return false;
                } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][tailCol + 1].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow][tailCol + 1].getSurface().equals("Captain")){
                    return false;
                }
            } else {
                if(headRow < tailRow && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow + 1][headCol].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow + 1][headCol].getSurface().equals("Captain"))) {
                    return false;
                } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[tailRow + 1][tailCol].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[tailRow + 1][tailCol].getSurface().equals("Captain")){
                    return false;
                }
            }
        }

        if(playerFleet.get(shipIndex).getName().equals("Battleship")) {
            if(headRow == tailRow){
                if(headCol < tailCol && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][headCol + 1].getSurface().equals("Ship") || myShips[headRow][headCol + 2].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow][headCol + 2].getSurface().equals("Captain"))) {
                    return false;
                } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][tailCol + 1].getSurface().equals("Ship") || myShips[headRow][tailCol + 2].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow][tailCol + 2].getSurface().equals("Captain")){
                    return false;
                }
            } else {
                if(headRow < tailRow && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow + 1][headCol].getSurface().equals("Ship") || myShips[headRow + 2][headCol].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[headRow + 2][headCol].getSurface().equals("Captain"))) {
                    return false;
                } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[tailRow + 1][tailCol].getSurface().equals("Ship") || myShips[tailRow + 2][tailCol].getSurface().equals("Armor") || myShips[tailRow][tailCol].getSurface().equals("Ship") || myShips[tailRow + 2][tailCol].getSurface().equals("Captain")){
                    return false;
                }
            }
        }

        if(playerFleet.get(shipIndex).getName().equals("Submarine")) {
            if(!playerFleet.get(shipIndex).getSubmerged()) {
                if (headRow == tailRow) {
                    if (headCol < tailCol && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][headCol + 1].getSurface().equals("Ship") || myShips[headRow][headCol + 2].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Armor") || myShips[tailRow - 1][tailCol - 1].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Captain"))) {
                        return false;
                    } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][tailCol + 1].getSurface().equals("Ship") || myShips[headRow][tailCol + 2].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Armor") || myShips[tailRow - 1][tailCol + 1].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Captain")){
                        return false;
                    }
                } else {
                    if (headRow < tailRow && (myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow + 1][headCol].getSurface().equals("Ship") || myShips[headRow + 2][headCol].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Armor") || myShips[tailRow - 1][tailCol + 1].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Captain"))) {
                        return false;
                    } else if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[tailRow + 1][tailCol].getSurface().equals("Ship") || myShips[tailRow + 2][tailCol].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Armor") || myShips[tailRow + 1][tailCol - 1].getSurface().equals("Ship") || myShips[tailRow][tailCol].getSurface().equals("Captain")){
                        return false;
                    }
                }
            } else {
                if (headRow == tailRow) {
                    if (headCol < tailCol && (myShips[headRow][headCol].getUnderwater().equals("Ship") || myShips[headRow][headCol + 1].getUnderwater().equals("Ship") || myShips[headRow][headCol + 2].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Armor") || myShips[tailRow - 1][tailCol - 1].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Captain"))) {
                        return false;
                    } else if(myShips[headRow][headCol].getUnderwater().equals("Ship") || myShips[headRow][tailCol + 1].getUnderwater().equals("Ship") || myShips[headRow][tailCol + 2].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Armor") || myShips[tailRow - 1][tailCol + 1].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Captain")){
                        return false;
                    }
                } else {
                    if (headRow < tailRow && (myShips[headRow][headCol].getUnderwater().equals("Ship") || myShips[headRow + 1][headCol].getUnderwater().equals("Ship") || myShips[headRow + 2][headCol].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Armor") || myShips[tailRow - 1][tailCol + 1].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Captain"))) {
                        return false;
                    } else if(myShips[headRow][headCol].getUnderwater().equals("Ship") || myShips[tailRow + 1][tailCol].getUnderwater().equals("Ship") || myShips[tailRow + 2][tailCol].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Armor") || myShips[tailRow + 1][tailCol - 1].getUnderwater().equals("Ship") || myShips[tailRow][tailCol].getUnderwater().equals("Captain")){
                        return false;
                    }
                }
            }
        }

        if(playerFleet.get(shipIndex).getName().equals("Tower Ship")) {
            if(myShips[headRow][headCol].getSurface().equals("Ship") || myShips[headRow][headCol].getSurface().equals("Captain")) {
                return false;
            }
        }

        if(!playerFleet.get(shipIndex).getCasualtyReported()) {
            System.out.println("You sunk my " + playerFleet.get(shipIndex).getName());
            shipCount--;
        }
        playerFleet.get(shipIndex).setCasualtyReported(true);

        return true;
    }

    public void move(int moveDirection, Ship[] fleet) {
        if(!isWaiting) {
            if (moveDirection == 1) {
                for (int i = 0; i < 10; i++) {
                    if (!myShips[0][i].getSurface().equals("Sea") || !myShips[0][i].getUnderwater().equals("Sea")) {
                        System.out.println("Error, at least one ship is too far north, unable to move fleet.");
                        return;
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 10; j++) {
                        myShips[i][j] = myShips[i + 1][j];
                    }
                }
                for (int i = 0; i < 10; i++) {
                    myShips[9][i] = new Cell("Sea", "Sea");
                }

                for (int i = 0; i < fleet.length; i++) {
                    String newHead = fleet[i].getHead().substring(0, 1) + (Integer.parseInt(fleet[i].getHead().substring(1)) +1);
                    String newTail = fleet[i].getTail().substring(0, 1) + (Integer.parseInt(fleet[i].getHead().substring(1)) +1);
                    String newCapt = fleet[i].getCaptainLocation().substring(0, 1) + (Integer.parseInt(fleet[i].getCaptainLocation().substring(1)) +1);
                    fleet[i].setHead(newHead);
                    fleet[i].setTail(newTail);
                    fleet[i].setCaptainLocation(newCapt);
                }

            } else if (moveDirection == -1) {
                for (int i = 0; i < 10; i++) {
                    if (!myShips[9][i].getSurface().equals("Sea") || !myShips[9][i].getUnderwater().equals("Sea")) {
                        System.out.println("Error, at least one ship is too far south, unable to move fleet.");
                        return;
                    }
                }
                for (int i = 9; i > 0; i--) {
                    for (int j = 0; j < 10; j++) {
                        myShips[i][j] = myShips[i - 1][j];
                    }
                }
                for (int i = 0; i < 10; i++) {
                    myShips[0][i] = new Cell("Sea", "Sea");
                }

                for (int i = 0; i < fleet.length; i++) {
                    String newHead = fleet[i].getHead().substring(0, 1) + (Integer.parseInt((fleet[i].getHead().substring(1))) + 1);
                    String newTail = fleet[i].getTail().substring(0, 1) + (Integer.parseInt((fleet[i].getTail().substring(1))) + 1);
                    String newCapt = fleet[i].getCaptainLocation().substring(0, 1) + (Integer.parseInt((fleet[i].getCaptainLocation().substring(1))) + 1);
                    fleet[i].setHead(newHead);
                    fleet[i].setTail(newTail);
                    fleet[i].setCaptainLocation(newCapt);
                }
            } else if (moveDirection == 2) {
                for (int i = 0; i < 10; i++) {
                    if (!myShips[i][9].getSurface().equals("Sea") || !myShips[i][9].getUnderwater().equals("Sea")) {
                        System.out.println("Error, at least one ship is too far east, unable to move fleet.");
                        return;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 9; j > 0; j--) {
                        myShips[i][j] = myShips[i][j - 1];
                    }
                }
                for (int i = 0; i < 10; i++) {
                    myShips[i][0] = new Cell("Sea", "Sea");
                }

                for (int i = 0; i < fleet.length; i++) {
                    String newHead = (char)(fleet[i].getHead().charAt(0) + 1) + fleet[i].getHead().substring(1);
                    String newTail = (char)(fleet[i].getTail().charAt(0) + 1) + fleet[i].getTail().substring(1);
                    String newCapt = (char)(fleet[i].getCaptainLocation().charAt(0) + 1) + fleet[i].getCaptainLocation().substring(1);
                    fleet[i].setHead(newHead);
                    fleet[i].setTail(newTail);
                    fleet[i].setCaptainLocation(newCapt);
                }
            } else if (moveDirection == -2) {
                for (int i = 0; i < 10; i++) {
                    if (!myShips[i][0].getSurface().equals("Sea") || !myShips[i][0].getUnderwater().equals("Sea")) {
                        System.out.println("Error, at least one ship is too far west, unable to move fleet.");
                        return;
                    }
                }
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 9; j++) {
                        myShips[i][j] = myShips[i][j + 1];
                    }
                }
                for (int i = 0; i < 10; i++) {
                    myShips[i][9] = new Cell("Sea", "Sea");
                }

                for (int i = 0; i < fleet.length; i++) {
                    String newHead = (char)(fleet[i].getHead().charAt(0) - 1) + fleet[i].getHead().substring(1);
                    String newTail = (char)(fleet[i].getTail().charAt(0) - 1) + fleet[i].getTail().substring(1);
                    String newCapt = (char)(fleet[i].getCaptainLocation().charAt(0) - 1) + fleet[i].getCaptainLocation().substring(1);
                    fleet[i].setHead(newHead);
                    fleet[i].setTail(newTail);
                    fleet[i].setCaptainLocation(newCapt);
                }
            }
        }
    }
}