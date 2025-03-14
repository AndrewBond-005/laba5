package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;


public class Show extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все" +
                " элементы коллекции в строковом представлении");
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
        console.println("Содержимое коллекции");
        var collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            console.println("пусто");
            return 0;
        }
        for (Worker worker : collection) {
            console.println(worker);
        }

        return 0;
    }

}
