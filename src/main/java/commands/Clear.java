package commands;

import managers.CollectionManager;
import utility.Console;

/**
 * Команда 'clear'. Очищает коллекцию.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link Clear}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду очистки коллекции.
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
        console.println("Очистка коллекции");
        collectionManager.removeAll();
        return 0;
    }
}