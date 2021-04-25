package edu.colorado.mtoftheholycross;

/**
 * Player class, represents one of the two players of the game.
 * Holds all necessary attributes for each player.
 * Includes name, weapons, turns, and if they are determined to be the winner
 */
public class Player {

    private String playerName;
    private Sonar sonar;
    private Cannon cannon;
    private Laser laser;
    private Hint hint;
    private int turnCount;
    private boolean isWinner;

    Player(){
        sonar = new Sonar();
        cannon = new Cannon();
        laser = new Laser();
        hint = new Hint();
        turnCount = 0;
    }

    public Cannon getCannon() {
        return cannon;
    }

    public Sonar getSonar() {
        return sonar;
    }

    public Laser getLaser() {
        return laser;
    }

    public Hint getHint() { return hint; }

    public int getTurnCount() {
        return turnCount;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean getIsWinner() {
        return isWinner;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public void setIsWinner(boolean winner) {
        isWinner = winner;
    }
}