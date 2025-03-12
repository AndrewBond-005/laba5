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
    public boolean execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            //console.print(arguments);
            return true;
        }
        console.println("Содержимое коллекции");
        var collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            console.println("пусто");
            return true;
        }
        for (Worker worker : collection) {
            console.println(worker);
        }

        return true;
    }

}
