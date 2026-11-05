package controller;

import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command cmd) {
        commands.add(cmd);
    }

    public void executeCommand(Command cmd) {
        cmd.execute();
    }
}
