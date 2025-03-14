package commands;

import managers.CollectionManager;
import utility.Console;

public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public int execute(String arguments, boolean scriprtMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        console.println("Очистка коллекции");
        collectionManager.removeAll();
        return 0;
    }
}
