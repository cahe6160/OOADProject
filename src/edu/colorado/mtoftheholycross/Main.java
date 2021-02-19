package edu.colorado.mtoftheholycross;
  
public class Main {

    public static void main(String[] args) {
        // write your code here
         Ship ship = new Ship();//
         ship.show();//

        Grid p0Grid = new Grid(false);
        Grid p1Grid = new Grid(true);

        p1Grid.addShip("A1", "A4");
        p1Grid.printMyShips();

        Boolean isHit = p1Grid.checkHit("A5");
        System.out.println(isHit);
        p0Grid.updateBoards("A5", isHit);
        System.out.println("\n");
        p1Grid.updateBoards("A5", isHit);

        p0Grid.printMyShots();
        System.out.println("\n");
        p1Grid.printMyShips();
    }
}