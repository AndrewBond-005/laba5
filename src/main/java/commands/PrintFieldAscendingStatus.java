package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Команда 'print_field_ascending_status'. Выводит значения поля status всех элементов в порядке возрастания.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class PrintFieldAscendingStatus extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link PrintFieldAscendingStatus}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public PrintFieldAscendingStatus(Console console, CollectionManager collectionManager) {
        super("print_field_ascending_status", "вывести значения поля status всех элементов в порядке возрастания");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду вывода значений поля status всех элементов {@link Worker} в порядке возрастания.
     *
     * @param arguments аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        console.println("значения поля status всех элементов в порядке возрастания: ");

        Comparator<Worker> comparator = new StatusComparator();
        TreeSet<Worker> workers = new TreeSet<>(comparator);
        workers.addAll(collectionManager.getCollection());
        for (Worker worker : workers) {
            console.println(worker.getStatus());
        }
        return 0;
    }

    /**
     * Внутренний класс для сравнения объектов {@link Worker} по полю status.
     *
     * @author Bondarenko Andrei
     * @since 1.0
     */
    public static class StatusComparator implements Comparator<Worker> {

        /**
         * Сравнивает двух работников {@link Worker} по значению поля status.
         *
         * @param o1 первый работник для сравнения
         * @param o2 второй работник для сравнения
         * @return отрицательное число, ноль или положительное число, если статус первого работника меньше, равен или больше статуса второго
         */
        @Override
        public int compare(Worker o1, Worker o2) {
            if (o1.getStatus() != o2.getStatus()) return o1.getStatus().ordinal() - o2.getStatus().ordinal();
            return o1.compareTo(o2);
        }
    }
}