package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Console;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Команда 'execute_script'. Считывает и исполняет скрипт из указанного файла.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class ExecuteScript extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final CommandManager commandManager;
    private HashMap<String, Integer> map = new HashMap<>();
    private boolean stop = false;

    /**
     * Создает новый экземпляр команды {@link ExecuteScript}.
     *
     * @param console консоль для взаимодействия с пользователем
     * @param collectionManager менеджер коллекции для управления элементами
     * @param commandManager менеджер команд для управления доступными командами
     */
    public ExecuteScript(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте" +
                " содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Запрашивает имя файла скрипта у пользователя, если оно не указано в аргументах.
     *
     * @param arguments аргументы команды (имя файла или null)
     * @return имя файла скрипта
     */
    private String enterScriptName(String arguments) {
        String name = arguments;
        if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
            console.print("Введите название скрипта: ");
            name = console.readln().trim();
        }
        return name;
    }

    /**
     * Выполняет скрипт из указанного файла, содержащего команды.
     *
     * @param arguments аргументы команды (имя файла скрипта)
     * @param scriptMode флаг режима выполнения скрипта (всегда true для этой команды)
     * @return код завершения: 0 - успех или ошибка, 1 - выход из программы, -1 - ошибка в скрипте
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        var name = enterScriptName(arguments);
        if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord())) {
            console.println("Отмена создания из-за ввода " + arguments);
            return 1;
        }
        if (map.get(name) == null) {
            map.put(name, 1);
        } else {
            map.put(name, map.get(name) + 1);
        }
        if (map.get(name) > commandManager.getMaxRecurtionDeep()) {
            console.println("Достигнут лимит глубины рекурсии.");
            return 1;
        }
        console.println("Начинается выполнение скрипта " + name);
        try (Scanner scriptScanner = new Scanner(new File(name))) {
            if (!scriptScanner.hasNextLine()) throw new NoSuchElementException();
            console.setFileScanner(scriptScanner);
            while (console.isCanReadln() && !stop) {
                var line = console.readln().trim();
                String[] tokens = line.split(" ", 2);
                var command = commandManager.getCommands().get(tokens[0]);
                if (arguments.equals(console.getStopWord()) || arguments.equals(console.getExitWord())) {
                    console.println("Отмена создания из-за ввода " + arguments);
                    return 1;
                }
                if (command == null) {
                    console.printError("Команды " + tokens[0] + " не обнаружено.");
                    continue;
                }

                if (!tokens[0].equals("execute_script")) {
                    int s = command.execute(tokens.length > 1 ? tokens[1] : null, true);
                    if (s == 1) {
                        commandManager.getCommands().get("exit").execute(null, true);
                    }
                    if (s == -1) {
                        return -1;
                    }
                    continue;
                }
                if (tokens.length == 1) {
                    var script = enterScriptName(null);
                    List<String> tokenList = new ArrayList<>(Arrays.asList(tokens));
                    tokenList.add(script);
                    tokens = tokenList.toArray(new String[0]);
                }

                if (map.containsKey(tokens[1])) {
                    // Обработка рекурсии закомментирована в исходном коде
                } else {
                    if (command.execute(tokens.length > 1 ? tokens[1] : null, true) == 1) {
                        commandManager.getCommands().get("exit").execute(null, true);
                    }
                    if (command.execute(tokens.length > 1 ? tokens[1] : null, true) == -1) {
                        return 0;
                    }
                }
                console.setFileScanner(scriptScanner);
            }
            return 0;
        } catch (FileNotFoundException exception) {
            console.printError("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            console.printError("Файл со скриптом пуст!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            console.selectConsoleScanner();
            map.remove(name);
        }
        return 0;
    }
}
