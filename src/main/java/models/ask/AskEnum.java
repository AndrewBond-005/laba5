package models.ask;

import commands.Exit;
//import commands.UpdateID;
import models.Person;
import models.Worker;
import utility.Console;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Класс для запроса значения перечисления у пользователя
 *
 * @author Bondarenko Andrei
 * @since 1.0
 */
public class AskEnum {
    /**
     * Запрашивает у пользователя значение перечисления через консоль
     *
     * @param <T> тип перечисления, наследуемого от {@link Enum}
     * @param param класс перечисления {@link Class<T>}
     * @param console объект {@link Console} для ввода-вывода
     * @param scriprtMode флаг режима скрипта (true - скрипт, false - интерактивный режим)
     * @return значение перечисления типа T или null при пустом вводе или ошибке
     * @throws AskBreak если пользователь ввел стоп-слово или слово выхода
     */
    public static <T extends Enum<T>> T askEnum(Class<T> param, Console console, boolean scriprtMode) throws AskBreak {
        while (true) {
            console.print("Введите " + "значение" + " " + param.getSimpleName() + Arrays.toString(param.getEnumConstants()) + ": ");
            var line = console.readln().trim();
            if (line.equals(console.getStopWord()) || line.equals(console.getExitWord()))
                throw new AskBreak(line);
            if (!line.isEmpty()) {
                try {
                    var res = T.valueOf(param, line.toUpperCase());
                    // BackUp.println(String.valueOf(res));
                    return res;
                } catch (IllegalArgumentException e) {
                    console.println("Ошибка! Введённае значение " + line.toUpperCase() + " не найдено среди значений " + param.getSimpleName());
                    if (console.getFileScanner() != null) {
                        return null;
                    }
                    if (scriprtMode) return null;
                }
            } else return null;
        }
    }
}