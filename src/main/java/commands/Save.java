package commands;

import managers.CollectionManager;
import utility.BackUp;
import utility.Console;


public class Save extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public int execute(String arguments, boolean scriprtMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            //console.print(arguments);
            return 0;
        }
        collectionManager.saveCollection();
        BackUp.clear();///////
        return 0;
    }

}