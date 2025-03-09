package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;


public class MinByPosition extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public MinByPosition(Console console, CollectionManager collectionManager) {
        super("min_by_position", "вывести любой объект из коллекции, значение поля position которого является минимальным");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            //console.print(arguments);
            return;
        }
        var workers = collectionManager.getCollection();
        for (Worker worker : workers) {
            if (worker.getPosition().ordinal() == 0) {
                console.println(worker);
                return;
            }
        }
    }

}
