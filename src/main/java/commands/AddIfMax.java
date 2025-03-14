package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Команда 'add_if_max'. Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class AddIfMax extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link AddIfMax}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public AddIfMax(Console console, CollectionManager collectionManager) {
        super("add_if_max {element}", "добавить новый элемент в коллекцию, если его значение " +
                "превышает значение наибольшего элемента этой коллекции");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду добавления нового элемента {@link Worker} в коллекцию, если он больше максимального.
     *
     * @param arguments аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех или отказ, 1 - выход из программы, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        try {
            console.println("Добавление нового Worker");
            Worker a = AskWorker.askWorker(console, collectionManager.getFreeId(), scriptMode);
            if (a == null && scriptMode) {
                return -1;
            }
            if (a != null && a.validate().isEmpty()) {
                if (collectionManager.getCollection().isEmpty()) {
                    collectionManager.add(a);
                    console.println("Worker успешно добавлен");
                    return 0;
                }
                Comparator<Worker> comparator = new NameComparator();
                TreeSet<Worker> workers = new TreeSet<>(comparator);
                workers.addAll(collectionManager.getCollection());
                Worker maxi = workers.last();
                maxi.setName(maxi.getName().toLowerCase());
                Worker b = a;
                b.setName(b.getName().toLowerCase());
                int result = comparator.compare(maxi, b);
                if (result < 0) {
                    if (Runtime.getRuntime().freeMemory() < 10_000_000) {
                        console.println("этого Worker можно добавить в коллекцию.");
                        console.printError("Добавление может привести к переполнению памяти и вылету программы.");
                        return 0;
                    }
                    collectionManager.add(a);
                    console.println("Worker успешно добавлен");
                    return 0;
                }
                console.println("Worker не добавлен. Он меньше максимального");
                console.println(maxi);
            } else {
                console.println("Worker не добавлен. Введены некорректные значения полей: " +
                        ((a != null) ? a.validate() : "он равен null"));
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода " + e.getReport());
            if (e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }

    /**
     * Внутренний класс для сравнения объектов {@link Worker} по имени и зарплате.
     *
     * @author Bondarenko Andrei
     * @since 1.0
     */
    public static class NameComparator implements Comparator<Worker> {

        /**
         * Сравнивает двух работников {@link Worker} по имени и зарплате.
         *
         * @param o1 первый работник для сравнения
         * @param o2 второй работник для сравнения
         * @return отрицательное число, ноль или положительное число, если первый работник меньше, равен или больше второго
         */
        @Override
        public int compare(Worker o1, Worker o2) {
            if (o1.getName().compareTo(o2.getName()) != 0) return o1.getName().compareTo(o2.getName());
            if (o1.getSalary() != o2.getSalary()) return Integer.compare(o1.getSalary(), o2.getSalary());
            return o1.compareTo(o2);
        }
    }
}