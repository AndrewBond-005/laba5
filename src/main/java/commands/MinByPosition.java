package commands;

import managers.CollectionManager;
import models.Worker;
import utility.Console;

/**
 * Команда 'min_by_position'. Выводит любой объект из коллекции, значение поля position которого является минимальным.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class MinByPosition extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link MinByPosition}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public MinByPosition(Console console, CollectionManager collectionManager) {
        super("min_by_position", "вывести любой объект из коллекции, значение поля position которого является минимальным");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду поиска и вывода объекта {@link Worker} с минимальным значением поля position.
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
        var workers = collectionManager.getCollection();
        for (Worker worker : workers) {
            if (worker.getPosition().ordinal() == 0) {
                console.println(worker);
                return 0;
            }
        }
        return 0;
    }
}