package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

import java.util.Comparator;
import java.util.TreeSet;

public class AddIfMax extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции");

        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public void execute(String arguments) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return;
        }
        try {
            console.println("Добавление нового Worker");
            Worker a = AskWorker.askWorker(console, collectionManager.getFreeId());
            if (a != null && a.validate().isEmpty()) {
                if (collectionManager.getCollection().isEmpty()) {
                    collectionManager.add(a);
                    console.println("Worker успешно добавлен");
                    return;
                }
                Comparator<Worker> comparator = new NameComparator();
                TreeSet<Worker> workers = new TreeSet<>(comparator);
                workers.addAll(collectionManager.getCollection());
                Worker maxi = (workers).last();
                maxi.setName(maxi.getName().toLowerCase());
                Worker b = a;
                b.setName(b.getName().toLowerCase());
                int result = comparator.compare(maxi, b);
                //int result = maxi.compareTo(a);
                if (result < 0) {
                    if (Runtime.getRuntime().freeMemory() < 10_000_000) {
                        console.println("этого Worker можно добавить в коллекцию.");
                        console.printError("Добавление может привести к переполнению памяти и вылету программы.");
                        return;
                    }
                    collectionManager.add(a);
                    console.println("Worker успешно добавлен");
                    return;
                }
                console.println("Worker не добавлен. Он меньше максимального");
                console.println(maxi);
            } else {
                console.println("Worker не добавлен. Введены некорректные занчения полей: " +
                        ((a != null) ? (a.validate()) : "он равен null"));
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода exit");
        }
    }

    public static class NameComparator implements Comparator<Worker> {

        @Override
        public int compare(Worker o1, Worker o2) {
            if (o1.getName().compareTo(o2.getName()) != 0) return o1.getName().compareTo(o2.getName());
            if (o1.getSalary() != o2.getSalary()) return Integer.compare(o1.getSalary(), o2.getSalary());
            return o1.compareTo(o2);
        }
    }
}
