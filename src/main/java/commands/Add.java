package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Add extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link Add}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду добавления нового элемента {@link Worker} в коллекцию.
     *
     * @param arguments аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех, 1 - выход из программы, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        try {
            console.println("Добавление нового Worker");
            if (Runtime.getRuntime().freeMemory() < 10_000_000) {
                console.printError("Добавление может привести к переполнению памяти и вылету программы.");
                return 0;
            }
            Worker a = AskWorker.askWorker(console, collectionManager.getFreeId(), scriptMode);
            if (a != null && a.validate().isEmpty()) {
                collectionManager.add(a);
                console.println("Worker успешно добавлен");
            } else {
                console.printError("Worker не добавлен. Введены некорректные значения полей:" +
                        ((a != null) ? a.validate() : "Worker равен null"));
            }
            if (a == null && scriptMode) {
                return -1;
            }
            return 0;
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода " + e.getReport());
            if (e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }
}