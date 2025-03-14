package commands;

import managers.CollectionManager;
import models.Status;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskEnum;
import utility.Console;
import utility.StandardConsole;

import java.util.Set;

/**
 * Команда 'filter_by_status'. Выводит элементы коллекции, значение поля status которых равно заданному.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class FilterByStatus extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    /**
     * Создает новый экземпляр команды {@link FilterByStatus}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     */
    public FilterByStatus(StandardConsole console, CollectionManager collectionManager) {
        super("filter_by_status status", "вывести элементы, значение поля status которых равно заданному");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду фильтрации элементов коллекции по заданному статусу.
     *
     * @param arguments аргументы команды (ожидается null, так как статус запрашивается интерактивно)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 0 - успех или ошибка, 1 - выход из программы, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 0;
        }
        try {
            Status status = AskEnum.askEnum(Status.class, console, scriptMode);
            if (status == null && scriptMode) {
                return -1;
            }
            if (status == null) {
                console.printError("Status не может быть null");
                return 0;
            }
            console.println("Вот те Worker, у которых значение поля Status равно " + status);

            Set<Worker> workers = collectionManager.getCollection();
            for (Worker worker : workers) {
                if (worker.getStatus() == status) {
                    console.println(worker);
                }
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода " + e.getReport());
            if (e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }
}