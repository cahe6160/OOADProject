package edu.colorado.mtoftheholycross;
import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private Command command;
    private List<Command> previousCommands = new ArrayList<Command>();
    private int index = -1;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void makeMove() {
        previousCommands.add(command);
        index++;
        command.execute();
    }

    public void undoMove() {
        if(index > -1) {
            command = previousCommands.get(index);
            command.undo();
            index--;
        }
    }

    public void redoMove() {
        if(index < previousCommands.size()) {
            index++;
            command = previousCommands.get(index);
            command.redo();
        }

    }
}
