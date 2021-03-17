package edu.colorado.mtoftheholycross;

public class Grid {
    private
        String[][] myShips;
        String[][] myShots;
        Boolean isWaiting;


    public Grid() {
        myShips = new String[10][10];
        myShots = new String[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                myShips[i][j] = "Sea";
                myShots[i][j] = "Fog";
            }
        }
        isWaiting = true;
    }

    public Grid(Boolean isWaiting) {
        myShips = new String[10][10];
        myShots = new String[10][10];
        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                myShips[i][j] = "Sea";
                myShots[i][j] = "Fog";
            }
        }
        this.isWaiting = isWaiting;
    }

    public Grid(String[][] myShips, String[][] myShots, Boolean isWaiting) {
        this.myShips = myShips;
        this.myShots = myShots;
        this.isWaiting = isWaiting;
    }

    public String[][] getMyShips() {
        return myShips;
    }

    public String[][] getMyShots() {
        return myShots;
    }

    public void addShip(Ship shipToAdd) {

        int[] headPosition = convertPosition(shipToAdd.getHead());
        int[] tailPosition = convertPosition(shipToAdd.getTail());
        int[] captainPosition = convertPosition(shipToAdd.getCaptainLocation());

        if(headPosition[0] == tailPosition[0]) {
            for(int i = headPosition[1]; i <= tailPosition[1]; i++) {
                myShips[i][headPosition[0]] = "Ship";
            }
        } else {
            for(int i = headPosition[0]; i <= tailPosition[0]; i++) {
                myShips[headPosition[1]][i] = "Ship";
            }
        }
        if(shipToAdd.getName().equals("Minesweeper")) {
            myShips[captainPosition[1]][captainPosition[0]] = "Captain";
        } else {
            myShips[captainPosition[1]][captainPosition[0]] = "Armor";
        }
    }

    public void printMyShips() {
        for (int i = 0; i < 10; i++) {
            for(int j=0; j < 10 ; j++) {
                System.out.print(myShips[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void printMyShots() {
        for (int i = 0; i < 10; i++) {
            for(int j=0; j < 10 ; j++) {
                System.out.print(this.myShots[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void updateBoards(String location, int[] hitResults) {

        int[] position = convertPosition(location);

       if(hitResults[0] == 1 && isWaiting && hitResults[1] == 0) {
           myShips[position[1]][position[0]] = "Damage";
       } else if(hitResults[0] == 1 && isWaiting && hitResults[1] == 1) {
           myShips[position[1]][position[0]] = "Critical";
       } else if(hitResults[0] == 1 && isWaiting) {
           myShips[position[1]][position[0]] = "Captain";
       } else if(hitResults[0] == 1 && hitResults[1] != 2) {
           myShots[position[1]][position[0]] = "HIT";
       } else if(hitResults[0] == 1) {
           myShots[position[1]][position[0]] = "MISS";
       } else if(!isWaiting){
           myShots[position[1]][position[0]] = "MISS";
       }
    }

    //Helper Functions

    //Converts string coordinate to indices
    public int[] convertPosition(String Location) {
        return new int[]{Location.charAt(0) -65, Integer.parseInt(Location.substring(1)) -1};
    }

    public Boolean isSunk(Ship shipToCheck){
        int[] headCord = convertPosition(shipToCheck.getHead());
        int[] tailCord = convertPosition(shipToCheck.getTail());
        int[] captainCord = convertPosition(shipToCheck.getCaptainLocation());

        if(headCord[0] == tailCord[0]) {
            for(int i = headCord[1]; i <= tailCord[1]; i++) {
                if(myShips[i][headCord[0]].equals("Ship") || myShips[i][headCord[0]].equals("Armor") || myShips[i][headCord[0]].equals("Captain")){
                    return false;
                }
            }
        } else {
            for(int i = headCord[0]; i <= tailCord[0]; i++) {
                if(myShips[headCord[1]][i].equals("Ship") || myShips[i][headCord[0]].equals("Armor") || myShips[i][headCord[0]].equals("Captain")){
                    return false;
                }
            }
        }

        if(headCord[0] == tailCord[0]) {
            for(int i = headCord[1]; i <= tailCord[1]; i++) {
                if(myShips[i][headCord[0]].equals("Critical") && !shipToCheck.getCasualtyReported()) {
                    System.out.println("You sunk my " + shipToCheck.getName());
                    shipToCheck.setCasualtyReported(true);
                }
            }
        } else {
            for(int i = headCord[0]; i <= tailCord[0]; i++) {
                if(myShips[headCord[1]][i].equals("Critical") && !shipToCheck.getCasualtyReported()){
                    System.out.println("You sunk my " + shipToCheck.getName());
                    shipToCheck.setCasualtyReported(true);
                }
            }
        }

        if(!shipToCheck.getCasualtyReported()) {
            System.out.println("You sunk my " + shipToCheck.getName());
        }
        shipToCheck.setCasualtyReported(true);
        return true;
    }
}

