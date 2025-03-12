package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

public class UpdateID extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    public static Worker worker = null;

    public UpdateID(Console console, CollectionManager collectionManager) {
        super("update_id {id}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public boolean execute(String arguments) {
        try {
            int id = 0;
            try {
                if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
                    console.println("Введите id: ");
                    arguments = console.readln().trim();
                    if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord()))
                        throw new AskBreak(arguments);
                }
                id = Integer.parseInt(arguments.trim());
            } catch (NumberFormatException e) {
                console.println("ID не распознан");
                return true;
            }
            worker = collectionManager.getById(id);
            var w = AskWorker.askWorker(console, id);
            if (w != null) {
                collectionManager.update(w);
            }

            console.println("Worker успешно обновлён");
            worker = null;
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(e.getReport().equals(console.getExitWord()))
                return false;
        }
        return true;
    }
}