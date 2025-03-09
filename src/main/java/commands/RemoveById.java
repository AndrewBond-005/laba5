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
    public void execute(String arguments) {
        try {
            int id = 0;
            if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
                console.println("Введите id: ");
                arguments = console.readln().trim();
                if (arguments.equals("exit")) throw new RuntimeException();
            }
            try {

                id = Integer.parseInt(arguments.trim());
            } catch (NumberFormatException e) {
                console.println("ID не распознан");
                return;
            }
            console.println(collectionManager.getById(id));
            if (!collectionManager.isContain(collectionManager.getById(id)) || collectionManager.getById(id) == null) {
                console.printError("Worker с таким id не существует");
                return;
            }
            collectionManager.remove(collectionManager.getById(id));
            console.println("Worker с id=" + id + " успешно удалён");
        } catch (RuntimeException e) {
            console.println("Worker с таким id не существует");
        }
    }
}
