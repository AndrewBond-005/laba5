package commands;

import utility.BackUp;
import utility.Console;

/**
 * Команда 'exit'. Завершает выполнение программы.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class Exit extends Command {
    private final Console console;

    /**
     * Создает новый экземпляр команды {@link Exit}.
     *
     * @param console консоль для взаимодействия с пользователем
     */
    public Exit(Console console) {
        super("exit", "выход из программы");
        this.console = console;
    }

    /**
     * Выполняет команду завершения программы.
     *
     * @param arguments  аргументы команды (ожидается null, так как команда не принимает аргументов)
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения: 1 - выход из программы
     */
    @Override
    public int execute(String arguments, boolean scriptMode) {
        if (arguments != null) {
            console.println("Введен лишний аргумент");
            return 1;
        }
        console.println("Выход из программы");
        BackUp.remove_last();
        System.exit(0);
        return 1;
    }
}