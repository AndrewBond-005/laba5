package commands;

import managers.CollectionManager;
import models.Status;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskEnum;
import utility.Console;
import utility.StandardConsole;

import java.util.Set;

public class FilterByStatus extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterByStatus(StandardConsole console, CollectionManager collectionManager) {
        super("filter_by_status status", "вывести элементы, значение поля status которых равно заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public int execute(String arguments, boolean scriprtMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        try {
            Status status = AskEnum.askEnum(Status.class, console,scriprtMode);
            if(status==null && scriprtMode){
                return -1;
            }
            if (status == null) {
                console.printError("Status не может быть null");
                return 0;
            }
            console.println("Вот те Worker, у которых значение поля Status равно" + status);

            Set<Worker> workers = collectionManager.getCollection();
            for (Worker worker : workers) {
                if (worker.getStatus() == status) {
                    console.println(worker);
                }
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода " + e.getReport());
            if (e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }
}
