package commands;

import managers.CommandManager;
import utility.Console;

import java.util.Map;


public class Help extends Command {
    private final CommandManager commandManager;
    private final Console console;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }


    @Override
    public boolean execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return true;
        }
        Map<String, Command> commands = commandManager.getCommands();
        for (Command command : commands.values()) {
            System.out.printf(" %-35s%-1s%n", command.getName(), command.getDescription());
        }
        return true;
    }

}
