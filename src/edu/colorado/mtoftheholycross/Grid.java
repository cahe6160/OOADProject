package edu.colorado.mtoftheholycross;

public class Grid {
    private
        String[][] myShips;
        String[][] myShots;
        Boolean isWaiting;


    public Grid() {
        myShips = new String[10][10];
        myShots = new String[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                myShips[i][j] = "Sea";
                myShots[i][j] = "Fog";
            }
        }
        isWaiting = true;
    }

    public Grid(Boolean isWaiting) {
        myShips = new String[10][10];
        myShots = new String[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
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

        if(headPosition[0] == tailPosition[0])
        {
            for(int i = headPosition[1]; i <= tailPosition[1]; i++)
            {
                myShips[i][headPosition[0]] = "Ship";
            }
        }
        else
        {
            for(int i = headPosition[0]; i <= tailPosition[0]; i++)
            {
                myShips[headPosition[1]][i] = "Ship";
            }
        }
    }

    public Boolean checkHit(String Location, String[][] opponentBoard) {
       int[] position = convertPosition(Location);

       if(opponentBoard[position[1]][position[0]].equals("Ship"))
       {
           System.out.println("Shot HIT");
           return true;
       } else
       {
           System.out.println("Shot MISS");
           return false;
       }
    }

    public void printMyShips() {
        for (int i = 0; i < 10; i++)
        {
            for(int j=0; j < 10 ; j++)
            {
                System.out.print(myShips[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void printMyShots() {
        for (int i = 0; i < 10; i++)
        {
            for(int j=0; j < 10 ; j++)
            {
                System.out.print(this.myShots[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void updateBoards(String Location, Boolean isHit){
        int[] position = convertPosition(Location);
        if(isHit && isWaiting){
            myShips[position[1]][position[0]] = "Damage";
        }else if(isHit){
            myShots[position[1]][position[0]] = "HIT";
        }else if(!isWaiting){
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

        if(headCord[0] == tailCord[0]) {
            for(int i = headCord[1]; i <= tailCord[1]; i++) {
                if(myShips[i][headCord[0]].equals("Ship")){
                    return false;
                }
            }
        } else {
            for(int i = headCord[0]; i <= tailCord[0]; i++) {
                if(myShips[headCord[1]][i].equals("Ship")){
                    return false;
                }
            }
        }
        return true;
    }
}

