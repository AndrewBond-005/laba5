package managers;

import commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    Map<String, Command> commands = new HashMap<>();
    private int maxRecurtionDeep=100;

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    public int getMaxRecurtionDeep() {
        return maxRecurtionDeep;
    }

    public void setMaxRecurtionDeep(int maxRecurtionDeep) {
        this.maxRecurtionDeep = maxRecurtionDeep;
    }
}
