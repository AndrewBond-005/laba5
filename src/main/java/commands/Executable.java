package commands;

/**
 * Интерфейс, определяющий метод для выполнения команды.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public interface Executable {

    /**
     * Выполняет команду с заданными аргументами.
     *
     * @param arguments аргументы команды
     * @param scriptMode флаг режима выполнения скрипта
     * @return код завершения выполнения команды
     */
    int execute(String arguments, boolean scriptMode);
}