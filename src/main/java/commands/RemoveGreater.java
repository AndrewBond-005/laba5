package commands;

import managers.CollectionManager;
import models.Worker;
import models.ask.AskBreak;
import models.ask.AskWorker;
import utility.Console;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда для удаления из коллекции всех элементов, превышающих заданный
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class RemoveGreater extends Command {
    private final CollectionManager collectionManager;
    private final Console console;

    /**
     * Конструктор команды RemoveGreater
     *
     * @param console объект {@link Console} для ввода-вывода
     * @param collectionManager объект {@link CollectionManager} для управления коллекцией
     */
    public RemoveGreater(Console console, CollectionManager collectionManager) {
        super("remove_greater {element}", "удалить из коллекции все элементы, большие, чем заданный");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Выполняет команду удаления элементов, превышающих заданный
     *
     * @param arguments аргументы команды (может содержать id или быть пустым)
     * @param scriprtMode флаг режима скрипта (true - скрипт, false - интерактивный режим)
     * @return код выполнения: 0 - успех, 1 - выход, -1 - ошибка в скриптовом режиме
     */
    @Override
    public int execute(String arguments, boolean scriprtMode) {
        int id = 0;
        try {
            if (arguments != null && !arguments.isEmpty() && !arguments.equals(" ")) {
                if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord()))
                    throw new AskBreak(arguments);
                id = Integer.parseInt(arguments.trim());
            }
        } catch (NumberFormatException | NullPointerException e) {
            console.print("");
            if(scriprtMode){
                return -1;
            }
        } catch (AskBreak e) {
            console.println("Отмена создания из-за ввода "+e.getReport());
            if(scriprtMode){
                return -1;
            }
            if(e.getReport().equals(console.getExitWord()))
                return 1;
        }
        try {
            Worker a = null;
            if (id != 0) {
                a = collectionManager.getById(id);
            } else {
                console.println("Введите Worker");
                while (true) {
                    console.print("Введите id: ");
                    var line = console.readln().trim();
                    if (line.equals(console.getStopWord()) || line.equals(console.getExitWord()))
                        throw new AskBreak(line);
                    try {
                        id = Integer.parseInt(line);
                        if (id > 0) break;
                        if(scriprtMode){
                            return -1;
                        }
                        else console.print(" Введено неположительное число!");
                    } catch (NumberFormatException e) {
                        console.printError("Ошибка! id - целое положительное число!");
                        if(scriprtMode){
                            return -1;
                        }
                    }
                }
                a = AskWorker.askWorker(console, id,scriprtMode);
            }
            if (a == null || !a.validate().isEmpty()) {
                console.printError("Введены некорректные занчения полей:" +
                        ((a != null) ? (a.validate()) : "он равен null"));
                if(scriprtMode){
                    return -1;
                }
            } else {
                List<Worker> workers = new ArrayList<>();
                var collection = collectionManager.getCollection();
                for (Worker w : collection) {
                    if (w.compareTo(a) < 0) {
                        workers.add(w);
                    }
                }
                collectionManager.removeAll();
                for (Worker w : workers) {
                    collectionManager.add(w);
                }
            }
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