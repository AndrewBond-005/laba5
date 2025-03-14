package commands;

import managers.CollectionManager;
import managers.CommandManager;
import utility.Console;

import javax.swing.plaf.IconUIResource;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ExecuteScript extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    private final CommandManager commandManager;
    private HashMap<String, Integer> map = new HashMap<>();
    private boolean stop = false;

    public ExecuteScript(Console console, CollectionManager collectionManager, CommandManager commandManager) {
        super("execute_script file_name", "считать и исполнить скрипт из указанного файла. В скрипте" +
                " содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
        this.collectionManager = collectionManager;
        this.console = console;
        this.commandManager = commandManager;
    }

    private String enterScriptName(String arguments) {
        String name = arguments;
        if (arguments == null || arguments.isEmpty() || arguments.equals(" ")) {
            console.print("Введите название скрипта: ");
            name = console.readln().trim();
        }
        return name;
    }

    @Override
    public int execute(String arguments, boolean scriprtMode) {
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
        if(map.get(name)> commandManager.getMaxRecurtionDeep()){
            console.println("Достигнут лимит глубины рекурсиии.");
            return 1;
        }
        console.println("Начинается выполнение скрипта " + name);
        try (Scanner scriptScanner = new Scanner(new File(name))) {
            if (!scriptScanner.hasNextLine()) throw new NoSuchElementException();
            console.setFileScanner(scriptScanner);
            //exit = false;///////
            // убрать комментарии чтобы выходить только из скрипта,
            // где написана эта строчка
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
                    // System.out.println(s);
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
//                    console.printError("ОБНАРУЖЕНА РЕКУРСИЯЯЯ!!!!");
//                    console.selectConsoleScanner();
//                    console.println("Если вы хотите игнорировать команду," +
//                            " вызывающую рекурсию, введите(ignore)");
//
//                    var word = console.readln().trim();
//                    if (!word.equals("ignore")) {
//                        stop = true;
//                        console.println("Выход из скрипта...");
//                    }
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

//            stack.push(name);
//        try (
//                Scanner scriptScanner = new Scanner(new File(name))) {
//            if (!scriptScanner.hasNextLine()) throw new NoSuchElementException();
//            console.setFileScanner(scriptScanner);
//            System.out.println("Выполняется скрипт: " + name);
//            while (scriptScanner.hasNextLine() && !exit) {
//                console.print("Введите следующую команду: ");
//                var line = console.readln().trim();
//                String[] tokens = line.split(" ", 2);
//                var command = commandManager.getCommands().get(tokens[0]);
//                if (command != null) {
//                    if (tokens[0].equals("execute_script")) {
//                        console.println(tokens[1]);
//                        console.println(stack);
//                        String result = "";
//                        for (String script : stack) {
//                            console.println(script);
//                            if (script.equals(tokens[1])) {
//                                console.selectConsoleScanner();
//                                console.println("ОБНАРУЖЕНА РЕКУРСИЯ!!!");
//                                console.println("Вы хотите игнорировать рекурсию(ignore) или заврешить работу скрипта(exit)");
//                                var word = console.readln().trim();
//                                if (word.equals("ignore")) {
//                                    result = word;
//                                    break;
//                                } else {
//                                    if (word.equals("exit")) console.println("Выход...");
//                                    else console.println("Команда не распознана. Выход...");
//                                    stack.clear();
//                                    exit = true;
//                                    return;
//                                }
//
//                            }
//                        }
//                        if (result.equals("ignore")) {
//                            stack.pop();
//                            return;
//                        }
//                    } else
//                        this.execute(tokens.length > 1 ? tokens[1] : null);
//
//                } else {
//                    console.print("Команды " + tokens[0] + " не обнаружено.");
//                }
//                if (tokens[0].equals("exit"))
//                    break;
//
//
//            }
//            console.selectConsoleScanner();
//        } catch (
//                FileNotFoundException exception) {
//            console.println("Файл со скриптом не найден!");
//        } catch (
//                NoSuchElementException exception) {
//            console.println("Файл со скриптом пуст!");
//        } catch (IllegalStateException exception) {
//            console.println("Непредвиденная ошибка!");
//            System.exit(0);
//        } finally {
//            if (!stack.isEmpty()) {
//                stack.pop();
//            }
//
//        }