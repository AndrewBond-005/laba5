/**
 * Класс, управляющий командами и их параметрами.
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
package managers;

import commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    Map<String, Command> commands = new HashMap<>();
    private int maxRecurtionDeep = 100;

    /**
     * Получает список команд.
     *
     * @return карта команд {@link Command}, где ключ - название команды
     */
    public Map<String, Command> getCommands() {
        return commands;
    }

    /**
     * Устанавливает список команд.
     *
     * @param commands карта команд {@link Command}
     */
    public void setCommands(Map<String, Command> commands) {
        this.commands = commands;
    }

    /**
     * Получает максимальную глубину рекурсии.
     *
     * @return максимальная глубина рекурсии
     */
    public int getMaxRecurtionDeep() {
        return maxRecurtionDeep;
    }

    /**
     * Устанавливает максимальную глубину рекурсии.
     *
     * @param maxRecurtionDeep новая максимальная глубина рекурсии
     */
    public void setMaxRecurtionDeep(int maxRecurtionDeep) {
        this.maxRecurtionDeep = maxRecurtionDeep;
    }
}
