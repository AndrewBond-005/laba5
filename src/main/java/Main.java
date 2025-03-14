import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.FileManager;
import utility.BackUp;
import utility.Execute;
import utility.StandardConsole;
import utility.Terminate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();
//        if (args.length == 0) {
//            console.println("Введите имя загружаемого файла как аргумент командной строки");
//            System.exit(1);
//        }
//        var dumpManager = new FileManager(args[0], console);
        // Запускаем поток, который слушает System.in
        var name = "";
        if (args.length == 0) {
            while (true) {
                console.print("Введите имя файла: ");
                name = console.readln().trim();
                if (!name.isEmpty()) break;
                else
                    console.print("Имя не может быть пустой строкой!");
            }
        } else {
            name = args[0];
        }

        String str = name;
        if (name.length() >= 4) {
            str = name.substring(name.length() - 4);
            if (!str.equals(".csv")) {
                str = name + ".csv";
            } else str = name;
        }
        else{
            str=name+".csv";
        }
        File file = new File(str);
        if (!file.exists()) {
            console.printError("Файл не существует");
            console.print("Вы хотите создать новый файл с именем " + str + " ?" +
                    " yes - если да");
            var line = console.readln().trim().toLowerCase();
            if (line.equals("yes")) {
                try {
                    file = new File(str);
                    if (file.createNewFile())
                        console.println("Файл " + str + " успешно создан");
                } catch (IOException e) {
                    console.printError("ошибка при создании файла!");
                }
            }
        }

        var dumpManager = new FileManager(str, console);
        Map<String, Command> com = new HashMap<>();
        CommandManager commandManager = new CommandManager();
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            console.println("Не удалось считать коллекцию из файла");
            System.exit(1);
        }

        com.put("add", new Add(console, collectionManager));
        com.put("add_if_max", new AddIfMax(console, collectionManager));
        com.put("clear", new Clear(console, collectionManager));
        com.put("filter_by_status", new FilterByStatus(console, collectionManager));
        com.put("generate", new Generate(console, collectionManager));
        com.put("help", new Help(console, commandManager));
        com.put("info", new Info(console, collectionManager));
        com.put("exit", new Exit(console));
        com.put("remove_by_id", new RemoveById(console, collectionManager));
        com.put("show", new Show(console, collectionManager));
        com.put("print_field_ascending_status", new PrintFieldAscendingStatus(console, collectionManager));
        com.put("min_by_position", new MinByPosition(console, collectionManager));
        com.put("update_id", new UpdateID(console, collectionManager));
        com.put("remove_lower", new RemoveLower(console, collectionManager));
        com.put("remove_greater", new RemoveGreater(console, collectionManager));
        com.put("execute_script", new ExecuteScript(console, collectionManager, commandManager));
        com.put("save", new Save(console, collectionManager));

        commandManager.setCommands(com);

        Runtime.getRuntime().addShutdownHook(new Terminate());
        BackUp.read((ExecuteScript) commandManager.getCommands().get("execute_script"), console);

        new Execute(commandManager, console).execute();
    }

}


