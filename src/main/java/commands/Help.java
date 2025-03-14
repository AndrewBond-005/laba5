package commands;

import managers.CommandManager;
import utility.Console;

import java.util.Map;

/**
 * Команда 'help'. Выводит справку по доступным командам.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Help extends Command {
    private final CommandManager commandManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link Help}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param commandManager менеджер команд для управления доступными командами
     */
    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.commandManager = commandManager;
        this.console = console;
    }

    /**
     * Выполняет команду вывода справки по всем доступным командам.
     *
     * @param arguments аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        Map<String, Command> commands = commandManager.getCommands();
        for (Command command : commands.values()) {
            System.out.printf(" %-35s%-1s%n", command.getName(), command.getDescription());
        }
        return 0;
    }
}