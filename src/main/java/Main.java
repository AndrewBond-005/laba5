import commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.BackLog;
import utility.Execute;
import utility.StandardConsole;
import utility.Terminate;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();
//        if (args.length == 0) {
//            console.println("Введите имя загружаемого файла как аргумент командной строки");
//            System.exit(1);
//        }
//        var dumpManager = new DumpManager(args[0], console);
        // Запускаем поток, который слушает System.in
        var dumpManager = new DumpManager("collection.csv", console);
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
        com.put("info", new Info(console, collectionManager));///доделать
        com.put("exit", new Exit(console));///доделать
        com.put("remove_by_id", new RemoveById(console, collectionManager));
        com.put("show", new Show(console, collectionManager));
        com.put("print_field_ascending_status", new PrintFieldAscendingStatus(console, collectionManager));
        com.put("min_by_position", new MinByPosition(console, collectionManager));
        com.put("update_id", new UpdateID(console, collectionManager));
        com.put("remove_lower", new RemoveLower(console, collectionManager));
        com.put("remove_greater", new RemoveGreater(console, collectionManager));
        com.put("execute_script", new ExecuteScript(console, collectionManager, commandManager));///доделать
        com.put("save", new Save(console, collectionManager));///доделать

        commandManager.setCommands(com);

        Runtime.getRuntime().addShutdownHook(new Terminate());
        BackLog.read((ExecuteScript) commandManager.getCommands().get("execute_script"), console);

        new Execute(commandManager, console).execute();

    }

}

//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("Завершение программы");
//            try (FileWriter writer = new FileWriter("back_log.txt")) {
//                String data = BackLog.getBackLog();
//                if (data.isEmpty()) {
//                    System.out.println("Данные в BackLog отсутствуют. Запись в файл не выполнена.");
//                    return;
//                }
//                writer.write(BackLog.getBackLog());
//                writer.flush();
//                System.out.println("ввод сохранёнв back_log");
//            } catch (IOException e) {
//                System.out.println("не удалось сохранить данные в back_log");
//            }
//        }));


//Signal.handle(new Signal("INT"), signal -> {
//            try (FileWriter writer = new FileWriter("back_log.txt")) {
//                writer.write(BackLog.getBackLog());
//                System.out.println(BackLog.getBackLog());
//                System.out.println("ввод сохранёнв back_log" + BackLog.getBackLog());
//            } catch (IOException e) {
//                System.out.println("не удалось сохранить данные в back_log");
//            }
//        });