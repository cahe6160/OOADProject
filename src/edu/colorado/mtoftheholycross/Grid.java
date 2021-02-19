package edu.colorado.mtoftheholycross;

public class Grid {
    String[][] board;
    String type;

    public Grid() {
        board = new String[10][10];
    }

    public Grid(String type) {
        board = new String[10][10];
        this.board = board;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                board[i][headPosition[0]] = "Ship";
            }
        }
        else
        {
            for(int i = headPosition[0]; i <= tailPosition[0]; i++)
            {
                board[headPosition[1]][i] = "Ship";
            }
        }
    }

    public String checkHit(String Location) {
       int[] position = convertPosition(Location);

       if(board[position[1]][position[0]] == "Ship" )
       {
           System.out.println("Shot HIT");
           return "HIT";
       }
       else
       {
           System.out.println("Shot MISS");
           return "MISS";
       }
    }

    public void printBoard() {
        for (int i = 0; i < 10; i++)
        {
            for(int j=0; j < 10 ; j++)
            {
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("");
        }

    }

    //Helper Functions
    public int[] convertPosition(String Location) {
        int[] position = {Location.charAt(0) -65, Integer.parseInt(Location.substring(1,2)) -1};
        return position;
    }
}

