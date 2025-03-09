package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;

import java.util.Comparator;
import java.util.TreeSet;


public class PrintFieldAscendingStatus extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public PrintFieldAscendingStatus(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_status", "вывести значения поля status всех элементов в порядке возрастания");
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
        console.println("значения поля status всех элементов в порядке возрастания: ");

        Comparator<Worker> comparator = new StatusComparator();
        TreeSet<Worker> workers = new TreeSet<>(comparator);
        workers.addAll(collectionManager.getCollection());
        for (Worker worker : workers) {
            console.println(worker.getStatus());
        }
        //var collection = collectionManager.getCollection();
//        for (Worker worker : collection) {
//            console.println(worker.getStatus());
//        }
    }

    public static class StatusComparator implements Comparator<Worker> {

        @Override
        public int compare(Worker o1, Worker o2) {
            if (o1.getStatus() != o2.getStatus()) return o1.getStatus().ordinal() - o2.getStatus().ordinal();
            return o1.compareTo(o2);
        }
    }
}
