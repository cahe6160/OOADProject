package edu.colorado.mtoftheholycross;

/**
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        Game newGame = Game.getInstance();
        newGame.play();
    }
}