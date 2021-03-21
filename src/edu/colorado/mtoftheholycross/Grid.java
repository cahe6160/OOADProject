package edu.colorado.mtoftheholycross;

public class Grid {
    private Cell[][] myShips;
    private Cell[][] myShots;
    private boolean isWaiting;

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
    }

    public Grid(Cell[][] myShips, Cell[][] myShots, Boolean isWaiting) {
        this.myShips = myShips;
        this.myShots = myShots;
        this.isWaiting = isWaiting;
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

    public void setIsWaiting(boolean waiting) {
        this.isWaiting = waiting;
    }

    public void addShip(Ship shipToAdd) {

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
                        myShips[tailRow - 1][tailCol + 1].setSurface("Ship");
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

    public void updateBoards(String location, int[] hitResults) {

        int[] position = convertPosition(location);
        int row = position[0];
        int col = position[1];


        if(hitResults[0] == 1 && isWaiting && hitResults[1] == 0) {
            myShips[row][col].setSurface("Damage");
        } else if(hitResults[0] == 1 && isWaiting && hitResults[1] == 1) {
            myShips[row][col].setSurface("Critical");
        } else if(hitResults[0] == 1 && isWaiting) {
            myShips[row][col].setSurface("Captain");
        } else if(hitResults[0] == 1 && hitResults[1] != 2) {
            myShots[row][col].setSurface("HIT");
        } else if(hitResults[0] == 1) {
            myShots[row][col].setSurface("MISS"); //This is miss b/c writeup says first hit to armor counts as miss.
        } else if(!isWaiting){
            myShots[row][col].setSurface("MISS");
        }

        if(hitResults.length == 4) {
            if(hitResults[2] == 1 && isWaiting && hitResults[3] == 0) {
                myShips[row][col].setUnderwater("Damage");
            } else if(hitResults[2] == 1 && isWaiting && hitResults[3] == 1) {
                myShips[row][col].setUnderwater("Critical");
            } else if(hitResults[2] == 1 && isWaiting) {
                myShips[row][col].setUnderwater("Captain");
            } else if(hitResults[2] == 1 && hitResults[3] != 2) {
                myShots[row][col].setUnderwater("HIT");
            } else if(hitResults[2] == 1) {
                myShots[row][col].setUnderwater("MISS");
            } else if(!isWaiting){
                myShots[row][col].setUnderwater("MISS");
            }
        }

    }

    //Helper Functions

    //Converts string coordinate to indices
    public int[] convertPosition(String Location) {
        return new int[]{Integer.parseInt(Location.substring(1)) -1, Location.charAt(0) -65};
    }

    public Boolean isSunk(Ship shipToCheck){
        int[] headPosition = convertPosition(shipToCheck.getHead());
        int[] tailPosition = convertPosition(shipToCheck.getTail());
        int[] captainPosition = convertPosition(shipToCheck.getCaptainLocation());

        int headRow = headPosition[0];
        int headCol = headPosition[1];
        int tailRow = tailPosition[0];
        int tailCol = tailPosition[1];
        int captainRow = captainPosition[0];
        int captainCol = captainPosition[1];

        if(shipToCheck.getName().equals("Minesweeper") && myShips[headRow][headCol].getSurface().equals("Captain")) {
            return false;
        }

        if(shipToCheck.getName().equals("Destroyer")) {
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

        if(shipToCheck.getName().equals("Battleship")) {
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

        if(shipToCheck.getName().equals("Submarine")) {
            if(!shipToCheck.getSubmerged()) {
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

        if((myShips[captainRow][captainCol].getSurface().equals("Critical") || myShips[captainRow][captainCol].getUnderwater().equals("Critical")) && !shipToCheck.getCasualtyReported()) {
            System.out.println("You sunk my " + shipToCheck.getName());
            shipToCheck.setCasualtyReported(true);
        }

        if(!shipToCheck.getCasualtyReported()) {
            System.out.println("You sunk my " + shipToCheck.getName());
        }
        shipToCheck.setCasualtyReported(true);

        return true;
    }

    public void move(int moveDirection) {
        if(moveDirection == 1) {
            System.out.println("Moved north");
        } else if(moveDirection == -1) {
            System.out.println("Moved south");
        } else if(moveDirection == 2) {
            System.out.println("Moved East");
        } else if(moveDirection == -2) {
            System.out.println("Moved West");
        }
    }
}

