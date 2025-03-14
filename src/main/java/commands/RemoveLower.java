package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

import java.util.ArrayList;
import java.util.List;


public class RemoveLower extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public RemoveLower(Console console, CollectionManager collectionManager) {
        super("remove_lower {element}", "удалить из коллекции все элементы, меньшие, чем заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }


    @Override
    public int execute(String arguments, boolean scriprtMode) {

        int id = 0;
        try {
            if (arguments != null && !arguments.isEmpty() && !arguments.equals(" ")) {
                if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord()))
                    throw new AskBreak(arguments);
                id = Integer.parseInt(arguments.trim());
            }
        } catch (NumberFormatException | NullPointerException e) {
            console.print("");
            if(scriprtMode){
                return -1;
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(scriprtMode){
                return -1;
            }
            if(e.getReport().equals(console.getExitWord()))
                return 1;
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
                    if (line.equals(console.getStopWord()) || line.equals(console.getExitWord()))
                        throw new AskBreak(line);
                    try {
                        id = Integer.parseInt(line);
                        if (id > 0) break;
                        else console.print(" Введено неположительное число!");
                        if(scriprtMode){
                            return -1;
                        }
                    } catch (NumberFormatException e) {
                        console.print("Ошибка! id - целое положительное число!");
                        if(scriprtMode){
                            return -1;
                        }
                    }
                }
                a = AskWorker.askWorker(console, id,scriprtMode);
            }
            if (a == null || !a.validate().isEmpty()) {
                console.println("Введены некорректные занчения полей: " +
                        ((a != null) ? (a.validate()) : "он равен null"));
                if(scriprtMode){
                    return -1;
                }
            } else {
                List<Worker> workers = new ArrayList<>();
                var collection = collectionManager.getCollection();
                for (Worker w : collection) {
                    if (w.compareTo(a) > 0) {
                        workers.add(w);
                    }
                }
                collectionManager.removeAll();
                for (Worker w : workers) {
                    collectionManager.add(w);
                }
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(scriprtMode){
                return -1;
            }
            if(e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }
}
