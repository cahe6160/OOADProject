package edu.colorado.mtoftheholycross;

import java.util.*;


public class MoveCommand implements Command{
    private Grid grid;
    private Ship[] fleet;
    List<Integer> previousDirections = new ArrayList<Integer>();
    Map<String, Integer> directionMap = new HashMap<String, Integer>();
    private String direction;
    private int index;

    MoveCommand(Grid grid, String direction, Ship[] fleet) {
        directionMap.put("NORTH", 1);
        directionMap.put("SOUTH", -1);
        directionMap.put("EAST", 2);
        directionMap.put("WEST", -2);

        this.grid = grid;
        this.direction = direction;
        this.fleet = fleet;
        index = -1;

        this.previousDirections.add(directionMap.get(direction) * -1);
    }

    MoveCommand(Grid grid, String direction, int index, List<Integer> previousDirections, Ship[] fleet) {
        this.grid = grid;
        this.direction = direction;
        this.fleet = fleet;

        this.previousDirections = previousDirections.subList(0, index);
        this.index = index;
    }

    @Override
    public void execute() {
        grid.move(directionMap.get(direction), fleet);
        index++;
    }

    @Override
    public void undo() {
        if(index > -1) {
            grid.move(previousDirections.get(index), fleet);
            index--;
        }
    }

    @Override
    public void redo() {
        if(index < previousDirections.size()) {
            index++;
            grid.move(-1 * previousDirections.get(index), fleet);
        }
    }


}
