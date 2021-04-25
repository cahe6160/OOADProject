package edu.colorado.mtoftheholycross;
import java.util.Scanner;

/**
 * Main class
 */
public class Main {
    public static void main(String[] args) {
        Game newGame = Game.getInstance();
        newGame.play();
    }
}