package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

import java.util.ArrayList;
import java.util.List;


public class RemoveGreater extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, большие, чем заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }


    @Override
    public void execute(String arguments) {

        int id = 0;
        try {
            if (arguments != null && !arguments.isEmpty() && !arguments.equals(" ")) {
                if (arguments.equals("exit")) throw new AskBreak();
                id = Integer.parseInt(arguments.trim());
            }
        } catch (NumberFormatException | NullPointerException e) {
            console.print("");
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода exit");
            return;
        }
        try {

            Worker a = null;
            if (id != 0) {
                a = collectionManager.getById(id);
            } else {
                console.println("Введите Worker");
                while (true) {
                    console.print("Введите id: ");
                    var line = console.readln().trim();
                    if (line.equals("exit")) throw new AskBreak();
                    try {
                        id = Integer.parseInt(line);
                        if (id > 0) break;
                        else console.print(" Введено неположительное число!");
                    } catch (NumberFormatException e) {
                        console.printError("Ошибка! id - целое положительное число!");
                    }
                }
                a = AskWorker.askWorker(console, id);
            }
            if (a == null || !a.validate().isEmpty()) {
                console.printError("Введены некорректные занчения полей:" +
                        ((a != null) ? (a.validate()) : "он равен null"));
            } else {
                List<Worker> workers = new ArrayList<>();
                var collection = collectionManager.getCollection();
                for (Worker w : collection) {
                    if (w.compareTo(a) < 0) {
                        workers.add(w);
                    }
                }
                collectionManager.removeAll();
                for (Worker w : workers) {
                    collectionManager.add(w);
                }
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода exit");
        }
    }
}
