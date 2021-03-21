package edu.colorado.mtoftheholycross;
import java.util.ArrayList;
import java.util.List;

//-----------------------INVOKER---------------------------
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void makeMove() {
        command.execute();
    }

    public void undoMove() {
        command.undo();
    }

    public void redoMove() {
        command.redo();
    }
}
