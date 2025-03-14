package commands;

import managers.CollectionManager;
import utility.Console;


public class RemoveById extends Command {
    private final CollectionManager collectionManager;
    private final Console console;


    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id {element}", "удалить элемент из коллекции по его id");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    @Override
    public int execute(String arguments, boolean scriprtMode) {
        try {
            int id = 0;
            if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
                console.println("Введите id: ");
                arguments = console.readln().trim();
                if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord())) {
                    console.println("Отмена создания из-за ввода " + arguments);
                    if(scriprtMode){
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
                if(scriprtMode){
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
