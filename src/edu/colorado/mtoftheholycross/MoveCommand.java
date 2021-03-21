package edu.colorado.mtoftheholycross;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MoveCommand implements Command{
    private Grid grid;
    List<Integer> previousDirections = new ArrayList<Integer>();
    private int direction;
    private int index;

    //1 is North
    //-1 is South
    //2 is East
    //-2 is West

    MoveCommand(Grid grid, int direction) {
        this.grid = grid;
        this.direction = direction;
        index = -1;

        this.previousDirections.add(direction * -1);
    }

    MoveCommand(Grid grid, int direction, int index, List<Integer> previousDirections) {
        this.grid = grid;
        this.direction = direction;

        this.previousDirections = previousDirections.subList(0, index);
        this.index = index;
    }

    @Override
    public void execute() {
        grid.move(direction);
        index++;
    }

    @Override
    public void undo() {
        if(index > -1) {
            grid.move(previousDirections.get(index));
            index--;
        }
    }

    @Override
    public void redo() {
        if(index < previousDirections.size()) {
            index++;
            grid.move(-1 * previousDirections.get(index));
        }
    }


}
