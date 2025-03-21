package utility;

import managers.CommandManager;
import models.ask.AskBreak;

import java.util.NoSuchElementException;

/**
 * Класс для выполнения команд в интерактивном режиме
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Execute {
    private final Console console;
    private final CommandManager commandManager;

    /**
     * Конструктор класса Execute
     *
     * @param commandManager объект {@link CommandManager} для управления командами
     * @param console объект {@link Console} для ввода-вывода
     */
    public Execute(CommandManager commandManager, Console console) {
        this.commandManager = commandManager;
        this.console = console;
    }

    /**
     * Запускает интерактивный режим выполнения команд
     * <p>Считывает команды из консоли, выполняет их и обрабатывает исключения</p>
     */
    public void execute() {
        try {
            var currentDate = System.currentTimeMillis();
            while (true) {
                //console.println("Время"+(System.currentTimeMillis() - currentDate));
                //формат вывода времени выполнения
                //console.printError(System.currentTimeMillis() - currentDate);
                //BackUp.clear();///////
                console.print("Введите следующую команду: ");
                var line = console.readln().trim();

                String[] tokens = line.split(" ", 2);
                var command = commandManager.getCommands().get(tokens[0]);
                currentDate = System.currentTimeMillis();
                if (command != null) {
                    if(command.execute(tokens.length > 1 ? tokens[1] : null,false)==1){
                        commandManager.getCommands().get("exit").execute(null,false);
                    }
//                    if(tokens.length > 1){
//                        BackUp.println(tokens[0]);
//                        BackUp.println(tokens[1]);
//                        command.execute(tokens[1]);
//                    }
//                    else{
//                        BackUp.println(tokens[0]);
//                        command.execute(null);
//                    }
                } else {
                    console.printError("Команды " + tokens[0] + " не обнаружено.");
                }
                if (line.equals("stop"))
                    break;
//            if (tokens[0].equals("execute_script")){
//                script();
//            }
            }
        } catch (NoSuchElementException exception) {
            console.printError("Пользовательский ввод не обнаружен!");
        } catch (IllegalStateException exception) {
            console.printError("Непредвиденная ошибка!");
        }
    }
}