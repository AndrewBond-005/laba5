package commands;

import managers.CollectionManager;
import utility.Console;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по его идентификатору.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class RemoveById extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link RemoveById}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id {element}", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду удаления элемента из коллекции по заданному идентификатору.
     *
     * @param arguments аргументы команды (идентификатор элемента)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех или ошибка, 1 - выход из программы, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        try {
            int id = 0;
            if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
                console.println("Введите id: ");
                arguments = console.readln().trim();
                if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord())) {
                    console.println("Отмена создания из-за ввода " + arguments);
                    if (scriptMode) {
                        return -1;
                    }
                    return 1;
                }
                return 0;
            }
            try {
                id = Integer.parseInt(arguments.trim());
            } catch (NumberFormatException e) {
                console.println("ID не распознан");
                if (scriptMode) {
                    return -1;
                }
                return 0;
            }
            console.println(collectionManager.getById(id));
            if (!collectionManager.isContain(collectionManager.getById(id)) || collectionManager.getById(id) == null) {
                console.printError("Worker с таким id не существует");
                return 0;
            }
            collectionManager.remove(collectionManager.getById(id));
            console.println("Worker с id=" + id + " успешно удалён");
        } catch (RuntimeException e) {
            console.println("Worker с таким id не существует");
        }
        return 0;
    }
}