package commands;

import managers.CollectionManager;
import utility.BackUp;
import utility.Console;

/**
 * Команда для сохранения коллекции в файл
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Save extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор команды Save
     *
     * @param console объект {@link Console} для ввода-вывода
     * @param collectionManager объект {@link CollectionManager} для управления коллекцией
     */
    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду сохранения коллекции в файл
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
        collectionManager.saveCollection();
        BackUp.clear();///////
        return 0;
    }
}