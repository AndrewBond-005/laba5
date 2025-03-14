package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;

/**
 * Команда для вывода всех элементов коллекции
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Show extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор команды Show
     *
     * @param console объект {@link Console} для ввода-вывода
     * @param collectionManager объект {@link CollectionManager} для управления коллекцией
     */
    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести в стандартный поток вывода все" +
                " элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду вывода всех элементов коллекции в строковом представлении
     *
     * @param arguments аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriprtMode флаг режима скрипта (true - скрипт, false - интерактивный режим)
     * @return код выполнения: 0 - успех
     */
    @Override
    public int execute(String arguments, boolean scriprtMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            //console.print(arguments);
            return 0;
        }
        console.println("Содержимое коллекции");
        var collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            console.println("пусто");
            return 0;
        }
        for (Worker worker : collection) {
            console.println(worker);
        }

        return 0;
    }
}