package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;


/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class Add extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
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
        try {
            console.println("Добавление нового Worker");
            if (Runtime.getRuntime().freeMemory() < 10_000_000) {
                console.printError("Добавление может привести к переполнению памяти и вылету программы.");
                return 0;
            }
            Worker a = AskWorker.askWorker(console, collectionManager.getFreeId(),scriprtMode);
            if (a != null && a.validate().isEmpty()) {
                collectionManager.add(a);
                console.println("Worker успешно добавлен");
            } else {
                console.printError("Worker не добавлен. Введены некорректные занчения полей:" +
                        ((a != null) ? a.validate() : "Worker равен null"));
            }
            if(a==null && scriprtMode){
                return -1;
            }
            return 0;
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(e.getReport().equals(console.getExitWord()))
                return 1;

        }
        return 0;
    }

}
