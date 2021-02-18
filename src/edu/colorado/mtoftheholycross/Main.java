package edu.colorado.mtoftheholycross;
  
public class Main {

    public static void main(String[] args) {
        // write your code here
         Ship ship = new Ship();//
         ship.show();//

        Grid shipGrid1 = new Grid("ships");
        Grid shotGrid1 = new Grid("shots");

        shipGrid1.addShip("A1", "A4");
        shipGrid1.printBoard();
        //shipGrid1.checkHit("A2");//HIT
        String result = shipGrid1.checkHit("A6");//MISS


    }
}
