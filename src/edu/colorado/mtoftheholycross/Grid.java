package edu.colorado.mtoftheholycross;

public class Grid {
    String[][] myShips;
    String[][] myShots;
    Boolean isPlayerOne;


    public Grid() {
        myShips = new String[10][10];
        myShots = new String[10][10];
        isPlayerOne = true;
    }

    public Grid(Boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
    }

    public Grid(String[][] myShips, String[][] myShots, Boolean isPlayerOne) {
        this.myShips = myShips;
        this.myShots = myShots;
        this.isPlayerOne = isPlayerOne;
    }

    public void addShip(String Head, String Tail) {

        int[] headPosition = { Head.charAt(0) -65, Integer.parseInt(Head.substring(1,2)) -1};
        //System.out.println("Head "+ headPosition[0] + " " + headPosition[1]);
        int[] tailPosition = { Tail.charAt(0) -65, Integer.parseInt(Tail.substring(1,2)) -1};
        //System.out.println("Tail "+ tailPosition[0] + " " + tailPosition[1]);

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

    public Boolean checkHit(String Location) {
       int[] position = convertPosition(Location);

       if(myShips[position[1]][position[0]] == "Ship" )
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
                System.out.print(myShots[i][j] + "  ");
            }
            System.out.println("");
        }
    }

    public void updateBoards(String Location, Boolean isHit){
        int[] position = convertPosition(Location);
        if(isHit && isPlayerOne){
            myShips[position[0]][position[1]] = "Damage";
        }else if(isHit){
            myShots[position[0]][position[1]] = "HIT";
        }else if(!isPlayerOne){
            myShots[position[0]][position[1]] = "MISS";
        }
    }

    //Helper Functions
    public int[] convertPosition(String Location) {
        return new int[]{Location.charAt(0) -65, Integer.parseInt(Location.substring(1,2)) -1};
    }
}

