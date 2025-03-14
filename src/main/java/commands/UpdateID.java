package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

/**
 * Команда для обновления элемента коллекции по заданному id
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class UpdateID extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    public static Worker worker = null;

    /**
     * Конструктор команды UpdateID
     *
     * @param console объект {@link Console} для ввода-вывода
     * @param collectionManager объект {@link CollectionManager} для управления коллекцией
     */
    public UpdateID(Console console, CollectionManager collectionManager) {
        super("update_id {id}", "обновить значение элемента коллекции, id которого равен заданному");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду обновления элемента коллекции по заданному id
     *
     * @param arguments аргументы команды (id элемента для обновления)
     * @param scriprtMode флаг режима скрипта (true - скрипт, false - интерактивный режим)
     * @return код выполнения: 0 - успех, 1 - выход, -1 - ошибка в скриптовом режиме
     */
    @Override
    public int execute(String arguments, boolean scriprtMode) {
        try {
            int id = 0;
            try {
                if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
                    if(scriprtMode){
                        return -1;
                    }
                    console.println("Введите id: ");
                    arguments = console.readln().trim();
                    if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord()))
                        throw new AskBreak(arguments);
                }
                id = Integer.parseInt(arguments.trim());
            } catch (NumberFormatException e) {
                if(scriprtMode){
                    return -1;
                }
                console.println("ID не распознан");
                return 0;
            }
            worker = collectionManager.getById(id);
            var w = AskWorker.askWorker(console, id,scriprtMode);
            if (w != null) {
                collectionManager.update(w);
            }

            console.println("Worker успешно обновлён");
            worker = null;
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(scriprtMode){
                return -1;
            }
            if(e.getReport().equals(console.getExitWord()))
                return 1;
        }
        return 0;
    }
}