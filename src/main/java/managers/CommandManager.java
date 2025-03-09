package managers;

import commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    Map<String, Command> commands = new HashMap<>();

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }
}
