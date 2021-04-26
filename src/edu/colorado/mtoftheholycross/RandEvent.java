package edu.colorado.mtoftheholycross;
import java.util.concurrent.ThreadLocalRandom;

/**
 * RandEvent represents the possible random events that can happen at every players turn,
 * Based on probability which is calculated in switchTurn(), randEvent will call a specific method.
 * (Either a good or a bad Event)
 */
public class RandEvent {

    public RandEvent(Grid playerGrid, Player currentPlayer, int event) {
        if(event == 0) {
            malfunction(playerGrid, currentPlayer);
        } else if (event == 1) {
            mineHit(playerGrid, currentPlayer);
        } else if (event == 2) {
            tsunami(playerGrid, currentPlayer);
        } else if (event == 3) {
            wellRested(playerGrid, currentPlayer);
        }
    }

    /**
     * Well rested is one of the good events, where crew is well rested,
     * In cooperation with switchTurn() this would give a player an extra turn.
     * @param playerGrid the current player's grid
     * @param currentPlayer the current player object
     */
    public void wellRested(Grid playerGrid, Player currentPlayer) {
        System.out.println("Calm waters! Your fleet travelled through calm waters, and your crew has been rested. You get another turn!");
    }

    /**
     * tsunami is considered one of the good events, where a tsunami may move your ships in a random direction.
     * @param playerGrid the current player's grid
     * @param currentPlayer the current player object
     */
    public void tsunami(Grid playerGrid, Player currentPlayer) {
        System.out.println("Tsunami! Your fleet was caught in a bad storm!");
        int direction = ThreadLocalRandom.current().nextInt(1, 4 + 1);
            if(direction == 1) {
                for(int i=0; i<10; i++) {
                    if(playerGrid.getMyShips()[0][i].getSurface().equals("Ship") || playerGrid.getMyShips()[0][i].getUnderwater().equals("Ship")) {
                        return;
                    }
                }
                playerGrid.move(1, playerGrid.getPlayerFleet().toArray(new Ship[playerGrid.getPlayerFleet().size()]));
            } else if(direction == 2) {
                for(int i=0; i<10; i++) {
                    if(playerGrid.getMyShips()[9][i].getSurface().equals("Ship") || playerGrid.getMyShips()[9][i].getUnderwater().equals("Ship")) {
                        return;
                    }
                }
                playerGrid.move(-1, playerGrid.getPlayerFleet().toArray(new Ship[playerGrid.getPlayerFleet().size()]));
            } else if(direction == 3) {
                for(int i=0; i<10; i++) {
                    if(playerGrid.getMyShips()[i][9].getSurface().equals("Ship") || playerGrid.getMyShips()[i][9].getUnderwater().equals("Ship")) {
                        return;
                    }
                }
                playerGrid.move(2, playerGrid.getPlayerFleet().toArray(new Ship[playerGrid.getPlayerFleet().size()]));
            } else if(direction == 4) {
                for(int i=0; i<10; i++) {
                    if(playerGrid.getMyShips()[i][0].getSurface().equals("Ship") || playerGrid.getMyShips()[i][0].getUnderwater().equals("Ship")) {
                        return;
                    }
                }
                playerGrid.move(-2, playerGrid.getPlayerFleet().toArray(new Ship[playerGrid.getPlayerFleet().size()]));
            }

    }

    /**
     * malfunction is considered one of the bad events. If this happens,
     * the current player loses one of their sonars.
     * @param playerGrid the current player's grid
     * @param currentPlayer the current player object
     */
    public void malfunction(Grid playerGrid, Player currentPlayer) {
        if(currentPlayer.getSonar().getSonarCount() > 1) {
            System.out.println("Malfunction! Your fleet was caught in a lightning storm and you lost one sonar charge.");
            currentPlayer.getSonar().setSonarCount(currentPlayer.getSonar().getSonarCount() - 1);
        }
    }

    /**
     * mineHit is considered one of the bad events. If this happens,
     * the method checks if you have a minesweeper.
     * If you dont, a random ship in the fleet takes some damage.
     * Cannot make damage to a captain location.
     * @param playerGrid the current player's grid
     * @param currentPlayer the current player object
     */
    public void mineHit(Grid playerGrid, Player currentPlayer) {
        if(playerGrid.getPlayerFleet().get(0).getCasualtyReported()){
            for(int i = 1; i < playerGrid.getShipCount(); i++) {
                int[] hitConversion = playerGrid.convertPosition(playerGrid.getPlayerFleet().get(i).getHead());
                if(!playerGrid.getPlayerFleet().get(i).getCasualtyReported() && playerGrid.getMyShips()[hitConversion[0]][hitConversion[1]].getSurface().equals("Ship")) {
                    playerGrid.getMyShips()[hitConversion[0]][hitConversion[1]].setSurface("Damage");
                    return;
                }
            }
        }
    }
}
