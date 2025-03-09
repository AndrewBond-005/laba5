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
    public void execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return;
        }
        console.println("Очистка коллекции");
        collectionManager.removeAll();
    }
}
