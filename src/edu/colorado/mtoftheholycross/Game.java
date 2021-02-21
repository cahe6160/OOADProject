package edu.colorado.mtoftheholycross;

public class Game {
    private
        Ship[] p1Fleet;
        Ship[] p2Fleet;
        Grid p1Grid;
        Grid p2Grid;

    public Game() {
        p2Fleet = new Ship[1];
        p2Fleet[0] = new Ship("Minesweeper", "A1", "A2");

        p1Fleet = new Ship[1];
        p1Fleet[0] = new Ship("Minesweeper", "A7", "A8");

        p1Grid = new Grid(false);
        p2Grid = new Grid(true);
    }

    public Grid getP1Grid() {
        return p1Grid;
    }

    public Grid getP2Grid() {
        return p2Grid;
    }

    public void setP1Grid(Grid p1Grid) {
        this.p1Grid = p1Grid;
    }

    public void setP2Grid(Grid p2Grid) {
        this.p2Grid = p2Grid;
    }

    public int switchTurn(){
        if(p2Grid.isWaiting && playerSurrender()) {
            System.out.println("Player 2 surrendered!");
            return 0;
        }else if(p1Grid.isWaiting && playerSurrender()) {
            System.out.println("Player 1 surrendered!");
            return 0;
        }
        p1Grid.isWaiting = !p1Grid.isWaiting;
        p2Grid.isWaiting = !p2Grid.isWaiting;
        return -1;
    }

    public Boolean playerSurrender() {
        if(p1Grid.isWaiting) {
            for (int i = 0; i < p1Fleet.length; i++) {
                if (!p1Grid.isSunk(p1Fleet[i])) {
                    return false;
                }
            }
        }
        if(p2Grid.isWaiting) {
            for (int i = 0; i < p2Fleet.length; i++) {
                if (!p2Grid.isSunk(p2Fleet[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Ship[] getP1Fleet() {
        return p1Fleet;
    }

    public Ship[] getP2Fleet() {
        return p2Fleet;
    }
}
