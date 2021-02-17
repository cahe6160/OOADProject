package edu.colorado.mtoftheholycross;
  
public class Main {

    public static void main(String[] args) {
        // write your code here
         Ship ship = new Ship();
         ship.show();

        Grid shipGrid1 = new Grid("ships");
        shipGrid1.printBoard();

        shipGrid1.addShip("A1", "A4");
        shipGrid1.printBoard();


    }
}
